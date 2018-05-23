package com.zzrbi.service;


import java.util.Map;

import com.zzrbi.entity.Staff;
import com.zzrbi.util.BusinessException;




/**
 * 用户业务处理接口
 * @author yinlonglong
 * @date 2017年3月21日 下午3:24:32
 */
public interface IStaffService {
     /**
      * 获取用户列表
      * @param staffName
      * @param parseInt
      * @param pageSize
      * @param startIndex
      * @return
      */
	public Map<String,Object> getStaffList(String staffName, String userRoleId, Integer pageSize,Integer startIndex)throws BusinessException;
    /**
     * 删除用户
     * @param id
     */
	public void removeUser(Integer id);
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int addUser(Staff user);
	/**
	 * 根据id查找用户
	 * @param id
	 * @return
	 */
	public Staff findUserById(Integer id);
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int updateUser(Staff user);
	/**
	 * 根据用户名称查询用户
	 * @param userName
	 * @return
	 */
	public Staff getUserLogin(String userName);

	/**
	 * 根据用户名称查询用户
	 * @param partyId
	 * @param pass
	 * @return
	 */
    int updateUserPassword(Integer partyId, String pass);
}
