package com.puc.soa.utils;

public class Utils {

	public static <T extends Comparable<T>> void quicksort(T[] values) {
		// Check for empty or null array
		if (values == null || values.length == 0) {
			return;
		}
		quicksort(values, 0, values.length - 1);
	}

	private static <T extends Comparable<T>> void quicksort(T[] values, int low, int high) {
		int i = low, j = high;
		// Get the pivot element from the middle of the list
		T pivot = values[low + (high - low) / 2];

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (values[i].compareTo(pivot) < 0) {
				i++;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (values[j].compareTo(pivot) > 0) {
				j--;
			}

			// If we have found a values in the left list which is larger then
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i <= j) {
				exchange(values, i, j);
				i++;
				j--;
			}
		}
		// Recursion
		if (low < j)
			quicksort(values, low, j);
		if (i < high)
			quicksort(values, i, high);
	}

	private static <T extends Comparable<T>> void exchange(T[] values, int i, int j) {
		T temp = values[i];
		values[i] = values[j];
		values[j] = temp;
	}

}
