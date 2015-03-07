package org.openbudget.russia.converter.impl;

import java.util.ArrayList;

import org.openbudget.converter.face.ModelsCreator;
import org.openbudget.exception.ConverterException;
import org.openbudget.model.SourceTable;
import org.openbudget.russia.model.EconomicRazdel;
import org.openbudget.utils.ConverterUtils;
import org.openbudget.utils.Log;

public class EconomicRazdelCreator implements ModelsCreator<EconomicRazdel> {

	protected ArrayList<EconomicRazdel> objects;
	
	@Override
	public ArrayList<EconomicRazdel> createModels(SourceTable table) throws ConverterException{
		ArrayList<EconomicRazdel> all = new ArrayList<EconomicRazdel>();

		for (int i = 0; i < table.rows(); i++) {

			if ((table.getCells()[i][3] == null || table.getCells()[i][3]
					.isEmpty())
					&& (table.getCells()[i][0] != null && !table.getCells()[i][0].isEmpty()
							&& table.getCells()[i][1] != null && !table.getCells()[i][1].isEmpty()
									&& table.getCells()[i][2] != null && !table.getCells()[i][2].isEmpty())) {

				EconomicRazdel razdel = new EconomicRazdel();
				razdel.setName(table.getCells()[i][0]);
				razdel.setCode(table.getCells()[i][2]);
				razdel.setSourceRowNumber(i);
				if (razdel.getCode().length() != 4) {
					Log.postWarn("Razdel is less then 4 symbols");
				} else {
					razdel.setRazdelCode(razdel.getCode().substring(0, 2));
					razdel.setPodRazdelCode(razdel.getCode().substring(2, 4));
				}
				// System.out.println(razdel.toString());
				all.add(razdel);
			}

		}
		
		ArrayList<EconomicRazdel> mainRazdels = new ArrayList<EconomicRazdel>();
		
		//build list of main razdels
		for(EconomicRazdel razdel:all){
			if(razdel.getPodRazdelCode().equals("00")){
				mainRazdels.add(razdel);
			}
		}
		
		//update current razdels with name
		for(EconomicRazdel razdel:all){
			if(!razdel.getPodRazdelCode().equals("00")){
				for(EconomicRazdel r:mainRazdels){
					if(r.getRazdelCode().equals(razdel.getRazdelCode())){
						razdel.setRazdelName(r.getName());
						break;
					}
				}
			}
		}
		
		//remove only main razdels
		all.removeAll(mainRazdels);
		
		return all;
	}

	@Override
	public void saveModels(ArrayList<EconomicRazdel> models)
			throws ConverterException {
		
		objects = ConverterUtils.createUniqueList(models);
		
	}

	@Override
	public ArrayList<EconomicRazdel> getModels() throws ConverterException {
		// TODO Auto-generated method stub
		return objects;
	}

}
