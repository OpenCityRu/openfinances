package org.openbudget.utils;

import java.util.ArrayList;

public abstract class Logger {
	
	protected ArrayList<LogMessage> messages = new ArrayList<LogMessage>();
	
	abstract public LogMessage postError(String message, Object... objects);
	
	abstract public LogMessage postWarn(String message, Object... objects);
	
	abstract public LogMessage postSuccess(String message, Object... objects);
	
	abstract public void notify(String message);

	protected LogMessage post(String prefix, String message, int type,
			Object[] objects) {
		
		LogMessage log = LogMessage.getEmpty();
		
		if (objects != null && objects.length!=0) {
			int i = 0;
			String s = "";
			for (Object str : objects) {
				message = message.replace("%" + i + "%", String.valueOf(str));
				i++;
			}

			if (isUniqueMessage(message, type)) {
				log = new LogMessage(message, type);
				messages.add(log);
				notify(prefix + message);
			}
		} else if (objects.length==0 && isUniqueMessage(message, type)) {
			log = new LogMessage(message, type);
			messages.add(log);
			notify(prefix + message);
		}
		return log;
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

}
