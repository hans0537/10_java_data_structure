package list.arraylist.implementation;

public class ArrayList {

	private int size = 0;
	
	private Object[] elementData = new Object[100];
	
	public boolean addLast(Object element) {	
		elementData[size] = element;
		size++;
		return true;
	}
	
	public boolean addFirst(Object element) {
		return add(0,element);
	}

	public boolean add(int index, Object element) {
		
		for (int i = size; i > index; i--) {
			elementData[i] = elementData[i-1];
		}
		elementData[index] = element;
		size++;
		return true;
	}
	
	public Object remove(int index) {
		Object removed = elementData[index];
		for (int i = index; i < size; i++) {
			elementData[i] = elementData[i+1];
		}
		size--;
		elementData[size] = null;
		return removed;
	}
	
	public Object removeFirst() {
		return remove(0);
	}
	
	public Object removeLast() {
		return remove(size -1);
	}
	
	public Object get(int index) {
		return elementData[index];
	}
	
	public int size() {
		return size;
	}
	
	public int indexOf(Object o) {
		
		for (int i = 0; i < size; i++) {
			if(o.equals(elementData[i])){
				return i;
			}
		}
		return -1;
	}
	
	public String toString() {
		String str = "[";
		for (int i = 0; i < size; i++) {
			if (i<size-1) {
				str += elementData[i] + ", ";
			}else {
				str += elementData[i];
			}
		}
		return str + "]";
	}
}
