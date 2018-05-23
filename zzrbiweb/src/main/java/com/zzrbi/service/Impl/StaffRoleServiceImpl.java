package com.zzrbi.service.Impl;

import com.zzrbi.dao.BaseDaoImpl;
import com.zzrbi.entity.RoleDirectoryRel;
import com.zzrbi.entity.UserRole;
import com.zzrbi.service.IStaffRoleService;
import com.zzrbi.util.BusinessException;
import com.zzrbi.util.DynamicContextChange;
import org.apache.log4j.Logger;
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
public class StaffRoleServiceImpl extends BaseDaoImpl implements IStaffRoleService {
	/**
	 * 获取log
	 */
	private Logger logger = Logger.getLogger(StaffRoleServiceImpl.class);
	@Resource
	private BaseDaoImpl baseDaoImpl;

	@Override
	public Map<String, Object> getStaffRoleList(Map<String, Object> context, int pageSize, int startIndex)
			throws BusinessException {
		logger.info("获取角色列表");
		StringBuffer sql = new StringBuffer("SELECT u.id,u.roleName,u.remark FROM user_role u");
		logger.info("用户流量的sql=" + sql);
		if (!"".equals(context.get("roleName")) && context.get("roleName") != null) {
			// sql.append(" WHERE ur.roleName LIKE '%"+roleName+"%'");
			sql.append(" WHERE u.roleName LIKE '%").append(context.get("roleName")).append("%'");
		}
		sql.append(" ORDER BY u.id DESC");
		logger.info("用户流量的sql=" + sql.toString());
		return baseDaoImpl.queryForListPage(sql.toString(), pageSize, startIndex);
	}

	@Override
	public void removeUserRole(int roleId) {
		logger.info("删除id=" + roleId + "的角色");
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("staff.role.removeUserRole");
		jdbcTemplate.update(sql, new Object[] { roleId });
		DynamicContextChange.clearCustomerType();
	}

	@Override
	public int addUserRole(UserRole userRole) {
		logger.info("新增角色");
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("staff.role.addUserRole");
		// 插入菜单对象
		int result = insertSqlAndReturnKeyId(sql, new Object[] { userRole.getRoleName(), userRole.getRoleType(),
				userRole.getRemark(), userRole.getPlatformType() });
		DynamicContextChange.clearCustomerType();
		return result;
	}

	@Override
	public UserRole findUserRoleById(Integer id) {
		if (id == 0 || id == null) {
			return null;
		}
		UserRole userRole = null;
		try {
			String sql = sqlMap("staff.role.findUserRoleById");
			userRole = queryForObject(sql, new Object[] { id }, UserRole.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userRole;
	}

	@Override
	public int updateUserRole(UserRole userRole) {
		logger.info("更新角色" + userRole);
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("staff.role.updateUserRole");
		int result = jdbcTemplate.update(sql,
				new Object[] { userRole.getRoleName(), userRole.getRemark(), userRole.getId() });
		DynamicContextChange.clearCustomerType();
		return result;
	}

	@Override
	public void deleteRoleDirectoryRel(int id) {
		logger.info("删除id=" + id + "的角色的权限");
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("staff.role.deleteRoleDirectoryRel");
		// 删除数据
		jdbcTemplate.update(sql, new Object[] { id });
		DynamicContextChange.clearCustomerType();
	}

	@Override
	public int insertRoleDirectoryRel(RoleDirectoryRel record) {
		logger.info("新增角色对应的权限");
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("staff.role.insertRoleDirectoryRel");
		// 插入菜单对象
		int result = insertSqlAndReturnKeyId(sql, new Object[] { record.getDirectoryId(), record.getRoleId() });
		DynamicContextChange.clearCustomerType();
		return result;
	}

	@Override
	public List<Map<String, Object>> queryUserRoleList() {
		logger.info("获取所有角色");
		String sql = sqlMap("staff.role.queryUserRoleList");
		return jdbcTemplate.queryForList(sql);
	}

}
