package org.openbudget.converter.face;

import org.openbudget.exception.ConverterException;
import org.openbudget.model.BudgetItem;

public interface BudgetItemPrototype<T extends BudgetItem> {
	
	public void copyValuesIn(T targetedItem) throws ConverterException;
	
	public T getOriginalItem();

}
