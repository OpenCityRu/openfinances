package org.openbudget.russia.converter;

import java.util.ArrayList;

import org.openbudget.converter.face.ModelsCreator;
import org.openbudget.exception.BrokenBudgetItemConverterException;
import org.openbudget.exception.ConverterException;
import org.openbudget.exception.StandardConverterException;
import org.openbudget.model.Dimension;
import org.openbudget.russia.model.Article;
import org.openbudget.russia.model.BudgetItemRus;

public class ConverterUtilsRus {
	
	public static String generateKBK(BudgetItemRus item) throws BrokenBudgetItemConverterException {
		
		if (item.getGrbs() == null || item.getArticle() == null
				|| item.getRazdel() == null) {
			throw new BrokenBudgetItemConverterException(item);
		}
		return item.getGrbs().getCode() + item.getRazdel().getCode()
				+ item.getArticle().getCode() + item.getSpendingType().getCode();
		
	}

	public static <T> ArrayList<T> getModelsByType(
			ArrayList<ModelsCreator> modelsCreators,
			Class clazz) throws ConverterException {
		
		for(ModelsCreator model:modelsCreators){
			if(clazz.isInstance(model)){
				return model.getModels();
			}
		}
		
		throw new StandardConverterException("Unknown Model Type");
	}

}
