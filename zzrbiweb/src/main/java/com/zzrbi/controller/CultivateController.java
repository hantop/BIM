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

	/**
	 *  @Description 黑名单列表页
	 *  @Author lihui
	 *  @Date 2018/6/9 18:29
	 * @param
	 * @Return
	 */
	@RequestMapping("/blackRecord")
	public ModelAndView blackRecord(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>黑名单列表页记录");

			return new ModelAndView("worker/blackRecord");
		} else {
			logger.info("用户未登录,跳转到登录页");
			return new ModelAndView("login");
		}
	}

	/**
	 *  @Description 工时统计列表页
	 *  @Author lihui
	 *  @Date 2018/6/9 18:29
	 * @param
	 * @Return
	 */
	@RequestMapping("/workerCardRecord")
	public ModelAndView workerCardRecord(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>工时统计列表页");

			return new ModelAndView("worker/workerCardRecord");
		} else {
			logger.info("用户未登录,跳转到登录页");
			return new ModelAndView("login");
		}
	}


	/**
	 *  @Description 工资发放列表
	 *  @Author lihui
	 *  @Date 2018/6/9 18:29
	 * @param
	 * @Return
	 */
	@RequestMapping("/wages")
	public ModelAndView wages(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>工资发放列表页");

			return new ModelAndView("worker/wages");
		} else {
			logger.info("用户未登录,跳转到登录页");
			return new ModelAndView("login");
		}
	}

	/**
	 *  @Description 楼宇展示列表
	 *  @Author lihui
	 *  @Date 2018/6/9 18:29
	 * @param
	 * @Return
	 */
	@RequestMapping("/floor")
	public ModelAndView floor(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>楼宇展示列表页");

			return new ModelAndView("worker/floor");
		} else {
			logger.info("用户未登录,跳转到登录页");
			return new ModelAndView("login");
		}
	}

	/**
	 *  @Description 房间展示列表
	 *  @Author lihui
	 *  @Date 2018/6/9 18:29
	 * @param
	 * @Return
	 */
	@RequestMapping("/room")
	public ModelAndView room(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>房间展示列表页");

			return new ModelAndView("worker/room");
		} else {
			logger.info("用户未登录,跳转到登录页");
			return new ModelAndView("login");
		}
	}

	/**
	 *  @Description 床位展示发放列表
	 *  @Author lihui
	 *  @Date 2018/6/9 18:29
	 * @param
	 * @Return
	 */
	@RequestMapping("/bed")
	public ModelAndView bed(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>床位展示列表页");

			return new ModelAndView("worker/bed");
		} else {
			logger.info("用户未登录,跳转到登录页");
			return new ModelAndView("login");
		}
	}

}
