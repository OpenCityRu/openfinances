package org.openbudget.russia.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openbudget.converter.OBFConverter;
import org.openbudget.exception.BrokenBudgetItemConverterException;
import org.openbudget.model.SourceTable;
import org.openbudget.russia.model.BudgetItemRus;

public class ConverterUtilsRus {

	public static String generateKBK(BudgetItemRus item)
			throws BrokenBudgetItemConverterException {

		if (item.getGrbs() == null || item.getArticle() == null
				|| item.getRazdel() == null) {
			throw new BrokenBudgetItemConverterException(item);
		}
		return item.getGrbs().getCode() + item.getRazdel().getCode()
				+ item.getArticle().getCode()
				+ item.getSpendingType().getCode();

	}

	public static int[] parseTable(SourceTable fullSource) {

		int[] result = new int[7];

		String[][] source = fullSource.getCells();

		// matrix of solution:
		// 1=amount of unique values in col
		// 2=string (0 if not, 1 if yes)
		// 3=number (0 if not, 1 if yes)
		// 4=Average length (in string equivalent)
		// 5=string min
		// 6=string max
		// 7=amount of words in string

		double[][] solution = new double[fullSource.cols()][7];

		// values of each col in array
		ArrayList<String[]> rows = new ArrayList<String[]>();

		for (int i = 0; i < fullSource.cols(); i++) {
			String[] nn = new String[fullSource.rows()];

			// count empty cells for each col
			int empty = 0;
			int fullLength = 0;
			int stringMax = 0;
			int stringMin = 0;
			int wordsCounter = 0;

			for (int j = 0; j < fullSource.rows(); j++) {
				// to save each value of col in cell
				nn[j] = source[j][i] != null ? source[j][i].trim()
						.toLowerCase() : "";

				// find empty values and full length
				if (source[j][i] == null || source[j][i].isEmpty()) {
					empty++;
				} else {
					fullLength += source[j][i].length();

					// find max and min for string
					if (source[j][i].length() > stringMax) {
						stringMax = source[j][i].length();
					}
					if (stringMin == 0) {
						stringMin = source[j][i].length();
					}
					if (source[j][i].length() < stringMin) {
						stringMin = source[j][i].length();
					}

					// count amount of words

					wordsCounter += source[j][i].split(" ").length;
				}
			}

			if ((fullSource.rows() - empty) != 0) {
				// count average length of each value
				solution[i][3] = (double) fullLength
						/ (fullSource.rows() - empty);
				// count average amount of words
				solution[i][6] = wordsCounter / (fullSource.rows() - empty);
			}

			solution[i][5] = stringMin;
			solution[i][4] = stringMax;

			rows.add(nn);
		}

		// create lists of unique values per col
		ArrayList<Set> sets = new ArrayList<Set>();

		for (int i = 0; i < fullSource.cols(); i++) {

			Set<String> uniqKeys = new TreeSet<String>();
			uniqKeys.addAll(Arrays.asList(rows.get(i)));

			solution[i][0] = uniqKeys.size();

			OBFConverter.log.postSuccess(
					OBFConverterRus.text.RU_EN_PARSING_FOUND_UNIQUE_VALUES,
					(i + 1), uniqKeys.size());
			sets.add(uniqKeys);
		}

		// count average string/number values (weight)
		int[][] strings = new int[fullSource.rows()][fullSource.cols()];
		int[][] numbers = new int[fullSource.rows()][fullSource.cols()];

		for (int i = 0; i < fullSource.rows(); i++) {

			for (int j = 0; j < fullSource.cols(); j++) {
				String v = source[i][j];
				try {
					if (source[i][j] != null && !source[i][j].isEmpty()) {
						checkDouble(source[i][j]);
						numbers[i][j] = 1;
					}
				} catch (NumberFormatException e) {
					strings[i][j] = 1;
				}
			}
		}

		double strSum, numSum;

		for (int i = 0; i < fullSource.cols(); i++) {
			strSum = 0;
			numSum = 0;
			int empty = 0;

			for (int j = 0; j < fullSource.rows(); j++) {
				if (strings[j][i] == 0 && numbers[j][i] == 0) {
					empty++;
				} else {
					strSum += strings[j][i];
					numSum += numbers[j][i];
				}
			}
			strSum = (double) strSum / (fullSource.rows() - empty);
			numSum = (double) numSum / (fullSource.rows() - empty);
			solution[i][1] = strSum;
			solution[i][2] = numSum;
		}

		int i = 0;
		for (double[] d : solution) {
			if (d[1] > 9) {
				OBFConverter.log.postSuccess(
						OBFConverterRus.text.RU_EN_PARSING_FOUND_STRINGS,
						i + 1, d[1]);
			}
			if (d[2] > 9) {
				OBFConverter.log.postSuccess(
						OBFConverterRus.text.RU_EN_PARSING_FOUND_NUMBERS,
						i + 1, d[2]);
			}
			i++;
		}

		OBFConverter.log
				.postSuccess(OBFConverterRus.text.RU_EN_PARSING_READY_FIND);

		// find classifications / make confident
		// this list will be changed
		List<double[]> newList = new ArrayList<double[]>();

		// this list will not be changed, equals original
		List<double[]> original = new ArrayList<double[]>();

		for (double[] solut : solution) {
			newList.add(solut);
			original.add(solut);
		}

		List<double[]> newListToRemove = new ArrayList<double[]>();
		// delete nulls

		OBFConverter.log
				.postWarn(OBFConverterRus.text.RU_EN_PARSING_DELETE_NULLS);

		for (double[] ll : newList) {
			if (ll[1] == ll[2]) {
				newListToRemove.add(ll);
				OBFConverter.log.postWarn(
						OBFConverterRus.text.RU_EN_REMOVED_EXCESS_COLUMN,
						original.indexOf(ll) + 1);
			}
		}

		newList.removeAll(newListToRemove);

		// find Amount: concept to find amount if high probability to be a
		// number (value of 5 element of matrix is more then 0,9

		// Map<Double,double[]> numbersListSorted = new
		// TreeMap<Double,double[]>();

		double[] amount = new double[newList.size()];

		for (double[] ll : newList) {
			if (ll[2] > 0.9) {
				if (result[5] == 0) {
					result[5] = original.indexOf(ll);
					amount = ll;
				} else if (ll[0] > amount[0]) {
					amount = ll;
				}
			}
		}

		if (amount != null && original.indexOf(amount) != -1) {
			result[5] = original.indexOf(amount);
			newList.remove(amount);
			OBFConverter.log.postSuccess(
					OBFConverterRus.text.RU_EN_PARSING_AMOUNT_FOUND,
					original.indexOf(amount) + 1);
		} else {
			OBFConverter.log
					.postError(OBFConverterRus.text.RU_EN_PARSING_AMOUNT_NOTFOUND);
			result[5] = -1;
		}

		// find valuable string name: concept is 1) to find string value with
		// high probability (>0,9), 2) the biggest value of amount of words, 3)
		// the longest average length, 4) big divesity of unique values

		List<double[]> findOne1st = new ArrayList<double[]>(), findOne2nd = null;

		// check 1st param
		for (double[] col : newList) {
			if (col[1] > 0.9) {
				findOne1st.add(col);
			}
		}

		// check second param if found more then one
		if (findOne1st.size() > 1) {
			findOne2nd = new ArrayList<double[]>();
			for (double[] col : findOne1st) {
				if (col[6] > 2) {

					findOne2nd.add(col);
				}
			}
		} else {

			if (findOne1st.get(0) != null
					&& original.indexOf(findOne1st.get(0)) != -1) {
				result[0] = original.indexOf(findOne1st.get(0));
				newList.remove(findOne1st.get(0));
				OBFConverter.log.postSuccess(
						OBFConverterRus.text.RU_EN_PARSING_NAME_FOUND,
						original.indexOf(findOne1st.get(0)) + 1);
			} else {
				OBFConverter.log
						.postError(OBFConverterRus.text.RU_EN_PARSING_NAME_NOTFOUND);
				result[0] = -1;
			}
		}

		// check average length
		if (findOne2nd != null) {
			if (findOne2nd.size() > 1) {
				double[] last = null;
				double aveLeng = 0;
				for (double[] col : findOne2nd) {
					if (col[3] > aveLeng) {
						last = col;
						aveLeng = col[3];
					}
				}

				if (last != null && original.indexOf(last) != -1) {
					result[0] = original.indexOf(last);
					newList.remove(last);
					OBFConverter.log.postSuccess(
							OBFConverterRus.text.RU_EN_PARSING_NAME_FOUND,
							original.indexOf(last) + 1);
				} else {
					OBFConverter.log
							.postError(OBFConverterRus.text.RU_EN_PARSING_NAME_NOTFOUND);
					result[0] = -1;
				}

			} else {

				if (findOne2nd.get(0) != null
						&& original.indexOf(findOne2nd.get(0)) != -1) {
					result[0] = original.indexOf(findOne2nd.get(0));
					newList.remove(findOne2nd.get(0));
					OBFConverter.log.postSuccess(
							OBFConverterRus.text.RU_EN_PARSING_NAME_FOUND,
							original.indexOf(findOne2nd.get(0)) + 1);
				} else {
					OBFConverter.log
							.postError(OBFConverterRus.text.RU_EN_PARSING_NAME_NOTFOUND);
					result[0] = -1;
				}
			}
		}

		// check diversity - additional checking could be implemented separately
		// for difficult cases. Mostly checking first three params is available.
		// if(findOne.size()>1){
		// for(double[] col : findOne){
		// if(col[6] > 2){
		// findOne.add(col);
		// }
		// }
		// }

		// we need to choose 4 params
		// - GRBS (length is 3 (if is string) or 5 (if is number)), could be
		// string or number
		// - Article (7 if string without spaces or 9 if string with spaces 9 if
		// number), could be string or number
		// - Spending Type (length could be 3 or 5), string, number or unknown
		// - Razdel (length could be 3,4,6), could be string or number

		// searchClassificationSmart(newList, original, result);

		if (result[0] != -1 && result[5] != -1) {

			// 1 is grbs
			result[1] = result[0] + 1;
			OBFConverter.log.postSuccess(
					OBFConverterRus.text.RU_EN_PARSING_GRBS_FOUND,
					result[1] + 1);

			// 2 is razdel
			result[2] = result[0] + 2;
			OBFConverter.log.postSuccess(
					OBFConverterRus.text.RU_EN_PARSING_RAZDEL_FOUND,
					result[2] + 1);

			// 3 is article
			result[3] = result[0] + 3;
			OBFConverter.log.postSuccess(
					OBFConverterRus.text.RU_EN_PARSING_ARTICLE_FOUND,
					result[3] + 1);

			// 4 is vid
			result[4] = result[0] + 4;
			OBFConverter.log.postSuccess(
					OBFConverterRus.text.RU_EN_PARSING_SPENDING_FOUND,
					result[4] + 1);

		}

		// find first row with amount
		Integer firstRowIndex = null;
		for (i = 0; i < fullSource.rows(); i++) {
			try {
				if (fullSource.getCells()[i][result[5]] != null
						&& !fullSource.getCells()[i][result[5]].isEmpty()) {
					checkDouble(fullSource.getCells()[i][result[5]]);
					firstRowIndex = i;
					break;
				}
			} catch (NumberFormatException e) {

			}
		}

		if (firstRowIndex == null) {
			OBFConverter.log
					.postError(OBFConverterRus.text.RU_EN_PARSING_FIRST_ROW_NOTFOUND).setPosition(firstRowIndex, 0);
			result[6] = -1;
		} else {
			result[6] = firstRowIndex;
		}

		for (int res : result) {
			if (res == -1) {
				return null;
			}
		}
		String resultStr = OBFConverterRus.text.RU_EN_ANALYZING_REPORT_START
				
				+ fullSource.getCells()[firstRowIndex - 1][result[0]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex - 1][result[1]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex - 1][result[2]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex - 1][result[3]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex - 1][result[4]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex - 1][result[5]]
				+ "|\n"
				
				+ OBFConverterRus.text.RU_EN_ANALYZING_REPORT_1_ROW
				
				+ fullSource.getCells()[firstRowIndex][result[0]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex][result[1]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex][result[2]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex][result[3]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex][result[4]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex][result[5]]
						
				+ OBFConverterRus.text.RU_EN_ANALYZING_REPORT_2_ROW
				
				+ fullSource.getCells()[firstRowIndex+1][result[0]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex+1][result[1]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex+1][result[2]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex+1][result[3]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex+1][result[4]]
				+ "|"
				+ fullSource.getCells()[firstRowIndex+1][result[5]]
						
				+ OBFConverterRus.text.RU_EN_ANALYZING_REPORT_END;

		OBFConverter.log.postWarn(resultStr);
		return result;
	}

