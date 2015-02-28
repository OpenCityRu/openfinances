package org.openbudget.russia.converter.impl;

import java.util.ArrayList;

import org.openbudget.converter.face.ModelsCreator;
import org.openbudget.exception.ConverterException;
import org.openbudget.model.SourceTable;
import org.openbudget.russia.model.GRBS;

public class GRBSCreator implements ModelsCreator<GRBS> {
	
	protected ArrayList<GRBS> objects;

	@Override
	public ArrayList<GRBS> createModels(SourceTable table) throws ConverterException{
		
		ArrayList<GRBS> all = new ArrayList<GRBS>();

		for (int i = 0; i < table.rows(); i++) {

			if ((table.getCells()[i][2] == null || table.getCells()[i][2]
					.isEmpty())
					&& (!table.getCells()[i][0].isEmpty()
							&& table.getCells()[i][0] != null
							&& !table.getCells()[i][1].isEmpty() && table
							.getCells()[i][1] != null)) {
				GRBS grbs = new GRBS();
				grbs.setName(table.getCells()[i][0]);
				grbs.setCode(table.getCells()[i][1]);
				grbs.setSourceRowNumber(i);
				all.add(grbs);
			}

		}
		return all;
	}

	@Override
	public void saveModels(ArrayList<GRBS> models) throws ConverterException {
		
		objects = models;
		
	}

	@Override
	public ArrayList<GRBS> getModels() throws ConverterException {
		// TODO Auto-generated method stub
		return objects;
	}

}
