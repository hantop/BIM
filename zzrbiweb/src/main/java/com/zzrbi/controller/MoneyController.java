package com.zzrbi.controller;

import com.zzrbi.service.IDirectoryService;
import com.zzrbi.service.IEveryTimeService;
import com.zzrbi.service.IStaffService;
import com.zzrbi.service.IStatisticsBalanceService;
import com.zzrbi.util.LateralAxisUtil;
import com.zzrbi.util.POIHandleDataUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Desc: 按钱统计业务处理
 * User: LIHUI
 * Date: 2018-01-25
 * Time: 18:01
 */
@Controller(value="moneyController")
public class MoneyController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MoneyController.class);
	@Resource
	private IEveryTimeService everyTimeService;
	@Resource
	private IStatisticsBalanceService statisticsBalanceService;


	/**
	 * 获取平台累计（人）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/capitalFlow")
	public ModelAndView treeHome(final HttpServletRequest request,final HttpServletResponse response) {
		logger.info("获取平台累计（人）");
		if (isLogin(request, response)) {

			try {
				//2018-01-25
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				Date yesDate = calendar.getTime();

				System.out.println(sdf.format(yesDate));
				System.out.println(sdf.format(date));

				List<Map<String, Object>> yesdayList = everyTimeService.queryMoneyListByDateTime(sdf.format(yesDate));
				if(yesdayList.size()>0){
					Map<String,Object> yesdayMap = yesdayList.get(0);
					yesdayMap.put("availableBalance", POIHandleDataUtils.formatMoney((BigDecimal) yesdayMap.get("availableBalance")));
					yesdayMap.put("investMoney",POIHandleDataUtils.formatMoney((BigDecimal) yesdayMap.get("investMoney")));
					yesdayMap.put("inflow",POIHandleDataUtils.formatMoney((BigDecimal) yesdayMap.get("inflow")));
					yesdayMap.put("recharge",POIHandleDataUtils.formatMoney((BigDecimal) yesdayMap.get("recharge")));
					yesdayMap.put("investPhase",POIHandleDataUtils.formatMoney((BigDecimal) yesdayMap.get("investPhase")));
					yesdayMap.put("cash",POIHandleDataUtils.formatMoney((BigDecimal) yesdayMap.get("cash")));
					yesdayMap.put("stand",POIHandleDataUtils.formatMoney((BigDecimal) yesdayMap.get("stand")));
					request.setAttribute("yesday", yesdayMap);
				}else {
					request.setAttribute("yesday", new HashMap<String,Object>());
				}
				List<Map<String, Object>> todayList = everyTimeService.queryMoneyListByDateTime(sdf.format(date));
				if(todayList.size()>0){
					Map<String,Object> todayMap = todayList.get(0);
					todayMap.put("availableBalance",POIHandleDataUtils.formatMoney((BigDecimal) todayMap.get("availableBalance")));
					todayMap.put("investMoney",POIHandleDataUtils.formatMoney((BigDecimal) todayMap.get("investMoney")));
					todayMap.put("inflow",POIHandleDataUtils.formatMoney((BigDecimal) todayMap.get("inflow")));
					todayMap.put("recharge",POIHandleDataUtils.formatMoney((BigDecimal) todayMap.get("recharge")));
					todayMap.put("investPhase",POIHandleDataUtils.formatMoney((BigDecimal) todayMap.get("investPhase")));
					todayMap.put("cash",POIHandleDataUtils.formatMoney((BigDecimal) todayMap.get("cash")));
					todayMap.put("stand",POIHandleDataUtils.formatMoney((BigDecimal) todayMap.get("stand")));
					request.setAttribute("today", todayMap);
				}else {
					request.setAttribute("today", new HashMap<String,Object>());
				}
				List<Map<String, Object>> balanceList = statisticsBalanceService.queryCumulativeMoney2();
				if(balanceList.size()>0){
					Map<String,Object> balanceMap = balanceList.get(0);
					balanceMap.put("availableBalance",POIHandleDataUtils.formatMoney((BigDecimal) balanceMap.get("availableBalance")));
					balanceMap.put("stand",POIHandleDataUtils.formatMoney((BigDecimal) balanceMap.get("stand")));
					balanceMap.put("invest",POIHandleDataUtils.formatMoney((BigDecimal) balanceMap.get("invest")));
					balanceMap.put("recharge",POIHandleDataUtils.formatMoney((BigDecimal) balanceMap.get("recharge")));
					balanceMap.put("investPhase",POIHandleDataUtils.formatMoney((BigDecimal) balanceMap.get("investPhase")));
					balanceMap.put("cash",POIHandleDataUtils.formatMoney((BigDecimal) balanceMap.get("cash")));
					balanceMap.put("inflow",POIHandleDataUtils.formatMoney((BigDecimal) balanceMap.get("inflow")));
					request.setAttribute("balance", balanceMap);
				}else {
					request.setAttribute("balance", new HashMap<String,Object>());
				}

			}catch(Exception ee){
				ee.printStackTrace();
			}

			return new ModelAndView("/flows/moneyFlow");

			}else{
				return new ModelAndView("to_login");
			}
		}




	/**
	 *  @Description 资金统计折线图
	 *  @Author zhaobiao
	 *  @Date 2018/2/6 14:30
	 * @param
	 */
	@RequestMapping("/money/stream")
	public  void getMoneyStream(HttpServletResponse response,HttpServletRequest request){


		Map<String, Object> returnMap = new HashMap<String, Object>();
		//判断是  2天  3周 4月
		String typeFlag = request.getParameter("type");
		//开始的时间
		String startTime = request.getParameter("startTime");
		//结束时间
		String endTime = request.getParameter("endTime");

		List<Map<String,Object>> listMap = null;

		try{

			switch (Integer.valueOf(typeFlag)){

				case 2:

					listMap = statisticsBalanceService.queryMoneyStreamByDay(startTime,endTime);

					break;

				case 3:

					listMap = statisticsBalanceService.queryMoneyStreamByWeek(LateralAxisUtil.getFirstWeek(startTime),LateralAxisUtil.getLastWeek(endTime));

					break;

				case 4:

					listMap = statisticsBalanceService.queryMoneyStreamByMoney(LateralAxisUtil.formatTimeToYm(startTime),LateralAxisUtil.formatTimeToYm(endTime));

					break;

			}

			Map<String,Object> monthMap = fromListToArrayByUser(listMap,Integer.valueOf(typeFlag));

			returnMap.putAll(monthMap);

		}catch (Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());

		}

		writeJsonToClient(response, returnMap);

	}




	/**
	 *  @Description 资金统计列表
	 *  @Author zhaobiao
	 *  @Date 2018/2/6 17:09
	 * @param
	 */
	@RequestMapping("/tableMoney/stream")
	public void getMoneyStreamTable(HttpServletRequest request,HttpServletResponse response){

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

					map = statisticsBalanceService.queryMoneyStreamByDayFy(startTime,endTime,pageSize,startIndex);

					break;

				case 3:

					map = statisticsBalanceService.queryMoneyStreamByWeekFy(LateralAxisUtil.getFirstWeek(startTime),LateralAxisUtil.getLastWeek(endTime),pageSize,startIndex);

					break;

				case 4:

					map = statisticsBalanceService.queryMoneyStreamByMoneyFy(LateralAxisUtil.formatTimeToYm(startTime),LateralAxisUtil.formatTimeToYm(endTime),pageSize,startIndex);

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
	 *  @Description 获取人员相关的统计数据 的组装数据
	 *  @Author zhaobiao
	 *  @Date 2018/1/31 15:00
	 */
	public Map<String,Object> fromListToArrayByUser(List<Map<String,Object>> listMap,int type){


		Map<String,Object> map = new HashMap<String,Object>();

		int listMapSize = listMap.size();

		if (listMapSize == 0){
			return new HashMap<String,Object>();
		}
		String[] recharge = new String[listMapSize];

		String[] availableBalance = new String[listMapSize];

		String[] stand = new String[listMapSize];

		String[] hzArray = new String[listMapSize];

		String[] cash = new String[listMapSize];

		String[] invest = new String[listMapSize];

		String[] investPhase = new String[listMapSize];

		String[] jlMoney = new String[listMapSize];

		for (int i=0;i<listMapSize;i++){
			recharge[i] = MapUtils.getString(listMap.get(i),"recharge");
			availableBalance[i] = MapUtils.getString(listMap.get(i),"availableBalance");
			stand[i] = MapUtils.getString(listMap.get(i),"stand");
			cash[i] = MapUtils.getString(listMap.get(i),"cash");
			invest[i] = MapUtils.getString(listMap.get(i),"invest");
			investPhase[i] = MapUtils.getString(listMap.get(i),"investPhase");
			jlMoney[i] = MapUtils.getString(listMap.get(i),"jlMoney");
			hzArray[i] = LateralAxisUtil.formatTime( MapUtils.getString(listMap.get(i),"dateTime"),type);
		}

		map.put("recharge",recharge);
		map.put("availableBalance",availableBalance);
		map.put("stand",stand);
		map.put("cash",cash);
		map.put("invest",invest);
		map.put("investPhase",investPhase);
		map.put("jlMoney",jlMoney);
		map.put("hz",hzArray);

		return  map;

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
