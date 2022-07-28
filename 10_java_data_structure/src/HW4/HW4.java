package HW4;

/**
 * Name: Sungjoo Shin
 * SBUID: 111303511
 */
enum OPER{PLUS, MINUS, TIMES, DIV, POW};

public class HW4{

	Stackable<String> stack; // Don't change this

	public HW4() {
		stack = new stack();
	}

	public boolean isOperator(String s){
		return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("^");
	}

	public int priority(String s) {
		if(s.equals("+")) return 1;
		else if (s.equals("-")) return 1;
		else if (s.equals("*")) return 2;
		else if (s.equals("/")) return 2;
		else if (s.equals("^")) return 3;
		return -1;
	}

	public boolean isIValid(String s) {
		int optCnt = 0;
		int opdCnt = 0;
		int p1 = 0;
		int p2 = 0;

		String[] tmp = s.split("");
		for (String i : tmp) {
			if (isOperator(i)) 		optCnt++;
			else if(!isOperator(i) && !i.equals("(") && !i.equals(")")) opdCnt++;
			else if(i.equals("("))  p1++;
			else if(i.equals(")"))  p2++;
		}

		if (isOperator(tmp[0]) || isOperator(tmp[tmp.length-1]) || p1!=p2 || optCnt != opdCnt -1 ) return false;
		return true;
	}

	public boolean isPValid(String s) {
		int optCnt = 0;
		int opdCnt = 0;

		String[] tmp = s.split("");
		for (String i : tmp) {
			if (isOperator(i)) 		optCnt++;
			else if(!isOperator(i)) opdCnt++;
		}

		if (isOperator(tmp[0]) || isOperator(tmp[1]) || !isOperator(tmp[tmp.length-1]) || optCnt != opdCnt -1 ) return false;
		return true;
	}

	/*
	 * These methods should return the converted infix/postfix. 
	 * If there is a syntax error, return an empty string.
	 * State the big-O (w.r.t. the length of input string) and explain why.
	 */

	/*
	 * infix2Postfix(String infix)
	 * 1. if (isIValid(infix))
	 * Check the String infix is valid to convert that dose not have syntax error.
	 * It takes linear time complexity for checking each letters which affected by infix.length => O(N)
	 * 
	 * 2. for (String i : tmp)
	 * When the String is valid it also check one by one (letters) which have to distinguish operaters and operands 
	 * which affected by infix.length => O(N)
	 * 
	 * 3. Each of stack methods such as push, pop and peek are constant time complexity. However when i is operater we need to compare
	 * with elements which is on top of stack array(peek()). And then when priority of i is less than priority of top of stack array, we
	 * need to add elements to empty String variable (postfix) which takes linear time complexity affected by size of stack array. 
	 * And adding elements is also in for loop so => O(N^2)
	 * 
	 * 4. while(!stack.isEmpty())
	 * When the the for loop is over, put rest of the value from stack array into String variable (postfix) which affected by stack size => O(N)   
	 * 
	 * Therefore this method takes O(N^2) in both worst and best case is
	 */


	public String infix2Postfix(String infix) {
		String postfix = "";
		String[] tmp = infix.split("");

		if (isIValid(infix)) {
			for (String i : tmp) {
				if(i.equals("(")) {
					stack.push(i);
				}else if (i.equals(")")){
					while(!stack.isEmpty() && !stack.peek().equals("(")) {
						postfix += stack.pop();
					}
					stack.pop();
				}
				else if(isOperator(i)) {
					if (stack.isEmpty()) {
						stack.push(i);
					}else {
						if(priority(i)>priority(stack.peek())) {
							stack.push(i);
						}else {
							while(!stack.isEmpty() && priority(i) <= priority(stack.peek())) {
								postfix += stack.pop();
							}
							stack.push(i);
						}
					}
				}else {
					postfix += i;
				}
			}
			while(!stack.isEmpty()) {
				postfix += stack.pop();
			}
			return postfix;
		}else {
			return "";
		}
	}

