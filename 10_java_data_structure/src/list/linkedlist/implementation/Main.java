package list.linkedlist.implementation;

import list.doublylinkedlist.implementation.DoublyLinkedList;

public class Main {

	public static void main(String[] args) {
		
		DoublyLinkedList numbers = new DoublyLinkedList();
		numbers.addLast(30);
		numbers.addLast(20);
		numbers.addLast(10);
		numbers.addFirst(5);
		numbers.add(2,25);
		
		
		System.out.println(numbers);
		System.out.println(numbers.indexOf(50));
		System.out.println(numbers);
	}
}
