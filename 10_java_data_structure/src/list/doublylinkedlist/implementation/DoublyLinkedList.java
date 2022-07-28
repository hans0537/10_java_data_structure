package list.doublylinkedlist.implementation;

public class DoublyLinkedList {

	private Node head;
	private Node tail;
	private int size = 0;
	
	private class Node{
		private Object data;
		private Node next;
		private Node prev;
		
		public Node(Object input) {
			this.data = input;
			this.next = null;
			this.prev = null;
		}
		
		public String toString() {
			return String.valueOf(this.data);
		}	
	}
	
	public void addFirst(Object input) {
		Node newNode = new Node(input);
		newNode.next = head;
		
		if(head != null) head.prev = newNode;
		head = newNode;
		size++;
		
		if(head.next==null) {
			tail = head;
		}
	}
	
	public void addLast(Object input) {
		Node newNode = new Node(input);
		if(size==0) {
			addFirst(input);
		}else {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
			size++;
		}
	}

	Node node(int index) {
		if(index< size/2) {
			Node x = head;
			for (int i = 0; i < index; i++) {
				x = x.next;
			}
			return x;
		}else {
			Node x = tail;
			for (int i = size - 1; i > index; i--) {
				x = x.prev;
			}
			return x;
		}
	}
	
	public void add(int k, Object input) {
		if (k==0) {
			addFirst(input);
		}else {
			Node temp1 = node(k-1);
			Node temp2 = temp1.next;
			Node newNode = new Node(input);
			temp1.next = newNode;
			newNode.next = temp2;
			if(temp2 != null) {
				temp2.prev = newNode;
			}
			newNode.prev = temp1;
			size++;
			if (newNode.next == null) {
				tail = newNode;
			}
		}
	}
	
	public String toString() {
		if(head == null) {
			return "";
		}
		Node temp = head;
		String str = "[";
		
		while(temp.next!=null) {
			str += temp.data + ", ";
			temp = temp.next;
		}
		str += temp.data;
		
		return str + "]";
	}
	
	public Object removeFirst() {
		Node temp = head;
		head = head.next;
		
		Object returnData = temp.data;
		
		temp = null;
		
		if (head != null) {
			head.prev = null;
		}
		size--;
		return returnData;
	}
	
	public Object remove(int k) {
		if(k == 0) {
			return removeFirst();
		}
		Node temp = node(k-1);
		Node todoDelete = temp.next;
		temp.next = temp.next.next;
		if(temp.next != null) {
			temp.next.prev = temp;
		}
		Object returnData = todoDelete.data;
		if (todoDelete == tail) {
			tail = temp;
		}
		todoDelete = null;
		size--;
		
		return returnData;
	}
	
	public Object removeLast() {
		return remove(size-1);
	}
	
	public int size() {
		return size;
	}
	
	public Object get(int k) {
		Node temp = node(k);
		return temp.data;
	}
	
	public int indexOf(Object data) {
		Node temp = head;
		int idx = 0;
		
		while(temp.data != data) {
			temp = temp.next;
			idx++;
			if(temp==null) {
				return - 1;
			}
		}
		return idx;
	}
}
