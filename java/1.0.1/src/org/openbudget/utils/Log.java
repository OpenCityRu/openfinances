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
public class Log {

	protected ArrayList<LogMessage> messages = new ArrayList<LogMessage>();
	
	protected ArrayList<String> warnings = new ArrayList<String>();
	protected ArrayList<String> success = new ArrayList<String>();

	public void postWarn(String message) {
		post(" !! WARN !!: ", message, 2);
	}
	
	public void postWarn(String message, Object... objects) {
		post(" !! WARN !!: ", message, 2, objects);
	}
	
	public void postSuccess(String message) {
		post(" !! SUCCESS !!: ", message, 1);
	}
	
	public void postSuccess(String message, Object... objects) {
		post(" !! SUCCESS !!: ", message, 1, objects);
	}
	

	private void post(String prefix, String message, int type) {
		if (isUniqueMessage(message, type)) {
			messages.add(new LogMessage(message, type));
			System.out.println(prefix + message);
		}
	}

	private void post(String prefix, String message, int type,
			Object[] objects) {
		if (objects != null) {
			int i = 0;
			String s = "";
			for (Object str : objects) {
				message = message.replace("%" + i + "%", String.valueOf(str));
				i++;
			}

			if (isUniqueMessage(message, type)) {
				messages.add(new LogMessage(message, type));
				System.out.println(prefix + message);
			}
		}
		
	}

	private boolean isUniqueMessage(String message, int type) {

		for (LogMessage one : messages) {
			if (one.getType()==type && one.getMessage().equals(message)) {
				return false;
			}
		}
		return true;
	}

	public void reset() {
		warnings = new ArrayList<String>();
		success = new ArrayList<String>();
	}
	
	/**
	 * types: 0 - error, 1 - success, 2 - warnings
	 * @author inxaoc
	 *
	 */
	public class LogMessage {
		protected String message;
		protected int type;
		
		public LogMessage(String message, int type) {
			super();
			this.message = message;
			this.type = type;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		
		
	}

}
