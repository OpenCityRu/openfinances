package org.openbudget.russia.converter.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.openbudget.converter.OBFConverter;
import org.openbudget.converter.face.BudgetFileReader;
import org.openbudget.exception.ConverterException;
import org.openbudget.exception.ExcelConverterException;
import org.openbudget.exception.StandardConverterException;
import org.openbudget.model.SourceTable;
import org.openbudget.russia.converter.ConverterUtilsRus;
import org.openbudget.utils.ConverterUtils;
import org.openbudget.utils.Log;

public class XLSReaderRus implements BudgetFileReader {

	@Override
	public SourceTable createSourceTable(InputStream file)
			throws ConverterException {

		SourceTable table = null;

		HSSFWorkbook excelSrc = null;

		try {
			excelSrc = new HSSFWorkbook(file);
		} catch (IOException e) {
			throw new ExcelConverterException("Problem with opening Excel file");
		}

		if (excelSrc.getNumberOfSheets() < 1) {
			throw new ExcelConverterException("Sheets in Excel file < 1");
		}

		if (excelSrc.getNumberOfSheets() > 1) {
			Log.postWarn("More than 1 sheet in Excel file");
		}

		Sheet sheet = excelSrc.getSheetAt(0);

		table = readXLS(sheet);

		return table;
	}

	private SourceTable readXLS(Sheet sheet) throws ConverterException {
		int lastRowIndex = sheet.getLastRowNum();
		int firstRowIndex = sheet.getFirstRowNum();
		
//		int lastRightColIndex = sheet.getRow(firstRowIndex).getLastCellNum();
		
		
		SourceTable fullSource = new SourceTable(lastRowIndex, sheet.getRow(firstRowIndex).getLastCellNum());

		for (int i = 0; i < lastRowIndex; i++) {

			for(int j=0;j<sheet.getRow(firstRowIndex).getLastCellNum();j++){
				setValue(sheet, fullSource, i, j, j, false);
			}
			
		}
		
		//0 is name
		//1 is grbs
		//2 is razdel
		//3 is article
		//4 is vid
		//5 is amount
		//6 is firstRowIndex
		int[] colNumbers = ConverterUtilsRus.parseTable(fullSource);
//		firstRowIndex = colNumbers[6];
		
		int sumIndexCol = colNumbers[5];
		int vidIndexCol = colNumbers[4];
		int articleIndexCol = colNumbers[3];
		int razdelIndexCol = colNumbers[2];
		int grbsIndexCol = colNumbers[1];
		int nameIndexCol = colNumbers[0];
		
//		int sumIndexCol = lastRightColIndex - 1;
//		int vidIndexCol = lastRightColIndex - 2;
//		int articleIndexCol = lastRightColIndex - 3;
//		int razdelIndexCol = lastRightColIndex - 4;
//		int grbsIndexCol = lastRightColIndex - 5;
//		int nameIndexCol = lastRightColIndex - 6;

		SourceTable table = new SourceTable(lastRowIndex + 1, 6);

		for (int i = 0; i < lastRowIndex + 1; i++) {

			setValue(sheet, table, i, 0, nameIndexCol, true);
			setValue(sheet, table, i, 1, grbsIndexCol, true);
			setValue(sheet, table, i, 2, razdelIndexCol, true);
			setValue(sheet, table, i, 3, articleIndexCol, true);
			setValue(sheet, table, i, 4, vidIndexCol, true);
			setValue(sheet, table, i, 5, sumIndexCol, false);

		}

		return table;
	}

	private void setValue(Sheet sheet, SourceTable table, int i, int j,
			int index, boolean makeString) throws ConverterException {

		Cell cell = sheet.getRow(i).getCell(index);
		if (cell == null) {
			table.addCell(i, j, "");
			return;
		}

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			table.addCell(i, j, ConverterUtils.normalizeUpperCaseString(cell
					.getStringCellValue()));

			// System.out.print("S"+table.getCells()[i][j].substring(0,
			// table.getCells()[i][j].length()<10?table.getCells()[i][j].length():10));
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			if (makeString)
				table.addCell(i, j,
						String.valueOf((long) cell.getNumericCellValue()));
			else
				table.addCell(i, j, String.valueOf(cell.getNumericCellValue()));

			// System.out.print("N"+table.getCells()[i][j]);
		}

		// System.out.print("|");

	}

}
