package org.openbudget.russia.converter.impl;

import org.openbudget.model.GlobalSettings;

public class GlobalSettingsRus extends GlobalSettings{
	
	public GlobalSettingsRus() {
		super("1.0", getFormatTypes());
	}
	
	protected static String[] getFormatTypes(){
		String[] formats = {"csv"};
		return formats;
	}

}
