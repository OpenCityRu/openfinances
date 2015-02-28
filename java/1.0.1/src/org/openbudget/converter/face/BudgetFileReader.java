package org.openbudget.converter.face;

import java.io.InputStream;

import org.openbudget.exception.ConverterException;
import org.openbudget.model.SourceTable;

public interface BudgetFileReader {

	public SourceTable createSourceTable(InputStream file) throws ConverterException;
}
