package org.openbudget.russia.converter.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openbudget.converter.CSVSaver;
import org.openbudget.converter.OBFConverter;
import org.openbudget.converter.face.Saver;
import org.openbudget.russia.converter.OBFConverterRus;
import org.openbudget.russia.model.BudgetItemRus;
import org.openbudget.russia.model.MetaDataRus;
import org.openbudget.utils.ConverterUtils;

public class CSVSaverRus extends CSVSaver implements Saver<BudgetItemRus,MetaDataRus> {
 
	@Override
	public void save(ArrayList<BudgetItemRus> items, MetaDataRus metadata, String outputFileName) {
		
		outputFileName += ".obf.csv";
		
		try {
			
			createWriter(outputFileName);
			
			Map<String, String> row = new HashMap<String, String>();

			//create header
			row.put("Id", "id");
			row.put("Period", "Период");
			row.put("Amount", "Сумма");
			row.put("Region", "Регион");
			row.put("Version", "Версия");
			row.put("DocVersion", "ДокВерсия");
			row.put("Date", "Дата изменения");
			row.put("Type", "Тип");
			row.put("Stage", "Стадия");
			row.put("Level", "Уровень");
			row.put("Admin_GRBSName", "Наименование ГРБС");
			row.put("Admin_GRBSCode", "Код ГРБС");
			row.put("Classification_ArticleName", "Название целевой статьи расходов");
			row.put("Classification_ArticleCode", "Код целевой статьи расходов");
			row.put("Classification_EconomicRazdelName", "Название раздела");
			row.put("Classification_EconomicRazdelCode", "Код раздела");
			row.put("Classification_EconomicRazdelSubName", "Название подраздела");
			row.put("Classification_EconomicRazdelSubCode", "Код подраздела");
			row.put("Classification_EconomicRazdelFullCode", "Код раздела и подраздела полностью");
			row.put("Classification_SpendingTypeSubName", "Название вида расходов");
			row.put("Classification_SpendingTypeCode", "Код вида расходов");
			row.put("NationalId", "КБК");
			row.put("Comment", "Комментарий");
			
			writeRow(row);

			//write other strings
			for (BudgetItemRus item : items) {
				
				row.put("Id", item.getId());
				row.put("Period", ConverterUtils.createStringDate(item.getPeriod()));
				row.put("Amount", (item.getAmount()!=null) ? String.format("%.2f", item.getAmount()): "");
				row.put("Region", item.getRegion());
				row.put("Version", item.getVersion());
				row.put("DocVersion", item.getDocVersion());
				row.put("Date", ConverterUtils.createStringDate(item.getDate()));
				row.put("Type", item.getType());
				row.put("Stage", item.getStage());
				row.put("Level", item.getLevel());
				row.put("Admin_GRBSName", item.getGrbs().getName());
				row.put("Admin_GRBSCode", item.getGrbs().getCode());
				row.put("Classification_ArticleName", item.getArticle().getName());
				row.put("Classification_ArticleCode", item.getArticle().getCode());
				row.put("Classification_EconomicRazdelName", item.getRazdel().getRazdelName());
				row.put("Classification_EconomicRazdelCode", item.getRazdel().getRazdelCode());
				row.put("Classification_EconomicRazdelSubName", item.getRazdel().getName());
				row.put("Classification_EconomicRazdelSubCode", item.getRazdel().getPodRazdelCode());
				row.put("Classification_EconomicRazdelFullCode", item.getRazdel().getCode());
				row.put("Classification_SpendingTypeSubName", item.getSpendingType().getName());
				row.put("Classification_SpendingTypeCode", item.getSpendingType().getCode());
				row.put("NationalId", item.getKBK());
				row.put("Comment", item.getComment());

				writeRow(row);

			}

			finish();
			
			OBFConverter.log.postSuccess(OBFConverterRus.text.RU_EN_FINISHED, outputFileName);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

	}
}
