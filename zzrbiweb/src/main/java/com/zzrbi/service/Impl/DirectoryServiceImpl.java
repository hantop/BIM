package com.zzrbi.service.Impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.zzrbi.dao.BaseDaoImpl;
import com.zzrbi.service.IDirectoryService;

/**
 * 菜单目录业务处理实现类
 * @author sihang
 *
 */
@Service
public class DirectoryServiceImpl extends BaseDaoImpl implements IDirectoryService {
	/**
	 * 获取log
	 */
	private Logger logger = Logger.getLogger(DirectoryServiceImpl.class);


	@Override
	public List<Map<String, Object>> queryParentMenu()throws Exception {
		logger.info("获取父级菜单");
		String sql = sqlMap("Directory.queryParentMenu");
		return jdbcTemplate.queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> querySubMenu() {
		logger.info("获取子级菜单");
		String sql = sqlMap("Directory.querySubMenu");
		return jdbcTemplate.queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> queryParentMenuById(int parseID) {
		logger.info("通过用户ID获取当前用户的父级目录");
		String sql = sqlMap("Directory.queryParentMenuById");
		return jdbcTemplate.queryForList(sql, parseID);
	}


	@Override
	public List<Map<String, Object>> querySubMenuById(int parseID) {
		logger.info("通过用户ID获取当前用户的子级目录");
		String sql = sqlMap("Directory.querySubMenuById");
		return jdbcTemplate.queryForList(sql,parseID);
	}

	   

}
