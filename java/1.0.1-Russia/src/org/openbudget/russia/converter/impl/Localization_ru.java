package org.openbudget.russia.converter.impl;

import java.util.ArrayList;
import java.util.Properties;

import org.openbudget.converter.Localization;
import org.openbudget.converter.Localization_en;
import org.openbudget.exception.ConverterException;

public class Localization_ru extends Localization {
	
	public static String RU_EN_READFILE_NOT_BUDGETITEM = "Row %0% is identified as a not budget item and missed.";
	
	public static String RU_EN_GRBS = "ГРБС";
	public static String RU_EN_ARTICLE = "Статья расходов";
	public static String RU_EN_SPENDINGTYPE = "Вид расходов";
	public static String RU_EN_RAZDEL = "Раздел";
	public static String RU_EN_AMOUNT = "Сумма";
	
	public static String RU_EN_CONVERTING_RAZDEL_SYMBOLS = "Razdel %0% in row %1% is less then 4 symbols. Tried to fix it. If output file is not reliable - please fix it manually before converting.";
	public static String RU_EN_READFILE_OPEN = "Problem with opening Excel file";
	public static String RU_EN_SHEETS_ZERO = "Sheets in Excel not found";
	public static String RU_EN_SHEETS_MORE = "More than 1 sheet in Excel file";
	public static String RU_EN_REMOVED_EXCESS_COLUMN = "Column %0% is not valuable and has been removed";
	public static String RU_EN_PARSING_FIRST_ROW_NOTFOUND = "Can't identify first row with values.";
	public static String RU_EN_PARSING_RAZDELS_NOTIDENTIFIED_MORE = "Can't identify razdel because more candifates or none have been found.";
	public static String RU_EN_PARSING_GRBS_VID_NOTIDENTIFIED_ONE = "Can't identify grbs and spending type column because only one candifate has been found. Maybe 1 or more excess columns in source file.";
	public static String RU_EN_PARSING_GRBS_VID_NOTIDENTIFIED_MORE = "Can't identify grbs and spending type column because more than two candifate or none have been found.  Maybe 1 or more excess columns in source file.";
	public static String RU_EN_PARSING_ARTICLE_NOT_FOUND = "Can't find article column in source table.";
	public static String RU_EN_PARSING_ARTICLE_MORE = "Can't identify article column because more then one candifates have been found.  Maybe 1 or more excess columns in source file.";
	public static String RU_EN_CONVERTING_ARTICLE_MODEL_NOTFOUND = "Article with code \"%0%\" not found in collection of Articles (row %1%).";
	public static String RU_EN_CONVERTING_GRBS_MODEL_NOTFOUND = "GRBS with code \"%0%\" not found in collection of GRBS (row %1%).";
	public static String RU_EN_CONVERTING_RAZDEL_MODEL_NOTFOUND = "Razdel with code \"%0%\" not found in collection of Razdel (row %1%).";
	public static String RU_EN_CONVERTING_SPENDING_MODEL_NOTFOUND = "Spending Type with code \"%0%\" not found in collection of Spending types (row %1%).";
	public static String RU_EN_CONVERTING_SPENDING_MODEL_DUPLICATED = "More then 1 Spending type with the same number found (row %0%): \"%1%\" and \"%2%\". You should resolve this problem before converting. Or converted budget will not be equaled to original.";
	public static String RU_EN_CONVERTING_GRBS_MODEL_DUPLICATED = "More then 1 GRBS with the same number found (row %0%): \"%1%\" and \"%2%\". You should resolve this problem before converting. Or converted budget will not be equaled to original.";
	public static String RU_EN_CONVERTING_ARTICLE_MODEL_DUPLICATED = "More then 1 Article with the same number found (row %0%): \"%1%\" and \"%2%\". You should resolve this problem before converting. Or converted budget will not be equaled to original.";
	public static String RU_EN_CONVERTING_RAZDEL_MODEL_DUPLICATED = "More then 1 razdel with the same number found (row %0%): \"%1%\" and \"%2%\". You should resolve this problem before converting. Or converted budget will not be equaled to original.";

