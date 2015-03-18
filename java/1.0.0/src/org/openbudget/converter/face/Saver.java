package org.openbudget.converter.face;

import java.util.ArrayList;

import org.openbudget.exception.ConverterException;
import org.openbudget.model.BudgetItem;
import org.openbudget.model.MetaData;

public interface Saver<B extends BudgetItem, M extends MetaData> {

	public void save (ArrayList<B> items, M metadata, String outputFileName) throws ConverterException;
}
