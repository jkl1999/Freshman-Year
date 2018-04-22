package apps;

import structures.*;
import java.util.ArrayList;

import apps.PartialTree.Arc;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	// steps 1 and 2
	public static PartialTreeList initialize(Graph graph) {
	
		/* COMPLETE THIS METHOD */
		
		PartialTreeList L = new PartialTreeList();
		
		for(int i = 0; i < graph.vertices.length; i++) {
			Vertex V = graph.vertices[i];
			PartialTree T = new PartialTree(V); //Create a partial tree T containing only v.
			V.parent = T.getRoot(); //mark V as belonging to T
			
			MinHeap<PartialTree.Arc> P = new MinHeap<PartialTree.Arc>();
			P = T.getArcs();//Create a priority queue (heap) P and associate it with T.
			
			Vertex.Neighbor tempNeighbor = V.neighbors;
			
			
			while(tempNeighbor!=null) {
				
			PartialTree.Arc	newArc = new PartialTree.Arc(V, tempNeighbor.vertex, tempNeighbor.weight);
				
			P.insert(newArc); //insert all of the arcs (edges) connected to v into P. The lower the weight on an arc, the higher its priority.
			
			tempNeighbor = tempNeighbor.next;
				
			}
			
			
			
			L.append(T); //Add the partial tree T to the list L.
		}
		
		
		
		return L;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	//steps 3-9
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		/* COMPLETE THIS METHOD */
		
		ArrayList<PartialTree.Arc> MST = new ArrayList<PartialTree.Arc>();
		
		while(ptlist.size()>0) {
		
		//Remove the first partial tree PTX from L. Let PQX be PTX's priority queue.	
			
		PartialTree X = ptlist.remove();
		
		MinHeap<PartialTree.Arc> Q = X.getArcs();
		
		//Remove the highest-priority arc from PQX. Say this arc is α. Let v1 and v2 be the two vertices connected by α, where v1 belongs to PTX.
		
		PartialTree.Arc a = Q.deleteMin();;
		
		while(X.getRoot().equals(a.v2.getRoot())) {
		
		//If v2 also belongs to PTX, go back to Step 4 and pick the next highest priority arc, otherwise continue to the next step.

		if(X.getRoot().equals(a.v2.getRoot())) {
			a=Q.deleteMin();
		}
		
		}
		
		//Report α - this is a component of the minimum spanning tree.
		
		
		MST.add(a);
		
		
		//Find the partial tree PTY to which v2 belongs. Remove PTY from the partial tree list L. Let PQY be PTY's priority queue.
		
		PartialTree Y = ptlist.removeTreeContaining(a.v2.parent);
		MinHeap<PartialTree.Arc> Q2 = Y.getArcs();
		
		//Combine PTX and PTY. This includes merging the priority queues PQX and PQY into a single priority queue. Append the resulting tree to the end of L.
		if(Y!=null) {	
		
			Y.getRoot().parent = X.getRoot();
			X.merge(Y);
			Q.merge(Q2);
		
			ptlist.append(X);
		}

		}
		return MST;
	}
}
