package HW9;

import java.util.Set;
import java.util.Iterator;

public class HW9Test {
	Connectable<Integer> hw;
	
	public HW9Test() {
		//hw = new HWFinal<Integer>(); 
		
		hw.addEdge(0, 1, 1);
		hw.addEdge(0, 2, 2);
		if(hw.addEdge(0, 1, 1)) System.err.println("addEdge admits duplicate edge.");
		hw.addEdge(2, 3, 3);
		hw.addEdge(4, 5, 4);
		hw.addEdge(2, 4, 5);
		hw.addEdge(1, 5, 6);
		hw.addEdge(3, 5, 7);
		hw.addEdge(2, 5, 1);
		if(hw.numNodes() != 6) System.err.println("Wrong # of nodes");
		if(hw.numEdges() != 8) System.err.println("Wrong # of edges");
		if(!hw.removeEdge(2, 5)) System.err.println("Error removing edge");
		if(hw.getWeight(2, 3) != 3) System.err.println("getWeight() error");
		if(hw.setWeight(0, 5, 1)) System.err.println("Setting weight on nonexisting edge");
		
		if(!testSets()) { System.err.println("Failed Set tests."); } 
		displayIterators();
		Connectable<Integer> mst = hw.getPrimMST();
		if(mst != null && testMST(mst)) System.out.println("PrimMST passed.");
		mst = hw.getKruskalMST();
		if(mst != null && testMST(mst)) System.out.println("KruskalMST passed.");
	}
	
	/*
	 * Test nodeSet() and getNeighbors()
	 */
	public boolean testSets() {
		Set<Integer> nodes = hw.nodeSet();
		Iterator<Integer> it = nodes.iterator();
		int[] neighbors = {3, 5, 7, 7, 7, 8};
		int sum = 15; // 0+1+2+3+4+5=15
		while(it.hasNext()) {
			int n = it.next();
			System.out.println(n);
			sum -= n;
			Set<Integer> s = hw.getNeighbors(n);
			Iterator<Integer> it2 = s.iterator();
			while(it2.hasNext()) {
				int n2 = it2.next();
				neighbors[n] -= n2; // not the best way to test nodes, but I'll be lenient 
			}
			if(neighbors[n] != 0) {
				System.err.println("Error in getNeighbors()");
				return false;
			}
		}
		if(sum != 0) {
			System.err.println("Error in nodeSet()");
			return false;
		}
		
		return true;
	}
	
	public void displayIterators() {
		System.out.println("Printing out DFS");
		Iterator<Integer> it = hw.depthFirstIterator();
		while(it.hasNext()) 
			System.out.print(it.next() + " ");
		
		System.out.println("\nPrinting out BFS");
		it = hw.breadthFirstIterator();
		while(it.hasNext())
			System.out.print(it.next() + " ");
	}
	
	public boolean testMST(Connectable<Integer> mst) {
		try{ // The returned MST should have the same node ID and edge weights.
			if(mst.numNodes() != 6) return false;
			if(mst.numEdges() != 5) return false;
			if(mst.getWeight(0, 1) != 1) return false;
			if(mst.getWeight(0, 2) != 2) return false;
			if(mst.getWeight(2, 4) != 5) return false;
			if(mst.getWeight(4, 5) != 4) return false;
			if(mst.getWeight(2, 3) != 3) return false;
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		new HW9Test();

	}

}