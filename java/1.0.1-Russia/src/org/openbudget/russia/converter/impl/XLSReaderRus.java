package org.openbudget.russia.converter.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openbudget.converter.OBFConverter;
import org.openbudget.converter.face.BudgetFileReader;
import org.openbudget.exception.CantReadFileConverterException;
import org.openbudget.exception.ConverterException;
import org.openbudget.exception.ExcelConverterException;
import org.openbudget.exception.SourceTableException;
import org.openbudget.model.SourceTable;
import org.openbudget.russia.converter.ConverterUtilsRus;
import org.openbudget.russia.converter.OBFConverterRus;
import org.openbudget.utils.ConverterUtils;

public class XLSReaderRus implements BudgetFileReader {

	@Override
	public SourceTable createSourceTable(String fileName)
			throws ConverterException {

		InputStream stream;

		try {

			stream = new FileInputStream(fileName);

		} catch (IOException e) {
			throw new CantReadFileConverterException(fileName);
		}

		SourceTable table = null;

		Workbook excelSrc = null;

		try {
			String[] file = fileName.split("\\.");
			if (file[file.length - 1].equals("xls")) {
				excelSrc = new HSSFWorkbook(stream);
			} else if (file[file.length - 1].equals("xlsx")) {
				excelSrc = new XSSFWorkbook(stream);
			} else {
				OBFConverter.log
						.postError(OBFConverter.text.EXCEPTION_INCORRECT_SOURCE_FILE_FORMAT);
				throw new CantReadFileConverterException(fileName);
			}

			OBFConverter.log
					.postSuccess(OBFConverterRus.text.RU_EN_SUCCESS_XLS_OK);
		} catch (IOException e) {
			throw new ExcelConverterException(
					OBFConverterRus.text.RU_EN_READFILE_OPEN);
		}

		if (excelSrc.getNumberOfSheets() < 1) {
			throw new ExcelConverterException(
					OBFConverterRus.text.RU_EN_SHEETS_ZERO);
		}

		if (excelSrc.getNumberOfSheets() > 1) {
			OBFConverter.log.postError(OBFConverterRus.text.RU_EN_SHEETS_MORE);
		}
		
		OBFConverter.log.postSuccess(OBFConverterRus.text.RU_EN_START_READING, fileName);

		Sheet sheet = excelSrc.getSheetAt(0);
		OBFConverter.log
				.postSuccess(OBFConverterRus.text.RU_EN_SUCCESS_SHEET_ONE_OK);

		table = readXLS(sheet);

		if (table != null) {
			OBFConverter.log
					.postSuccess(OBFConverterRus.text.RU_EN_SUCCESS_FINISH_SOURCETABLE);
		}
		return table;
	}

