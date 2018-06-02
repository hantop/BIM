package com.zzrbi.controller;

import com.zzrbi.service.*;
import com.zzrbi.util.LateralAxisUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	private IStaffRoleService staffRoleService;
	@Resource
	private IPersonService personService;

	@RequestMapping("/workTeam")
	public ModelAndView staffIndex(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("----->>>>>人员管理");
			// 查询所有公司
			List<Map<String, Object>> userRoleList = staffRoleService.queryUserRoleList();
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
	@RequestMapping(value = "/addWork", method = RequestMethod.POST)
	@ResponseBody
	public void  addWork(final HttpServletRequest request,final HttpServletResponse response) {
		logger.info("添加工作组");
		if (isLogin(request, response)) {

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
				//2018-01-25
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				Date date = new Date();
//
//				Calendar calendar = Calendar.getInstance();
//				calendar.setTime(date);
//				calendar.add(Calendar.DAY_OF_MONTH, -1);
//				Date yesDate = calendar.getTime();
//
//				System.out.println(sdf.format(yesDate));
//				System.out.println(sdf.format(date));
//
//				List<Map<String, Object>> yesdayList = everyTimeService.queryPersonListByDateTime(sdf.format(yesDate));
//				request.setAttribute("yesdayList", yesdayList);
//
//				List<Map<String, Object>> todayList = everyTimeService.queryPersonListByDateTime(sdf.format(date));
//				request.setAttribute("todayList", todayList);
			} catch (Exception e) {
				logger.error("保存出现异常");
				message = "error";
				e.printStackTrace();
			}

			writeJsonToClient(response, message);
		}

	}



	/**
	 *  用户流量获取数据管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/moneyStream")
	public void getUserMoneyStream(HttpServletRequest request,HttpServletResponse response){

		Map<String, Object> returnMap = new HashMap<String, Object>();
		//判断是  2天  3周 4月
		String typeFlag = request.getParameter("type");
		//开始的时间
		String startTime = request.getParameter("startTime");
		//结束时间
		String endTime = request.getParameter("endTime");
		//数量
		Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
		//页码
		Integer startIndex = Integer.valueOf(request.getParameter("startIndex"));

		Map<String,Object> map = null;

        try{

			switch (Integer.valueOf(typeFlag)){

				case 2:

					map = personService.queryCumulativePersonByDay(startTime,endTime,pageSize,startIndex);

					break;

				case 3:

					map = personService.queryCumulativePersonByWeek(LateralAxisUtil.getFirstWeek(startTime),LateralAxisUtil.getLastWeek(endTime),pageSize,startIndex);

					break;

				case 4:

					map = personService.queryCumulativePersonByMonth(LateralAxisUtil.formatTimeToYm(startTime),LateralAxisUtil.formatTimeToYm(endTime),pageSize,startIndex);

					break;

			}

			returnMap.putAll(updateMap(map,Integer.valueOf(typeFlag)));

		}catch (Exception e){
        	e.printStackTrace();
        	logger.info(e.getMessage());

		}

		writeJsonToClient(response, returnMap);

	}


	/**
	 *  @Description 格式化返回数据的时间格式
	 *  @Author zhaobiao
	 *  @Date 2018/2/5 16:20
	 */
	public Map<String,Object> updateMap(Map<String,Object> map,int type){

		List<Map<String,Object>> listMap = (List<Map<String,Object>>) map.get("rows");

		for (Map<String,Object> dataMap : listMap){
			dataMap.put("dateTime",LateralAxisUtil.formatTime( MapUtils.getString(dataMap,"dateTime"),type));
		}
		return map;
	}

}
