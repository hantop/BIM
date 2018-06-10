package com.zzrbi.controller;

import com.zzrbi.entity.Staff;
import com.zzrbi.entity.WorkTeam;
import com.zzrbi.service.*;
import com.zzrbi.util.BusinessException;
import com.zzrbi.util.GoogleAuthenticator;
import com.zzrbi.util.MD5;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Desc: 人员管理
 * User: LIHUI
 * Date: 2018-01-25
 * Time: 18:01
 */
@Controller(value="workerController")
public class WorkerController extends BaseController {

	private static Logger logger = Logger.getLogger(WorkerController.class);
	@Resource
	private IWorkTeamService workTeamService;
	@Resource

	private IWorkerService workerService;

	@RequestMapping("/workTeam")
	public ModelAndView staffIndex(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>人员管理");
			// 查询所有公司
			List<Map<String, Object>> userRoleList = workTeamService.queryWorkCompanyList();
			request.setAttribute("userRoleList", userRoleList);
			return new ModelAndView("worker/workTeam");
		} else {
			logger.info("用户未登录,跳转到登录页");
			return new ModelAndView("login");
		}
	}
	/**
	 * 添加工作组
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addAndUpdateWorkTeam", method = RequestMethod.POST)
	@ResponseBody
	public void  addWork(final HttpServletRequest request,final HttpServletResponse response) {
		logger.info("添加工作组");
		if (isLogin(request, response)) {

			setCharacterEncodingUTF_8(request);
			String message = "";
			// 获取数据
			String id = request.getParameter("partyId");
			String teamName = request.getParameter("userName");
			String teamManager = request.getParameter("password");
			String mobile = request.getParameter("phoneCode");
			String companyId = request.getParameter("userRoleId");

			try {
				if (id != null && !"".equals(id)) {
					WorkTeam user = workTeamService.findUserById(Integer.parseInt(id));
					user.setTeamName(teamName);
					user.setTeamManager(teamManager);
					user.setMobile(mobile);
					user.setCompanyId(Integer.parseInt(companyId));

					logger.info("修改用户" + user.toString());
					int num = workTeamService.updateUser(user);
					if (num > 0) {
						message = "success";
					} else {
						message = "error";
					}
				} else {
					WorkTeam user = new WorkTeam();
					user.setTeamName(teamName);
					user.setTeamManager(teamManager);
					user.setMobile(mobile);
					user.setCompanyId(Integer.parseInt(companyId));
					logger.info("新增角色" + user.toString());

					int roleId = workTeamService.addUser(user);
					if (roleId > 0) {
						message = "success";

					} else {
						message = "error";
					}
				}
			} catch (Exception e) {
				logger.error("保存出现异常");
				message = "error";
				e.printStackTrace();
			}

			writeJsonToClient(response, message);
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
	@RequestMapping("/getWorkTeamList")
	public void getWorkTeamList(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		setCharacterEncodingUTF_8(request);
		String staffName = request.getParameter("staffName");
		String userRoleId = request.getParameter("userRoleId");
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer startIndex = Integer.parseInt(request.getParameter("startIndex"));
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.putAll(workTeamService.getWorkTeamList(staffName, userRoleId, pageSize, startIndex));
		writeJsonToClient(response, returnMap);
	}


	@RequestMapping("/worker")
	public ModelAndView worker(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>人员管理");
			// 查询所有公司
			List<Map<String, Object>> userRoleList = workTeamService.queryWorkCompanyList();
			request.setAttribute("userRoleList", userRoleList);

			List<Map<String, Object>> teamList = workTeamService.queryWorkTeamList(1);
			request.setAttribute("teamList", teamList);

			return new ModelAndView("worker/worker");
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
	@RequestMapping("/getWorkerList")
	public void getWorkerList(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		setCharacterEncodingUTF_8(request);
		String staffName = request.getParameter("staffName");
		String userRoleId = request.getParameter("userRoleId");
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer startIndex = Integer.parseInt(request.getParameter("startIndex"));
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.putAll(workerService.getWorkerList(staffName, userRoleId, pageSize, startIndex));
		writeJsonToClient(response, returnMap);
	}
}
