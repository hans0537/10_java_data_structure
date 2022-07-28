package tree.search;

class Tree1{
	class Node{
		int data;
		Node left;
		Node right;
		Node (int data){
			this.data = data;
		}
	}
	Node root;
	
/*
 * 이진 검색 트리 배열로 생성 및 검색
 */
//	public void makeTree(int[] a) {
//		root = makeTreeR(a, 0, a.length - 1);
//	}
//	public Node makeTreeR(int[] a, int start, int end) {
//		if (start > end) return null;
//		int mid = (start + end) / 2;
//		Node node = new Node(a[mid]);
//		node.left = makeTreeR(a, start, mid - 1);
//		node.right = makeTreeR(a, mid + 1, end);
//		return node;
//	}
//	
//	public void searchBTree (Node n, int find) {
//		if(find < n.data) {
//			System.out.println("Data is smaller than " + n.data);
//			searchBTree(n.left, find);
//		}else if(find > n.data) {
//			System.out.println("Data is larger than " + n.data);
//			searchBTree(n.right, find);
//		}else {
//			System.out.println("Data found");
//		}
//	}
	
/*
 * Tree의 Balance 확인하기
 * 
 */
	Tree1 (int size){
		root = makeBST(0, size - 1);
		root.right.right.right.right = new Node(10);
	}
	
	Node makeBST(int start, int end) {
		if(start > end) return null;
		int mid = (start + end) / 2;
		Node node = new Node(mid);
		node.left = makeBST(start, mid - 1);
		node.right = makeBST(mid + 1, end);
		return node;
	}

	boolean isBalanced(Node root) {
		if(root == null) return true;
		int heightDiff = getHeight(root.left) - getHeight(root.right);
		if(Math.abs(heightDiff) > 1) {
			return false;
		}else {
			return isBalanced(root.left) && isBalanced(root.right);
		}
	}
	
	int getHeight(Node root) {
		if (root == null) return -1;
		return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
	}

}
public class Test2 {

	public static void main(String[] args) {
		
		Tree1 t = new Tree1(10);
		System.out.println(t.isBalanced(t.root));
		
		
//		int[] a = new int[10];
//		for (int i = 0; i < a.length; i++) {
//			a[i] = i;
//		}
//		
//		Tree1 t = new Tree1();
//		t.makeTree(a);
//		t.searchBTree(t.root, 2);
	}
}
