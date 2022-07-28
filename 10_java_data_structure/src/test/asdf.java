package test;

class Stack<E>{
	public E[] arr;
	public int capacity;
	public int arrSize;

	public Stack(){
		capacity = 10;
		arrSize = 0;
		arr = (E[]) new String[capacity];
	}

	public void sizeUp(int num) {
		E[] temp = arr;
		arr = (E[]) new String[num];
		for (int i = 0; i < arrSize; i++) {
			arr[i] = (E) temp[i];
		}
		capacity = num;
	}

	public E peek() {
		if (arrSize == 0) {
			throw new IndexOutOfBoundsException("This is empty");
		}
		return arr[arrSize-1];
	}

	public void push(E obj) {
		if (arrSize == capacity) {
			sizeUp(capacity*10);
		}
		arr[arrSize] = obj;
		arrSize++;
	}

	public E pop() {
		if (arrSize == 0) {
			throw new IndexOutOfBoundsException("This is empty");
		}
		E result = arr[arrSize-1];
		arr[arrSize-1] = null;
		arrSize--;
		return result;
	}

	public boolean isEmpty() {
		return arrSize == 0;
	}
}

class Queue {
	String[] arr;
	int capacity;
	int front;	
	int back;
	
	public Queue() {
		capacity = 10;
		arr = new String[capacity];
		front = 0;
		back = 0;
	}
	
	public void sizeUp(int num) {
		String[] temp = arr;
		arr = new String[num];
		for (int i = front; i <= back; i++) {
			arr[i] = temp[i];
		}
		capacity = num;
	}
	
	public void offer(String s) {
		if (back == capacity - 1) {
			sizeUp(capacity*10);
		}
		arr[back] = s;
		back++;
	}

	public String poll() {
		if (front == back) {
			return null;
		}
		String result = arr[front];
		arr[front] = null;
		front++;
		return result;
	}
	
	public boolean isEmpty() {
		return front == back;
	}
}
public class asdf {

	public static int recAvgR(int[] input, int idx, int total) {
		if(idx == input.length) return total;
		return recAvgR(input, idx + 1, total+input[idx]);
	}
	public static double recAvg(int[] input) {
		int total = recAvgR(input,0,0);
		return total / (double)input.length;
	}

	public static String wordRevR(Stack<String> tmpStack, String res) {
		if(tmpStack.isEmpty()) return res;
		
		return wordRevR(tmpStack, res + tmpStack.pop() + " ");
	}
	public static String wordRev(String input) {
		String res = "";
		String[] tmp = input.split(" ");
		Stack<String> tmpStack = new Stack<String>(); 
		for (int i = 0; i < tmp.length; i++) {
			tmpStack.push(tmp[i]);
		}
		res = wordRevR(tmpStack, res);
		
		return res;
	}
	
	public static void queue(String[] s) {
		Queue q = new Queue();
		for (int i = 0; i < s.length; i++) {
			q.offer(s[i]);
		}
		for (int i = 0; i < s.length; i++) {
			System.out.println(q.poll());
		}
		
		System.out.println(q.isEmpty());
	}
	public static void main(String[] args) {
		int[] a = {1,2,3,4,5};
		System.out.println(recAvg(a));
		
		System.out.println(wordRev("example word sequence"));
		String s = "example word sequence";
		System.out.println(s.length());
		
		String[] asd = "1234567".split("");
		queue(asd);
	
	}


}
