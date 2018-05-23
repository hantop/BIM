package com.zzrbi.service;


import java.util.List;
import java.util.Map;




/**
 * 菜单目录业务处理接口
 * @author yinlonglong
 * @date 2017年3月21日 下午3:24:32
 */
public interface IDirectoryService {


	/**
	 * 获取父级目录
	 * @return
	 */
	public List<Map<String, Object>> queryParentMenu()throws Exception;
    /**
     * 获取子级目录
     * @return
     */
	public List<Map<String, Object>> querySubMenu();
	/**
	 * 通过用户ID获取当前用户的父级目录
	 * @param parseId
	 * @return
	 */
	public List<Map<String, Object>> queryParentMenuById(int parseID);
	/**
	 * 通过用户ID获取当前用户的子级目录
	 * @param parseID
	 * @return
	 */
	public List<Map<String, Object>> querySubMenuById(int parseID);
	
	
	
}
