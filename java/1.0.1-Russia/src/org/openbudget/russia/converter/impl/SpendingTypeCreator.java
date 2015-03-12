package org.openbudget.russia.converter.impl;

import java.util.ArrayList;

import org.openbudget.converter.face.ModelsCreator;
import org.openbudget.exception.ConverterException;
import org.openbudget.model.SourceTable;
import org.openbudget.russia.model.Article;
import org.openbudget.russia.model.SpendingType;
import org.openbudget.utils.ConverterUtils;

public class SpendingTypeCreator implements ModelsCreator<SpendingType> {

	protected ArrayList<SpendingType> objects;

	@Override
	public ArrayList<SpendingType> createModels(SourceTable table)
			throws ConverterException {

		ArrayList<SpendingType> all = new ArrayList<SpendingType>();

		for (int i = 0; i < table.rows(); i++) {

			if (table.getCells()[i][0] != null && !table.getCells()[i][0].isEmpty()
					&& table.getCells()[i][1] != null && !table.getCells()[i][1].isEmpty()
							&& table.getCells()[i][2] != null && !table.getCells()[i][2].isEmpty()
									&& table.getCells()[i][3] != null && !table.getCells()[i][3].isEmpty()
											&& table.getCells()[i][4] != null && !table.getCells()[i][4].isEmpty() ) {

				SpendingType spending = new SpendingType();
				spending.setName(table.getCells()[i][0]);
				spending.setCode(ConverterUtils.trimAndRemoveSpaces(table
						.getCells()[i][4]));
				spending.setSourceRowNumber(i);

				all.add(spending);
			}

		}
		return all;

	}

	@Override
	public void saveModels(ArrayList<SpendingType> models)
			throws ConverterException {

		objects = ConverterUtils.createUniqueList(models);

	}

	@Override
	public ArrayList<SpendingType> getModels() throws ConverterException {
		// TODO Auto-generated method stub
		return objects;
	}

}
