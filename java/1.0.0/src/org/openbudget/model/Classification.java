package org.openbudget.model;

import org.openbudget.converter.OBFConverter;

/**
 * 
 * @author inxaoc
 *
 */
public class Classification extends Dimension{
	
	/**
	 * Human-readable name of element of classification 
	 */
	protected String name;
	
	/**
	 * Machine-readable code of element of classification
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
		return "["+OBFConverter.text.MODEL_NAME+"=" + name + ", "+OBFConverter.text.MODEL_CODE+"=" + code
				+ ", "+OBFConverter.text.MODEL_SOURCE_ROW_NUMBER+"=" + sourceRowNumber + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Classification other = (Classification) obj;
		if (code == null) {
			if (other.getCode() != null)
				return false;
		} else if (!code.equals(other.getCode()))
			return false;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		return true;
	}

}
