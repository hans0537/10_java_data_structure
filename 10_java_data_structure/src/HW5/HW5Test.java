package HW5;

import java.util.*;

public class HW5Test {
	HW5 hw;
	public HW5Test() {
		hw = new HW5();
		verify1();
		verify2();
		verify3();
		verify4();
		verify5();
		verify6();
	}
	
	/*
	 * Base case: For a single-digit input, simply enumerate all possible letters.
	 * Recursive case: Take out the first digit. Make a recursive call on the remaining digits, 
	 *    and sequentially prepend the removed first digit's letters to all the combinations returned
	 *    by the recursive call.
	 */
	public double verify1() { // mnemonics
		System.out.println("\nTesting Mnemonics");
		double iscore = 2, rscore = 12;
		String[] tests = {"2", "67", "323", "852", "222", "2334"}; // TUV, JKL, ABC
		String[][] answers = {{"A", "B", "C"}, {"MP", "MQ", "MR", "MS", "NP", "NQ", "NR", "NS", "OP", "OQ", "OR", "OS"},
				{"DAD", "DAE", "DAF", "DBD", "DBE", "DBF", "DCD", "DCE", "DCF", "EAD", "EAE", "EAF", "EBD", "EBE", "EBF", "ECD", "ECE", "ECF", "FAD", "FAE", "FAF", "FBD", "FBE", "FBF", "FCD", "FCE", "FCF"},
				{"TJA", "TJB", "TJC", "TKA", "TKB", "TKC", "TLA", "TLB", "TLC", "UJA", "UJB", "UJC", "UKA", "UKB", "UKC", "ULA", "ULB", "ULC", "VJA", "VJB", "VJC", "VKA", "VKB", "VKC", "VLA", "VLB", "VLC"},
				{"AAA", "AAB", "AAC", "ABA", "ABB", "ABC", "ACA", "ACB", "ACC", "BAA", "BAB", "BAC", "BBA", "BBB", "BBC", "BCA", "BCB", "BCC", "CAA", "CAB", "CAC", "CBA", "CBB", "CBC", "CCA", "CCB", "CCC"},
				{"ADDG", "ADDH",	"ADDI",	"ADEG",	"ADEH", "ADEI", "ADFG", "ADFH",	"ADFI",
					"AEDG",	"AEDH",	"AEDI",	"AEEG",	"AEEH",	"AEEI",	"AEFG",	"AEFH",	"AEFI",
					"AFDG",	"AFDH",	"AFDI",	"AFEG",	"AFEH",	"AFEI",	"AFFG",	"AFFH",	"AFFI",
					"BDDG",	"BDDH",	"BDDI",	"BDEG",	"BDEH",	"BDEI",	"BDFG",	"BDFH",	"BDFI",
					"BEDG",	"BEDH",	"BEDI",	"BEEG",	"BEEH",	"BEEI",	"BEFG",	"BEFH",	"BEFI",
					"BFDG",	"BFDH",	"BFDI",	"BFEG",	"BFEH",	"BFEI",	"BFFG",	"BFFH",	"BFFI",
					"CDDG",	"CDDH",	"CDDI",	"CDEG",	"CDEH",	"CDEI",	"CDFG",	"CDFH",	"CDFI",
					"CEDG",	"CEDH",	"CEDI",	"CEEG",	"CEEH",	"CEEI",	"CEFG",	"CEFH",	"CEFI",
					"CFDG",	"CFDH",	"CFDI",	"CFEG",	"CFEH",	"CFEI",	"CFFG",	"CFFH",	"CFFI"}};
		for(int i = 0; i < tests.length; i++) {
			String[] res1 = hw.recursiveMnemonics(tests[i]),
					res2 = null;
			if(!checkMnemonic(res1, answers[i])) {
				rscore -= 2;
				System.err.println("\tFailed recursive case " + i);
			}
			res2 = hw.iterativeMnemonics(tests[i]);
			if(!checkMnemonic(res2, answers[i])) {
				iscore -= 0.333;
				System.err.println("\tFailed iterative case " + i);
			}
		}
		System.out.println((rscore + iscore) + "/14");
		return rscore + iscore;
	}
	
	private boolean checkMnemonic(String[] ans, String[] gt) {
		if(ans == null || ans.length != gt.length) return false;
		for(String s : ans) {
			boolean found = false;
			for(String t : gt) 
				if(s.toUpperCase().equals(t)) { found = true; break; }
			if(!found) return false;
		}
		return true;
	}
	
