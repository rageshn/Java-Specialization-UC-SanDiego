package geometricshapes;

import java.util.HashSet;
import java.util.HashMap;
import graph.Graph;

public class GeoShapes {

	/*
	 * This method loads the twitter data set and adds the vertices and edges.
	 * @parameter: Twitter Graph object which will be loaded from the data set
	 * @return: Twitter Graph object
	 */
	public TwitterGraph load(TwitterGraph tGraph) {
		tGraph = new TwitterGraph();
		GraphLoader.loadGraph(tGraph, "data/twitter_combined.txt");
		return tGraph;
	}

	/*
	 * This method computes the number of triangles in the graph.
	 * @param: Twitter Graph in which the computation will be performed.
	 * @return: Number of triangles
	 */
	public int getTriangles(TwitterGraph tGraph) {
		int triangles_count = 0;

		try {
			HashMap<Integer, HashSet<Integer>> graph_data = tGraph.exportGraph();
			//for every vertex
			for (int node_A : graph_data.keySet()) {
				HashSet<Integer> neighbors_A = graph_data.get(node_A);
				//for every A's neighbors
				for (int node_B : neighbors_A) {
					HashSet<Integer> neighbors_B = graph_data.get(node_B);
					//For every B's neighbors
					for (int node_C : neighbors_B) {
						if(node_B != node_C) {
							HashSet<Integer> neighbors_C = graph_data.get(node_C);
							//If A is available in C's neighbors
							if (neighbors_C.contains(node_A)) {
								triangles_count++;
							}
						}
					}
				}

			}
		} catch (Exception ex) {
			System.out.println("Exception while finding triangles: " + ex.getMessage());
			System.out.println("Stack Trace: " + ex.getStackTrace());
		}
		return triangles_count/3;
	}

	public static void main(String[] args) {
		GeoShapes gShapes = new GeoShapes();

		System.out.println("Loading graph");
		long graphLoadStart = System.nanoTime();
		TwitterGraph twitterGraph = gShapes.load(new TwitterGraph());
		long graphLoadStop = System.nanoTime();
		double graphLoadTimeTaken = ((graphLoadStop - graphLoadStart) / 1000000000.0);
		System.out.println("Graph loaded - Time Taken: " + graphLoadTimeTaken);

		HashMap<Integer, Integer> gDetails = twitterGraph.getGraphSize();
		for (int key : gDetails.keySet()) {
			System.out.println("Vertices: " + key + " - Edges: " + gDetails.get(key));
		}

		System.out.println("Computing triangles count");
		long triComputeStart = System.nanoTime();
		int triangles_count = gShapes.getTriangles(twitterGraph);
		long triComputeStop = System.nanoTime();
		double triComputeTimeTaken = ((triComputeStop - triComputeStart) / 1000000000.0);
		System.out.println("Time taken: " + triComputeTimeTaken);

		System.out.println("Triangles Count: " + triangles_count);
	}
}
