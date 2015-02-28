package org.openbudget.utils;

/**
 * This class will organize notification of user about errors, warns or other informational messages.
 * By default messages will be posted in Console as they appeared.
 *  
 * @author inxaoc
 *
 */
public class Log {
	
	public static void postWarn(String message){
		System.out.println(" !! WARN !!: "+ message);
	}

}
