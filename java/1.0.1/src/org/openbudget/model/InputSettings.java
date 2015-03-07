package org.openbudget.model;

import org.openbudget.converter.OBFConverter;
import org.openbudget.exception.ConverterException;
import org.openbudget.exception.InputSettingsException;
import org.openbudget.utils.ConverterUtils;

/**
 * This class contains initial settings for OBFConverter.
 * @author inxaoc
 *
 */
public class InputSettings {

	//all data here from params
	protected String version;
	protected String format;
	protected String sourceFilePath;
	protected String outputFileName;
	
	protected String period;
	protected String region;
	protected String type;
	protected String level;
	protected String stage;
	
	protected String[][] params;
	
	public InputSettings(String[][] params) throws ConverterException{
		this.params = params;
		if(params.length == 0){
			throw new InputSettingsException(params);
		}
		
		//check and save version
		version = ConverterUtils.getValueByKey(params, "version");
		if(version.isEmpty() || !version.equals(OBFConverter.getGlobalSettings().getCurrentVersion())){
			throw new InputSettingsException(true,"Unknown version of format");
		}
		
		//check and save format
		format = ConverterUtils.getValueByKey(params, "format");
		if(!format.isEmpty()){
			
			if(!ConverterUtils.checkAvailbaleFormatType(format)){
				throw new InputSettingsException(true, "Unknown output format");
			}
			
		} else {
			throw new InputSettingsException(true,"Unknown output format");
		} 
		
		//save source path
		sourceFilePath = ConverterUtils.getValueByKey(params, "file");
		if(sourceFilePath==null || sourceFilePath.isEmpty()){
			throw new InputSettingsException(true, "Unknown source file path");
		}
		
		//save output name
		outputFileName = ConverterUtils.getValueByKey(params, "name");
		if(outputFileName==null || outputFileName.isEmpty()){
			outputFileName = "Unknown";
		}
		
		//save input values
		period = ConverterUtils.getValueByKey(params, "period");
		region = ConverterUtils.getValueByKey(params, "region");
		level = ConverterUtils.getValueByKey(params, "level");
		stage = ConverterUtils.getValueByKey(params, "stage");
		type = ConverterUtils.getValueByKey(params, "type");
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String[][] getParams() {
		return params;
	}

	public void setParams(String[][] params) {
		this.params = params;
	}

	public String getSourceFilePath() {
		return sourceFilePath;
	}

	public void setSourceFilePath(String sourceFilePath) {
		this.sourceFilePath = sourceFilePath;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}
	
	

}
