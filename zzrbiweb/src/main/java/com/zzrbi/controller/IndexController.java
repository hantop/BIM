package com.zzrbi.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzrbi.service.IEveryTimeService;
import com.zzrbi.service.IStatisticsBalanceService;
import com.zzrbi.service.*;
import com.zzrbi.util.LateralAxisUtil;
import com.zzrbi.util.POIHandleDataUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.zzrbi.service.IDirectoryService;
import com.zzrbi.service.IStaffService;
import com.zzrbi.util.StringUtils;
import com.zzrbi.controller.BaseController;
import com.zzrbi.entity.Staff;

/**
 * 访问本系统入口请求相关处理操作
 * @author sihang
 *
 */
@Controller(value="indexController")
public class IndexController extends BaseController {
	
	private static Logger logger = Logger.getLogger(IndexController.class);
	@Resource
	private IDirectoryService directoryService;
	@Resource
	private IStaffService staffService;
	@Resource
	private IEveryTimeService everyTimeService;
	@Resource
	private IPersonService personService;
	@Resource
	private IStatisticsBalanceService statisticsBalanceService;

	/**
	 * 访问首页
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response) {
		if (isLogin(request, response)) {
			logger.info("用户已经登录,跳转到操作页");
			String partyId = StringUtils.objectToString(request.getSession().getAttribute("partyId"));
		try {
			List<Map<String, Object>> dicParentList = directoryService.queryParentMenuById(Integer.parseInt(partyId));
			request.setAttribute("dicParentList", dicParentList);
			List<Map<String, Object>> dicList = directoryService.querySubMenuById(Integer.parseInt(partyId));
			request.setAttribute("dicList", dicList);
			Staff user = staffService.findUserById(Integer.parseInt(partyId));
			request.setAttribute("userData", user);
			return new ModelAndView("index");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error");
		}
		
		}else{
			logger.info("用户未登录,跳转到登录页");
			return new ModelAndView("login");
		}
	}
	/**
	 * 首页内容
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/home")
	public ModelAndView treeHome(final HttpServletRequest request,final HttpServletResponse response) {
		logger.info("加载主页");
		if (isLogin(request, response)) {
			try {
				//2018-01-25
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				Date yesDate = calendar.getTime();

				System.out.println(sdf.format(yesDate));
				System.out.println(sdf.format(date));

				Map<String, Object> yesdayPerson = personService.queryCumulativePersonByDay(sdf.format(yesDate),sdf.format(yesDate),1,0);
				request.setAttribute("yesdayPerson", yesdayPerson);
				List<Map<String, Object>> yesdayList = everyTimeService.queryPersonListByDateTime(sdf.format(yesDate));
				if(yesdayList.size()>0){
					Map<String,Object> yesdayMap = yesdayList.get(0);
					yesdayMap.put("availableBalance",POIHandleDataUtils.formatMoney((BigDecimal) yesdayMap.get("availableBalance")));
					yesdayMap.put("investMoney",POIHandleDataUtils.formatMoney((BigDecimal) yesdayMap.get("investMoney")));
					yesdayMap.put("inflow",POIHandleDataUtils.formatMoney((BigDecimal) yesdayMap.get("inflow")));
					request.setAttribute("yesday", yesdayMap);
				}else {
					request.setAttribute("yesday", new HashMap<String,Object>());
				}

				List<Map<String, Object>> todayList = everyTimeService.queryPersonListByDateTime(sdf.format(date));
				if(todayList.size()>0){
					Map<String,Object> todayMap = todayList.get(0);
					todayMap.put("availableBalance",POIHandleDataUtils.formatMoney((BigDecimal) todayMap.get("availableBalance")));
					todayMap.put("investMoney",POIHandleDataUtils.formatMoney((BigDecimal) todayMap.get("investMoney")));
					todayMap.put("inflow",POIHandleDataUtils.formatMoney((BigDecimal) todayMap.get("inflow")));
					request.setAttribute("today", todayMap);
				}else {
					request.setAttribute("today", new HashMap<String,Object>());
				}
				List<Map<String, Object>> personList = personService.queryCumulativePerson();
				request.setAttribute("person", personList.get(0));

				List<Map<String, Object>> balanceList = statisticsBalanceService.queryCumulativeMoney();
				if(balanceList.size()>0){
					Map<String,Object> balanceMap = balanceList.get(0);
					balanceMap.put("availableBalance",POIHandleDataUtils.formatMoney((BigDecimal) balanceMap.get("availableBalance")));
					balanceMap.put("invest",POIHandleDataUtils.formatMoney((BigDecimal) balanceMap.get("invest")));
					balanceMap.put("inflow",POIHandleDataUtils.formatMoney((BigDecimal) balanceMap.get("inflow")));
					request.setAttribute("balance", balanceMap);
				}else {
					request.setAttribute("balance", new HashMap<String,Object>());
				}

			}catch(Exception ee){
				ee.printStackTrace();
			}
			return new ModelAndView("home");
		}else{
			return new ModelAndView("to_login");
		}
	}

	/**
	 *  @Description 获取人员相关的统计数据
	 *  @Author zhaobiao
	 *  @Date 2018/1/30 15:00
	 */
	@RequestMapping("/person/list")
	public  void getIndexCartogram(HttpServletRequest request,HttpServletResponse response){

		Map<String, Object> returnMap = new HashMap<String, Object>();
		//判断是 1时  2天  3周 4月
		String typeFlag = request.getParameter("type");
        //开始的时间
		String startTime = request.getParameter("startTime");
		//结束时间
		String endTime = request.getParameter("endTime");

		List<Map<String,Object>> listMap = null;

		try{

			int num = Integer.valueOf(typeFlag);

			switch(num){
				case 1:
                    //按照时间查询数据   注册人数  投资人数   首投的人数
					listMap = everyTimeService.queryUserSumByTime(startTime);

					break;

				case 2:
                    // 按照天查询数据   注册人数  投资人数   首投的人数
					listMap = everyTimeService.queryUserSumByDays(startTime,endTime);

					break;

				case 3:
                    // 按照周查询数据   注册人数  投资人数   首投的人数
					listMap = everyTimeService.queryUserSumByWeek(LateralAxisUtil.getFirstWeek(startTime),LateralAxisUtil.getLastWeek(endTime));

					break;

				case 4:
					// 按照月查询数据   注册人数  投资人数   首投的人数
					listMap = everyTimeService.queryUserSumByMonth(LateralAxisUtil.formatTimeToYm(startTime),LateralAxisUtil.formatTimeToYm(endTime));

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
	 *  @Description 获取钱相关的统计数据
	 *  @Author zhaobiao
	 *  @Date 2018/2/1 13:00
	 */
	@RequestMapping("/money/list")
	public void getIndexCartogramAboutMoney(HttpServletRequest request,HttpServletResponse response){

		Map<String, Object> returnMap = new HashMap<String, Object>();
		//判断是 1时  2天  3周 4月
		String typeFlag = request.getParameter("type");
		//开始的时间
		String startTime = request.getParameter("startTime");
		//结束时间
		String endTime = request.getParameter("endTime");

		List<Map<String,Object>> listMap = null;

		try{

			int num = Integer.valueOf(typeFlag);

			switch(num){
				case 1:
					//按照时间查询数据   存量金额  交易额   净流入
					listMap = everyTimeService.queryUserSumByTime(startTime);

					break;

				case 2:
					// 按照天查询数据   存量金额  交易额   净流入
					listMap = statisticsBalanceService.queryCumulativeMoneyByDay(startTime,endTime);

					break;

				case 3:
					// 按照周查询数据   存量金额  交易额   净流入
					listMap = statisticsBalanceService.queryCumulativeMoneyByWeek(LateralAxisUtil.getFirstWeek(startTime),LateralAxisUtil.getLastWeek(endTime));

					break;

				case 4:
					// 按照月查询数据   存量金额  交易额   净流入
					listMap = statisticsBalanceService.queryCumulativeMoneyByMonth(LateralAxisUtil.formatTimeToYm(startTime),LateralAxisUtil.formatTimeToYm(endTime));

					break;
			}

			Map<String,Object> monthMap = fromListToArrayByMoney(listMap,Integer.valueOf(typeFlag));

			returnMap.putAll(monthMap);

		}catch (Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
		}

		writeJsonToClient(response, returnMap);
	}


	/**
	 *  @Description 首页的列表数据
	 *  @Author zhaobiao
	 *  @Date 2018/2/5 15:00
	 */
	@RequestMapping("/table/list")
    public void getUserAboutMoney(HttpServletRequest request ,HttpServletResponse response){

        Map<String, Object> returnMap = new HashMap<String, Object>();
        //判断是 1时  2天  3周 4月
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

            int num = Integer.valueOf(typeFlag);

            switch(num){
                case 1:
                    //按照时间查询数据   所有的数据
                    map =everyTimeService.queryUserSumByTimeFy(startTime,pageSize,startIndex);

                    break;

                case 2:
                    // 按照天查询数据
                    map = statisticsBalanceService.queryCumulativeMoneyByDayFy(startTime,endTime,pageSize,startIndex);

                    break;

                case 3:
                    // 按照周查询数据
                    map =  statisticsBalanceService.queryCumulativeMoneyByWeekFy(LateralAxisUtil.getFirstWeek(startTime),LateralAxisUtil.getLastWeek(endTime),pageSize,startIndex);

                    break;

                case 4:
                    // 按照月查询数据
                    map = statisticsBalanceService.queryCumulativeMoneyByMonthFy(LateralAxisUtil.formatTimeToYm(startTime),LateralAxisUtil.formatTimeToYm(endTime),pageSize,startIndex);

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
	 * 异常页面，404
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/404")
    public ModelAndView model404(final HttpServletRequest request,final HttpServletResponse response) {
		
		logger.info("页面未找到，进入404");
    	return new ModelAndView("404");
    }
	
	/**
	 * 异常页面，500
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/500")
    public ModelAndView model500(final HttpServletRequest request,final HttpServletResponse response) {
    	
    	logger.info("服务器异常，进入500");
    	return new ModelAndView("500");
    }
    /**
     * 浏览器 ie
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/ie")
    public ModelAndView modelie(final HttpServletRequest request,final HttpServletResponse response) {
    	return new ModelAndView("ie");
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
		String[] registerArray = new String[listMapSize];

		String[] investArray = new String[listMapSize];

		String[] firstInvestArray = new String[listMapSize];

		String[] hzArray = new String[listMapSize];

		for (int i=0;i<listMapSize;i++){
			registerArray[i] = MapUtils.getString(listMap.get(i),"registerCount");
			investArray[i] = MapUtils.getString(listMap.get(i),"investCount");
			firstInvestArray[i] = MapUtils.getString(listMap.get(i),"firstInvestCount");
			hzArray[i] = LateralAxisUtil.formatTime( MapUtils.getString(listMap.get(i),"dateTime"),type);
		}

		map.put("registerArray",registerArray);
		map.put("investArray",investArray);
		map.put("firstInvestArray",firstInvestArray);
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


	/**
	 *  @Description 获取人员相关的统计数据 的组装数据
	 *  @Author zhaobiao
	 *  @Date 2018/2/1 10:10
	 */
	public Map<String,Object> fromListToArrayByMoney(List<Map<String,Object>> listMap,int type){


		Map<String,Object> map = new HashMap<String,Object>();

		int listMapSize = listMap.size();

		if (listMapSize == 0){
			return new HashMap<String,Object>();
		}
		String[] clArray = new String[listMapSize];

		String[] jyArray = new String[listMapSize];

		String[] jlArray = new String[listMapSize];

		String[] hzArray = new String[listMapSize];

		for (int i=0;i<listMapSize;i++){
			//存量金额
			clArray[i] = MapUtils.getString(listMap.get(i),"availableBalance");
			//交易额
			jyArray[i] = MapUtils.getString(listMap.get(i),"investMoney");
			//净流入
			jlArray[i] = MapUtils.getString(listMap.get(i),"jlMoney");
			hzArray[i] = LateralAxisUtil.formatTime( MapUtils.getString(listMap.get(i),"dateTime"),type);
		}

		map.put("clArray",clArray);
		map.put("jyArray",jyArray);
		map.put("jlArray",jlArray);
		map.put("hz",hzArray);

		return  map;

	}

}
