package com.zzrbi.controller;

import com.zzrbi.entity.Staff;
import com.zzrbi.service.IStaffService;
import com.zzrbi.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Random;



/**
 * 登录相关接口
 * @author suzhenxing
 * Create by 2017年1月3日
 */
@Controller(value="loginController")
public class LoginController extends BaseController{

	private final Logger logger = Logger.getLogger(getClass());

	@Resource
	private IStaffService staffService;
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
    public void inLogin(final HttpServletRequest request,HttpServletResponse response){
		logger.info("用户登录中....");
		// 请求参数
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String verCode = request.getParameter("verCode");

		// session中的验证码
		//String loginCode = StringUtils.objectToString(request.getSession().getAttribute("loginCode"));
		// 初始化返回参数
		Map<String,Object> returnMap = ReturnMapUtil.getSuccessMap("0");
		try{
//			if(!UtilValidate.areEqual(verCode.toLowerCase(), loginCode.toLowerCase())){
//				returnMap = ReturnMapUtil.getErrorMap("1", "验证码错误");
//			}else{
				Staff userLogin = staffService.getUserLogin(userName);

				if(UtilValidate.isEmpty(userLogin)){
					returnMap = ReturnMapUtil.getErrorMap("3", "用户名错误");
				}else{
					String currentPassword = StringUtils.objectToString(userLogin.getPasswd());
					if(!UtilValidate.areEqual(currentPassword,MD5.encodeByMd5AndSaltWithJs(password))){
						returnMap = ReturnMapUtil.getErrorMap("2", "用户名或密码错误");
					} else if (!isValid(verCode, userLogin.getSecurityCode())) {
						returnMap = ReturnMapUtil.getErrorMap("4", "令牌错误");
					} else{
						request.getSession().setAttribute("partyId", userLogin.getId());
						request.getSession().setAttribute("userName", userName);
					}
				}
		//	}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			returnMap = ReturnMapUtil.getErrorMap("99", "系统异常");
		}
		writeJsonToClient(response, returnMap);
	}
	
	@RequestMapping("/checkLogin")
    public void inCheckLogin(final HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		// 请求参数
		String userName = new String(request.getParameter("userName").getBytes("ISO-8859-1"),"utf-8");
		boolean isValidate = true;
		try{
			Staff user = staffService.getUserLogin(userName);
			if(UtilValidate.isNotEmpty(user)){
				isValidate = false;
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			isValidate = false;
		}
		writeToClient(response, StringUtils.objectToString(isValidate).toLowerCase());
	}
	
	/**
	 * 安全退出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logout")
    public ModelAndView inLogout(final HttpServletRequest request,HttpServletResponse response) {
		// 清楚session，并跳转到登录页
		logger.info("安全退出，退出系统");
		request.getSession().invalidate();
		return new ModelAndView("login");
	}

	@RequestMapping("/updatePass")
	@ResponseBody
	public String updatePass(final HttpServletRequest request, final HttpServletResponse response) {
		String pass = request.getParameter("password");
		String confirmPass = request.getParameter("confirmPass");
		Integer partyId = (Integer) request.getSession().getAttribute("partyId");

		if (!pass.equals(confirmPass)) {
			return "500";
		} else {
			int updatecount = staffService.updateUserPassword(partyId,MD5.encodeByMd5AndSaltWithJs(pass));
			if (updatecount == 1) {
				return "200";
			} else {
				return "500";
			}
		}
	}
	
	/**
	 * 获取登录验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCode")
	public void inRandomCode(final HttpServletRequest request, HttpServletResponse response){
		logger.info("获取登录码....");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 在内存中创建图象
		int width = 104, height = 36;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 生成随机类
		Random random = new Random();

		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(new Font("Courier", Font.PLAIN, 18));

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 160; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String codeList = "1234567890abcdefghijklmnpqrstuvwxyz";

		String sRand = "";

		for (int i = 0; i < 4; i++) {
			int a = random.nextInt(codeList.length() - 1);
			String rand = codeList.substring(a, a + 1);
			sRand += rand;
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.translate(random.nextInt(3), random.nextInt(3));
			g.drawString(rand, 23 * i + 6, 26);
		}
		// 将认证码存入SESSION
		request.getSession().removeAttribute("loginCode");
		request.getSession().setAttribute("loginCode", sRand);
		// 图象生效
		g.dispose();
		// 输出图象到页面
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ImageIO.write(image, "JPEG", out);
		} catch (IOException e) {
			logger.error("输出流异常");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取随机颜色
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
	}
	/**
	 * 验证用户二维码
	 *
	 * @param code
	 */
	private boolean isValid(String code, String savedSecret) {
		boolean falg = false;
		long validateCode = Long.parseLong(code);
		long t = System.currentTimeMillis();
		GoogleAuthenticator ga = new GoogleAuthenticator();
		ga.setWindowSize(2); //should give 5 * 30 seconds of grace...
		boolean r = ga.check_code(savedSecret, validateCode, t);
		return r;
	}
}
