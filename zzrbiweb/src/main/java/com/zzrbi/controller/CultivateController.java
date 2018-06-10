package com.zzrbi.controller;

import com.zzrbi.entity.WorkTeam;
import com.zzrbi.service.IWorkTeamService;
import com.zzrbi.service.IWorkerService;
import com.zzrbi.util.BusinessException;
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
 * Desc: 培训管理
 * User: LIHUI
 * Date: 2018-01-25
 * Time: 18:01
 */
@Controller(value="cultivateController")
public class CultivateController extends BaseController {

	private static Logger logger = Logger.getLogger(CultivateController.class);
	@Resource
	private IWorkTeamService workTeamService;
	@Resource

	private IWorkerService workerService;


	/**
	 *  @Description 培训管理列表页
	 *  @Author lihui
	 *  @Date 2018/6/9 18:29
	 * @param
	 * @Return
	 */
	@RequestMapping("/cultivate")
	public ModelAndView cultivate(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>培训管理");

			return new ModelAndView("worker/cultivate");
		} else {
			logger.info("用户未登录,跳转到登录页");
			return new ModelAndView("login");
		}
	}




	/**
	 *  @Description 0=不良记录1=奖励记录列表页
	 *  @Author lihui
	 *  @Date 2018/6/9 18:29
	 * @param
	 * @Return
	 */
	@RequestMapping("/workerRecord")
	public ModelAndView workerRecord(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>不良记录  奖励记录");

			String rtype = request.getParameter("rtype");
			request.setAttribute("rtype", rtype);

			return new ModelAndView("worker/workerRecord");
		} else {
			logger.info("用户未登录,跳转到登录页");
			return new ModelAndView("login");
		}
	}

}
