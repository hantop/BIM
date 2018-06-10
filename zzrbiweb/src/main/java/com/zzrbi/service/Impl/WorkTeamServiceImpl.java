package com.zzrbi.service.Impl;

import com.zzrbi.dao.BaseDaoImpl;
import com.zzrbi.entity.WorkTeam;
import com.zzrbi.service.IWorkTeamService;
import com.zzrbi.util.BusinessException;
import com.zzrbi.util.DynamicContextChange;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户业务处理实现类
 * 
 * @author sihang
 *
 */
@Service
public class WorkTeamServiceImpl extends BaseDaoImpl implements IWorkTeamService {
	/**
	 * 获取log
	 */
	private Logger logger = Logger.getLogger(WorkTeamServiceImpl.class);
	@Resource
	private BaseDaoImpl baseDaoImpl;

	@Override
	public Map<String, Object> getWorkTeamList(String WorkTeamName, String userRoleId, Integer pageSize, Integer startIndex) throws BusinessException {
 		logger.info("获取用户列表");
		StringBuffer sql = new StringBuffer("SELECT * FROM work_team WHERE 1=1");
		logger.info("获取用户列表的sql=" + sql);
		if(!"".equals("companyId") && userRoleId!=null && userRoleId!=""){
			int roleId=Integer.parseInt(userRoleId);
			sql.append(" AND companyId ="+roleId+"");
		}
		if (!"".equals("teamName") && WorkTeamName != null && WorkTeamName!="") {
			sql.append(" AND teamName LIKE '%"+WorkTeamName+"%'");
		}
		sql.append(" ORDER BY id DESC");
		return baseDaoImpl.queryForListPage(sql.toString(), pageSize, startIndex);

	}

	@Override
	public void removeUser(Integer id) {
		logger.info("删除id=" + id + "的用户");
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("workTeam.removeUser");
		jdbcTemplate.update(sql, new Object[] { id });
		DynamicContextChange.clearCustomerType();
	}

	@Override
	public int addUser(WorkTeam user) {
		logger.info("新增用户");
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("workTeam.addUser");
		// 插入菜单对象
		int result = insertSqlAndReturnKeyId(sql, new Object[] { user.getTeamName(),user.getTeamManager(),user.getMobile(),user.getCompanyId() });
		DynamicContextChange.clearCustomerType();
		return result;
	}

	@Override
	public WorkTeam findUserById(Integer id) {
		if (id == 0 || id == null) {
			return null;
		}
		WorkTeam user = null;
		try {
			logger.info("根据id查找用户");
			String sql = sqlMap("workTeam.findUserById");
			user = queryForObject(sql, new Object[] { id }, WorkTeam.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public int updateUser(WorkTeam user) {
		logger.info("更新用户" + user);
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("workTeam.updateUser");
		int result = jdbcTemplate.update(sql,
				new Object[] { user.getTeamName(),user.getTeamManager(),user.getMobile(),user.getCompanyId()});
		DynamicContextChange.clearCustomerType();
		return result;
	}

	@Override
	public WorkTeam getUserLogin(String userName) {
		if ("".equals(userName) || userName == null) {
			return null;
		}
		try {
			logger.info("根据用户名称查找用户");
			String sql = sqlMap("workTeam.getUserLogin");
			return jdbcTemplate.queryForObject(sql, new Object[]{userName},new BeanPropertyRowMapper<WorkTeam>(WorkTeam.class));
		} catch (DataAccessException e) {
			logger.error("Rresult data is empty");
			return null;
		}
	}

	@Override
	public int updateUserPassword(Integer partyId, String pass) {
		logger.info("修改密码的用户ID" + partyId);
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("WorkTeam.updateUserPassword");
		int result = jdbcTemplate.update(sql,new Object[] { pass,partyId});
		DynamicContextChange.clearCustomerType();
		return result;
	}
	@Override
	public List<Map<String, Object>> queryWorkCompanyList() {
		logger.info("获取所有公司");
		String sql = "select * from work_company";
		return jdbcTemplate.queryForList(sql);
	}
	@Override
	public List<Map<String, Object>> queryWorkTeamList(int companyId) {
		logger.info("获取所有公司班组");
		String sql = "select * from work_team WHERE  companyId = "+ companyId;
		return jdbcTemplate.queryForList(sql);
	}
	//查找所有的班组，不限公司
	@Override
	public List<Map<String, Object>> queryAllWorkTeam() {
		logger.info("查找所有的班组，不限公司");
		String sql="select * from work_team";
		return jdbcTemplate.queryForList(sql);
	}
}
