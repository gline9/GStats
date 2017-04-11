package gstats.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import gcore.util.WrapperUtils;

/**
 * class that represents a sample for statistics purposes.
 * 
 * @author Gavin
 *
 */
public class Sample implements Iterable<Double> {

	// stores all of the individual data points as a sorted list
	private ArrayList<Double> sortedList = new ArrayList<>();

	// stores how many duplicates exist in the list by putting them in a map
	// with key being the value and value being how many items it is in
	// the list.
	private HashMap<Double, Integer> duplicateMap = new HashMap<>();

	// stores the size of the sample
	private int size = 0;

	/**
	 * default constructor for the sample, just makes a new one for use.
	 */
	public Sample() {}

	/**
	 * copy constructor for the sample
	 * 
	 * @param s
	 *            sample to copy.
	 */
	public Sample(Sample s) {

		// just call the corresponding copy constructors.
		sortedList = new ArrayList<>(s.sortedList);
		duplicateMap = new HashMap<>(s.duplicateMap);
		size = s.size;
	}

	/**
	 * used if a lot of data needs to be collected at once, this eliminates the
	 * overhead of sorting as the information is being put into the sample.
	 * 
	 * @param list
	 *            list of data points to make a sample from
	 */
	public Sample(ArrayList<Double> list) {

		// set the size to the size of the list
		size = list.size();

		// make a copy of the list
		ArrayList<Double> copy = new ArrayList<Double>(list);

		// sort the copy
		Collections.sort(copy);

		// get all of the duplicate elements and put them in a map
		double previous = Double.NaN;
		for (double value : copy) {
			// check if it is the first value
			if (previous == Double.NaN) {
				previous = value;
				continue;
			}

			// otherwise check if the previous value is the same as the current
			// value
			if (previous == value) {

				// if so check if it is already in the map
				if (duplicateMap.containsKey(value)) {
					// increment the value in the map
					duplicateMap.put(value, duplicateMap.get(value) + 1);
					continue;
				}

				// otherwise add an element in the map for two
				duplicateMap.put(value, 2);
				continue;
			}

			// if not equal set the previous value to the current value
			previous = value;
		}

		// remove all of the previous elements from the list starting from 1
		// since the first element can't be a duplicate.
		for (int i = 1; i < copy.size(); i++) {
			// check if the previous element is the same as this one
			if (copy.get(i).equals(copy.get(i - 1))) {

				// if so remove the element
				copy.remove(i);

				// decrement the pointer as the size is smaller
				i--;
			}

			// if not just continue
		}

		// set the sorted list as copy
		sortedList = copy;

	}

	/**
	 * adds the given data point to the samples data
	 * 
	 * @param dataPoint
	 *            data point to add to the sample
	 * @since Mar 21, 2016
	 */
	public void add(double dataPoint) {
		// check if the data point is already in the sorted list or not.
		if (sortedList.contains(dataPoint)) {

			// make sure there already is an entry in the map, if not create
			// one.
			if (duplicateMap.containsKey(dataPoint)) {
				duplicateMap.put(dataPoint, duplicateMap.get(dataPoint) + 1);
			} else {
				duplicateMap.put(dataPoint, 2);
			}
		} else {
			// if it doesn't contain the point, add it to the array list in the
			// correct order.
			int i = 0;
			for (; i < sortedList.size(); i++) {
				// if the current value is less than the size, put it at the
				// next value.
				if (sortedList.get(i) >= dataPoint) {
					sortedList.add(i, dataPoint);
					break;
				}
			}

			// if it wasn't added, add it to the end
			if (i == sortedList.size()) {
				sortedList.add(dataPoint);
			}
		}

		// increase the size of the sample
		size++;
	}

