package org.openbudget.russia.model;

import org.openbudget.converter.OBFConverter;
import org.openbudget.model.Admin;

/**
 * 
 * @author inxaoc
 *
 */
public class GRBS extends Admin {
	
	@Override
	public String toString() {
		return OBFConverter.text.TERM_ADMIN+" "+super.toString();
	}

}
