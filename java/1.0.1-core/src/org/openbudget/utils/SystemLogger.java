package org.openbudget.utils;

import java.util.ArrayList;

/**
 * Logger should be initialized in OBFConverter before. Don't use default Log
 * constructor without instantiation in OBFConverter. This class will organize
 * notification of user about errors, warns or other informational messages. By
 * default messages will be posted in Console as they appeared.
 * 
 * Loggers could be extended to save messages in session for example (in
 * servlets).
 * 
 * @author inxaoc
 * 
 */
public class SystemLogger extends Logger{
	
	/**
	 * 
	 */
	@Override
	public void postWarn(String message, Object... objects) {
		post(" !! WARN !!: ", message, LogMessage.TYPE_WARNING, objects);
	}
	
	/**
	 * 
	 */
	@Override
	public void postError(String message, Object... objects) {
		post(" !! ERROR !!: ", message, LogMessage.TYPE_ERROR, objects);
	}
	
	/**
	 * 
	 */
	@Override
	public void postSuccess(String message, Object... objects) {
		post(" !! SUCCESS !!: ", message, LogMessage.TYPE_SUCCESS, objects);
	}

	@Override
	public void notify(String message) {
		
		System.out.println(message);
		
	}

}
