package org.openbudget.utils;

import java.io.Serializable;

/**
 * types: 0 - error, 1 - success, 2 - warnings
 * @author inxaoc
 *
 */
public class LogMessage implements Serializable{
	public static final int TYPE_ERROR = 0;
	public static final int TYPE_SUCCESS = 1;
	public static final int TYPE_WARNING = 2;
	
	protected String message;
	protected int type;
	protected int row;
	protected int col;
	
	protected LogMessage (){
		
	}
	
	public LogMessage(String message, int type) {
		super();
		this.message = message;
		this.type = type;
	}
	
	public static LogMessage getEmpty(){
		return new LogMessage();
	}
	
	public LogMessage setPosition(int row, int col){
		
		this.row = row;
		this.col = col;
		
		return this;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
}
