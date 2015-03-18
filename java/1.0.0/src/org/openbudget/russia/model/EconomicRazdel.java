package org.openbudget.russia.model;

import org.openbudget.model.Classification;

/**
 * Name is a subrazdelName.
 * Code is a full code.
 * @author inxaoc
 *
 */
public class EconomicRazdel extends Classification{
	
	protected String razdelCode;
	protected String subRazdelCode;
	protected String razdelName;
	
	/**
	 * Code of razdel only (2 digits)
	 * @return
	 */
	public String getRazdelCode() {
		return razdelCode;
	}
	public void setRazdelCode(String razdelCode) {
		this.razdelCode = razdelCode;
	}
	
	/**
	 * Code of sub razdel only (2 digits)
	 * @return
	 */
	public String getPodRazdelCode() {
		return subRazdelCode;
	}
	public void setPodRazdelCode(String subRazdelCode) {
		this.subRazdelCode = subRazdelCode;
	}
	
	/**
	 * Name of main razdel
	 * @return
	 */
	public String getRazdelName() {
		return razdelName;
	}
	public void setRazdelName(String razdelName) {
		this.razdelName = razdelName;
	}
	
	@Override
	public String toString() {
		return "Razdel "+super.toString();
	}
}
