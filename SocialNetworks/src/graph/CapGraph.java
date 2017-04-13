/**
 *
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import util.GraphLoader;

/**
 * @author Your name here.
 *
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {

	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	private HashMap<Integer, HashSet<Integer>> adjListMap;
	private List<Graph> subGraphs = null;

	public CapGraph() {
		adjListMap = new HashMap<Integer, HashSet<Integer>>();
	}

	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		HashSet<Integer> neighbors = new HashSet<Integer>();
		adjListMap.put(num, neighbors);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		(adjListMap.get(from)).add(to);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		Graph egonet = new CapGraph();
		if (!adjListMap.containsKey(center))
		{
			return egonet;
		}
		HashSet<Integer> center_neighbors = adjListMap.get(center);
		egonet.addVertex(center);
		for(int neighbor : center_neighbors)
		{
			egonet.addVertex(neighbor);
			egonet.addEdge(center, neighbor);
		}
		for(int neighbor : center_neighbors)
		{
			HashSet<Integer> neighbor_2 = adjListMap.get(neighbor);
			for(int n : neighbor_2)
			{
				if(center_neighbors.contains(n)) {
					egonet.addEdge(neighbor,n);
				}
			}
		}
		return egonet;
	}


	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		try
		{
			Stack<Integer> vertices = this.getVertices();
			Stack<Integer> finished = DepthFirstSearch(this, vertices);
			Graph transposedGraph = transpose(this);
			Stack<Integer> finished_transpose = DepthFirstSearch(transposedGraph, finished);
		}
		catch(Exception ex) {
			System.out.println("Exception in Get SCC: " + ex.getMessage());
		}

		return subGraphs;
	}

	private Stack<Integer> DepthFirstSearch(Graph G, Stack<Integer> vertices) {
		subGraphs = new ArrayList<Graph>();
		Stack<Integer> finished = new Stack<Integer>();
		try {
			Set<Integer> visited = new HashSet<Integer>();
			while(!vertices.isEmpty())
			{
				int vertex = vertices.pop();
				if(!visited.contains(vertex))
				{
					Graph subGraph = new CapGraph();
					subGraph = DFS_Visit(G, vertex, visited, finished);
					subGraphs.add(subGraph);
				}
			}
		}
		catch(Exception ex) {
			System.out.println("Exception in DFS: " + ex.getMessage());
		}
		return finished;
	}

	private Graph DFS_Visit(Graph G, Integer vertex, Set<Integer> visited, Stack<Integer> finished) {
		Graph subGraph = new CapGraph();
		try {
			if(vertex != null) {
				visited.add(vertex);
				HashMap<Integer, HashSet<Integer>> g = G.exportGraph();
				HashSet<Integer> neighbors = g.get(vertex);
				for(int neighbor : neighbors)
				{
					if (!visited.contains(neighbor))
					{
						subGraph = DFS_Visit(G, neighbor, visited, finished);
					}
				}
				finished.push(vertex);
				subGraph.addVertex(vertex);
			}
		}
		catch(Exception ex) {
			System.out.println("Excpetion in DFS Visit:" + ex.getMessage());
		}
		return subGraph;
	}

	private Stack<Integer> getVertices() {
		Stack<Integer> vertices = new Stack<Integer>();
		try {
			Set<Integer> vertices_set = adjListMap.keySet();
			for(int v : vertices_set) {
				vertices.add(v);
			}
		}
		catch(Exception ex) {
			System.out.println("Exception in Get Vertices : " + ex.getMessage());
		}
		return vertices;
	}

	private Graph transpose(Graph G) {
		Graph transposed = new CapGraph();
		try {
			HashMap<Integer, HashSet<Integer>> exported = G.exportGraph();
			for(int vertex : exported.keySet()) {
				HashSet<Integer> neighbors = exported.get(vertex);
				if (neighbors != null || neighbors.size() > 0) {
					for(int neighbor : neighbors) {
						transposed.addVertex(neighbor);
						transposed.addEdge(neighbor, vertex);
					}
				}
			}
		}
		catch(Exception ex) {
			System.out.println("Exception in graph transpose: " + ex.getMessage());
		}
		return transposed;
	}

	private static void printGraph(Graph G) {
		HashMap<Integer, HashSet<Integer>> graph = G.exportGraph();
		for(int vertex : graph.keySet()) {
			HashSet<Integer> neighbors = graph.get(vertex);
			System.out.print(" " + vertex + " -> ");
			for(int v : neighbors) {
				System.out.print(v + " - ");
			}
			System.out.println();
		}
	}
	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		return adjListMap;
	}

	/*public static void main(String[] args) {
		Graph G = new CapGraph();
		GraphLoader.loadGraph(G, "data/scc/test_7.txt");
		List<Graph> graphSCCs = G.getSCCs();
	}*/
}
