package org.openbudget.model;

public class MetaData {
	
	/**
	 * Use special international codes of currencies
	 */
	protected String currency;
	/**
	 * name of country
	 */
	protected String country;
	/**
	 * Use international standards
	 */
	protected String countryCode;
	/**
	 * if several languages it should be specified additionally
	 */
	protected String lang;
	/**
	 * full official name of budget (for example Accepted Budget by Act of Goverment - Number-Date)
	 */
	protected String originalDocName;
	/**
	 * link to officially published source (budget) file
	 */
	protected String originalDocLink;
	/**
	 * who is responsible for budget, e.g. Ministry of Finance
	 */
	protected String departmentName;
	protected String departmentAddress;
	protected String departmentWeb;
	protected String contactName;
	protected String contactEmail;
	protected String contactPhone;
	/**
	 * link to current for this document law about budget (e.g. in Russia Budget Codex)
	 */
	protected String budgetLawLink;
	/**
	 * name of budget law in a language of country (e.g. in Russia: “Бюджетный кодекс”
	 */
	protected String budgetLawName;
	protected String comment;
	
	/**
	 * name of all data-files (with extensions name.csv, name.json,...)
	 */
	protected String dataFiles;
	/**
	 * link to meta-data file
	 */
	protected String metaDataDocLink;
	/**
	 * all data-files directly
	 */
	protected String dataDocLinks;
	/**
	 * link of published data scheme for current country
	 */
	protected String dataSchemeLink;
	/**
	 * creator of this file
	 */
	protected String authorName;
	protected String authorEmail;
	
	protected String structure;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getOriginalDocName() {
		return originalDocName;
	}

	public void setOriginalDocName(String originalDocName) {
		this.originalDocName = originalDocName;
	}

	public String getOriginalDocLink() {
		return originalDocLink;
	}

	public void setOriginalDocLink(String originalDocLink) {
		this.originalDocLink = originalDocLink;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentAddress() {
		return departmentAddress;
	}

	public void setDepartmentAddress(String departmentAddress) {
		this.departmentAddress = departmentAddress;
	}

	public String getDepartmentWeb() {
		return departmentWeb;
	}

	public void setDepartmentWeb(String departmentWeb) {
		this.departmentWeb = departmentWeb;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getBudgetLawLink() {
		return budgetLawLink;
	}

	public void setBudgetLawLink(String budgetLawLink) {
		this.budgetLawLink = budgetLawLink;
	}

	public String getBudgetLawName() {
		return budgetLawName;
	}

	public void setBudgetLawName(String budgetLawName) {
		this.budgetLawName = budgetLawName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDataFiles() {
		return dataFiles;
	}

	public void setDataFiles(String dataFiles) {
		this.dataFiles = dataFiles;
	}

	public String getMetaDataDocLink() {
		return metaDataDocLink;
	}

	public void setMetaDataDocLink(String metaDataDocLink) {
		this.metaDataDocLink = metaDataDocLink;
	}

	public String getDataDocLinks() {
		return dataDocLinks;
	}

	public void setDataDocLinks(String dataDocLinks) {
		this.dataDocLinks = dataDocLinks;
	}

	public String getDataSchemeLink() {
		return dataSchemeLink;
	}

	public void setDataSchemeLink(String dataSchemeLink) {
		this.dataSchemeLink = dataSchemeLink;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}
	
	

}
