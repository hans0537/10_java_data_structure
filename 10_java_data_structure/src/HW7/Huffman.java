package HW7;

/*
 * Do not import anything else.
 * You may add other methods and classes as needed, but do not alter what's given.
 */

import java.util.*; // You can use other classes in this package, but only the ones we learned in class.

public class Huffman {

	private Queueable<Node> pq;
	private Node root;

	public Huffman() {
		pq = new MinPriorityQueue();
	}

	// Helper function for building tree with two minimum node.cnt from pq.
	public Node buildTree(Node left, Node right) {
		int plusCnt = left.cnt + right.cnt;
		Node newNode = new Node(null, plusCnt, left, right);
		left.parent = newNode;
		right.parent = newNode;
		return newNode;
	}

	// Helper function for building root tree with pq. 
	public void buildRoot() {
		Node left = null;
		Node right = null;
		while(true) {
			left = pq.dequeue();
			right = pq.dequeue();
			if(right == null) break;
			if(left != null && right != null) {
				Node newNode = buildTree(left, right);
				pq.enqueue(newNode);
			}
		}
		root = left;
	}

	// Get letters from String msg and calculate each letter's frequency.
	public Queue<String> stringFreqList(String[] msgList){
		if(msgList.length == 0) return null;
		ArrayList<String> temp = new ArrayList<String>();
		for(String i : msgList) {
			if(temp.indexOf(i) < 0) {
				temp.add(i);
				temp.add("1");
			}else {
				int tmp1 = Integer.parseInt(temp.get(temp.indexOf(i) + 1));
				String tmp2 = Integer.toString(tmp1 + 1);
				temp.set(temp.indexOf(i) + 1, tmp2);
			}
		}

		Queue<String> res = new LinkedList<String>();
		for (int i = 0; i < temp.size(); i++) {
			res.offer(temp.get(i));
		}
		return res;
	}
	/*
	 * Perform a Huffman encoding of 'msg', and return a String containing 0s and 1s that
	 * encodes 'msg'. You MUST use a priority queue-based algorithm for this assignment, unlike I did.
	 * 'msg' is guaranteed to consist only of ASCII values (0 - 255). See https://www.asciitable.com/
	 */

	/*
	 * First when I get String 'msg', I calculate each frequency of letter and build node with data(letter) and cnt(frequency) 
	 * and stored into pq(MinPriorityQueue) sorted by ascending frequency order.
	 * When the letter has the same frequency with other letter I gave min priority to the later letter occur in the sentence.
	 * When I finished storing all nodes of letters and frequency in the pq, I started with building root node.
	 * dequeue two elements in pq(it will dequeue the two least frequent letter node everytime in pq because it is a minpriorityqueue)
	 * and build tree with the two elements' frequency(cnt) and enqueue in pq. At the end, one final node will be stored in pq, which is the root node.
	 * Now it is finished that building huffman tree with String 'msg'.
	 * For encoding with huffman tree, start from root node.
	 * For Base Case, when the node is null finish the recursion.
	 * For recursive case, keep call recursive case until the node is leaf node
	 * while traversing the tree, left traversing will be "0" and the right traversing will be "1" and store into String code.
	 * When the node is leaf node store node.data and code into ArrayList.
	 * In the end, the codes of the letters of the string msg are stored in the ArrayList(a) list.
	 * Therefore find each letter of codes in 'msg' order and store into String resCode.
	 * 
	 * Time Complexity: MinPriorityQueue takes O(n) time to initially construct the pq, 
	 * 					and O(logn) time for pq operations such as removing the minimum value and inserting a new element. 
	 * 					Since the for loop is executed, the total execution time of the building huffman tree becomes O(nlogn). 
	 * 					And encode takes O(n)
	 * 					O(nlogn)
	 * Space Complexity: O(n)
	 * 
	 
	 */
	public void encodeRecursion(Node node, String code, ArrayList<String> a) {
		// Base case
		if(node == null) return;

		// Recursive case: count left node for 0
		encodeRecursion(node.left, code + "0", a);
		// Recursive case: count right node for 0
		encodeRecursion(node.right, code + "1", a);

		// if current node is leaf node add data and code to list
		if(node.left == null && node.right == null) {
			if(code.equals("")) { // when 'msg' consists of only one letter.
				a.add(node.data);
				a.add("0");
			}else {
				a.add(node.data);
				a.add(code);
			}
		}
	}

	public String encode(String msg) {

		String[] msgList = msg.split("");
		Queue<String> qs = stringFreqList(msgList);
		
		// first enqueue node with data and cnt (which will be leaf node)
		while(!qs.isEmpty()) {
			String data = qs.poll();
			int cnt = Integer.parseInt(qs.poll());
			Node newNode = new Node(data, cnt, null, null);
			pq.enqueue(newNode);
		}

		// build root node with MinPriorityQueue(pq)
		buildRoot();

		// store letters and codes in ArrayList<String> res
		ArrayList<String> res = new ArrayList<String>();
		encodeRecursion(root, "", res);

		String resCode = "";
		for (int i = 0; i < msgList.length; i++) {
			resCode += res.get(res.indexOf(msgList[i])+1);
		}
		return resCode;
	}

	/*
	 * Perform decoding of the binary string 'code' using the Huffman tree represented by 'this.root'.
	 * This method should return a null in case the given code cannot be decoded.
	 * (e.g., error in code, or Huffman tree doesn't exist)
	 */

