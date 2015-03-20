package org.openbudget.utils;

import java.util.ArrayList;

public abstract class Logger {
	
	protected ArrayList<LogMessage> messages = new ArrayList<LogMessage>();
	
	abstract public void postError(String message, Object... objects);
	
	abstract public void postWarn(String message, Object... objects);
	
	abstract public void postSuccess(String message, Object... objects);
	
	abstract public void notify(String message);

	protected void post(String prefix, String message, int type,
			Object[] objects) {
		if (objects != null && objects.length!=0) {
			int i = 0;
			String s = "";
			for (Object str : objects) {
				message = message.replace("%" + i + "%", String.valueOf(str));
				i++;
			}

			if (isUniqueMessage(message, type)) {
				messages.add(new LogMessage(message, type));
				notify(prefix + message);
			}
		} else if (objects.length!=0 && isUniqueMessage(message, type)) {
			messages.add(new LogMessage(message, type));
			notify(prefix + message);
		}
		
	}
	
	protected boolean isUniqueMessage(String message, int type) {

		for (LogMessage one : messages) {
			if (one.getType()==type && one.getMessage().equals(message)) {
				return false;
			}
		}
		return true;
	}

	public void reset() {
		messages = new ArrayList<LogMessage>();
	}
	
	
	
	public ArrayList<LogMessage> getMessages() {
		return messages;
	}
	/**
	 * types: 0 - error, 1 - success, 2 - warnings
	 * @author inxaoc
	 *
	 */
	public class LogMessage {
		
		public static final int TYPE_ERROR = 0;
		public static final int TYPE_SUCCESS = 1;
		public static final int TYPE_WARNING = 2;
		
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
