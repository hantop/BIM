package com.zzrbi.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.zzrbi.service.IPersonService;
import com.zzrbi.util.LateralAxisUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.zzrbi.service.IDirectoryService;
import com.zzrbi.service.IStaffService;
import com.zzrbi.service.IEveryTimeService;
/**
 * Desc: 按人统计业务处理
 * User: LIHUI
 * Date: 2018-01-25
 * Time: 18:01
 */
@Controller(value="personController")
public class PersonController extends BaseController {
	
	private static Logger logger = Logger.getLogger(PersonController.class);
	@Resource
	private IDirectoryService directoryService;
	@Resource
	private IStaffService staffService;
	@Resource
	private IEveryTimeService everyTimeService;

	@Resource
	private IPersonService personService;


	/**
	 * 获取平台累计（人）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userTraffic")
	public ModelAndView treeHome(final HttpServletRequest request,final HttpServletResponse response) {
		logger.info("获取平台累计（人）");
		if (isLogin(request, response)) {

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
			}catch(Exception ee){
				ee.printStackTrace();
			}

			return new ModelAndView("/flows/userFlow");

			}else{
				return new ModelAndView("to_login");
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
