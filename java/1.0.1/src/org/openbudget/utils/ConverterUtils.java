package org.openbudget.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openbudget.converter.OBFConverter;
import org.openbudget.converter.face.ModelsCreator;
import org.openbudget.exception.ConverterException;
import org.openbudget.exception.InputSettingsException;
import org.openbudget.exception.StandardConverterException;
import org.openbudget.model.SourceTable;

public class ConverterUtils {

	public static boolean checkAvailbaleFormatType(String type)
			throws ConverterException {
		String[] types = OBFConverter.getGlobalSettings()
				.getListAvailableFormatTypes();
		for (String s : types) {
			if (s.equals(type)) {
				return true;
			}
		}
		return false;
	}

	public static String normalizeUpperCaseString(String upperCasedString) {
		if (upperCasedString == null) {
			return "";
		}
		if (upperCasedString.length() < 2) {
			return upperCasedString.toLowerCase();
		}
		upperCasedString = upperCasedString.trim();
		return upperCasedString.substring(0, 1)
				+ upperCasedString.substring(1, upperCasedString.length())
						.toLowerCase();
	}

	public static String trimAndRemoveSpaces(String str) {
		if (str == null) {
			return "";
		}

		str = str.trim();
		StringBuffer newStr = new StringBuffer();
		String[] mas = str.split(" ");
		for (String s : mas) {
			newStr.append(s);
		}
		return newStr.toString();
	}

	public static String createStringDate(Date period) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(period);
		// int year = cal.get(Calendar.YEAR);
		// int month = cal.get(Calendar.MONTH);
		// int day = cal.get(Calendar.DAY_OF_MONTH);
		// return year+"-"+month+"-"+day;
		return cal.get(Calendar.YEAR) + "";

	}

	public static String getValueByKey(String[][] settings, String key)
			throws InputSettingsException {

		for (String[] s : settings) {
			if (s[0].toLowerCase().equals(key)) {
				return s[1];
			}
		}
		OBFConverter.log.postError(OBFConverter.text.SETTING_NOT_FOUND,key);
		return "";
		// throw new InputSettingsException(true,
		// "Setting \""+key+"\" is not found");
	}

	public static Date getDateFromPeriod(String date)
			throws InputSettingsException {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			throw new InputSettingsException(true, OBFConverter.text.UNKNOWN_DATE);
		}
	}

	public static List<String[]> findRowByParams(SourceTable table, int index,
			String value) {

		List<String[]> list = new ArrayList<String[]>();

		for (int i = 0; i < table.rows(); i++) {

			if (table.getCells()[i][index] == null) {

			} else if (table.getCells()[i][index].equals(value)) {

			}

		}
		return list;

	}

	// public static <T> ArrayList<T> getModelsByType(
	// ArrayList<ModelsCreator> models, T emptyInstance) throws
	// ConverterException {
	//
	// for (ModelsCreator model: models){
	// if(model.getModels() != null && model.getModels().size()!=0){
	// if(model.getModels().get(0).getClass().isInstance(emptyInstance)){
	// return model.getModels();
	// }
	// }
	// }
	//
	// throw new StandardConverterException("Unknown creator model.");
	// }

	public static <T> ArrayList<T> getModelsByType(
			ArrayList<ModelsCreator> modelsCreators, Class clazz)
			throws ConverterException {

		for (ModelsCreator model : modelsCreators) {
			if (clazz.isInstance(model)) {
				return model.getModels();
			}
		}

		throw new StandardConverterException(OBFConverter.text.UNKNOWN_MODEL);
	}

	public static <T> ArrayList<T> createUniqueList(ArrayList<T> originalListObj) {

		ArrayList<T> newList = new ArrayList<T>();

		for (T obj : originalListObj) {

			if (!newList.contains(obj)) {
				newList.add(obj);
			}

		}

		return newList;
	}

	public static <T> T getModelsCreator(ArrayList<ModelsCreator> models,
			T emptyInstance) {

		for (ModelsCreator model : models) {
			if (model.getClass().isInstance(emptyInstance)) {
				return (T) model;
			}
		}
		return null;
	}

}
