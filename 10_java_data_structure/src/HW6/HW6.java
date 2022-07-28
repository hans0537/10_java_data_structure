package HW6;

/**
 * Name: Sungjoo Shin
 * SBUID: 111303511
 * Do not use any unauthorized packages.
 * For all recursive methods, feel free to use helper methods.
 */
public class HW6 {

	Node root;
	enum OP {PLUS, TIMES, MINUS, DIVIDE}; // This is just for your reference. You're not required to use this.

	public HW6() {
	}

	//Helper function from my HW4 
	public boolean isOperator(String s){
		return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
	}

	//Helper function from my HW4 
	public int priority(String s) {
		if(s.equals("+")) return 1;
		else if (s.equals("-")) return 1;
		else if (s.equals("*")) return 2;
		else if (s.equals("/")) return 2;
		return -1;
	}

	//Similar helper function from my HW4 
	public boolean isIValid(String s) {
		int optCheck = -1;
		int p1 = 0;
		int p2 = 0;
		String[] tmp = s.split("");
		for (int i = 0; i < tmp.length-1; i++) {
			if(isOperator(tmp[i]) && (isOperator(tmp[i+1]) || tmp[i+1].equals(")"))) {
				optCheck = 1;
			}
		}

		for (String i : tmp) {
			if(i.equals("("))  p1++;
			else if(i.equals(")"))  p2++;
		}

		if (tmp[0].equals("+") || tmp[0].equals("*") || tmp[0].equals("/") || isOperator(tmp[tmp.length-1]) || p1!=p2 || optCheck == 1) return false;
		return true;
	}

	/*
	 *  This method is similar with my HW4 method. 
	 *  This method change input infix(expression) to postfix.
	 *  Because postfix is easy to build Tree.
	 */
	public ArrayList infix2Postfix(String infix) {
		ArrayList postfix = new ArrayList();
		String[] tmp = infix.split("");

		//This can help to handle multi-digit and negative operands from string. And store seperately into ArrayList.
		Queue tmpQ = new Queue();
		ArrayList tmpA = new ArrayList();
		for (int i = 0; i < tmp.length; i++) {
			if(tmp[i].equals("-")) {
				if(i == 0) tmpQ.offer(tmp[i]);
				else if(tmp[i-1].equals("(")) tmpQ.offer(tmp[i]);
				else {
					if(!tmpQ.isEmpty()) {
						String s = "";
						while(!tmpQ.isEmpty()) {
							s += tmpQ.poll();
						}
						tmpA.add(s);
					}
					tmpA.add(tmp[i]);
				}
			}
			else if(tmp[i].equals("+") || tmp[i].equals("*") || tmp[i].equals("/") || tmp[i].equals("(") || tmp[i].equals(")")) {
				if(!tmpQ.isEmpty()) {
					String s = "";
					while(!tmpQ.isEmpty()) {
						s += tmpQ.poll();
					}
					tmpA.add(s);
				}
				tmpA.add(tmp[i]);
			}else tmpQ.offer(tmp[i]);
		}
		if(!tmpQ.isEmpty()) {
			String s = "";
			while(!tmpQ.isEmpty()) {
				s += tmpQ.poll();
			}
			tmpA.add(s);
			s = null;
		}

		//And now change infix to postfix 
		Stack<String> a = new Stack<String>();
		for (int i = 0; i < tmpA.size(); i++) {
			if(tmpA.get(i).equals("(")) {                                    
				a.push(tmpA.get(i));                                 
			}else if (tmpA.get(i).equals(")")){   
				while(!a.isEmpty() && !a.peek().equals("(")) {
					postfix.add(a.pop());               
				}
				a.pop();                                 
			}
			else if(isOperator(tmpA.get(i))) {                           
				if (a.isEmpty()) {                                    
					a.push(tmpA.get(i));                               
				}else {
					if(priority(tmpA.get(i))>priority(a.peek())) {
						a.push(tmpA.get(i));                     
					}else {
						while(!a.isEmpty() && priority(tmpA.get(i)) <= priority(a.peek())) {
							postfix.add(a.pop());                     
						}
						a.push(tmpA.get(i));                               
					}
				}
			}else {
				postfix.add(tmpA.get(i));
			}
		}
		while(!a.isEmpty()) {
			postfix.add(a.pop());                              
		}
		return postfix;
	}

