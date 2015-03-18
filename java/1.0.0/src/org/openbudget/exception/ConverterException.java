package org.openbudget.exception;

/**
 * Base class for all exceptions.
 * 
 * @author inxaoc
 *
 */
public class ConverterException extends Exception{

	public ConverterException(){
		super();
	}

	public ConverterException(String message){
		super(message);
	}
	
	public ConverterException(Throwable e){
		super(e);
	}
	
	public ConverterException(String message, Throwable e){
		super(message, e);
	}
}