	/**
	 * removes the given data point from the sample, if the data point isn't in
	 * the sample, nothing happens and false is returned otherwise true is
	 * returned.
	 * 
	 * @param dataPoint
	 *            data point to remove
	 * @return if the removal was successful.
	 * @since Mar 29, 2016
	 */
	public boolean remove(double dataPoint) {
		// make sure the data point is in the list
		if (!sortedList.contains(dataPoint))
			return false;

		// check if it is in the map
		if (duplicateMap.containsKey(dataPoint)) {

			int occurrences = duplicateMap.get(dataPoint);

			// if 2 occurrences remove from the map
			if (occurrences == 2) {
				duplicateMap.remove(dataPoint);
			} else {
				// otherwise decrement the occurrences
				duplicateMap.put(dataPoint, --occurrences);
			}

		} else {
			// if it isn't in the duplicate map remove it from the array list
			sortedList.remove(dataPoint);
		}

		// removal was successful so decrease size and return true
		size--;
		return true;

	}

	/**
	 * removes the smallest data point from the sample, this is to save time on
	 * the search with the normal remove method.
	 */
	public void removeSmallest() {
		double smallest = getSmallest();

		// check if it is a duplicate
		if (duplicateMap.containsKey(smallest)) {
			int occurrences = duplicateMap.get(smallest);

			// if 2 occurrences remove from the map
			if (occurrences == 2) {
				duplicateMap.remove(smallest);
			} else {
				// otherwise decrement the occurrences
				duplicateMap.put(smallest, --occurrences);
			}
		} else {
			// otherwise just remove the first element from the array list
			sortedList.remove(0);
		}
	}

	/**
	 * removes the largest data point from the sample, this is to save time on
	 * the search with the normal remove method.
	 */
	public void removeLargest() {
		double largest = getLargest();

		// check if it is a duplicate
		if (duplicateMap.containsKey(largest)) {
			int occurrences = duplicateMap.get(largest);

			// if 2 occurrences remove from the map
			if (occurrences == 2) {
				duplicateMap.remove(largest);
			} else {
				// otherwise decrement the occurrences
				duplicateMap.put(largest, --occurrences);
			}

		} else {
			// otherwise just remove the last element from the array
			sortedList.remove(sortedList.size() - 1);
		}
	}

	/**
	 * returns a list of the entries that have been added to the sample.
	 * 
	 * @return list of entries in the sample
	 * @since Mar 21, 2016
	 */
	public ArrayList<Double> entries() {
		// just copy and return the sortedList
		return new ArrayList<>(sortedList);
	}

	/**
	 * this method computes the modes of the data set, or which values have the
	 * most occurrences in the given data set. This method is protected as a
	 * user should use the mode method in the sample statistics class.
	 * 
	 * @return modes of the current sample
	 */
	protected double[] modes() {
		// stores all of the modes
		ArrayList<Double> modes = new ArrayList<>();

		// stores the maximum number of occurrences to become mode, if one is
		// found higher the array list is cleared and the new value is set.
		int maxOccurrences = 0;

		for (Entry<Double, Integer> entry : duplicateMap.entrySet()) {
			// check if the occurrences are greater than the maximum number of
			// occurrences
			if (entry.getValue() > maxOccurrences) {
				maxOccurrences = entry.getValue();
				modes.clear();
				modes.add(entry.getKey());
				continue;
			}

			// otherwise check if they are equal
			if (entry.getValue() == maxOccurrences) {
				modes.add(entry.getKey());
			}
		}

		// check if maxOccurrences is still 0
		if (maxOccurrences == 0) {
			modes = new ArrayList<>(sortedList);
		}

		// make a new array for the results
		Double[] results = (Double[]) modes.toArray(new Double[0]);

		return WrapperUtils.toPrimitive(results);
	}

	/**
	 * returns how many occurrances there are of the given value
	 * 
	 * @param value
	 *            value to look up
	 * @return how many occurrances of that value there are.
	 * @since Mar 21, 2016
	 */
	public int occurances(double value) {
		// check if it actually occurs in the array list.
		if (!sortedList.contains(value))
			return 0;

		// if it does check if its index is in the hash map
		if (!duplicateMap.containsKey(value))
			return 1;

		// if everything has passed so far, return how many the duplicate map
		// says there are
		return duplicateMap.get(value);
	}

