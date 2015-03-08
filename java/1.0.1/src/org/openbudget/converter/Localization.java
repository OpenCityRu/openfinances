package org.openbudget.converter;

import java.util.HashMap;
import java.util.Map;

/**
 * All language specific consts must start with prefix (e.g. "RU_")
 * @author inxaoc
 *
 */
abstract public class Localization {
	
	protected Localization() {
		codes=initCodes();
	}
	
	public static Localization getInstance(){
		return new Localization_en();
	}
	
	protected static HashMap<Integer,String> map;
	
	public static Map<Integer,String> codes = Localization.getInstance().initCodes(); 
	
	public static final String TERM_ADMIN = "Admin";
	public static final String TERM_CLASSIFICATION = "Classification";
	public static final String TERM_ROW = "Row";
	
	public static final String MODEL_NAME = "name";
	public static final String MODEL_CODE = "code";
	public static final String MODEL_SOURCE_ROW_NUMBER = "source row number";
			
	public static final String GLOBAL_SETTINGS_NULL = "Global settings are null. You should create new Instance of OBFConverter first then create InputSettings and then call initiate() method.";
	public static final String EXCEPTION_INCORRECT_BUDGETITEM = "This is not correct budget item: ";
	public static final String EXCEPTION_CANT_READ_FILE = "Can't read file ";
	public static final String EXCEPTION_INPUT_SETTINGS_WRONG_FORMAT = "Wrong format of source file: ";
	public static final String EXCEPTION_INPUT_SETTINGS_NO_PARAMS = "No params";
	public static final String EXCEPTION_INPUT_SETTINGS_UNKNOWN_VERSION = "Unknown version of format";
	public static final String EXCEPTION_INPUT_SETTINGS_UNKNOWN_OUTPUT_FORMAT = "Unknown output format";
	public static final String EXCEPTION_INPUT_SETTINGS_UNKNOWN_SOURCE_FILE = "Unknown source file path";
	public static final String EXCEPTION_INPUT_SETTINGS_PAIRS = "Incorrect params (must be pairs (key, value) ) in a set;";
	public static final String EXCEPTION_INPUT_SETTINGS_PROBLEMS = "There are some problems with input params: ";

	public static final String SETTING_NOT_FOUND = "Setting \"%0%\" is not found";
	public static final String UNKNOWN_DATE = "Unknown date";
	public static final String UNKNOWN_MODEL = "Unknown Model Type";

	public HashMap<Integer,String> initCodes(){
		
		if(map==null){
			map = new HashMap<Integer,String>();
			map.put(110, GLOBAL_SETTINGS_NULL);
			map.put(111, EXCEPTION_INCORRECT_BUDGETITEM);
			map.put(112, EXCEPTION_CANT_READ_FILE);
			map.put(113, EXCEPTION_INPUT_SETTINGS_WRONG_FORMAT);
			map.put(114, EXCEPTION_INPUT_SETTINGS_NO_PARAMS);
			map.put(115, EXCEPTION_INPUT_SETTINGS_UNKNOWN_VERSION);
			map.put(116, EXCEPTION_INPUT_SETTINGS_UNKNOWN_OUTPUT_FORMAT);
			map.put(117, EXCEPTION_INPUT_SETTINGS_UNKNOWN_SOURCE_FILE);
			map.put(118, EXCEPTION_INPUT_SETTINGS_PAIRS);
			map.put(119, EXCEPTION_INPUT_SETTINGS_PROBLEMS);
			
			map.put(210, MODEL_NAME);
			map.put(211, MODEL_CODE);
			map.put(212, MODEL_SOURCE_ROW_NUMBER);
			
			map.put(213, TERM_ADMIN);
			map.put(214, TERM_CLASSIFICATION);
			map.put(215, TERM_ROW);
			
			map.put(301, SETTING_NOT_FOUND);
			map.put(302, UNKNOWN_DATE);
			map.put(303, UNKNOWN_MODEL);
			
			init();
		}
		return map;
	}


	//this method should be overrided if need to add local inits
	abstract protected void init();

}
