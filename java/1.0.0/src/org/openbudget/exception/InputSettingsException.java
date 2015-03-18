package org.openbudget.exception;

import org.openbudget.converter.OBFConverter;

/**
 * Class for all exceptions connected with Input Settings.
 * Use Constructor(String fileName) if you have problem with input file.
 * Use Constructor(Boolean justMessage, String message) for all general messages.
 * Use Constructor(String[][] params) if you have problems with params.
 * @author inxaoc
 *
 */
public class InputSettingsException extends ConverterException {

	public InputSettingsException(String fileName){
		super(OBFConverter.text.EXCEPTION_INPUT_SETTINGS_WRONG_FORMAT+ fileName);
	}

	public InputSettingsException(Boolean justMessage, String message){
		super(message);
	}
	
	public InputSettingsException(String[][] params){
		super(createMessage(params));
	}

	private static String createMessage(String[][] params) {
		
		String pars = "";
		if(params==null || params.length==0){
			return OBFConverter.text.EXCEPTION_INPUT_SETTINGS_NO_PARAMS;
		}
		for(String[] p : params){
			if(p.length==2){
				pars += p[0] + ":" + p[1] + ";";
			} else {
				return OBFConverter.text.EXCEPTION_INPUT_SETTINGS_PAIRS + " ("+pars+")";
			}
		}
		
		return OBFConverter.text.EXCEPTION_INPUT_SETTINGS_PROBLEMS+ pars;
	}
}
