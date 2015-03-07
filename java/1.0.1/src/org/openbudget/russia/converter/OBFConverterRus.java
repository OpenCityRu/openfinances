package org.openbudget.russia.converter;

import java.util.ArrayList;

import org.openbudget.converter.OBFConverter;
import org.openbudget.converter.face.ModelsCreator;
import org.openbudget.exception.BrokenBudgetItemConverterException;
import org.openbudget.exception.ConverterException;
import org.openbudget.model.Admin;
import org.openbudget.model.BudgetItem;
import org.openbudget.model.Classification;
import org.openbudget.russia.converter.impl.ArticleCreator;
import org.openbudget.russia.converter.impl.EconomicRazdelCreator;
import org.openbudget.russia.converter.impl.GRBSCreator;
import org.openbudget.russia.converter.impl.GlobalSettingsRus;
import org.openbudget.russia.converter.impl.SpendingTypeCreator;
import org.openbudget.russia.model.Article;
import org.openbudget.russia.model.BudgetItemRus;
import org.openbudget.russia.model.EconomicRazdel;
import org.openbudget.russia.model.GRBS;
import org.openbudget.russia.model.MetaDataRus;
import org.openbudget.russia.model.SpendingType;
import org.openbudget.utils.ConverterUtils;
import org.openbudget.utils.Log;

/**
 * Specific OBF converter for russian budgets.
 * 
 * @author inxaoc
 * 
 */
public class OBFConverterRus extends OBFConverter<BudgetItemRus, MetaDataRus> {

	protected Integer currentId = 1;
	protected String docVersion = "1";

	public OBFConverterRus() throws ConverterException {

		super(new GlobalSettingsRus());

	}

	/**
	 * 
	 */
	@Override
	public void setId(BudgetItemRus item) throws ConverterException {

		item.setId(Integer.toString(currentId));
		currentId++;

	}

	/**
	 * 
	 */
	@Override
	public void setVersions(BudgetItemRus item) throws ConverterException {

		item.setVersion(BudgetItem.VERSION.FINAL);
		item.setDocVersion(docVersion);

	}

	/**
	 * Use matrix (source table data) and modelsCreators (saved models from xls)
	 * 
	 * @throws ConverterException
	 */
	@Override
	protected ArrayList<BudgetItemRus> createBudgetItems()
			throws ConverterException {

		budgetItems = new ArrayList<BudgetItemRus>();

		for (int i = 0; i < matrix.rows(); i++) {

			if (matrix.getCells()[i][0] != null && !matrix.getCells()[i][0].isEmpty()
					&& matrix.getCells()[i][1] != null && !matrix.getCells()[i][1].isEmpty()
							&& matrix.getCells()[i][2] != null && !matrix.getCells()[i][2].isEmpty()
									&& matrix.getCells()[i][3] != null && !matrix.getCells()[i][3].isEmpty()
											&& matrix.getCells()[i][4] != null && !matrix.getCells()[i][4].isEmpty()
													&& matrix.getCells()[i][5] != null && !matrix.getCells()[i][5].isEmpty()) {

				BudgetItemRus item = new BudgetItemRus();
				// item.setName(matrix.getCells()[i][0]);

				item.setGrbsCode(matrix.getCells()[i][1]);
				item.setRazdelCode(matrix.getCells()[i][2]);
				item.setArticleCode(ConverterUtils.trimAndRemoveSpaces(matrix
						.getCells()[i][3]));
				item.setSpendingCode(matrix.getCells()[i][4]);

				try {
					item.setAmount(1000 * Double.valueOf(matrix.getCells()[i][5]));

					item.setSourceRowNumber(i);

					budgetItems.add(item);

				} catch (NumberFormatException e) {
					Log.postWarn("Row " + (i+1)
							+ " is identified as a not budget item and missed.");
					continue;
				}

			}

		}

		// update with data from models and remove not valuable items

		// fixit
		int[] mas = new int[matrix.rows()];
		ArrayList<BudgetItemRus> removeList = new ArrayList<BudgetItemRus>();

		int i = 0;
		for (BudgetItemRus item : budgetItems) {

			item.setArticle(findArticle(modelsCreators, item.getArticleCode()));
			if(item.getArticle()==null){
				Log.postWarn("Article with code \""+item.getArticleCode()+"\" not found in collection of Articles.");
			}
			item.setGrbs(findGRBS(modelsCreators, item.getGrbsCode()));
			if(item.getGrbs()==null){
				Log.postWarn("GRBS with code \""+item.getGrbsCode()+"\" not found in collection of GRBS.");
			}
			item.setRazdel(findRazdel(modelsCreators, item.getRazdelCode()));
			if(item.getRazdel()==null){
				Log.postWarn("Razdel with code \""+item.getRazdelCode()+"\" not found in collection of Razdels.");
			}
			item.setSpendingType(findSpendingType(modelsCreators,
					item.getSpendingCode()));
			if(item.getSpendingCode()==null){
				Log.postWarn("Spending Type with code \""+item.getSpendingCode()+"\" not found in collection of Spending types.");
			}

			try {

				item.setKBK(ConverterUtilsRus.generateKBK(item));

			} catch (BrokenBudgetItemConverterException e) {
				Log.postWarn(e.getMessage());
				removeList.add(item);
			}

			mas[i] = item.getSourceRowNumber();
			i++;

		}

		if (removeList.size() > 0) {
			budgetItems.removeAll(removeList);
		}

		return budgetItems;
	}

