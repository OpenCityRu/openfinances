package org.openbudget.russia.converter.impl;

import java.util.ArrayList;

import org.openbudget.converter.face.ModelsCreator;
import org.openbudget.exception.ConverterException;
import org.openbudget.model.SourceTable;
import org.openbudget.russia.model.Article;
import org.openbudget.utils.ConverterUtils;

public class ArticleCreator implements ModelsCreator<Article> {
	
	protected ArrayList<Article> objects;

	@Override
	public ArrayList<Article> createModels(SourceTable table) throws ConverterException {
		
		ArrayList<Article> all = new ArrayList<Article>();

		for (int i = 0; i < table.rows(); i++) {

			if ((table.getCells()[i][4] == null || table.getCells()[i][4]
					.isEmpty())
					&& (table.getCells()[i][0] != null && !table.getCells()[i][0].isEmpty()
							&& table.getCells()[i][1] != null && !table.getCells()[i][1].isEmpty()
									&& table.getCells()[i][2] != null && !table.getCells()[i][2].isEmpty()
											&& table.getCells()[i][3] != null && !table.getCells()[i][3].isEmpty() )) {

				Article article = new Article();
				article.setName(table.getCells()[i][0]);
				article.setCode(ConverterUtils.trimAndRemoveSpaces(table
						.getCells()[i][3]));
				article.setSourceRowNumber(i);

				all.add(article);
			}

		}
		return all;
		
	}

	@Override
	public void saveModels(ArrayList<Article> models) throws ConverterException {
		
		objects = ConverterUtils.createUniqueList(models);
		
	}

	@Override
	public ArrayList<Article> getModels() throws ConverterException {
		return objects;
	}

}