	/*
	 * Base case: Empty set - just return 0.
	 * Recursive case: Take out the first element and make a recursive call on the remaining subset.
	 *                When the recursive call returns a set, sum the removed first element to each
	 *                of the elements in the returned set and add the result back into that set.
	 */
	public double verify2() { // subset sums
		System.out.println("\nTesting subset sums");
		double rscore = 12, iscore = 2;
		int[][] tests = {{0, 1, 2}, {1}, {5, 4, 6, 7}, {1, 1, 2}};
		int[][] answers = {{0, 0, 1, 2, 1, 2, 3, 3}, {0, 1}, {0, 5, 4, 6, 7, 9, 11, 12, 10, 11, 13, 15, 17, 18, 22, 16}, {0, 1, 1, 2, 2, 3, 3, 4}};
		for(int i = 0; i < tests.length; i++) {
			int[] res1 = hw.recursiveSubsetSum(tests[i]),
				  res2 = null;			
			if(!compArrays(res1, answers[i], answers[i].length)) {
				System.err.println("\tFailed recursive case " + i);
				rscore -= 3;
			}
			res2 = hw.iterativeSubsetSum(tests[i]);
			if(!compArrays(res2, answers[i], answers[i].length)) {
				System.err.println("\tFailed iterative case " + i);
				iscore -= 0.5;
			}
			
		}
		System.out.println((rscore + iscore) + "/14");
		return rscore + iscore;
	}
	
	private boolean compArrays(int[] res1, int[] res2, int targetLen) {
		if(res1 == null || res2 == null) return false;
		Arrays.sort(res1);
		Arrays.sort(res2);
		if(res1.length != targetLen && res1.length != res2.length) return false;
		else {
			for(int j = 0; j < res1.length; j++) {
				if(res1[j] != res2[j]) return false;
			}
		}
		return true;
	}
	
	/*
	 * Base case: Empty or single-element list. Just return the list itself.
	 * Recursive case: Take out the first element in the LL, and make a recursive call.
	 *                 Append the first element to the returned list by calling addLast().
	 */
	public double verify3() { // skip-n
		System.out.println("\nTesting Skip-N");
		LinkedList<Integer> ll = null;
		int[][] tests = {{5, 0,1,2,3,4,5,6,7,8,9}, {10, 0,1,2,3,4}, {1, 0,0,0,0}, {2, 1,2,3,4}};
		int[][] answers = {{0,1,2,3,5,6,7,8}, {0,1,2,3,4}, {}, {1, 3}};
		double iscore = 1, rscore = 10;
		for(int i = 0; i < tests.length; i++) {
			ll = new LinkedList<Integer>();
			for(int j = 1; j < tests[i].length; j++) ll.add(tests[i][j]);
			LinkedList<Integer> ans = hw.recursiveSkipNth(ll, tests[i][0]);
			for(int j = 0; j < answers[i].length; j++) 
				if(ans == null || ans.get(j) != answers[i][j]) {
					rscore -= 2.5;
					System.err.println("\tFailed recursive case " + i);
					break;
				}
			LinkedList<Integer> ans2 = hw.iterativeSkipNth(ll, tests[i][0]);
			for(int j = 0; j < answers[i].length; j++) 
				if(ans2 == null || ans2.get(j) != answers[i][j]) {
					iscore -= 0.25;
					System.err.println("\tFailed iterative case " + i);
					break;
				}
			
		}
		System.out.println((rscore + iscore) + "/11");
		return rscore + iscore;
	}
	
