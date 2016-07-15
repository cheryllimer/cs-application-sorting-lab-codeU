/**
 *
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {

		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * Returns a list that might be new.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator)
	{
        if(list.size() == 1)
        	return list;

		int half = list.size()/2;
		List<T> half1 = list.subList(0, half);
        List<T> half2 = list.subList(half, list.size());
        half1 = mergeSort(half1, comparator);
        half2 = mergeSort(half2, comparator);

        List<T> all = new LinkedList<T>();
        int h1 = 0;
        int h2 = 0;
        T thing1 = half1.get(h1);
        T thing2 = half2.get(h2);

        while(all.size() < list.size())
        {

        	if(comparator.compare(thing1, thing2) < 0)
        	{
        		all.add(thing1);
        		h1++;
        		if(h1 == half1.size())
        		{
        			all.add(thing2);
        			h2++;
        			if(h2 < half2.size())
        				all.add(half2.get(h2));
        		}
        		else
        		{
        			thing1 = half1.get(h1);
        		}
        	}
        	else
        	{
        		all.add(thing2);
        		h2++;
        		if(h2 == half2.size())
        		{
        			all.add(thing1);
        			h1++;
        			if(h1 < half1.size())
        				all.add(half1.get(h1));
        		}
        		else
        		{
        			thing2 = half2.get(h2);
        		}
        	}
        }

        return all;
	}


	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator)
	{
        PriorityQueue<T> heap = new PriorityQueue<T>();

        for(T current : list)
        {
        	heap.offer(current);
        }

        list.clear();

        while(!heap.isEmpty())
        {
        	list.add(heap.poll());
        }
	}


	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 *
	 * @param k
	 * @param list
	 * @param comparator
	 * @return
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator)
	{
        PriorityQueue<T> heap = new PriorityQueue<T>();
        List<T> topK = new LinkedList<T>();
        T temp;

		for(T current : list)
        {
			if(heap.size() < k)
        		heap.offer(current);
        	else
        	{
        		temp = heap.poll();
        		if(comparator.compare(current, temp) > 0)
        			heap.offer(current);
        		else
        			heap.offer(temp);
        	}
        }

		while(!heap.isEmpty())
			topK.add(heap.poll());

		return topK;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));

		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};

		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
