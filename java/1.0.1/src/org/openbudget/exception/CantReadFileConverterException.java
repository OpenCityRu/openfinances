package org.openbudget.exception;

public class CantReadFileConverterException extends ConverterException{
	
	public CantReadFileConverterException(String fileName){
		super("Can't read file "+ fileName);
	}

}
