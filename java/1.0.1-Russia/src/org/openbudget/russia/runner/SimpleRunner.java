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
import org.openbudget.utils.Log;
import org.openbudget.converter.face.Runnable;

public class SimpleRunner implements Runnable {
	
	public static void main(String[] args) throws ConverterException {
		
		String name="output";
		String file="input.xls";
		String year="2015";
		String region="Unknown region";
				
		int i=0;
		for(String arg :args){
			if(arg.equals("-name")){
				name=args[i+1];
			}
			if(arg.equals("-file")){
				file=args[i+1];
			}
			if(arg.equals("-year")){
				year=args[i+1];
			}
			if(arg.equals("-region")){
				region=args[i+1];
			}
			i++;
		}

		String[][] settings = {
				{"name",name},
				{"format","csv"},
				{"version","1.0"},
				{"period",year+"-01-01"},
				{"region",region},
				{"file",file},
				{"stage","EN"},
				{"level","M"},
				{"type","E"},
		};
		
		// defining converter which take XLS file and convert in CSV
		OBFConverter converter =  new OBFConverterRus("text_ru.properties", new Log());
		
		InputSettings isettings = new InputSettings(settings);
		
		converter.initiate(isettings, new XLSReaderRus(), 
				new BudgetItemPrototypeRus(), 
				new CSVSaverRus());
		
		converter.addModelCreatorSample(new GRBSCreator());
		converter.addModelCreatorSample(new ArticleCreator());
		converter.addModelCreatorSample(new EconomicRazdelCreator());
		converter.addModelCreatorSample(new SpendingTypeCreator());
		
		converter.convert();
		
	}

}
