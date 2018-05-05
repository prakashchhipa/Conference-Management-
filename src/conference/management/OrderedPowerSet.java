package conference.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrderedPowerSet<E> {
	private List<E> inputList;
	private int N;
	private Map<Integer, List<Set<E>>> map = new HashMap<Integer, List<Set<E>>>();

	
	
	/**
	 * @return the inputList
	 */
	public List<E> getInputList() {
		return inputList;
	}

	/**
	 * @param inputList the inputList to set
	 */
	public void setInputList(List<E> inputList) {
		this.inputList = inputList;
	}

	/**
	 * @return the n
	 */
	public int getN() {
		return N;
	}

	/**
	 * @param n the n to set
	 */
	public void setN(int n) {
		N = n;
	}

	/**
	 * @return the map
	 */
	public Map<Integer, List<Set<E>>> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<Integer, List<Set<E>>> map) {
		this.map = map;
	}

	public OrderedPowerSet(List<E> list) {
		inputList = list;
		N = list.size();
	}

	public List<Set<E>> getPermutationsList(int elementCount) {
		if (elementCount < 1 || elementCount > N) {
			throw new IndexOutOfBoundsException(
					"Can only generate permutations for a count between 1 to "
							+ N);
		}
		if (map.containsKey(elementCount)) {
			return map.get(elementCount);
		}

		ArrayList<Set<E>> list = new ArrayList<Set<E>>();

		if (elementCount == N) {
			list.add(new HashSet<E>(inputList));
		} else if (elementCount == 1) {
			for (int i = 0; i < N; i++) {
				Set<E> set = new HashSet<E>();
				set.add(inputList.get(i));
				list.add(set);
			}
		} else {
			for (int i = 0; i < N - elementCount; i++) {
				@SuppressWarnings("unchecked")
				List<E> subList = (List<E>) list.clone();
				subList.remove(0);
				OrderedPowerSet<E> subPowerSet = new OrderedPowerSet<E>(subList);
				for (Set<E> s : subPowerSet
						.getPermutationsList(elementCount - 1)) {
					Set<E> set = new HashSet<E>();
					set.add(inputList.get(i));
					set.addAll(s);
				}

			}
		}

		map.put(elementCount, list);

		return map.get(elementCount);
	}
	
	public static void main(String[] args){
		List<Integer> integers = new ArrayList<Integer>();
		integers.add(1); integers.add(2);integers.add(3);integers.add(4);
		OrderedPowerSet<Integer> orderedPowerSet = new OrderedPowerSet<Integer>(integers);
		System.out.println(orderedPowerSet.getPermutationsList(3));
		//System.out.println(orderedPowerSet.getPermutationsList(2));
		System.out.println(orderedPowerSet.getMap());
	}
}