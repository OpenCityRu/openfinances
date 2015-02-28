package org.openbudget.exception;

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
		super("Wrong format of source file: "+ fileName);
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
			return "No input params";
		}
		for(String[] p : params){
			if(p.length==2){
				pars += p[0] + ":" + p[1] + ";";
			} else {
				return "Incorrect params (must be pairs (key, value) ) in a set ("+pars+")";
			}
		}
		
		return "There are some problems with input params: "+ pars;
	}
}