	private class Node {
		Node parent, left, right;
		// Add more necessary fields and methods
		String data;
		public Node(String exp) { // Modify the constructor accordingly
			this.data = exp;
			this.left = null;
			this.right = null;
			this.parent = null;
		}
	}
	/*
	 * Build a parse tree, to be pointed by root, that represents 'expression'.
	 * Implement recursively.
	 */

	/*
	 * This is helper recursive function 
	 * For Base case, when idx is postfix.size, return node from Stack array which will point to the root.
	 * For recursive case, when postfix.get(idx) is operand, create node and push into stack array and call recursive with idx + 1
	 * when postfix.get(idx) is operator, create node with the operator and point each right and left with operands which were stored in stack array
	 * and then push operator node to stack and call recursive until postfix.size().
	 */
	public Node buildTreeR(ArrayList postfix, int idx, Stack<Node> a) {
		// Base case
		if(idx == postfix.size()) {
			return a.pop();
		}

		// Recursive case
		if(!isOperator(postfix.get(idx))) {
			Node node = new Node(postfix.get(idx));
			a.push(node); 
			return buildTreeR(postfix, idx + 1, a);
		}
		else {
			Node node = new Node(postfix.get(idx));
			node.right = a.pop();
			node.right.parent = node;
			node.left = a.pop();
			node.left.parent = node;
			a.push(node);
			return buildTreeR(postfix, idx + 1, a);
		}
	}
	public void buildTree(String expression) {
		if (isIValid(expression)) {
			ArrayList postfix = infix2Postfix(expression);
			Stack<Node> a = new Stack<Node>();
			root = buildTreeR(postfix, 0, a);
		}else {
			return;
		}
	}

	/*
	 * Evaluate the expression represented by 'root'.
	 * Implement recursively. 
	 */

	/*
	 * This is helper recursive function.
	 * For Base case, when the node(root) is null return 0.
	 * For recursive case, double l is left sub tree calculation result and double r is right sub tree calculation result.
	 * Checks whether the current node is an operand, and calculates the left sub tree and the right sub tree, 
	 * and call recursive in order to goes up to the root.
	 * 
	 */
	public double evalR(Node node) {
		// Base case
		if (node == null) return 0;

		// Recursive case
 		if (node.left != null) {
			double l = evalR(node.left);
			double r = evalR(node.right);
			if(isOperator(node.data)) {
				if(node.data.equals("+")) {
					return l + r;
				} else if(node.data.equals("-")) {
					return l - r;
				} else if(node.data.equals("*")) {
					return l * r;
				} else if(node.data.equals("/")) {
					return l / r;
				}
			}
		}
		//When node is leaf node it is operand so change data(string) to double in order to evaluate 
		return Double.parseDouble(node.data);
	}
	public double eval() {
		return evalR(root);
	}

	/*
	 * Evaluate the expression represented by 'root'.
	 * Implement iteratively. 
	 */
	
	/*
	 * This is helper recursive function.
	 * When this function get root node, it change to postfix order and store into String ArrayList separately.
	 * And then when the tmp.get(i) is operand, change to Double type and push into Stack.
	 * When tmp.get(i) is operator, pop two elements in stack and evaluate two operands and push the result into Stack.
	 * Finally return the last elements in the Stack.
	 * 
	 */
	public double evalI(Node node) {
		ArrayList tmp = new ArrayList();
		postOrder(node, tmp);
		
		Stack<Double> s = new Stack<Double>();
		for (int i = 0; i < tmp.size(); i++) {
			if (isOperator(tmp.get(i))) {
				double a = s.pop();
				double b = s.pop();
				if(tmp.get(i).equals("+")) {
					s.push(b + a);;
				} else if(tmp.get(i).equals("-")) {
					s.push(b - a);
				} else if(tmp.get(i).equals("*")) {
					s.push(b * a);
				} else if(tmp.get(i).equals("/")) {
					s.push(b / a);
				}
			}else {
				s.push(Double.parseDouble(tmp.get(i)));
			}
		}
		return s.pop();
	}
	
