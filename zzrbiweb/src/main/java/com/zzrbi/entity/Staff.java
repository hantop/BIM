package com.zzrbi.entity;

import java.io.Serializable;

/**
 * Staff的实体
 * @author yinlonglong
 * @date 2017年3月21日 下午2:54:44
 */
public class Staff implements Serializable{
	
	private static final long serialVersionUID = 8039758271286614416L;
	
	/**
	 * 员工id
	 */
	private Integer id;
	
	/**
	 * 用户角色id
	 */
	private Integer userRoleId;
	
	/**
	 * 用户uid
	 */
	private Integer userId;
	
	/**
	 * 密码
	 */
	private String passwd;
	
	/**
	 * 员工名称
	 */
	private String staffName;
	
	/**
	 * 0男  1女
	 */
	private Integer sex;
	
	/**
	 *电子邮件
	 */
	private String email;
	
	/**
	 * 移动电话
	 */
	private String mobile;
	
	/**
	 * 固定电话
	 */
	private String phone;
	
	/**
	 * 年龄
	 */
	private Integer age;
	
	/**
	 * 生日
	 */
	private String birthday;
	
	/**
	 * 身份证号
	 */
	private String idCard;
	
	/**
	 * QQ号码
	 */
	private String qq;
	
	/**
	 * 最低审批额度
	 */
	private Integer lowest;
	
	/**
	 * 最高审批额度
	 */
	private Integer highest;
	
	/**
	 * 当前初审任务数
	 */
	private Integer firstTaskNo;
	
	/**
	 * 当前复审任务数
	 */
	private Integer secondTaskNo;
	
	/**
	 * X.509 数字证书使用者密钥标识符
	 */
	private String x509SKeyId;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 平台类型 1:管理平台 2:商户平台
	 */
	private Integer platformType;
	
	/**
	 * 商户企业id
	 */
	private Integer companyID;
	
	/**
	 * 银客网账户创建方式  0:新建  1:关联
	 */
	private Integer createMethod;
	
	/**
	 * 创建步骤顺序号
	 */
	private Integer stepNO;
	
	/**
	 * 帐户状态 0:新建  1:生效
	 */
	private Integer status;
	
	/**
	 * 真实姓名
	 */
	private String realname;
	
	/**
	 * 
	 */
	private String companyKey;
    /**
     *  二维码
     */
	private String QRCode;
	/**
	 *  安全码
	 */
	private String securityCode;
	
	/**
	 * 员工id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 员工id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 用户角色id
	 */
	public Integer getUserRoleId() {
		return userRoleId;
	}
	/**
	 * 用户角色id
	 */
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	/**
	 * 用户uid
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 用户uid
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 密码
	 */
	public String getPasswd() {
		return passwd;
	}
	/**
	 * 密码
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	/**
	 * 员工名称
	 */
	public String getStaffName() {
		return staffName;
	}
	/**
	 * 员工名称
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	/**
	 * 0男  1女
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 0男  1女
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 *电子邮件
	 */
	public String getEmail() {
		return email;
	}
	/**
	 *电子邮件
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 移动电话
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 移动电话
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 固定电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 固定电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 年龄
	 */
	public Integer getAge() {
		return age;
	}
	/**
	 * 年龄
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * 生日
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * 生日
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * 身份证号
	 */
	public String getIdCard() {
		return idCard;
	}
	/**
	 * 身份证号
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	/**
	 * QQ号码
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * QQ号码
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * 最低审批额度
	 */
	public Integer getLowest() {
		return lowest;
	}
	/**
	 * 最低审批额度
	 */
	public void setLowest(Integer lowest) {
		this.lowest = lowest;
	}
	/**
	 * 最高审批额度
	 */
	public Integer getHighest() {
		return highest;
	}
	/**
	 * 最高审批额度
	 */
	public void setHighest(Integer highest) {
		this.highest = highest;
	}
	/**
	 * 当前初审任务数
	 */
	public Integer getFirstTaskNo() {
		return firstTaskNo;
	}
	/**
	 * 当前初审任务数
	 */
	public void setFirstTaskNo(Integer firstTaskNo) {
		this.firstTaskNo = firstTaskNo;
	}
	/**
	 * 当前复审任务数
	 */
	public Integer getSecondTaskNo() {
		return secondTaskNo;
	}
	/**
	 * 当前复审任务数
	 */
	public void setSecondTaskNo(Integer secondTaskNo) {
		this.secondTaskNo = secondTaskNo;
	}
	/**
	 * X.509 数字证书使用者密钥标识符
	 */
	public String getX509SKeyId() {
		return x509SKeyId;
	}
	/**
	 * X.509 数字证书使用者密钥标识符
	 */
	public void setX509SKeyId(String x509sKeyId) {
		x509SKeyId = x509sKeyId;
	}
	/**
	 * 备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 平台类型 1:管理平台 2:商户平台
	 */
	public Integer getPlatformType() {
		return platformType;
	}
	/**
	 * 平台类型 1:管理平台 2:商户平台
	 */
	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}
	/**
	 * 商户企业id
	 */
	public Integer getCompanyID() {
		return companyID;
	}
	/**
	 * 商户企业id
	 */
	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}
	/**
	 * 银客网账户创建方式  0:新建  1:关联
	 */
	public Integer getCreateMethod() {
		return createMethod;
	}
	/**
	 * 银客网账户创建方式  0:新建  1:关联
	 */
	public void setCreateMethod(Integer createMethod) {
		this.createMethod = createMethod;
	}
	/**
	 * 创建步骤顺序号
	 */
	public Integer getStepNO() {
		return stepNO;
	}
	/**
	 * 创建步骤顺序号
	 */
	public void setStepNO(Integer stepNO) {
		this.stepNO = stepNO;
	}
	/**
	 * 帐户状态 0:新建  1:生效
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 帐户状态 0:新建  1:生效
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 真实姓名
	 */
	public String getRealname() {
		return realname;
	}
	/**
	 * 真实姓名
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCompanyKey() {
		return companyKey;
	}

	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}

	public String getQRCode() {
		return QRCode;
	}

	public void setQRCode(String QRCode) {
		this.QRCode = QRCode;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
}