	private static void searchClassificationSmart(List<double[]> newList,
			List<double[]> original, int[] result) {
		OBFConverter.log
				.postWarn(OBFConverterRus.text.RU_EN_PARSING_CLASSIFICATIONS);

		List<double[]> findAvailable = new ArrayList<double[]>();

		for (double[] av : newList) {
			if (av[3] >= 2.8 && av[3] <= 9.5) {
				findAvailable.add(av);
			}
		}

		// search article (at least 7 symbols), amount of unique articles could
		// not be more then amount of names
		List<double[]> findArticles = new ArrayList<double[]>();
		for (double[] av : findAvailable) {
			if (av[3] >= 7 && av[0] < original.get(result[0])[0]) {
				findArticles.add(av);
			}
		}

		if (findArticles.size() == 1) {

			if (findArticles.get(0) != null
					&& original.indexOf(findArticles.get(0)) != -1) {
				result[3] = original.indexOf(findArticles.get(0));
				newList.remove(findArticles.get(0));
				OBFConverter.log.postSuccess(
						OBFConverterRus.text.RU_EN_PARSING_ARTICLE_FOUND,
						original.indexOf(findArticles.get(0)) + 1);
			} else {
				OBFConverter.log
						.postError(OBFConverterRus.text.RU_EN_PARSING_ARTICLE_NOT_FOUND);
				result[3] = -1;
			}

		} else if (findArticles.size() == 0) {
			OBFConverter.log
					.postError(OBFConverterRus.text.RU_EN_PARSING_ARTICLE_NOT_FOUND);
			result[3] = -1;
		} else {
			// need to analyze content of cells
			OBFConverter.log
					.postError(OBFConverterRus.text.RU_EN_PARSING_ARTICLE_MORE);
			result[3] = -1;
		}

		// find grbs and spending type: the difference that amount of grbs <
		// amount of types usually
		List<double[]> findGRBS = new ArrayList<double[]>();
		for (double[] av : newList) {
			if ((av[3] > 2.5 && av[3] < 3.5) || (av[3] > 4.5 && av[3] < 5.5)) {
				findGRBS.add(av);
			}
		}

		if (findGRBS.size() == 2) {
			if (findGRBS.get(0)[0] < findGRBS.get(1)[0]) {

				if (findGRBS.get(0) != null
						&& original.indexOf(findGRBS.get(0)) != -1) {
					result[4] = original.indexOf(findGRBS.get(0));
					newList.remove(findGRBS.get(0));
					OBFConverter.log.postSuccess(
							OBFConverterRus.text.RU_EN_PARSING_SPENDING_FOUND,
							original.indexOf(findGRBS.get(0)) + 1);
				} else {
					OBFConverter.log
							.postError(OBFConverterRus.text.RU_EN_PARSING_SPENDING_NOT_FOUND);
					result[4] = -1;
				}

				if (findGRBS.get(1) != null
						&& original.indexOf(findGRBS.get(1)) != -1) {
					result[1] = original.indexOf(findGRBS.get(1));
					newList.remove(findGRBS.get(1));
					OBFConverter.log.postSuccess(
							OBFConverterRus.text.RU_EN_PARSING_GRBS_FOUND,
							original.indexOf(findGRBS.get(1)) + 1);
				} else {
					OBFConverter.log
							.postError(OBFConverterRus.text.RU_EN_PARSING_GRBS_NOT_FOUND);
					result[1] = -1;
				}

			} else {

				if (findGRBS.get(1) != null
						&& original.indexOf(findGRBS.get(1)) != -1) {
					result[4] = original.indexOf(findGRBS.get(1));
					newList.remove(findGRBS.get(1));
					OBFConverter.log.postSuccess(
							OBFConverterRus.text.RU_EN_PARSING_SPENDING_FOUND,
							original.indexOf(findGRBS.get(1)) + 1);
				} else {
					OBFConverter.log
							.postError(OBFConverterRus.text.RU_EN_PARSING_SPENDING_NOT_FOUND);
					result[4] = -1;
				}

				if (findGRBS.get(1) != null
						&& original.indexOf(findGRBS.get(0)) != -1) {
					result[1] = original.indexOf(findGRBS.get(0));
					newList.remove(findGRBS.get(0));
					OBFConverter.log.postSuccess(
							OBFConverterRus.text.RU_EN_PARSING_GRBS_FOUND,
							original.indexOf(findGRBS.get(0)) + 1);
				} else {
					OBFConverter.log
							.postError(OBFConverterRus.text.RU_EN_PARSING_GRBS_NOT_FOUND);
					result[1] = -1;
				}

			}

		} else if (findGRBS.size() == 1) {
			OBFConverter.log
					.postError(OBFConverterRus.text.RU_EN_PARSING_GRBS_VID_NOTIDENTIFIED_ONE);
			result[4] = -1;
			result[1] = -1;
		} else {
			OBFConverter.log
					.postError(OBFConverterRus.text.RU_EN_PARSING_GRBS_VID_NOTIDENTIFIED_MORE);
			result[4] = -1;
			result[1] = -1;
		}

		// find razdel
		List<double[]> findRaz = new ArrayList<double[]>();
		for (double[] rz : newList) {
			if ((rz[3] > 3.5 && rz[3] < 4.5) || (rz[3] > 5.5 && rz[3] < 6.5)) {
				findRaz.add(rz);
			}
		}

		if (findRaz.size() == 1) {

			if (findRaz.get(0) != null
					&& original.indexOf(findRaz.get(0)) != -1) {
				result[2] = original.indexOf(findRaz.get(0));
				newList.remove(findRaz.get(0));
				OBFConverter.log.postSuccess(
						OBFConverterRus.text.RU_EN_PARSING_RAZDEL_FOUND,
						original.indexOf(findRaz.get(0)) + 1);
			} else {
				OBFConverter.log
						.postError(OBFConverterRus.text.RU_EN_PARSING_RAZDEL_NOT_FOUND);
				result[2] = -1;
			}

		} else {
			OBFConverter.log
					.postError(OBFConverterRus.text.RU_EN_PARSING_RAZDELS_NOTIDENTIFIED_MORE);
			result[2] = -1;
		}

	}

	public static String checkDouble(String string) {

		if (string == null || string.isEmpty()) {
			throw new NumberFormatException();
		}
		try {
			Double.valueOf(string);
			return string;
		} catch (NumberFormatException e) {

			if (string.contains(".") || string.contains(",")) {

				string = string.replaceAll(" ", "").replace(",", ".");
				Double.valueOf(string);
				return string;

			} else {
				throw new NumberFormatException();
			}
		}
	}

}
