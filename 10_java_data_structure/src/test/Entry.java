package test;

import java.util.Map;

final class Entry<K,V> implements Map.Entry<K,V>{
	private static final boolean BLACK = false;
	K key;
	V value;
	Entry<K, V> left;
	Entry<K, V> right;
	Entry<K, V> parent;
	boolean color = BLACK;
	
	@Override
	public K getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public V getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public V setValue(V value) {
		// TODO Auto-generated method stub
		return null;
	}

}