	/*
	 * postfix2Infix(String postfix)
	 * 
	 * 1. if(isPValid(postfix))
	 * Check the String postfix is valid to convert that dose not have syntax error.
	 * It takes linear time complexity for checking each letters which affected by postfix.length => O(N)
	 * 
	 * 2. for (String i : tmp) 
	 * When the String is valid it also check one by one (letters) which have to distinguish operaters and operands 
	 * which affected by postfix.length => O(N)
	 * 
	 * 3. Each of stack methods such as push, pop and peek are constant time complexity. When i is operator it just
	 * need to pop values from stack array. Or i is operand just push to stack array => O(1)
	 * 
	 * 4. while(!stack.isEmpty())
	 * When the the for loop is over, put rest of the value from stack array into String variable (postfix) which affected by stack size => O(N)
	 * 
	 * Therefore this method takes O(N) in both worst and best case is
	 */

	public String postfix2Infix(String postfix) {
		String infix = "";
		String[] tmp = postfix.split("");
		String tmp1 = "";
		String tmp2 = "";
		if(isPValid(postfix)) {
			for (String i : tmp) {
				if(isOperator(i)) {
					tmp1 = stack.pop();
					tmp2 = stack.pop();
					stack.push(tmp2 + i + tmp1);
				}else {
					stack.push(i);
				}
			}
			while(!stack.isEmpty()) {
				infix += stack.pop();
			}
			return infix;
		}else {
			return "";
		}
	}

	public static void main(String[] args) {
		// The following are just test codes, which you can change all you want.
		HW4 hw = new HW4();
		String infix = "x-y+z/1-7*y"; //"x-y+z/1-7*y" //"a-a-a-a-a*b*b*b*b" 
		String postfix = "xy-z1/+7y*-";//"xy-z1/+7y*-" //"aa-a-a-ab*b*b*b*-"
		if(hw.infix2Postfix(infix).equals(postfix)) System.out.println("Success");
		System.out.println(hw.infix2Postfix(infix));

		if(hw.postfix2Infix(postfix).equals(infix)) System.out.println("Success");
		System.out.println(hw.postfix2Infix(postfix));

		String badInfix = "a+-b";  //"a-a-a-a-a*b*b*b*b*"
		String badPostfix = "a+b"; //"a+b-c"
		if(hw.infix2Postfix(badInfix).equals("")) System.out.println("Success");
		if(hw.postfix2Infix(badPostfix).equals("")) System.out.println("Success");
	}
}
/**
 * Your stack should use the following interface. You may use any of the data structures
 * that we looked at in class so far, but the time complexity for each of the required
 * methods should be minimal.
 * Note: Having more than one classes/interfaces in the same file is generally undesirable.
 * But we'll just keep it this way for the sake of simplicity.
 */
interface Stackable<E> {
	public E peek();
	public void push(E obj);
	public E pop();
	public boolean isEmpty();
}

class stack implements Stackable<String>{
	public String[] arr;
	public int capacity;
	public int arrSize;

	public stack() {
		capacity = 10;
		arrSize = 0;
		arr = new String[capacity];
	}

	public void sizeUp(int num) {
		String[] temp = arr;
		arr = new String[num];
		for (int i = 0; i < arrSize; i++) {
			arr[i] = temp[i];
		}
		capacity = num;
	}

	@Override
	public String peek() {
		if (arrSize == 0) {
			throw new IndexOutOfBoundsException("This is empty");
		}
		return arr[arrSize-1];
	}

	@Override
	public void push(String obj) {
		if (arrSize == capacity) {
			sizeUp(capacity*10);
		}
		arr[arrSize] = obj;
		arrSize++;
	}

	@Override
	public String pop() {
		if (arrSize == 0) {
			throw new IndexOutOfBoundsException("This is empty");
		}
		String result = arr[arrSize-1];
		arr[arrSize-1] = null;
		arrSize--;
		return result;
	}

	@Override
	public boolean isEmpty() {
		return arrSize == 0;
	}
}
