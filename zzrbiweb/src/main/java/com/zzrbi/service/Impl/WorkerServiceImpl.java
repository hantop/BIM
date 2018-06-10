package com.zzrbi.service.Impl;

import com.zzrbi.dao.BaseDaoImpl;
import com.zzrbi.entity.Worker;
import com.zzrbi.service.IWorkerService;
import com.zzrbi.util.BusinessException;
import com.zzrbi.util.DynamicContextChange;
import com.zzrbi.util.StringUtils;
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
public class WorkerServiceImpl extends BaseDaoImpl implements IWorkerService {
	/**
	 * 获取log
	 */
	private Logger logger = Logger.getLogger(WorkerServiceImpl.class);
	@Resource
	private BaseDaoImpl baseDaoImpl;
	//获得工作人员的列表，用于页面初始化时候显示和查询时候显示
	@Override
	public Map<String, Object> getWorkerList(String WorkerName, String userRoleId, Integer pageSize, Integer startIndex) throws BusinessException {
 		logger.info("获取用户列表");
		StringBuffer sql = new StringBuffer("SELECT * FROM Worker s LEFT JOIN (select wcr.workerId,wcr.cardTime from worker_card_record wcr group by wcr.workerId) a on a.workerId = s.id WHERE 1=1");
		logger.info("获取用户列表的sql=" + sql);
		if(!"".equals("teamId") && userRoleId!=null && userRoleId!=""){
			int roleId=Integer.parseInt(userRoleId);
			sql.append(" AND s.teamId ="+roleId+"");
		}
		if (!"".equals("realName") && WorkerName != null && WorkerName!="") {
			sql.append(" AND s.realName LIKE '%"+WorkerName+"%'");
		}
		sql.append(" ORDER BY s.id DESC");
		return baseDaoImpl.queryForListPage(sql.toString(), pageSize, startIndex);

	}
	//用于劳务人员角色的删除
	@Override
	public void removeUser(Integer id) {
		logger.info("删除id=" + id + "的用户");
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("worker.removeWorker");
		jdbcTemplate.update(sql, new Object[] { id });
		DynamicContextChange.clearCustomerType();
	}
	//劳务人员导入添加
	@Override
	public int addWorker(Worker worker) {
		logger.info("新增用户");
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("worker.addWorker");
		// 插入菜单对象
		int result = insertSqlAndReturnKeyId(sql,
				new Object[] {
						worker.getRealName(),
						worker.getSex(),
						worker.getAge(),
						worker.getNation(),
						worker.getIdCard(),
						worker.getContractStatus(),
						worker.getType(),
						worker.getStation(),
						worker.getDepartment(),
						worker.getTeamId()
		});
		DynamicContextChange.clearCustomerType();
		return result;
	}
	//根据id查找用户
	@Override
	public Worker findUserById(Integer id) {
		if (id == 0 || id == null) {
			return null;
		}
		Worker user = null;
		try {
			logger.info("根据id查找用户");
			String sql = sqlMap("Worker.findUserById");
			user = queryForObject(sql, new Object[] { id }, Worker.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	//更改用户
	@Override
	public int updateUser(Worker worker) {
		logger.info("更新用户" + worker);
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("worker.updateWorker");
		int result = jdbcTemplate.update(sql,
				new Object[] {
						worker.getRealName(),
						worker.getSex(),
						worker.getAge(),
						worker.getNation(),
						worker.getIdCard(),
						worker.getContractStatus(),
						worker.getType(),
						worker.getStation(),
						worker.getDepartment(),
						worker.getTeamId(),
						worker.getId()

				});
		DynamicContextChange.clearCustomerType();
		return result;
	}

	@Override
	public Worker getUserLogin(String userName) {
		if ("".equals(userName) || userName == null) {
			return null;
		}
		try {
			logger.info("根据用户名称查找用户");
			String sql = sqlMap("Worker.getUserLogin");
			return jdbcTemplate.queryForObject(sql, new Object[]{userName},new BeanPropertyRowMapper<Worker>(Worker.class));
		} catch (DataAccessException e) {
			logger.error("Rresult data is empty");
			return null;
		}
	}

	@Override
	public int updateUserPassword(Integer partyId, String pass) {
		logger.info("修改密码的用户ID" + partyId);
		DynamicContextChange.setDataSourceTypeMaster();
		String sql = sqlMap("Worker.updateUserPassword");
		int result = jdbcTemplate.update(sql,new Object[] { pass,partyId});
		DynamicContextChange.clearCustomerType();
		return result;
	}


}
