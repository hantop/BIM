package com.zzrbi.controller;

import com.zzrbi.entity.RoleDirectoryRel;
import com.zzrbi.entity.Staff;
import com.zzrbi.entity.UserRole;
import com.zzrbi.service.IDirectoryService;
import com.zzrbi.service.IStaffRoleService;
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户请求相关处理操作
 * 
 * @author sihang
 *
 */
@Controller(value = "staffController")
public class StaffController extends BaseController {

	private static Logger logger = Logger.getLogger(StaffController.class);

	@Resource
	private IStaffRoleService staffRoleService;
	@Resource
	private IDirectoryService directoryService;
	@Resource
	private IStaffService staffService;

	/**
	 * 访问用户管理首页
	 * 
	 * @return
	 */
	@RequestMapping("/staffIndex")
	public ModelAndView staffIndex(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>进入首页");
			// 查询所有角色
			List<Map<String, Object>> userRoleList = staffRoleService.queryUserRoleList();
			request.setAttribute("userRoleList", userRoleList);
			return new ModelAndView("staff/staffParty");
		} else {
			logger.info("用户未登录,跳转到登录页");
			return new ModelAndView("login");
		}
	}

	/**
	 * 获取用户列表
	 * 
	 * @param request
	 * @param response
	 * @throws BusinessException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getStaffList")
	public void getStaffList(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		setCharacterEncodingUTF_8(request);
		String staffName = request.getParameter("staffName");
		String userRoleId = request.getParameter("userRoleId");
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer startIndex = Integer.parseInt(request.getParameter("startIndex"));
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.putAll(staffService.getStaffList(staffName, userRoleId, pageSize, startIndex));
		writeJsonToClient(response, returnMap);
	}

	/**
	 * 新增或修改用户
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addAndUpdateUser", method = RequestMethod.POST)
	@ResponseBody
	public void addAndUpdateUser(final HttpServletRequest request, final HttpServletResponse response) {
		setCharacterEncodingUTF_8(request);
		String message = "";
		// 获取数据
		String id = request.getParameter("partyId");
		String staffName = request.getParameter("userName");
		String userRoleId = request.getParameter("userRoleId");
		String password = request.getParameter("password");
		String mobile = request.getParameter("phoneCode");
		String email = request.getParameter("emailCode");

		try {
			if (id != null && !"".equals(id)) {
				Staff user = staffService.findUserById(Integer.parseInt(id));
				user.setStaffName(staffName);
				user.setUserRoleId(Integer.parseInt(userRoleId));
				if (!"".equals(password) && password != null) {
					user.setPasswd(MD5.encodeByMd5AndSaltWithJs(password));
				}
				user.setMobile(mobile);
				user.setEmail(email);
				logger.info("修改用户" + user.toString());
				int num = staffService.updateUser(user);
				if (num > 0) {
					message = "success";
				} else {
					message = "error";
				}
			} else {
				String secret = GoogleAuthenticator.generateSecretKey();
				String url = "otpauth://totp/" + staffName + "@zhongzairong.cn" + "?secret=" + secret;
				Staff user = new Staff();
				user.setStaffName(staffName);
				user.setUserRoleId(Integer.parseInt(userRoleId));
				user.setPasswd(MD5.encodeByMd5AndSaltWithJs(password));
				user.setMobile(mobile);
				user.setEmail(email);
				user.setStatus(1);
				user.setQRCode(url);
				user.setSecurityCode(secret);
				logger.info("新增角色" + user.toString());
				int roleId = staffService.addUser(user);
				if (roleId > 0) {
					message = "success";

				} else {
					message = "error";
				}
			}
		} catch (Exception e) {
			logger.error("角色保存出现异常");
			message = "error";
			e.printStackTrace();
		}
		writeJsonToClient(response, message);
	}

	/**
	 * 生成用户的私钥
	 */
	public String getSecret(String staffName) {
		String secret = GoogleAuthenticator.generateSecretKey();
		System.out.println("Secret key is " + secret);
		return secret;
	}

	/**
	 * 生成用户验证码
	 */
	@RequestMapping("/getStaffQrCode")
	public void getStaffQrCode(HttpServletRequest request, HttpServletResponse response) {
		String staffId = request.getParameter("partId");
		if (staffId != null) {
			ServletOutputStream out = null;
			Staff user = staffService.findUserById(Integer.parseInt(staffId));
//			String sql = "select qrCode from staff where id = " + staffId;
			String uri = user.getQRCode();
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("image/jpeg");

			try {
				BufferedImage bi = QRCode.writeToFile(QRCode.Encode_QR_CODE(uri));
				out = response.getOutputStream();
				ImageIO.write(bi, "jpg", out);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("生成二维码时出现异常");
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
	}
	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/removeUser")
	public void removeUser(final HttpServletRequest request, final HttpServletResponse response) {
		String id = request.getParameter("id");
		String message = "";
		if (StringUtils.isNotEmpty(id)) {
			try {
				staffService.removeUser(Integer.valueOf(id));
				logger.info("删除用户成功，id=" + id);
				message = "success";
			} catch (Exception e) {
				logger.error("删除用户异常，id=" + id);
				message = "error";
				e.printStackTrace();
			}
		} else {
			message = "error";
		}
		writeJsonToClient(response, message);
	}

	/**
	 * 访问角色管理首页
	 * 
	 * @return
	 */
	@RequestMapping("/roleIndex")
	public ModelAndView roleIndex(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>进入首页");
			try {
				List<Map<String, Object>> dicParentList = directoryService.queryParentMenu();
				request.setAttribute("dicParentList", dicParentList);
				List<Map<String, Object>> dicList = directoryService.querySubMenu();
				request.setAttribute("dicList", dicList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView("staff/staffRole");
		} else {
			logger.info("用户未登录,跳转到登录页");
			return new ModelAndView("login");
		}
	}

	/**
	 * 获取角色列表
	 * 
	 * @param request
	 * @param response
	 * @throws BusinessException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getStaffRoleList")
	public void getStaffRoleList(final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {

		setCharacterEncodingUTF_8(request);

		String roleName = request.getParameter("roleName");
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer startIndex = Integer.parseInt(request.getParameter("startIndex"));
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("roleName", roleName);
		Map<String, Object> returnMap = new HashMap<String, Object>();

		returnMap.putAll(staffRoleService.getStaffRoleList(inputMap, pageSize, startIndex));
		writeJsonToClient(response, returnMap);
	}

	/**
	 * 删除用户角色
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/removeRole")
	public void inRemoveRole(final HttpServletRequest request, final HttpServletResponse response) {
		String roleId = request.getParameter("roleId");
		String message = "";
		if (StringUtils.isNotEmpty(roleId)) {
			try {
				staffRoleService.deleteRoleDirectoryRel(Integer.parseInt(roleId));
				staffRoleService.removeUserRole(Integer.valueOf(roleId));
				logger.info("删除角色成功，roleId=" + roleId);
				message = "success";
			} catch (Exception e) {
				logger.error("删除角色异常，roleId= " + roleId);
				message = "error";
				e.printStackTrace();
			}
		} else {
			message = "error";
		}
		writeJsonToClient(response, message);
	}

	/**
	 * 新增或修改角色
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addAndUpdateRole")
	public void addRole(final HttpServletRequest request, final HttpServletResponse response) {
		setCharacterEncodingUTF_8(request);
		String message = "";
		// 获取数据
		String id = request.getParameter("id");
		String roleName = request.getParameter("roleName");
		String remark = request.getParameter("remark");
		String permissionListStr = request.getParameter("permissionList");
		try {
			if (id != null && !"".equals(id)) {
				UserRole userRole = staffRoleService.findUserRoleById(Integer.parseInt(id));
				userRole.setRoleName(roleName);
				userRole.setRemark(remark);
				logger.info("修改角色" + userRole.toString());
				int num = staffRoleService.updateUserRole(userRole);
				if (num > 0) {
					staffRoleService.deleteRoleDirectoryRel(Integer.parseInt(id));
					List<Map<String, Object>> permissionList = new ArrayList<Map<String, Object>>();
					if (UtilValidate.isNotEmpty(permissionListStr)) {
						permissionListStr = permissionListStr.replaceAll("“", "\"").replaceAll("”", "\"");
						permissionList = (List<Map<String, Object>>) gson.fromJson(permissionListStr, List.class);
						for (Map<String, Object> map : permissionList) {
							RoleDirectoryRel record = new RoleDirectoryRel();
							record.setRoleId(userRole.getId());
							record.setDirectoryId(Integer.parseInt((String) map.get("id")));
							int number = staffRoleService.insertRoleDirectoryRel(record);
							if (number > 0) {
								message = "success";
							} else {
								message = "error";
							}
						}
					}
				} else {
					message = "error";
				}
			} else {
				UserRole userRole = new UserRole();
				userRole.setRoleName(roleName);
				userRole.setPlatformType(1);
				userRole.setRemark(remark);
				logger.info("新增角色" + userRole.toString());
				int roleId = staffRoleService.addUserRole(userRole);
				if (roleId > 0) {
					List<Map<String, Object>> permissionList = new ArrayList<Map<String, Object>>();
					if (UtilValidate.isNotEmpty(permissionListStr)) {
						permissionListStr = permissionListStr.replaceAll("“", "\"").replaceAll("”", "\"");
						permissionList = (List<Map<String, Object>>) gson.fromJson(permissionListStr, List.class);
						for (Map<String, Object> map : permissionList) {
							RoleDirectoryRel record = new RoleDirectoryRel();
							record.setRoleId(roleId);
							record.setDirectoryId(Integer.parseInt((String) map.get("id")));
							int reselt = staffRoleService.insertRoleDirectoryRel(record);
							if (reselt > 0) {
								message = "success";
							} else {
								message = "error";
							}
						}
					}
				} else {
					message = "error";
				}
			}
		} catch (Exception e) {
			logger.error("角色保存出现异常");
			message = "error";
			e.printStackTrace();
		}
		writeJsonToClient(response, message);
	}
}
