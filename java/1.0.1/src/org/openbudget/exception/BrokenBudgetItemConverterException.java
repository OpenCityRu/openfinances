package org.openbudget.exception;

import org.openbudget.converter.OBFConverter;
import org.openbudget.model.BudgetItem;

public class BrokenBudgetItemConverterException extends ConverterException{
	
	public BrokenBudgetItemConverterException(BudgetItem item){
		
		super(OBFConverter.text.EXCEPTION_INCORRECT_BUDGETITEM + item.toString());
		
	}

}
