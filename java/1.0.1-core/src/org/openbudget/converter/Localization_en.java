package org.openbudget.converter;

import java.util.Properties;

import org.openbudget.exception.ConverterException;

public class Localization_en extends Localization {

	protected Localization_en(){
		codes=initCodes();
	}
	
	public static Localization getInstance(String propertiesFileName) throws ConverterException{
		
		getInstance(propertiesFileName, new Localization_en());
		
		return new Localization_en();
	}
	
	public static Localization getInstance(Properties properties, Properties baseProperies) throws ConverterException{
		
		getInstance(properties, new Localization_en());
		
		getInstance(baseProperies, new Localization());
		
		return new Localization_en();
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

}
