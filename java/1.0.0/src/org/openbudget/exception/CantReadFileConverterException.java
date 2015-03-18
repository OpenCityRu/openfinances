package org.openbudget.exception;

import org.openbudget.converter.OBFConverter;

public class CantReadFileConverterException extends ConverterException{
	
	public CantReadFileConverterException(String fileName){
		super(OBFConverter.text.EXCEPTION_CANT_READ_FILE + fileName);
	}

}
