package com.shortener.exception;


public class ErrorResponse {
	
	private String alias;
	private String errorCode;
	private String description;
	
	public ErrorResponse(String alias, String errorCode, String description) {
		super();
		this.alias = alias;
		this.errorCode = errorCode;
		this.description = description;
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