	@Override
	protected MetaDataRus createMetaData() throws ConverterException {

		metadata.setAuthorEmail(ConverterUtils.getValueByKey(
				settings.getParams(), "authorEmail"));
		metadata.setAuthorName(ConverterUtils.getValueByKey(
				settings.getParams(), "authorName"));
		metadata.setBudgetLawLink(ConverterUtils.getValueByKey(
				settings.getParams(), "budgetLawLink"));
		metadata.setBudgetLawName(ConverterUtils.getValueByKey(
				settings.getParams(), "budgetLawName"));
		metadata.setComment(ConverterUtils.getValueByKey(settings.getParams(),
				"comment"));
		metadata.setContactEmail(ConverterUtils.getValueByKey(
				settings.getParams(), "contactEmail"));
		metadata.setContactName(ConverterUtils.getValueByKey(
				settings.getParams(), "contactName"));
		metadata.setContactPhone(ConverterUtils.getValueByKey(
				settings.getParams(), "contactPhone"));
		metadata.setCountry(ConverterUtils.getValueByKey(settings.getParams(),
				"country"));
		metadata.setCountryCode(ConverterUtils.getValueByKey(
				settings.getParams(), "countryCode"));
		metadata.setCurrency(ConverterUtils.getValueByKey(settings.getParams(),
				"currency"));
		metadata.setDataDocLinks(ConverterUtils.getValueByKey(
				settings.getParams(), "dataDocLinks"));
		metadata.setDataFiles(ConverterUtils.getValueByKey(
				settings.getParams(), "dataFiles"));
		metadata.setDataSchemeLink(ConverterUtils.getValueByKey(
				settings.getParams(), "dataSchemeLink"));
		metadata.setDepartmentAddress(ConverterUtils.getValueByKey(
				settings.getParams(), "departmentAddress"));
		metadata.setDepartmentName(ConverterUtils.getValueByKey(
				settings.getParams(), "departmentName"));
		metadata.setDepartmentWeb(ConverterUtils.getValueByKey(
				settings.getParams(), "departmentWeb"));
		metadata.setLang(ConverterUtils.getValueByKey(settings.getParams(),
				"lang"));
		metadata.setMetaDataDocLink(ConverterUtils.getValueByKey(
				settings.getParams(), "metaDataDocLink"));
		metadata.setOriginalDocLink(ConverterUtils.getValueByKey(
				settings.getParams(), "originalDocLink"));
		metadata.setOriginalDocName(ConverterUtils.getValueByKey(
				settings.getParams(), "originalDocName"));
		metadata.setStructure(Admin.class.getName() + " > "
				+ GRBS.class.getName() + "; " + Classification.class.getName()
				+ " > " + SpendingType.class.getName() + ", "
				+ EconomicRazdel.class.getName() + ", "
				+ Article.class.getName());

		return metadata;
	}

	@Override
	protected void beforeReadFile() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beforeCreateSourceTable() throws ConverterException {

	}

	/**
	 * We use this method to clean repeated objects in Spending Type (because in
	 * comparison with other dimensions these objects are not fully unique in
	 * one budget file.
	 * 
	 * @throws ConverterException
	 */
	@Override
	protected void beforeCreateBudgetItems() throws ConverterException {
		
		
	}

	@Override
	protected void afterCreateBudgetItems() {

	}

	@Override
	protected void beforeSave() {
		// TODO Auto-generated method stub
	}

	private SpendingType findSpendingType(
			ArrayList<ModelsCreator> modelsCreators, String spendingCode)
			throws ConverterException {

		ArrayList<SpendingType> list = ConverterUtils.getModelsByType(
				modelsCreators, SpendingTypeCreator.class);
		SpendingType type = null;

		for (SpendingType r : list) {
			if (r.getCode().equals(spendingCode)) {
				if (type == null) {
					type = r;
				} else {
					Log.postWarn("More then 1 spending type with the same number found (row "
							+ (r.getSourceRowNumber() + 1) + ")");
				}
			}
		}
		return type;

	}

	private EconomicRazdel findRazdel(ArrayList<ModelsCreator> modelsCreators,
			String razdelCode) throws ConverterException {

		ArrayList<EconomicRazdel> list = ConverterUtils.getModelsByType(
				modelsCreators, EconomicRazdelCreator.class);
		EconomicRazdel razdel = null;

		for (EconomicRazdel r : list) {
			if (r.getCode().equals(razdelCode)) {
				if (razdel == null) {
					razdel = r;
				} else {
					Log.postWarn("More then 1 razdel with the same number found (row "
							+ (r.getSourceRowNumber() + 1) + ")");
				}
			}
		}
		return razdel;
	}

	private GRBS findGRBS(ArrayList<ModelsCreator> modelsCreators,
			String grbsCode) throws ConverterException {

		ArrayList<GRBS> list = ConverterUtils.getModelsByType(modelsCreators,
				GRBSCreator.class);
		GRBS grbs = null;

		for (GRBS g : list) {
			if (g.getCode().equals(grbsCode)) {
				if (grbs == null) {
					grbs = g;
				} else {
					Log.postWarn("More then 1 GRBS with the same number found (row "
							+ (g.getSourceRowNumber() + 1) + ")");
				}
			}
		}
		return grbs;
	}

	private Article findArticle(ArrayList<ModelsCreator> modelsCreators,
			String articleCode) throws ConverterException {

		ArrayList<Article> list = ConverterUtils.getModelsByType(
				modelsCreators, ArticleCreator.class);
		Article article = null;

		for (Article art : list) {
			if (art.getCode().equals(articleCode)) {
				if (article == null) {
					article = art;
				} else {
					Log.postWarn("More then 1 article with the same number found (row "
							+ (art.getSourceRowNumber() + 1) + ")");
				}
			}
		}
		return article;
	}

}