	public static String RU_EN_SUCCESS_XLS_OK = "File recognized as XLS and okay.";
	public static String RU_EN_SUCCESS_SHEET_ONE_OK = "First speadsheet in XLS workbook is recognized and will be used for converting.";
	public static String RU_EN_SUCCESS_CREATE_SOURCETABLE = "We have found content in XLS (%0% columns, %1% rows). Table is okay and now will be parsed to find valuable cells.";
	public static String RU_EN_SUCCESS_FINISH_SOURCETABLE = "XLS file has been parsed successfully!";
	public static String RU_EN_SUCCESS_START_ANALYZE = "We started analyzing of XLS file.";
	public static String RU_EN_SUCCESS_END_ANALYZE = "Analyzing is finished succesfully!";
	public static String RU_EN_PARSING_FOUND_UNIQUE_VALUES = "We have found in column N %0% %1% unique values.";
	public static String RU_EN_PARSING_FOUND_STRINGS = "We found that column N %0% contains mostly strings (probability is %1%).";
	public static String RU_EN_PARSING_FOUND_NUMBERS = "We found that column N %0% contains mostly numbers (probability is %1%).";
	public static String RU_EN_PARSING_READY_FIND = "Now we are ready to identify valuable columns (GRBS, Articles, etc.).";
	public static String RU_EN_PARSING_DELETE_NULLS = "Deleting empty columns.";
	public static String RU_EN_PARSING_AMOUNT_FOUND = "We have found column with amounts (N %0%).";
	public static String RU_EN_PARSING_AMOUNT_NOTFOUND = "We have not found column with amounts.";
	public static String RU_EN_PARSING_NAME_FOUND = "We have found column with string Names (N %0%).";
	public static String RU_EN_PARSING_NAME_NOTFOUND = "We have not found column with string Names.";
	public static String RU_EN_PARSING_CLASSIFICATIONS = "Now we will try to identify columns with Classifications (GRBS, Articles, etc).";
	public static String RU_EN_PARSING_ARTICLE_FOUND = "We have found Article column (N %0%).";
	public static String RU_EN_PARSING_SPENDING_FOUND = "We have found Spending Type column (N %0%).";
	public static String RU_EN_PARSING_SPENDING_NOT_FOUND = "We have not found Spending Type column.";
	public static String RU_EN_PARSING_GRBS_FOUND = "We have found GRBS column (N %0%).";
	public static String RU_EN_PARSING_GRBS_NOT_FOUND = "We have not found GRBS column.";
	public static String RU_EN_PARSING_RAZDEL_FOUND = "We have found Razdel column (N %0%).";
	public static String RU_EN_PARSING_RAZDEL_NOT_FOUND = "We have not found Razdel column.";
	public static String RU_EN_CONVERTING_RAZDEL_SYMBOLS_SERIOUS = "Serious problem with razdel code %0% (more then 4 symbols) in row %1%. You should solve this problem before.";
	public static String RU_EN_FINISHED = "Succesfully converted data and saved in file %0%";
	public static String RU_EN_START_READING = "Start reading file %0%";
	public static String RU_EN_PARSING_ERROR_FORMULA = "There is wrong value in one of cell (row is %0%, column %1%, formula is \"%2%\")";
	
	protected Localization_ru(){
		codes=initCodes();
	}
	
	
	public static Localization getInstance(String propertiesFileName) throws ConverterException{
		
		getInstance(propertiesFileName, new Localization_ru());
		
		return new Localization_ru();
	}
	
	public static Localization getInstance(Properties properties, Properties baseProperies) throws ConverterException{
		
		getInstance(properties, new Localization_ru());
		
		setInstanceBase(baseProperies);
		
		return new Localization_ru();
	}

	@Override
	protected void init() {
		
	}

}
