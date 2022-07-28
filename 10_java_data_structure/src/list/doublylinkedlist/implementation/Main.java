package list.doublylinkedlist.implementation;

import list.linkedlist.implementation.LinkedList;

public class Main {

	public static void main(String[] args) {
		
		LinkedList numbers = new LinkedList();
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
