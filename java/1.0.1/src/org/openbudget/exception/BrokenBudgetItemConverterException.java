package org.openbudget.exception;

import org.openbudget.model.BudgetItem;

public class BrokenBudgetItemConverterException extends ConverterException{
	
	public BrokenBudgetItemConverterException(BudgetItem item){
		super("This is not budget item: "+ item.toString());
	}

}