	public double iterativeEval() {
		return evalI(root);
	}

	/*
	 * Return the original infix notation. You shouldn't just return the stored input string.
	 * Implement recursively.
	 */

	/*
	 * Infix notation is inorder traversal of parse tree.
	 * For base case, if node is null return.
	 * For recursive case, first check the current node has parent. If has no parent, call recursive case of left node and 
	 * get data and then call recursive case of right node. If the current node is "-" or "+" and their parent node is "*" or "/"
	 * add "(" and then call left recursive, get data, call right recursive and finally add ")". If the current node is 
	 * "-" or "+" and their parent node is not "*" or "/" call inorder traversal recursive.
	 * 
	 */
	public void inOrder(Node node, ArrayList tmp) {
		// Base case
		if(node == null) return;

		// Recursive case
		if(node.parent == null) {
			inOrder(node.left, tmp);
			tmp.add(node.data);
			inOrder(node.right, tmp);
		}else if (node.data.equals("-") || node.data.equals("+")) {
			if(node.parent.data.equals("*") || node.parent.data.equals("/")) {
				tmp.add("(");
				inOrder(node.left, tmp);
				tmp.add(node.data);
				inOrder(node.right, tmp);
				tmp.add(")");
			}else {
				inOrder(node.left, tmp);
				tmp.add(node.data);
				inOrder(node.right, tmp);
			}
		}else {
			inOrder(node.left, tmp);
			tmp.add(node.data);
			inOrder(node.right, tmp);
		}
	}

	public String toString() {
		String s = "";
		ArrayList tmp = new ArrayList();
		inOrder(root, tmp);
		for (int i = 0; i < tmp.size(); i++) {
			s += tmp.get(i);
		}
		return s;
	}

	/*
	 * Return the postfix version of the expression.
	 * Implement recursively.
	 */
	
	/*
	 * Postfix notation is postorder traversal of parse tree.
	 * For base case, if node is null return.
	 * For recursive case, first call left recursive and call right recursive and then get node.data. 
	 * 
	 */
	public void postOrder(Node node, ArrayList tmp) {
		// Base case
		if(node == null) return;

		// recursive case
		postOrder(node.left, tmp);
		postOrder(node.right, tmp);
		tmp.add(node.data);

	}
	public String toPostfixString() {
		String s = "";
		ArrayList tmp = new ArrayList();
		postOrder(root, tmp);
		for (int i = 0; i < tmp.size(); i++) {
			s += tmp.get(i);
		}
		return s;
	}

	public static void main(String[] args) {
	}

}

/*
 * From here, I implemented Stack, ArrayList, and Queue data structures.
 * It will be similar with my HW4 Stack implementation.
 * And I only made necessary functions for each data structure.
 */

class Stack<E>{
	public Object[] arr;
	public int capacity;
	public int arrSize;

	public Stack(){
		capacity = 10;
		arrSize = 0;
		arr = new Object[capacity];
	}

	public void sizeUp(int num) {
		Object[] temp = arr;
		arr = new Object[num];
		for (int i = 0; i < arrSize; i++) {
			arr[i] = temp[i];
		}
		capacity = num;
	}

	public E peek() {
		if (arrSize == 0) {
			return null;
		}
		return (E) arr[arrSize-1];
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
			return null;
		}
		E result = (E) arr[arrSize-1];
		arr[arrSize-1] = null;
		arrSize--;
		return result;
	}

	public boolean isEmpty() {
		return arrSize == 0;
	}
}

class ArrayList {
	String[] arr;
	int capacity;
	int arrSize;

	public ArrayList() {
		capacity = 10;
		arr = new String[capacity];
		arrSize = 0;
	}

	public void sizeUp(int num) {
		String[] temp = arr;
		arr = new String[num];
		for (int i = 0; i < arrSize; i++) {
			arr[i] = temp[i];
		}
		capacity = num;
	}

	public String get(int idx) {
		if(arrSize==0) return null;
		return arr[idx];
	}

	public void add(String s) {
		if (arrSize == capacity) {
			sizeUp(capacity*10);
		}
		arr[arrSize] = s;
		arrSize++;
	}

	public int size() {
		return arrSize;
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
