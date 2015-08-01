package org.openbudget.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.openbudget.converter.OBFConverter;

/**
 * This is absctract class and could not be used directly. Use only specific subclasses.
 * Each subclass must define own Admins and Classifications.
 * @author inxaoc
 *
 */
abstract public class BudgetItem implements Serializable{
	
	/**
	 * constants to define type, level, stage securely.
	 */
	public final static Type TYPE = new Type(); 
	public final static Level LEVEL = new Level();
	public final static Stage STAGE = new Stage(); 
	public final static Version VERSION = new Version(); 
	
	/**
	 * Versions
	 * @author inxaoc
	 *
	 */
	public static class Version {
		public final static String FINAL="final";
	}
	
	/**
	 * Types
	 * @author inxaoc
	 *
	 */
	public static class Type {
		public final static String EXPENDITURE="E";
		public final static String INCOME="I";
		public final static String CONTRACT="C";
		public final static String REPORT="R";
	}
	
	/**
	 * Levels
	 * @author inxaoc
	 *
	 */
	public static class Level {
		public final static String NATIONAL="N";
		public final static String REGION="R";
		public final static String MUNICIPALITY="M";
	}

	/**
	 * Stages
	 * @author inxaoc
	 *
	 */
	public static class Stage {
		public final static String PREBUDGET="PB";
		public final static String PROPOSAL="PR";
		public final static String ENACTED="EN";
		public final static String CITIZEN="CI";
		/**
		 * For monthly report also number of month (1-12) must be added, e.g. M1 for January, M12 for december 
		 */
		public final static String REPORT_MONTHLY="M";
		/**
		 * For quarter report also number of quarter (1-4) must be added, e.g. Q1, Q2, Q3, Q4. 
		 */
		public final static String REPORT_QUARTER="Q";
		public final static String REPORT_FULL="FR";
		public final static String AUDIT="AR";
	}
	
	/**
	 * Unique id in current file. Format of each id could be specified for each country.
	 */
	protected String id;
	
	/**
	 * It accept year, month-year, day-month-year. As Date class it anyway will save time. But generally it doesn't matter.
	 * If all dates in document is "01-01-2015" it means that only Year is valuable (2015 will be exported in output file).
	 * If dates in document have format "01-01-2015","01-02-2015".."01-12-2015" (always first day of month) it means that Year and month are valuable (01-2015, 02-2015 will be exported in output file).
	 * If there are different days in document (not only 1st day of each month) it means that full date is valuable (31-01-2015, 25-02-2015 will be exported in output file).
	 */
	protected Date period;
	protected Double amount;
	protected String region;
	protected String version;
	protected String docVersion;
	protected Date date;
	protected String type;
	protected String level;
	protected String stage;
	protected String comment;
	
	protected Integer sourceRowNumber;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getPeriod() {
		return period;
	}
	public void setPeriod(Date period) {
		this.period = period;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	/**
	 * return all admins 
	 * @return
	 */
	abstract public List<Admin> getAdmins();
	
	/**
	 * At least 1 Classification must be specified in subclass. Also define concrete class (don't use Classification class).
	 * @return
	 */
	abstract public List<Classification> getClassifications();
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDocVersion() {
		return docVersion;
	}
	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Integer getSourceRowNumber() {
		return sourceRowNumber;
	}
	public void setSourceRowNumber(Integer sourceRowNumber) {
		this.sourceRowNumber = sourceRowNumber;
	}
	@Override
	public String toString() {
		return "BudgetItem [id=" + id + ", period=" + period + ", amount="
				+ amount + ", region=" + region + ", date=" + date + ", type="
				+ type + ", level=" + level + ", stage=" + stage
				+ ", "+OBFConverter.text.MODEL_SOURCE_ROW_NUMBER+"=" + sourceRowNumber + "]";
	}
	

	
}
