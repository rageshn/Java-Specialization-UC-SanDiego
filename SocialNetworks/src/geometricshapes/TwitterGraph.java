package geometricshapes;

import graph.Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TwitterGraph implements Graph {

	//Adjacency list representation of the graph.
	private HashMap<Integer, HashSet<Integer>> twitterdata = new HashMap<Integer, HashSet<Integer>>();

	/*
	 * This method adds the vertex to the graph.
	 * @param: Vertex to be added.
	 */
	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		twitterdata.put(num, new HashSet<Integer>());
	}

	/*
	 * Adds the edges from one vertex to other.
	 * @param - from: Source of the edge
	 * @param - to: Vertex to which the edge points to.
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		HashSet<Integer> neighbors = twitterdata.get(from);
		neighbors.add(to);
		twitterdata.put(from, neighbors);
	}

	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Exports the graph in adjacency list representation.
	 * @return: Adjacency list representation
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		return twitterdata;
	}

	/*
	 * This method returns the number of neighbors for every vertex in form of HashMap.
	 * @return: the graph data in <Vertex Count, Edge Count> format.
	 */
	public HashMap<Integer, Integer> getGraphSize() {
		HashMap<Integer, Integer> graphSize = new HashMap<Integer, Integer>();
		int edgesCount = 0;
		for(int key : twitterdata.keySet()) {
			edgesCount += twitterdata.get(key).size();
		}
		graphSize.put(twitterdata.keySet().size(), edgesCount);
		return graphSize;
	}
}
