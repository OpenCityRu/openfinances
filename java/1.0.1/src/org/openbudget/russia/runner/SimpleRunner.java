package org.openbudget.russia.runner;

import org.openbudget.converter.OBFConverter;
import org.openbudget.exception.ConverterException;
import org.openbudget.model.InputSettings;
import org.openbudget.russia.converter.OBFConverterRus;
import org.openbudget.russia.converter.impl.ArticleCreator;
import org.openbudget.russia.converter.impl.BudgetItemPrototypeRus;
import org.openbudget.russia.converter.impl.CSVSaverRus;
import org.openbudget.russia.converter.impl.EconomicRazdelCreator;
import org.openbudget.russia.converter.impl.GRBSCreator;
import org.openbudget.russia.converter.impl.SpendingTypeCreator;
import org.openbudget.russia.converter.impl.XLSReaderRus;
import org.openbudget.converter.face.Runnable;

public class SimpleRunner implements Runnable {
	
	public static void main(String[] args) throws ConverterException {

		String[][] settings = {
				{"name","Dachnoe_vedomst_2014_original"},
				{"format","csv"},
				{"version","1.0"},
				{"period","2014-01-01"},
				{"region","Долгое озеро"},
				{"file","files/Dachnoe_vedomst_2014_original.xls"},
				{"stage","PR"},
				{"level","M"},
				{"type","E"},
		};
		
		// defining converter which take XLS file and convert in CSV
		OBFConverter converter =  new OBFConverterRus();
		
		InputSettings isettings = new InputSettings(settings);
		
		converter.initiate(isettings, new XLSReaderRus(), 
				new BudgetItemPrototypeRus(), 
				new CSVSaverRus());
		
		converter.addModelCreatorSample(new GRBSCreator());
		converter.addModelCreatorSample(new ArticleCreator());
		converter.addModelCreatorSample(new EconomicRazdelCreator());
		converter.addModelCreatorSample(new SpendingTypeCreator());
		
		converter.convert();
		
		System.out.println("Finished.");
		
	}

}
