package HW5;

/**
 * Homework 5: Due Oct. 27th @ 23:59 Korea time.
 * Name: Sungjoo Shin
 * SBUID: 111303511
 * 
 * You are to solve a collection of individual recursion puzzles. For each puzzle, you must implement
 * both a recursive and an iterative version. You are free to write helper methods that will do the
 * actual recursion. Not using a recursion in a recursive version will result in a zero score.
 * For each puzzle, clearly describe what your base and recursive cases are in the header comment.
 * Also provide a description of your approach in a single paragraph.
 * 
 * GENERAL INSTRUCTIONS (failure to follow these instructions will result in a deduction of points):
 * Your final submission should be your own work.
 * Do not import any unauthorized packages. 
 * Do not use any Java data structures unless told to do so.
 * Do not change the class or method names.
 * Submit a single HW5.java file with no package structure.
 * 50% off for late submissions, up to 24 hours.
 * Detailed instructions are given per puzzle.
 * 
 * RUBRIC
 * Correctness (70 points): See the individual method head for individual point allocation. 
 * Comments (30 points): 5 points for each puzzle. This should include your base and recursive 
 *                       case descriptions, as well as the description of your overall approach.
 *                       Place this information in the comment block right before the implementation
 *                       of each puzzle.
 */

import java.util.*;

public class HW5 {

	/*
	 * 1. Enumerate all possible combinations letters in the given phone number digits. Use numCodes for the corresponding mnemonic.
	 * 'n' is a numeric string containing a sequence of digits, each between 2 and 9, inclusive.
	 * E.g., n = "45" --> your answer should be: {"GJ", "GK", "GL", "HK", "HK", "HL", "IJ", "IK", "IL"} 
	 * (not necessarily in that order) This is because you have to produce all possible combinations of
	 * characters that belong to the digits 4 (GHI) and 5 (JKL), respectively.
	 * For the iterative version, you are free to use any data structures mentioned in class.
	 */

