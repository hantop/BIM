package com.zzrbi.service.Impl;

import com.zzrbi.dao.BaseDaoImpl;
import com.zzrbi.entity.Staff;
import com.zzrbi.service.IStaffService;
import com.zzrbi.util.BusinessException;
import com.zzrbi.util.DynamicContextChange;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户业务处理实现类
 * 
 * @author sihang
 *
 */
@Service
public class StaffServiceImpl extends BaseDaoImpl implements IStaffService {
	/**
	 * 获取log
	 */
	private Logger logger = Logger.getLogger(StaffServiceImpl.class);
	@Resource
	private BaseDaoImpl baseDaoImpl;

	@Override
	public Map<String, Object> getStaffList(String staffName, String userRoleId, Integer pageSize, Integer startIndex) throws BusinessException {
 		logger.info("获取用户列表");
		StringBuffer sql = new StringBuffer("SELECT s.id,s.staffName,s.userRoleId,ur.roleName,s.mobile,s.email FROM staff s LEFT JOIN user_role ur ON s.userRoleId=ur.id WHERE 1=1");
		logger.info("获取用户列表的sql=" + sql);
		if(!"".equals("userRoleId") && userRoleId!=null && userRoleId!=""){
			int roleId=Integer.parseInt(userRoleId);
			sql.append(" AND s.userRoleId ="+roleId+"");
		}
		if (!"".equals("staffName") && staffName != null && staffName!="") {			
			sql.append(" AND s.staffName LIKE '%"+staffName+"%'");			
		}
		sql.append(" ORDER BY s.id DESC");
		return baseDaoImpl.queryForListPage(sql.toString(), pageSize, startIndex);

	}

	@Override
	public void removeUser(Integer id) {
		logger.info("删除id=" + id + "的用户");
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("staff.removeUser");
		jdbcTemplate.update(sql, new Object[] { id });
		DynamicContextChange.clearCustomerType();
	}

	@Override
	public int addUser(Staff user) {
		logger.info("新增用户");
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("staff.addUser");
		// 插入菜单对象
		int result = insertSqlAndReturnKeyId(sql, new Object[] { user.getUserRoleId(),user.getPasswd(),user.getStaffName(),user.getEmail(),user.getMobile(),user.getStatus(),user.getQRCode(),user.getSecurityCode() });
		DynamicContextChange.clearCustomerType();
		return result;
	}

	@Override
	public Staff findUserById(Integer id) {
		if (id == 0 || id == null) {
			return null;
		}
		Staff user = null;
		try {
			logger.info("根据id查找用户");
			String sql = sqlMap("staff.findUserById");
			user = queryForObject(sql, new Object[] { id }, Staff.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public int updateUser(Staff user) {
		logger.info("更新用户" + user);
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("staff.updateUser");
		int result = jdbcTemplate.update(sql,
				new Object[] { user.getUserRoleId(),user.getPasswd(),user.getStaffName(),user.getEmail(),user.getMobile(), user.getId() });
		DynamicContextChange.clearCustomerType();
		return result;
	}

	@Override
	public Staff getUserLogin(String userName) {
		if ("".equals(userName) || userName == null) {
			return null;
		}
		try {
			logger.info("根据用户名称查找用户");
			String sql = sqlMap("staff.getUserLogin");
			return jdbcTemplate.queryForObject(sql, new Object[]{userName},new BeanPropertyRowMapper<Staff>(Staff.class));
		} catch (DataAccessException e) {
			logger.error("Rresult data is empty");
			return null;
		}
	}

	@Override
	public int updateUserPassword(Integer partyId, String pass) {
		logger.info("修改密码的用户ID" + partyId);
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("staff.updateUserPassword");
		int result = jdbcTemplate.update(sql,new Object[] { pass,partyId});
		DynamicContextChange.clearCustomerType();
		return result;
	}


}
