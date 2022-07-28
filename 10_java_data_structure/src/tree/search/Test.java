package tree.search;
class Node{
	int data;
	Node left;
	Node right;
}

class Tree{
	public Node root;

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
	
	public Node makeNode(Node left, int data, Node right) {
		Node node = new Node();
		node.data = data;
		node.left = left;
		node.right = right;
		return node;
	}
	
	public void inorder(Node node) {
		if(node != null) {
			inorder(node.left);
			System.out.println(node.data);
			inorder(node.right);
		}
	}
	
	public void preorder(Node node) {
		if(node != null) {
			System.out.println(node.data);
			preorder(node.left);
			preorder(node.right);
		}
	}

	public void postorder(Node node) {
		if(node != null) {
			postorder(node.left);
			postorder(node.right);
			System.out.println(node.data);
		}
	}
	
}
class BinarySearchTree {

	class Node {
		int data;
		Node left, right;
		
		public Node(int data) {
			this.data = data;
		}
	}
	
	Node root;
	public Node search(Node root, int key) {
		if(root == null || root.data == key) return root;
		if(root.data > key) return search(root.left, key);
		return search(root.right, key);
	}
	
	public void insert(int data) {
		root = insert(root, data);
	}
	
	private Node insert(Node root, int data) {
		if(root == null) {
			root = new Node(data);
			return root;
		}
		
		if(data < root.data) {
			root.left = insert(root.left, data);
		}else if (data > root.data) {
			root.right = insert(root.right, data);
		}
		
		return root;
	}
	
	public void delete(int data) {
		root = delete(root, data);
	}
	
	private Node delete(Node root, int data) {
		if(root == null) return root;
		if(root.data > data) {
			root.left = delete(root.left, data);
		}else if(root.data < data) {
			root.right = delete(root.right, data);
		}else {
			if(root.left == null && root.right == null) return null;
			else if (root.left == null) return root.right;
			else if (root.right == null) return root.left;
			
			root.data = findMin(root.right);
			root.right = delete(root.right, data);
		}
		return root;
	}
	
	int findMin(Node root) {
		int min = root.data;
		while(root.left != null) {
			min = root.left.data;
			root = root.left;
		}
		return min;
	}
	
	public void inorder() {
		inorder(root);
		System.out.println("");
	}
	
	private void inorder(Node root) {
		if(root != null) {
			inorder(root.left);
			System.out.print(root.data + " ");
			inorder(root.right);
		}
	}
}
public class Test {
	public static void main(String[] args) {
//		Tree t = new Tree();
//		Node n4 = t.makeNode(null, 4, null);
//		Node n5 = t.makeNode(null, 5, null);
//		Node n2 = t.makeNode(n4, 2, n5);
//		Node n3 = t.makeNode(null, 3, null);
//		Node n1 = t.makeNode(n2, 1, n3);
//		t.setRoot(n1);
//		
//		t.postorder(t.getRoot());
		
		BinarySearchTree tree = new BinarySearchTree();
		tree.insert(4);
		tree.insert(2);
		tree.insert(1);
		tree.insert(3);
		tree.insert(6);
		tree.insert(5);
		tree.insert(7);
		
		tree.inorder();
		tree.delete(4);
		tree.inorder();
		
	}
}