	static final String[] numCodes = {"", "", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"};

	/*
	 * For base case if cnt is n.length() or single digit input, enumerate all possible letters
	 * For recursive case put first letter in to result and keep combined with other letters until cnt == n.length()
	 *  
	 */
	public String[] recursiveMnemonics(String n) { // 12 points
		int size = 1;
		String[] temp1 = n.split("");
		for (int i = 0; i < n.length(); i++) {
			if(numCodes[Integer.parseInt(temp1[i])].equals("")) continue;
			size *= numCodes[Integer.parseInt(temp1[i])].length();
		}
		String[] mnemonics = new String[size];

		ArrayList<String> result = new ArrayList<String>();

		//using recursive helper function
		recursiveCombination(n, "", 0, result, temp1);

		for (int i = 0; i < result.size(); i++) {
			mnemonics[i] = result.get(i);
		}
		return mnemonics;
	}

	public void recursiveCombination(String n, String res, int cnt, ArrayList<String> result, String[] temp1) {
		// Base case
		if(cnt == n.length()) {
			result.add(res);
			return;
		}

		// Recursive case
		String[] temp2 = numCodes[Integer.parseInt(temp1[cnt])].split("");
		for (int i = 0; i < numCodes[Integer.parseInt(temp1[cnt])].length(); i++) {
			recursiveCombination(n, res + temp2[i], cnt + 1, result, temp1);
		}
	}


	public String[] iterativeMnemonics(String n) { // 2 points
		int size = 1;
		String[] temp1 = n.split("");

		for (int i = 0; i < n.length(); i++) {
			if(numCodes[Integer.parseInt(temp1[i])].equals("")) continue;
			size *= numCodes[Integer.parseInt(temp1[i])].length();
		}

		String[] mnemonics = new String[size];

		if(n.length() == 0) return mnemonics;

		Queue<String> res = new LinkedList<String>();
		res.offer("");

		for (int i = 0; i < n.length(); i++) {
			while(res.peek().length() == i) {
				String s = res.poll();
				String[] temp2 = numCodes[Integer.parseInt(temp1[i])].split("");
				for (int j = 0; j < temp2.length; j++) {
					res.offer(s + temp2[j]);
				}
			}
		}

		for (int i = 0; i < mnemonics.length; i++) {
			mnemonics[i] = res.poll();
		}

		return mnemonics;
	}

	/*
	 * 2. Print all subset sums of a given array. A value of 0 is always part of the answer.
	 * (i.e., when NO elements are added)
	 * For example, when the input is {1, 2, 3}, the result should be the array 
	 * {0, 1, 2, 3, 3, 4, 5, 6} (singletons and duplicates should also be present)
	 * Do not use any data structures for both cases.
	 */

	/*
	 * For base case when cnt == a.length put subset sum into ss(result) array.
	 * For recursive case, until cnt == a.length keep make a recursive call for
	 * adding current location (a[cnt]) to sum 
	 * or not adding current location(a[cnt]) to sum.
	 * 
	 */

	public int[] recursiveSubsetSum(int[] a) { // 12 points
		int ssSize = (int) (Math.pow(2, a.length));
		int[] ss = new int[ssSize];

		// call recursive helper method
		subsetSum(a, ss, 0, 0);
		return ss;
	}

	public void subsetSum(int[] a, int[] ss, int cnt, int sum) {
		// Base case
		if (cnt == a.length) {
			ssAdd(ss, sum);
			return;
		}

		// Recursive case adding a[cnt] to sum 
		subsetSum(a, ss, cnt + 1, sum + a[cnt]);
		// Recursive case not adding a[cnt] to sum
		subsetSum(a, ss, cnt + 1, sum);
	}

	// Helper function for adding sum to result array
	int idx = 0;
	public void ssAdd(int[] ss, int sum) {
		ss[idx++] = sum;
		if (idx == ss.length) idx = 0;
	}

	public int[] iterativeSubsetSum(int[] a) { // 2 points
		int ssSize = (int) (Math.pow(2, a.length));
		int[] ss = new int[ssSize];
		for (int i = 0; i < (1 << a.length); i++) {
			int sum = 0;
			for (int j = 0; j < a.length; j++) {
				if((i & (1 << j)) == 0) {
					sum += a[j];
				}
			}
			ss[i] = sum;
		}
		return ss;
	}

	/*
	 * 3. Skip every n-th element in the LinkedList (n > 1).
	 * For example, in a list 1-2-3-4-5-6 with n=3, the output is 1-2-4-5.
	 * Use only a LinkedList, and no other data structures.
	 */

	/*
	 * For Base case, if l is empty or n <= 1 return empty list (nthList)
	 * For recursive case, if it is not n-th element take out first element from l and
	 * put into nthList(result), and make a recursive call
	 *  
	 */
	public LinkedList<Integer> recursiveSkipNth(LinkedList<Integer> l, int n) { // 10 points
		LinkedList<Integer> nthList = new LinkedList<Integer>();
		if(n > l.size()) return l;
		nthList = helperRSkipNth(l, nthList, n, 1);
		return nthList;
	}

	public LinkedList<Integer> helperRSkipNth(LinkedList<Integer> l, LinkedList<Integer> nthList, int n, int cnt){
		// Base case
		if (l.isEmpty() || n <= 1) return nthList;

		// Recursive case
		if(n > 1 && cnt % n != 0) nthList.add(l.get(0));
		l.remove(0);
		return helperRSkipNth(l, nthList, n, cnt + 1);

	}

	public LinkedList<Integer> iterativeSkipNth(LinkedList<Integer> l, int n) { // 1 points
		LinkedList<Integer> nthList = new LinkedList<Integer>();
		if (l.isEmpty() || n <= 1) return null;
		else if (n >= l.size()) return l;
		else {
			for (int i = 0; i < l.size(); i++) {
				if((i + 1) % n != 0) nthList.add(l.get(i)); 
			}
			return nthList;
		}
	}
	/*
	 * 4. Sort a given integer Stack in descending order.
	 * For example, if the initial stack is: <TOP> 3-4-1-2-9 <BOTTOM>, the result should be
	 * <TOP> 9-4-3-2-1 <BOTTOM>.
	 * Use only a Stack, and no other data structures.
	 */
	
	/*
	 * For base case when cnt(s.size()-1) is less then 0 return result stack(sortStack)
	 * For recursive case pop the first element push to sortStack and call recursive case
	 * also pop the first element and compare with sortStack and call sortStack(helper method) until cnt < 0
	 */
	
	public Stack<Integer> recursiveSortStack(Stack<Integer> s) { // 12 points
		Stack<Integer> sortStack = new Stack<Integer>();
		Stack<Integer> tmpStack = new Stack<Integer>();
		sortStack = helperRSortStack(s, tmpStack, sortStack, s.size()-1);
		return sortStack;
	}

	public Stack<Integer> helperRSortStack(Stack<Integer> s, Stack<Integer> tmpStack, Stack<Integer> sortStack, int cnt){
		// Base case
		if (cnt < 0) return sortStack;
		// Recursive case
		int tmp = s.get(cnt);
		sortStack(tmpStack, sortStack, tmp);
		return helperRSortStack(s, tmpStack , sortStack, cnt - 1);
	}

	public void sortStack(Stack<Integer> tmpStack, Stack<Integer> sortStack, int tmp) {
		if(sortStack.isEmpty() || sortStack.peek() <= tmp) {sortStack.push(tmp);}
		else {
			for (int i = sortStack.size() - 1; i >= 0; i--) {
				if (sortStack.get(i) > tmp) {
					tmpStack.push(sortStack.remove(i));
				}
			}
			sortStack.push(tmp);
			while(!tmpStack.isEmpty()) {
				sortStack.push(tmpStack.pop());
			}
		}
	}

	public Stack<Integer> iterativeSortStack(Stack<Integer> s) { // 2 points
		Stack<Integer> sortStack = new Stack<Integer>();
		Stack<Integer> tmpStack = new Stack<Integer>();
		if(s.isEmpty()) return sortStack;
		while(!s.isEmpty()) {
			int tmp = s.pop();
			if (sortStack.isEmpty() || sortStack.peek() <= tmp) {
				sortStack.push(tmp);
			}else{
				while(!sortStack.isEmpty() && sortStack.peek() >= tmp) {
					tmpStack.push(sortStack.pop());
				}
				sortStack.push(tmp);
				while(!tmpStack.isEmpty()) {
					sortStack.push(tmpStack.pop());
				}
			}
		}
		return sortStack;
	}

	/*
	 * 5. Even-odd sum. Return the sums of all even and odd numbers, respectively, in a given array.
	 * For example, when the array is {4, 2, 1, 5, 6}, it should return {12, 6} (in that order).
	 * Do not use any data structures.
	 */

	/*
	 * I made two recursive methods one is adding odd another is adding even
	 * For base case, if index is < 0 just return 0
	 * For recursive case, find a[index] is even or odd and keep adding until index < 0
	 * Finally, put each sum into result array
	 */
	
	public int[] recursiveEvenOddSums(int[] a) { // 10 points
		int[] eoSum = new int[2];
		eoSum[0] = sumEven(a, a.length - 1);
		eoSum[1] = sumOdd(a, a.length - 1);
		return eoSum;
	}

	public int sumEven(int[] a, int idx) {
		// Base case
		if (idx < 0) return 0;
		// Recursive case
		else if(a[idx] % 2 == 0) return a[idx] + sumEven(a, idx-1); 
		return 0 + sumEven(a, idx - 1);
	}
	public int sumOdd(int[] a, int idx) {
		// Base case
		if (idx < 0) return 0;
		// Recursive case
		else if (a[idx] % 2 == 1) return a[idx] + sumOdd(a, idx-1); 
		return 0 + sumOdd(a, idx - 1);
	}

	public int[] iterativeEvenOddSums(int[] a) { // 1 point
		int[] eoSum = new int[2];
		if(a.length == 0) return null;
		for (int i = 0; i < a.length; i++) {
			if(a[i] % 2 == 0) eoSum[0] += a[i];
			else eoSum[1] += a[i];
		}
		return eoSum;
	}

	/*
	 * 6. Reverse the order of elements in a given queue.
	 * For example, if the queue has the following elements [A, B, C, D], the result should
	 * be [D, C, B, A], where the last element is the front of the queue.
	 * The contents of the given queue should not change.
	 * Use only a queue, and no other data structures.
	 */

	/*
	 * The base case is when input queue(Queue<Integer> a) is empty return result queue.
	 * The recursive case is poll out the first element form input queue and put in to tmp variable.
	 * And call reversing helper method until a is empty
	 */

	public Queue<Integer> recursiveReverseQ(Queue<Integer> a) { // 5 points
		Queue<Integer> revQ = new LinkedList<Integer>();
		revQ = helperRreverseQ(a, revQ);
		return revQ;
	}

	// reversing helper method
	public Queue<Integer> helperRreverseQ (Queue<Integer> a, Queue<Integer> revQ) {
		// Base case
		if (a.isEmpty()) return revQ;

		// Recursive case
		int tmp = a.poll();
		Queue<Integer> tmpQ = new LinkedList<Integer>();
		if (revQ.isEmpty()) {
			revQ.offer(tmp);
			return helperRreverseQ(a, revQ);
		}
		else {
			while(!revQ.isEmpty()) {
				tmpQ.offer(revQ.poll());
			}
			revQ.offer(tmp);
			while(!tmpQ.isEmpty()) {
				revQ.offer(tmpQ.poll());
			}
			return helperRreverseQ(a, revQ);
		}
	}

	public Queue<Integer> iterativeReverseQ(Queue<Integer> a) { // 1 point
		Queue<Integer> revQ = new LinkedList<Integer>();
		Queue<Integer> tmpQ = new LinkedList<Integer>();
		if (a.isEmpty()) return revQ;

		while(!a.isEmpty()) {
			int tmp = a.poll();
			if (revQ.isEmpty()) {
				revQ.offer(tmp);
			}else{
				while(!revQ.isEmpty()) {
					tmpQ.offer(revQ.poll());
				}
				revQ.offer(tmp);
				while(!tmpQ.isEmpty()) {
					revQ.offer(tmpQ.poll());
				}
			}
		}
		return revQ;
	}

	public static void main(String[] args) {
	}
}