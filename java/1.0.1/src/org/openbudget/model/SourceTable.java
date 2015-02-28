package org.openbudget.model;

public class SourceTable {
	
protected String[][] cells;
	
	public SourceTable(int rows, int cols){
		cells = new String[rows][cols];
	}

	public String[][] getCells() {
		return cells;
	}
	
	public void addCell(int row, int col, String value){
		cells[row][col] = value;
	}
	
	public int rows(){
		return cells!=null?cells.length:0;
	}
	
	public int cols(){
		return cells[0]!=null?cells[0].length:0;
	}

}
