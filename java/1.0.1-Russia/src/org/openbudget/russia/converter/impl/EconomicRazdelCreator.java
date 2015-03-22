package org.openbudget.russia.converter.impl;

import java.util.ArrayList;

import org.openbudget.converter.OBFConverter;
import org.openbudget.converter.face.ModelsCreator;
import org.openbudget.exception.ConverterException;
import org.openbudget.exception.SourceTableException;
import org.openbudget.model.SourceTable;
import org.openbudget.russia.converter.OBFConverterRus;
import org.openbudget.russia.model.EconomicRazdel;
import org.openbudget.utils.ConverterUtils;

public class EconomicRazdelCreator implements ModelsCreator<EconomicRazdel> {

	protected ArrayList<EconomicRazdel> objects;

	@Override
	public ArrayList<EconomicRazdel> createModels(SourceTable table)
			throws ConverterException {
		ArrayList<EconomicRazdel> all = new ArrayList<EconomicRazdel>();

		boolean problem = false;
		boolean serious = false;

		for (int i = 0; i < table.rows(); i++) {

			if ((table.getCells()[i][3] == null || table.getCells()[i][3]
					.isEmpty())
					&& (table.getCells()[i][0] != null
							&& !table.getCells()[i][0].isEmpty()
							&& table.getCells()[i][1] != null
							&& !table.getCells()[i][1].isEmpty()
							&& table.getCells()[i][2] != null && !table
								.getCells()[i][2].isEmpty())) {

				EconomicRazdel razdel = new EconomicRazdel();
				razdel.setName(table.getCells()[i][0]);
				razdel.setCode(table.getCells()[i][2]);
				razdel.setSourceRowNumber(i);
				if (razdel.getCode().length() != 4) {

					if (razdel.getCode().length() > 4) {
						OBFConverter.log
								.postError(
										OBFConverterRus.text.RU_EN_CONVERTING_RAZDEL_SYMBOLS_SERIOUS,
										razdel.getCode(),
										razdel.getSourceRowNumber()+1);
						serious = true;
					} else {

						OBFConverter.log
								.postWarn(
										OBFConverterRus.text.RU_EN_CONVERTING_RAZDEL_SYMBOLS,
										razdel.getCode(),
										razdel.getSourceRowNumber()+1);
						problem = true;
						razdel.setRazdelCode("0"
								+ razdel.getCode().substring(0, 1));
						razdel.setPodRazdelCode(razdel.getCode()
								.substring(1, 3));
						razdel.setCode("0" + razdel.getCode());
					}
				} else {
					razdel.setRazdelCode(razdel.getCode().substring(0, 2));
					razdel.setPodRazdelCode(razdel.getCode().substring(2, 4));
				}
				// System.out.println(razdel.toString());
				all.add(razdel);
			}

		}

		// fix error with number of razdels (less then 4 symbols)
		if (problem) {
			for (int i = 0; i < table.rows(); i++) {
				if(table.getCells()[i][2]!=null && table.getCells()[i][2].length()==3){
					table.getCells()[i][2] = "0" + table.getCells()[i][2];
				}
			}
		}

		ArrayList<EconomicRazdel> mainRazdels = new ArrayList<EconomicRazdel>();

		// build list of main razdels
		for (EconomicRazdel razdel : all) {
			if (razdel.getPodRazdelCode() != null
					&& razdel.getPodRazdelCode().equals("00")) {
				mainRazdels.add(razdel);
			}
		}

		// update current razdels with name
		for (EconomicRazdel razdel : all) {
			if (razdel.getPodRazdelCode() != null
					&& !razdel.getPodRazdelCode().equals("00")) {
				for (EconomicRazdel r : mainRazdels) {
					if (r.getRazdelCode() != null
							&& r.getRazdelCode().equals(razdel.getRazdelCode())) {
						razdel.setRazdelName(r.getName());
						break;
					}
				}
			}
		}

		// remove only main razdels
		all.removeAll(mainRazdels);

		if (serious) {
			throw new SourceTableException(OBFConverter.text.SOURCETABLE_ERRORS);
		}
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
