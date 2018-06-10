package com.zzrbi.service;


import com.zzrbi.entity.Worker;
import com.zzrbi.util.BusinessException;

import java.util.Map;


/**
 * 用户业务处理接口
 * @author yinlonglong
 * @date 2017年3月21日 下午3:24:32
 */
public interface IWorkerService {
     /**
      * 获取用户列表
      * @param WorkerName
      * @param parseInt
      * @param pageSize
      * @param startIndex
      * @return
      */
	public Map<String,Object> getWorkerList(String WorkerName, String userRoleId, Integer pageSize, Integer startIndex)throws BusinessException;
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
    public int addWorker(Worker user);

    /**
     * 根据id查找用户
     * @param id
     * @return
	 */
	public Worker findUserById(Integer id);
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int updateUser(Worker user);
	/**
	 * 根据用户名称查询用户
	 * @param userName
	 * @return
	 */
	public Worker getUserLogin(String userName);

	/**
	 * 根据用户名称查询用户
	 * @param partyId
	 * @param pass
	 * @return
	 */
    int updateUserPassword(Integer partyId, String pass);
}
