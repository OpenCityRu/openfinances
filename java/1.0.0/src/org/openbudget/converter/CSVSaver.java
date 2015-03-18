package org.openbudget.converter;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import au.com.bytecode.opencsv.CSVWriter;

public class CSVSaver {
	
	protected CSVWriter writer;

	public void createWriter(String outputFileName) throws UnsupportedEncodingException, FileNotFoundException{
		writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outputFileName), "UTF-8")));
	}
	
	protected void writeRow(Map<String, String> row) {
		
		String[] cells = new String[row.entrySet().size()];
		int i=0;
		for(Entry<String, String> entry: row.entrySet()){
			cells[i]=entry.getValue();
			i++;
		}
		
		writer.writeNext(cells);
		
	}
	
	protected void finish() throws IOException{
		
		writer.close();
		
	}
	
	

}
