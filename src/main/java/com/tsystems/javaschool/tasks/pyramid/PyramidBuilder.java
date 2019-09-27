package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PyramidBuilder {
	private List<Integer> sortedList;
	private int[][] resultArray;
	/**
	 * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
	 * from left to right). All vacant positions in the array are zeros.
	 *
	 * @param inputNumbers to be used in the pyramid
	 * @return 2d array with pyramid inside
	 * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
	 */
	public int[][] buildPyramid(List<Integer> inputNumbers) {
		sortedList = listSorter(inputNumbers);
		resultArray = buildArray();
		fillResultArray();
		return resultArray;
	}

	private List<Integer> listSorter(List<Integer> list) throws CannotBuildPyramidException {
		List<Integer> result;
		try {
			result = new LinkedList<>(list);
			Collections.sort(result);
		} catch (NullPointerException | OutOfMemoryError e) {
			throw new CannotBuildPyramidException();
		}
		return result;
	}

	private int[][] buildArray() throws CannotBuildPyramidException {
		int progressionSum = sortedList.size();
		double doubleRows = ((Math.sqrt(8 * sortedList.size() + 1) - 1) / 2);
		int rows = (int)doubleRows;
		if (doubleRows != rows) {
			throw new CannotBuildPyramidException();
		}
		int columns = rows * 2 - 1;
		return new int[rows][columns];
	}

	private void fillResultArray() {
		int rows = resultArray.length;
		int columns = resultArray[0].length;
		int k = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < 1 + i; j++) {
				resultArray[i][columns / 2 - i + j * 2] = sortedList.get(k);
				k++;
			}
		}
	}
}
