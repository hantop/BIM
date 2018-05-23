package com.zzrbi.service;

import java.util.List;
import java.util.Map;

import com.zzrbi.entity.RoleDirectoryRel;
import com.zzrbi.entity.UserRole;
import com.zzrbi.util.BusinessException;

/**
 * 角色业务处理接口
 * 
 * @author yinlonglong
 * @date 2017年3月21日 下午3:24:32
 */
public interface IStaffRoleService {
	/**
	 * 查询角色列表
	 * 
	 * @param roleName
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public Map<String, Object> getStaffRoleList(Map<String, Object> context, int pageSize, int startIndex)
			throws BusinessException;

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 */
	public void removeUserRole(int roleId);

	/**
	 * 新增角色
	 * 
	 * @param userRole
	 * @return
	 */
	public int addUserRole(UserRole userRole);

	/**
	 * 新增角色对应的权限
	 * 
	 * @param record
	 */
	public int insertRoleDirectoryRel(RoleDirectoryRel record);

	/**
	 * 根据id查找角色
	 * 
	 * @param id
	 * @return
	 */
	public UserRole findUserRoleById(Integer id);

	/**
	 * 修改角色
	 * 
	 * @param userRole
	 * @return
	 */
	public int updateUserRole(UserRole userRole);

	/**
	 * 删除角色对应的权限
	 * 
	 * @param parseInt
	 */
	public void deleteRoleDirectoryRel(int parseInt);

	/**
	 * 查询所有的角色
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryUserRoleList();

}