	/*
	 * Base case: size <= 1. Return the stack itself.
	 * Recursive case: Pop the first element and make a recursive call on the rest.
	 *                 When the call returns, keep popping the elements until peek() returns
	 *                 a value smaller than the first element. The popped elements should be
	 *                 stored in a separate stack (or an array). Push the first element in, and
	 *                 re-push the popped elements in reverse order.
	 */
	public double verify4() { // stack sort
		System.out.println("\nTesting stack sort");
		int[][] tests = {{0, 0, 0, 1}, {5, 2, 10, 5, -1, 2}, {1, 2, 1, 2, 1}, {1, 1, 1}};
		int[][] answers = {{1, 0, 0, 0}, {10, 5, 5, 2, 2, -1}, {2, 2, 1, 1, 1}, {1, 1, 1}};
		double iscore = 2, rscore = 12;
		for(int i = 0; i < tests.length; i++) {
			Stack<Integer> s = new Stack<Integer>();
			for(int j = 0; j < tests[i].length; j++) s.push(tests[i][j]);
			Stack<Integer> a1 = hw.recursiveSortStack(s);
			for(int j = 0; j < answers[i].length; j++) {
				if(a1 == null || a1.pop() != answers[i][j]) {
					iscore -= 0.5;
					System.err.println("\tFailed iterative case " + i);
					break;
				}
			}
			Stack<Integer> a2 = hw.iterativeSortStack(s);
			for(int j = 0; j < answers[i].length; j++) {
				if(a2 == null || a2.pop() != answers[i][j]) {
					rscore -= 3;
					System.err.println("\tFailed recursive case " + i);
					break;
				}
			}
		}
		System.out.println((rscore + iscore) + "/14");
		return rscore + iscore;
	}
	
	/*
	 * Base case: Empty array. Just return {0, 0}
	 * Recursive case: Remove the first element and make the recursive call. 
	 *                 If the first element is odd, increment the returned result's second element.
	 *                 If it's even, increment the first element.
	 *                 
	 */
	public double verify5() { // even-odd sums
		System.out.println("\nTesting Even-odd sums");
		int[][] tests = {{0, 1, 2, 3}, {1, 1, 1, 1}, {2, 2, 2}, {1}};
		int[][] answers = {{2, 4}, {0, 4}, {6, 0}, {0, 1}};
		double rscore = 10, iscore = 1;
		for(int i = 0; i < tests.length; i++) {
			int[] a1 = hw.iterativeEvenOddSums(tests[i]);
			if(a1 == null || a1[0] != answers[i][0] || a1[1] != answers[i][1]) {
				System.err.println("\tFailed iterative case " + i);
				iscore -= 0.25;
			}
			a1 = hw.recursiveEvenOddSums(tests[i]);
			if(a1 == null || a1[0] != answers[i][0] || a1[1] != answers[i][1]) {
				System.err.println("\tFailed recursive case " + i);
				rscore -= 2.5;
			}
		}
		System.out.println((rscore + iscore) + "/11");
		return rscore + iscore;
	}
	
	/*
	 * Base case: only one element in the queue.
	 * Recursive case: dequeue element, recursively reverse the Q, then enqueue element.
	 */
	public double verify6() {
		double iscore = 1, rscore = 5;
		Queue<Integer> q1 = new LinkedList<Integer>();
		for(int i = 0; i < 100; i++) q1.offer(i); // 0 - 99
		Queue<Integer> q = hw.recursiveReverseQ(q1);
		if(q.size() != 100) rscore = 0;
		else {
			for(int i = 99; i >= 0; i--) {
				if(q.poll() != i) rscore -= 1;
				rscore = Math.max(0,  rscore);
			}
		}
		
		q = hw.iterativeReverseQ(q1);
		if(q.size() != 100) iscore = 0;
		else {
			for(int i = 99; i >= 0; i--) {
				if(q.poll() != i) iscore -= 0.1;
				iscore = Math.max(0,  iscore);
			}
		}
		return rscore + iscore;
	}
	
	/*
	 * Base case: If level is 0, return 0.
	 * Recursive case: Add level^2 to the recursive call on level - 1. 
	 */
	/*
	 * public double verify7() { // ball pyramid
	 * System.out.println("\nTesting Pyramid"); double iscore = 1, rscore = 5; int[]
	 * tests = {5, 2, 6, 1, 10}; for(int i = 0; i < tests.length; i++) { int gt =
	 * pyramid(tests[i]); int b = hw.iterativePyramid(tests[i]); if(b != gt) {
	 * System.err.println("\tFailed iterative case " + i); iscore -= 0.2; } b =
	 * hw.recursivePyramid(tests[i]); if(b != gt) {
	 * System.err.println("\tFailed recursive case " + i); rscore -= 1; } }
	 * System.out.println((rscore + iscore) + "/6"); return rscore + iscore; }
	
	
	private int pyramid(int n) {
		return (n <= 0) ? 0 : n*n + pyramid(n - 1);
	}*/
	
	public static void main(String[] args) {
		
		new HW5Test();
	}

}
