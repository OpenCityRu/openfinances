package org.openbudget.model;

public class Admin extends Dimension{
	
	/**
	 * Human-readable name of administrator 
	 */
	protected String name;
	
	/**
	 * Machine-readable code of administrator
	 */
	protected String code;
	
	/**
	 * Number of row where data has been got, need for checking
	 */
	protected Integer sourceRowNumber;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getSourceRowNumber() {
		return sourceRowNumber;
	}
	public void setSourceRowNumber(Integer sourceRowNumber) {
		this.sourceRowNumber = sourceRowNumber;
	}
	
	@Override
	public String toString() {
		return "[name=" + name + ", code=" + code
				+ ", sourceRowNumber=" + sourceRowNumber + "]";
	}

}
