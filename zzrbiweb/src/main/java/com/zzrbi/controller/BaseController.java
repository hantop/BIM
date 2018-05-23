package com.zzrbi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zzrbi.util.StringUtils;
import com.zzrbi.util.UtilValidate;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 控制器基本类（封装常见操作）
 * 
 * @author sihang
 *
 */
public class BaseController {

	private static Logger logger = Logger.getLogger(BaseController.class);

	public Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	public void setCharacterEncodingUTF_8(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("设置编码格式异常");
		}
	}

	/**
	 * 将对象以json形式返回给前台
	 * 
	 * @param response
	 * @param object
	 */
	public void writeJsonToClient(HttpServletResponse response, Object object) {
		String json = gson.toJson(object);
		logger.info(json);
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			PrintWriter out = response.getWriter();
			out.println(json);
			out.close();
		} catch (IOException e) {
			logger.error("返回客户端json出现异常！");
		}
	}

	/**
	 * 将数据返回给前台
	 * 
	 * @param response
	 * @param object
	 */
	public void writeToClient(HttpServletResponse response, Object object) {
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.println(object);
		} catch (IOException e) {
			logger.error("返回客户端json出现异常！");
		}
	}

	

	/**
	 * 校验用户是否已经登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean isLogin(final HttpServletRequest request, final HttpServletResponse response) {
		String partyId = StringUtils.objectToString(request.getSession().getAttribute("partyId"));
		if (UtilValidate.isEmpty(partyId)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取客户端IP
	 * 
	 * @param request
	 * @return
	 */
	public String getRemoteHost(final HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	/**
	 * 获取浏览器类型
	 * 
	 * @param request
	 * @return
	 */
	public String getBrowser(final HttpServletRequest request) {
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		Browser browser = userAgent.getBrowser();
		return browser.getName();
	}

	/**
	 * 获取操作系统类型
	 * 
	 * @param request
	 * @return
	 */
	public String getOperatingSystem(final HttpServletRequest request) {
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		OperatingSystem os = userAgent.getOperatingSystem();
		return os.getName();
	}
	
	public static String readAsText(String url) {
		StringBuilder result = new StringBuilder();
		try {
			URL urlfile = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(urlfile.openStream()));// 构造一个BufferedReader类来读取文件
			String inputLine = br.readLine();
			while (inputLine != null) {
				result.append(inputLine);
				inputLine = br.readLine();
			}
			br.close();
			return result.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
