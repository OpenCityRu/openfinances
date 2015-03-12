package org.openbudget.converter.face;

import org.openbudget.exception.ConverterException;
import org.openbudget.model.SourceTable;

public interface BudgetFileReader {

	public SourceTable createSourceTable(String fileName) throws ConverterException;
}
