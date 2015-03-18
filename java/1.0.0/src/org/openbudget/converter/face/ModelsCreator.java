package org.openbudget.converter.face;

import java.util.ArrayList;

import org.openbudget.exception.ConverterException;
import org.openbudget.model.Dimension;
import org.openbudget.model.SourceTable;

public interface ModelsCreator<T extends Dimension> {
	
	public ArrayList<T> createModels(SourceTable table) throws ConverterException;
	
	public void saveModels(ArrayList<T> models) throws ConverterException;
	
	public ArrayList<T> getModels() throws ConverterException;

}
