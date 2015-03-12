package org.openbudget.russia.model;

import java.util.ArrayList;
import java.util.List;

import org.openbudget.converter.OBFConverter;
import org.openbudget.model.Admin;
import org.openbudget.model.BudgetItem;
import org.openbudget.model.Classification;
import org.openbudget.russia.converter.OBFConverterRus;

public class BudgetItemRus extends BudgetItem {
	
	protected String KBK;
	protected GRBS grbs;
	protected Article article;
	protected EconomicRazdel razdel;
	protected SpendingType spendingType;
	
	protected String grbsCode;
	protected String razdelCode;
	protected String articleCode;
	protected String spendingCode;
	
	/**
	 * Child classes could add own values for type, level or stage by adding new subclass of inner classes Level, Type, Stage (with "_").
	 * Constants must be overrided also. 
	 * For example you can use <code>new BudgetItemRus().LEVEL.FEDERAL_CITY</code>. Level variable in this case is an instance of
	 * new class "BudgetItemRus.Level_" which is parent of "BudgetItem.Level".
	 */
	public final static Level_ LEVEL = new Level_();
	
	/**
	 * For adding new values create new inner subclass from original inner class of Type, Level or Stage.
	 * @author inxaoc
	 *
	 */
	public static class Level_ extends Level {
		public static String FEDERAL_CITY;
	}

	@Override
	public List<Admin> getAdmins() {
		ArrayList<Admin> list = new ArrayList<Admin>();
		list.add(grbs);
		return list;
	}

	@Override
	public List<Classification> getClassifications() {
		ArrayList<Classification> list = new ArrayList<Classification>();
		list.add(article);
		list.add(spendingType);
		list.add(razdel);
		return list;
	}

	public String getKBK() {
		return KBK;
	}

	public void setKBK(String kBK) {
		KBK = kBK;
	}

	public GRBS getGrbs() {
		return grbs;
	}

	public void setGrbs(GRBS grbs) {
		this.grbs = grbs;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public EconomicRazdel getRazdel() {
		return razdel;
	}

	public void setRazdel(EconomicRazdel razdel) {
		this.razdel = razdel;
	}

	public SpendingType getSpendingType() {
		return spendingType;
	}

	public void setSpendingType(SpendingType spendingType) {
		this.spendingType = spendingType;
	}

	public String getGrbsCode() {
		return grbsCode;
	}

	public void setGrbsCode(String grbsCode) {
		this.grbsCode = grbsCode;
	}

	public String getRazdelCode() {
		return razdelCode;
	}

	public void setRazdelCode(String razdelCode) {
		this.razdelCode = razdelCode;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}
	
	public String getSpendingCode() {
		return spendingCode;
	}

	public void setSpendingCode(String spendingCode) {
		this.spendingCode = spendingCode;
	}

	@Override
	public String toString() {
		return "BudgetItemRus ["+OBFConverter.text.MODEL_SOURCE_ROW_NUMBER+"="+(sourceRowNumber+1)+": "+OBFConverterRus.text.RU_EN_GRBS+"=" + grbsCode
				+ ", "+OBFConverterRus.text.RU_EN_RAZDEL+"=" + razdelCode + ", "+OBFConverterRus.text.RU_EN_ARTICLE+"=" + articleCode
				+ ", "+OBFConverterRus.text.RU_EN_SPENDINGTYPE+"=" + spendingType + ", "+OBFConverterRus.text.RU_EN_AMOUNT+"=" + super.getAmount() + "]";
	}

}
