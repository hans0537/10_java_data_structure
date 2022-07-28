package test;

public class BST <T extends Comparable<T>>{
	Node root;
	int size;
	
	public BST() {
		root = null;
	}
	
	private static class Node{
		Object element;
		Node left = null, right = null, parent;
		
		Node(Object elem, Node parent){
			this.element = elem;
			this.parent = parent;
		}
	}

	public boolean contains(T obj) {
		Node tmp = root;
		int comp;
		while(tmp!=null) {
			comp = ((Comparable<T>)obj).compareTo((T)tmp.element);
			if(comp == 0) return true;
			else if(comp < 0) tmp = tmp.left;
			else tmp = tmp.right;
		}
		return false;
	}
	
	public boolean add(T obj) {
		if(root == null) {
			root = new Node(obj, null);
			size++;
			return true;
		}else {
			Node tmp = root;
			int comp;
			while(true) {
				comp = ((Comparable<T>)obj).compareTo((T)tmp.element);
				if(comp == 0) return false;
				if(comp < 0) {
					if(tmp.left != null) tmp = tmp.left;
					else {
						tmp.left = new Node(obj, tmp);
						size++;
						return true;
					}
				}else if(tmp.right != null) tmp = tmp.right;
				else {
					tmp.right = new Node(obj, tmp);
					size++;
					return true;
				}
			}
		}
	}
	
	public boolean remove(T obj) {
		Node n = getNode(obj);
		if(n == null) return false;
		deleteEntry(n);
		return true;
	}
	
	private Node getNode(T obj) {
		int comp;
		Node n = root;
		while(n != null) {
			comp = ((Comparable<T>)obj).compareTo((T)n.element); 
			if(comp == 0) return n;
			else if(comp < 0) n = n.left;
			else n = n.right;
		}
		return null;
	}
	
	private void deleteEntry(Node n) {
		size--;
		// 자식이 두개 있으면 석섹서 찾아서 해당 노드 삭제 안하고 석섹서 노드로 교체한다.
		if(n.left != null && n.right != null) {
			Node s = successor(n);
			n.element = s.element;
			n = s;
		}
		// 하나의 자식만 있는거 찾기 왼쪽 or 오른쪽
		Node replacement;
		if(n.left != null) replacement = n.left;
		else replacement = n.right;
		// 그 찾은게 있으면 
		if(replacement != null) {
			replacement.parent = n.parent;
			if(n.parent == null) root = replacement;
			else if(n == n.parent.left) n.parent.left = replacement;
			else n.parent.right = replacement;
		}
		// 없으면
		else if (n.parent == null) root = null;
		else {
			if(n == n.parent.left) n.parent.left = null;
			else n.parent.right = null;
		}
			
	}
	/*
	 * successor 는 root 삭제시 오른쪽 자식의 최솟값을 찾는것
	 * 그래서 그 최솟값을 root 로 해주려고 
	 * https://new93helloworld.tistory.com/116?category=691027
	 */
	private Node successor(Node n) {
		if(n == null) return null;
		else if(n.right != null) {
			Node p = n.right;
			while(p.left != null) p = p.left;
			return p;
		}else {
			Node p = n.parent;
			Node ch = n;
			while(p != null && ch == p.right) {
				ch = p;
				p = p.parent;
			}
			return p;
		}
	}
	public static void main(String[] args) {
		
		
	}
}