	private SourceTable readXLS(Sheet sheet) throws ConverterException {
		int lastRowIndex = sheet.getLastRowNum();
		int firstRowIndex = sheet.getFirstRowNum();

		// define maximum of columns in sheet
		int widthTable = 0;
		for (int row = 0; row <= (lastRowIndex + 1); row++) {
			if (sheet.getRow(row) != null
					&& sheet.getRow(row).getLastCellNum() > widthTable) {
				widthTable = sheet.getRow(row).getLastCellNum();
			}
		}

		SourceTable fullSource = new SourceTable(lastRowIndex + 1, widthTable);

		for (int i = 0; i < fullSource.rows(); i++) {

			for (int j = 0; j < fullSource.cols(); j++) {
				setValue(sheet, fullSource, i, j, j, false);
			}

		}

		if (fullSource != null && fullSource.cols() != 0
				&& fullSource.rows() != 0) {
			OBFConverter.log.postSuccess(
					OBFConverterRus.text.RU_EN_SUCCESS_CREATE_SOURCETABLE,
					fullSource.cols(), fullSource.rows());
		}

		// 0 is name
		// 1 is grbs
		// 2 is razdel
		// 3 is article
		// 4 is vid
		// 5 is amount
		// 6 is firstRowIndex

		OBFConverter.log
				.postSuccess(OBFConverterRus.text.RU_EN_SUCCESS_START_ANALYZE);

		int[] colNumbers = ConverterUtilsRus.parseTable(fullSource);

		if (colNumbers != null) {
			OBFConverter.log
					.postSuccess(OBFConverterRus.text.RU_EN_SUCCESS_END_ANALYZE);
		} else {
			throw new SourceTableException(OBFConverter.text.SOURCETABLE_ERRORS);
		}

		// firstRowIndex = colNumbers[6];

		int sumIndexCol = colNumbers[5];
		int vidIndexCol = colNumbers[4];
		int articleIndexCol = colNumbers[3];
		int razdelIndexCol = colNumbers[2];
		int grbsIndexCol = colNumbers[1];
		int nameIndexCol = colNumbers[0];

		// int sumIndexCol = lastRightColIndex - 1;
		// int vidIndexCol = lastRightColIndex - 2;
		// int articleIndexCol = lastRightColIndex - 3;
		// int razdelIndexCol = lastRightColIndex - 4;
		// int grbsIndexCol = lastRightColIndex - 5;
		// int nameIndexCol = lastRightColIndex - 6;

		SourceTable table = new SourceTable(lastRowIndex + 1, 6);

		for (int i = 0; i < table.rows(); i++) {

			try {
				setValue(sheet, table, i, 0, nameIndexCol, true);
				setValue(sheet, table, i, 1, grbsIndexCol, true);
				setValue(sheet, table, i, 2, razdelIndexCol, true);
				setValue(sheet, table, i, 3, articleIndexCol, true);
				setValue(sheet, table, i, 4, vidIndexCol, true);
				setValue(sheet, table, i, 5, sumIndexCol, false);

				// values in Amount columns should be converted if possible in
				// Double even if stored as StringCell
				if (sheet.getRow(i) != null
						&& sheet.getRow(i).getCell(sumIndexCol) != null
						&& sheet.getRow(i).getCell(sumIndexCol).getCellType() == Cell.CELL_TYPE_STRING) {
					table.addCell(
							i,
							5,
							ConverterUtilsRus.checkDouble(String.valueOf(sheet
									.getRow(i).getCell(sumIndexCol)
									.getStringCellValue())));
				}
			} catch (NumberFormatException e) {

			}
		}

		return table;
	}

	private void setValue(Sheet sheet, SourceTable table, int i, int j,
			int index, boolean makeString) throws ConverterException {

		// System.out.println(i);
		if (sheet.getRow(i) != null && sheet.getRow(i).getCell(index) != null) {
			Cell cell = sheet.getRow(i).getCell(index);

			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				table.addCell(i, j, ConverterUtils
						.normalizeUpperCaseString(cell.getStringCellValue()));

				// System.out.print("S"+table.getCells()[i][j].substring(0,
				// table.getCells()[i][j].length()<10?table.getCells()[i][j].length():10));
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
					|| cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				if (makeString) {
					try {
						table.addCell(i, j, String.valueOf((long) cell
								.getNumericCellValue()));
					} catch (IllegalStateException e) {
						
						//it needs if formula is a string
						table.addCell(i, j, ConverterUtilsRus
								.checkDouble(String.valueOf(cell
										.getStringCellValue())));
					}
				} else {
					try {
						// System.out.println(i+" "+j+" "+cell.getNumericCellValue());
						table.addCell(i, j, ConverterUtilsRus
								.checkDouble(String.valueOf(cell
										.getNumericCellValue())));
					} catch (IllegalStateException e) {
						//it needs if formula is a string
						try {
							table.addCell(i, j, ConverterUtilsRus
									.checkDouble(String.valueOf(cell
											.getStringCellValue())));
						} catch (IllegalStateException e1) {
							// TODO Auto-generated catch block
							OBFConverter.log.postError(OBFConverterRus.text.RU_EN_PARSING_ERROR_FORMULA, i+1, j+1, cell.getCellFormula());
						}
					}

				}
			}

			// System.out.print("|");
		} else {
			table.addCell(i, j, "");
		}

	}

}
