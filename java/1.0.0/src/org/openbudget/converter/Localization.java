package org.openbudget.converter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.openbudget.exception.ConverterException;
import org.openbudget.exception.InputSettingsException;
import org.openbudget.russia.converter.impl.Localization_ru;

/**
 * All language specific consts must start with prefix (e.g. "RU_")
 * 
 * @author inxaoc
 * 
 */
public class Localization {

	protected Localization() {
		codes = initCodes();
	}

	public static Localization getInstance(String propertiesFileName)
			throws ConverterException {

		getInstance(propertiesFileName, new Localization_en());

		return new Localization_en();
	}

	protected static HashMap<Integer, String> map;

	public static Map<Integer, String> codes;

	public static String TERM_ADMIN = "Admin";
	public static String TERM_CLASSIFICATION = "Classification";
	public static String TERM_ROW = "Row";

	public static String MODEL_NAME = "name";
	public static String MODEL_CODE = "code";
	public static String MODEL_SOURCE_ROW_NUMBER = "source row number";

	public static String GLOBAL_SETTINGS_NULL = "Global settings are null. You should create new Instance of OBFConverter first then create InputSettings and then call initiate() method.";
	public static String EXCEPTION_INCORRECT_BUDGETITEM = "This is not correct budget item: ";
	public static String EXCEPTION_CANT_READ_FILE = "Can't read file ";
	public static String EXCEPTION_INPUT_SETTINGS_WRONG_FORMAT = "Wrong format of source file: ";
	public static String EXCEPTION_INCORRECT_SOURCE_FILE_FORMAT = "Incorrect source file format.";
	public static String EXCEPTION_INPUT_SETTINGS_NO_PARAMS = "No params";
	public static String EXCEPTION_INPUT_SETTINGS_UNKNOWN_VERSION = "Unknown version of format";
	public static String EXCEPTION_INPUT_SETTINGS_UNKNOWN_OUTPUT_FORMAT = "Unknown output format";
	public static String EXCEPTION_INPUT_SETTINGS_UNKNOWN_SOURCE_FILE = "Unknown source file path";
	public static String EXCEPTION_INPUT_SETTINGS_PAIRS = "Incorrect params (must be pairs (key, value) ) in a set;";
	public static String EXCEPTION_INPUT_SETTINGS_PROBLEMS = "There are some problems with input params: ";

	public static String SETTING_NOT_FOUND = "Setting \"%0%\" is not found";
	public static String UNKNOWN_DATE = "Unknown date";
	public static String UNKNOWN_MODEL = "Unknown Model Type";

	public static String SOURCETABLE_ERRORS = "Looks like file contains a lot of errors or incorrect values. I can't identify valuable columns in source file. Check file, fix errors and repeat again.";
	public static String SOURCETABLE_ERRORS_CELLS = "Looks like serious problem with content. It contains incorrect value. Check file, fix errors and repeat again.";

	public HashMap<Integer, String> initCodes() {

		// if(map==null){
		// map = new HashMap<Integer,String>();
		// map.put(110, GLOBAL_SETTINGS_NULL);
		// map.put(111, EXCEPTION_INCORRECT_BUDGETITEM);
		// map.put(112, EXCEPTION_CANT_READ_FILE);
		// map.put(113, EXCEPTION_INPUT_SETTINGS_WRONG_FORMAT);
		// map.put(114, EXCEPTION_INPUT_SETTINGS_NO_PARAMS);
		// map.put(115, EXCEPTION_INPUT_SETTINGS_UNKNOWN_VERSION);
		// map.put(116, EXCEPTION_INPUT_SETTINGS_UNKNOWN_OUTPUT_FORMAT);
		// map.put(117, EXCEPTION_INPUT_SETTINGS_UNKNOWN_SOURCE_FILE);
		// map.put(118, EXCEPTION_INPUT_SETTINGS_PAIRS);
		// map.put(119, EXCEPTION_INPUT_SETTINGS_PROBLEMS);
		//
		// map.put(210, MODEL_NAME);
		// map.put(211, MODEL_CODE);
		// map.put(212, MODEL_SOURCE_ROW_NUMBER);
		//
		// map.put(213, TERM_ADMIN);
		// map.put(214, TERM_CLASSIFICATION);
		// map.put(215, TERM_ROW);
		//
		// map.put(301, SETTING_NOT_FOUND);
		// map.put(302, UNKNOWN_DATE);
		// map.put(303, UNKNOWN_MODEL);
		//
		// init();
		// }
		return map;
	}

	protected static <T> void setProperties(Properties properties, T clazz)
			throws IllegalArgumentException, IllegalAccessException {

		for (Entry<Object, Object> e : properties.entrySet()) {
			String key = (String) e.getKey();
			String value = (String) e.getValue();

			Field[] fields = clazz.getClass().getDeclaredFields();

			for (Field field : fields) {
				if (field.getName().equals(key)) {
					field.set(null, value);
				}
			}
		}

	}

	protected static <T> void getInstance(String propertiesFileName, T clazz)
			throws ConverterException {

		String propertiesBase = "text_base.properties";

		Properties properties = new Properties(), properties2 = new Properties();

		try {

			InputStream stream1 = ClassLoader.getSystemClassLoader()
					.getResourceAsStream(propertiesFileName);
			InputStream stream2 = ClassLoader.getSystemClassLoader()
					.getResourceAsStream(propertiesBase);
			if (stream1 != null) {
				properties.load(stream1);
			}
			if (stream1 != null) {
				properties2.load(stream2);
			}

		} catch (FileNotFoundException e) {

			// throw new InputSettingsException(true,
			// "Can't read properties file.");

		} catch (IOException e) {

			// throw new InputSettingsException(true,
			// "Can't read properties file.");

		}

		try {
			setProperties(properties, clazz);
			setProperties(properties2, new Localization());

		} catch (IllegalArgumentException e) {

			// throw new InputSettingsException(true,
			// "Can't read property from file.");

		} catch (IllegalAccessException e) {

			// throw new InputSettingsException(true,
			// "Can't read property from file.");

		}

	}

	// this method should be overrided if need to add local inits
	protected void init() {

	}

}
