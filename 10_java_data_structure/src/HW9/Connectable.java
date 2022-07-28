package HW9;

import java.util.Iterator;
import java.util.Set;

public interface Connectable<E> {
	public Set<E> nodeSet(); // return the set of nodes
	public Set<E> getNeighbors(E node); // return the set of neighbors connected to 'node'
	
	public Iterator<E> breadthFirstIterator(); // return the iterator on nodes in a breadth-first manner
	public Iterator<E> depthFirstIterator(); // same as above in a depth-first manner
		
	public void addNode(E node); // add a new vertex into the current graph.
	public boolean addEdge(E source, E target, double w); // add a new edge. also add non-existing nodes. return false if edge already exists. 
	public boolean removeNode(E node); // remove node. return false if node doesn't exist.
	public boolean removeEdge(E source, E target); // remove edge. return false if edge doesn't exist.
	
	public double getWeight(E source, E target); // return weight of edge (what if edge doesn't exist?).
	public boolean setWeight(E source, E target, double w); // set the weight. existing weights are overwritten. if edge doesn't exist, return false;
	public int numNodes(); // return the total number of nodes. should have O(1) time complexity.
	public int numEdges(); // return the total number of edges. should have O(1) time complexity.
	
	public Connectable<E> getPrimMST(); // HW10: Implement Prim's algorithm
	public Connectable<E> getKruskalMST(); // HW10:Implement Kruskal's algorithm
}
