package conference.management;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Combination {

	private static final Combination combination = new Combination();

	private Combination() {

	}

	public static Combination getInstance() {
		return combination;
	}

	public Set<List<Integer>> getTalkCombination(List<Integer> arr, int combinationSize) {
		int data[] = new int[combinationSize];
		Set<List<Integer>> combinationSet = new HashSet<List<Integer>>();
		combinationUtil(combinationSet, arr, data, 0, arr.size() - 1, 0, combinationSize);
		return combinationSet;
	}

	private void combinationUtil(Set<List<Integer>> combinationSet, List<Integer> arr,
			int data[], int start, int end, int index, int r) {
		if (index == r) {
			List<Integer> col = new ArrayList<Integer>();
			for (int j = 0; j < r; j++) {
				col.add(data[j]);
				// System.out.print("" + data[j]);
			}
			//System.out.println();
			combinationSet.add(col);
			return;
		}

		for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
			data[index] = arr.get(i);
			combinationUtil(combinationSet, arr, data, i + 1, end, index + 1, r);
		}
	}

	// Driver program to test above functions
	/*public static void main(String args[]) {

		Combination C = new Combination();
		List<Integer> arr = new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			if(i%3==0){
				arr.add(i);
			}
		}
		int combinationNo = 3;
		System.out.println(C.getTalkCombination(arr, combinationNo));
	}*/
}