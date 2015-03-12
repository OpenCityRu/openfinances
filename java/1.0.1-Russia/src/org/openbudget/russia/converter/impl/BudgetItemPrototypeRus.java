package org.openbudget.russia.converter.impl;

import org.openbudget.converter.face.BudgetItemPrototype;
import org.openbudget.russia.model.BudgetItemRus;

public class BudgetItemPrototypeRus implements BudgetItemPrototype<BudgetItemRus> {
	
	protected BudgetItemRus budgetItem = new BudgetItemRus();

	@Override
	public void copyValuesIn(BudgetItemRus targetedItem) {
		
		targetedItem.setDate(budgetItem.getDate());
		targetedItem.setLevel(budgetItem.getLevel());
		targetedItem.setStage(budgetItem.getStage());
		targetedItem.setRegion(budgetItem.getRegion());
		targetedItem.setType(budgetItem.getType());
		targetedItem.setPeriod(budgetItem.getPeriod());
		
	}

	@Override
	public BudgetItemRus getOriginalItem() {
		return budgetItem;
	}

}
