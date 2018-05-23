package com.zzrbi.util;

/**
 * 业务异常类
 * 
 * @author zx
 * 
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private String errorId;

	private String message;

	public BusinessException(String errorId) {
		this.errorId = errorId;
	}

	public BusinessException(String errorId, String message) {
		super(message);
		this.errorId = errorId;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
}