	/*
	 * First check the code is possible to decode. Check when to root node is null or is a leaf node.
	 * If the root node is null return null.
	 * If the root node is a leaf node, return root.data as much as code.length.
	 * When I building huffman tree with the 'msg', I set 0 is left and 1 is right.
	 * So check the code one by one and if it is 0, traverse to left node 
	 * and if it is 1, traverse to the right node. If the current node is a leaf node, 
	 * return the data and temp is initialized as the root node again.
	 * When the traverse is finished and decoding is normally completed, the temp node should be initialized as a root node again. 
	 * If it is not a root node, since the corresponding code is different from the huffman tree, decoding is impossible, so null is returned.
	 * 
	 * Time Complexity: we need to check the code whether it is 0 or 1 => O(n) 
	 *                  and then traverse all the nodes of(k) the huffman tree with the 0 or 1  => O(logk)
	 * 					so it will takes O(nlogk) time complexity.
	 * Space Complexity: O(n)
	 *  
	 */
	public String decode(String code) {
		
		String[] codeList = code.split("");
		String res = "";
		
		// When the root node is null
		if(root == null) return null;
		
		// When the root node is a leaf node(when 'msg' consists of only one letter.)
		if(root.left == null && root.right == null) {
			for (int i = 0; i < codeList.length; i++) {
				res += root.data;
			}
			return res;
		}
		
		Node temp = root;
		for (int i = 0; i < codeList.length; i++) {
			if(codeList[i].equals("0")) {
				temp = temp.left;
			}else if(codeList[i].equals("1")) {
				temp = temp.right;
			}else {// when the code has numbers other than 0 and 1
				return null;
			}

			if(temp.left == null && temp.right == null) {
				res += temp.data;
				temp = root;
			}
		}
		
		// When the code fails to traverse the huffman tree(root) 
		if(temp!=root) return null;

		return res;
	}

	/*
	 * This is just for testing purposes, and you do not have to use it in your implementation.
	 * You can use this method to see what the binary representation of the original string looks like.
	 * It's probably useless in this assignment, but just in case you're curious....
	 */
	public String toBinary(String s) {
		String ret = "";
		for(int i = 0; i < s.length(); i++) 
			ret = toBinary(s.charAt(i)) + ret;
		return ret;
	}

	private String toBinary(int ch) {
		ch = 0xFFFF & ch; // Just want to deal with char's
		String ret = "";
		for(int i = 0; i < 16; i++) {
			ret = (ch & 1) + ret;
			ch = ch >> 1;
		}
		return ret;
	}

	public static void main(String[] args) {
		Huffman h = new Huffman();
		String msg = "There is a pleasure in philosophy, and a lure even in the mirages of metaphysics, which every student feels until the coarse necessities of physical existence drag him from the heights of thought into the mart of economic strife and gain.";
		//String msg = "She sells sea shells by the sea shore.\nThe shells she sells are seashells, I\'m sure.\nAnd if she sells seashells on the seashore\nThen I\'m sure she sells seashells.";
		//String msg = "And I shall have some peace there, for peace comes dropping slow, Dropping from the veils of the morning to where the cricket sings; There midnight\'s all a glimmer, and noon a purple glow, And evening full of the linnet\'s wings.";
		//String msg = "Paying anything to roll the dice, just one more time. Some will win, some will lose, some are born to sing the blues. Oh the movie never ends it goes on and on and on and on.";
		//String msg = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
		//String msg = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaazzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
		//String msg = "azazazazazazazazazazazazazazazazazazazazazazazazazazazazazazazazaz";
		//System.out.println(h.toBinary(msg).length());
		String code = h.encode(msg);
		System.out.println(code);
		System.out.println(h.decode(code));
		assert(h.decode(code).equals(msg)); // Original message should be reconstructed.
		assert(code.length() < msg.length() * 16); // Code should be compressed.
	}
}

/*
 * Priority queue implementation.
 */
class MinPriorityQueue implements Queueable{
	ArrayList<Node> list = new ArrayList<Node>();

	@Override
	public void enqueue(Comparable obj) {
		Node n = (Node) obj;
		Queue<Node> temp = new LinkedList<Node>();

		if(list.isEmpty()) list.add(n);
		else {
			int idxCheck = -1;
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).compareTo(n) == 0 || list.get(i).compareTo(n) > 0) {
					idxCheck = i;
					break;
				}
			}
			if(idxCheck == -1) {
				list.add(n);
			}else {
				for (int i = idxCheck; i < list.size(); i++) {
					temp.offer(list.get(i));
				}
				while(list.size() - 1 >= idxCheck) {
					list.remove(idxCheck);
				}
				list.add(n);
				while(!temp.isEmpty()) {
					list.add(temp.poll());
				}
			}
		}
	}

	@Override
	public Comparable dequeue() {
		if(list.isEmpty()) return null;
		Node remove = list.remove(0);
		return remove;
	}
}

interface Queueable<E extends Comparable> {
	public void enqueue(E obj);
	public E dequeue();
}

class Node implements Comparable<Node>{
	Node parent, left, right;
	String data;
	int cnt;
	public Node(String s, int cnt, Node left, Node right) {
		this.data = s;
		this.cnt = cnt;
		this.left = left;
		this.right = right;
		this.parent = null;
	}
	@Override
	public int compareTo(Node o) {
		return this.cnt - o.cnt;
	}
}