	/**
	 * returns the smallest element in the sample
	 * 
	 * @return smallest element in sample
	 */
	public double getSmallest() {
		// throw an exception if no elements in the sample yet
		if (size == 0)
			throw new RuntimeException("Cannot get smallest element of an empty set!");

		// return the first element.
		return sortedList.get(0);
	}

	public double getLargest() {
		// throw an exception if no elements in the sample yet
		if (size == 0)
			throw new RuntimeException("Cannot get the largest element of an empty set!");

		// return the last element.
		return sortedList.get(sortedList.size() - 1);
	}

	/**
	 * method for obtaining the size of the sample, this will be equal to how
	 * many times add has been called with values.
	 * 
	 * @return size of the sample.
	 */
	public int size() {
		return size;
	}

	/**
	 * gets the requested entry in the sample, note that this method is slow and
	 * not recommended for statistics calculations.
	 * 
	 * @param n
	 *            index to get from
	 * @return element at that index.
	 */
	public double get(int n) {
		// check for out of bounds
		if (n < 0 || n >= size())
			throw new IllegalArgumentException(n + " is too big of an index for the sample!");

		// check for a zero size sample
		if (size() == 0)
			throw new IllegalArgumentException("Sample must have more than 0 size to find values inside it!");

		// loop through n times to the nth value.
		int index = 0;
		for (double value : this) {
			if (index == n)
				return value;

			index++;
		}

		return 0;
	}

	/**
	 * drops all of the outliers from the sample that lie outside the
	 * iqrMultiplier*iqr from the first or third quartiles.
	 * 
	 * @param iqrMultiplier
	 *            multiplier for distance away from quartiles to remove
	 */
	public void dropOutliers(double iqrMultiplier) {
		double q1 = SampleStatistics.firstQuartile(this);
		double q3 = SampleStatistics.thirdQuartile(this);

		// get the inner quartile range
		double iqr = q3 - q1;

		// these are the maximum and minimum allowable values
		double max = q3 + 1.5 * iqr;
		double min = q1 - 1.5 * iqr;

		// remove any data value that lies outside the range.
		while (getSmallest() < min) {
			removeSmallest();
		}

		while (getLargest() > max) {
			removeLargest();
		}
	}

	/**
	 * method for obtaining the iterator for the class
	 */
	@Override
	public Iterator<Double> iterator() {
		return new Itr();
	}

	/**
	 * class for the iterator that is returned by the {@link Sample#iterator()
	 * iterator()} method.
	 * 
	 * @author Gavin
	 *
	 */
	private class Itr implements Iterator<Double> {

		// stores the pointer for the current index
		private int ptr = 0;

		// stores how many multiple occurrences have been used at that index
		private int multiplePtr = 0;

		private int expectedSize = size;

		@Override
		public boolean hasNext() {
			// make sure the sample wasn't modified
			checkForComodification();

			// if the index is bigger than the size of the array list return
			// false
			if (ptr >= sortedList.size())
				return false;

			// if all of the tests pass return true
			return true;
		}

		@Override
		public Double next() {
			// make sure the sample wasn't modified
			checkForComodification();

			// if the multiple ptr is bigger than 0 then there are duplicates
			if (multiplePtr > 0) {

				// get the value for returning
				double results = sortedList.get(ptr);

				// if the multplePtr value when added by 1 is equal to the
				// number of occurrences in the duplicate map then go to the
				// next element.
				if (++multiplePtr == duplicateMap.get(sortedList.get(ptr))) {
					multiplePtr = 0;
					ptr++;
				}

				// return the results
				return results;

			} else {

				// get the value for returning
				double results = sortedList.get(ptr);

				// if the results are in the duplicate map increment the
				// multiple pointer otherwise increment the normal pointer
				if (duplicateMap.containsKey(results)) {
					multiplePtr++;
				} else {
					ptr++;
				}

				// return the results
				return results;

			}
		}

		public void checkForComodification() {
			if (size != expectedSize) {
				throw new ConcurrentModificationException();
			}
		}

	}

}
