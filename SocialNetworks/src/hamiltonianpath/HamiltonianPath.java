package hamiltonianpath;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import geometricshapes.GraphLoader;
import geometricshapes.TwitterGraph;

public class HamiltonianPath {

	/*
	 * This method loads the data set and creates the Graph object
	 *
	 * @param - tGraph: TwitterGraph object which needs to be loaded with data
	 *
	 * @return: Twitter Graph object loaded with data.
	 */
	public TwitterGraph load(TwitterGraph tGraph) {
		tGraph = new TwitterGraph();
		GraphLoader.loadGraph(tGraph, "data/twitter_7.txt");
		return tGraph;
	}

	/*
	 * This method process the Twitter Graph and checks whether the graph has
	 * Hamiltonian Path or not. If there exists a path, that will be returned,
	 * else null will be returned.
	 *
	 * @param - tGraph: Graph to be checked
	 *
	 * @return: Returns the path, else null.
	 */
	public List<Integer> getHamiltonianPath(TwitterGraph tGraph, Integer startNode, Integer destinationNode) {
		HashMap<Integer, HashSet<Integer>> graph = tGraph.exportGraph();
		//Gets all the possible paths between start and destination
		List<List<Integer>> allPossiblePaths = getAllPaths(graph, startNode, destinationNode);
		for (List<Integer> path : allPossiblePaths) {
			//If there exists an edge between the current and the next element in the list,
			//then the path is valid and it will be returned.
			if (isValidPath(graph, startNode, path, destinationNode)) {
				List<Integer> fullPath = new ArrayList<Integer>();
				fullPath.add(0, startNode);
				fullPath.addAll(path);
				fullPath.add(destinationNode);
				return fullPath;
			}
		}
		return null;
	}

	/*
	 * This helper method returns the all the permutation of the paths.
	 *
	 * @param - graph: The graph object
	 *
	 * @param - startNode: starting node in graph
	 *
	 * @param - destination: Destination node in the graph
	 *
	 * @return - List<List<Integer>>: List of all possible paths.
	 */
	private List<List<Integer>> getAllPaths(HashMap<Integer, HashSet<Integer>> graph, Integer startNode,
			Integer destination) {
		List<List<Integer>> allPermutations = null;
		try {
			List<Integer> nodesList = new LinkedList<Integer>();
			for (Integer node : graph.keySet()) {
				//Except for start and destination, all remaining nodes will be added
				if (node != startNode && node != destination) {
					nodesList.add(node);
				}
			}
			allPermutations = generatePerm(nodesList);
		} catch (Exception ex) {
			System.out.println("Exception while getting path: " + ex.getMessage());
		}
		return allPermutations;
	}

	/*
	 * This method computes the permutation of paths between all the vertices
	 * except start and end vertices.
	 *
	 * @param - original: The vertices in the graph except start and
	 * destination.
	 *
	 * @return - List<List<Integer>>: List of all paths
	 */
	private List<List<Integer>> generatePerm(List<Integer> original) {
		if (original.size() == 0) {
			List<List<Integer>> result = new ArrayList<List<Integer>>();
			result.add(new ArrayList<Integer>());
			return result;
		}
		Integer firstElement = original.remove(0);
		List<List<Integer>> returnValue = new ArrayList<List<Integer>>();
		List<List<Integer>> permutations = generatePerm(original);
		for (List<Integer> smallerPermutated : permutations) {
			for (int index = 0; index <= smallerPermutated.size(); index++) {
				List<Integer> temp = new ArrayList<Integer>(smallerPermutated);
				temp.add(index, firstElement);
				returnValue.add(temp);
			}
		}
		return returnValue;
	}

	/*
	 * This method checks whether the path is a valid path or not.
	 *
	 * @param - graph: Graph object
	 *
	 * @param - start: Start Vertex.
	 *
	 * @param - path: path between start and destination vertices.
	 *
	 * @param - end: Destination vertex.
	 *
	 * @return - boolean: returns true if its a valid path, else false.
	 */
	private boolean isValidPath(HashMap<Integer, HashSet<Integer>> graph, Integer start, List<Integer> path,
			Integer end) {
		int index = 0;
		//If the graph has only one element
		if (graph.size() < 2) {
			return false;
		} else if (graph.size() == 2) {
			//If it has only 2 elements, returns true if there exists a path between the start and destination
			if (graph.get(start).contains(end)) {
				return true;
			}
			else {
				return false;
			}
		}
		Integer current = start;
		while (index < path.size()) {
			HashSet<Integer> neighbors_current = graph.get(current);
			Integer nextElement = path.get(index);
			//Checks whether the next element in the list is in current elements' neighbors.
			if (neighbors_current.contains(nextElement)) {
				current = nextElement;
				index++;
			} else {
				return false;
			}
		}
		//Checks whether the destination is last element's neighbor
		HashSet<Integer> last_neighbors = graph.get(current);
		if (last_neighbors.contains(end)) {
			return true;
		}

		return false;
	}

	/*
	 * Computes permutation for provided count - (Not used)
	 */
	private long getPermutation(int graphSize) {
		if (graphSize == 1) {
			return 1;
		}
		return graphSize * getPermutation(graphSize - 1);
	}

	/*
	 * Helper method to print the path
	 */
	private void printPath(List<Integer> path) {
		System.out.println("The path between start and destination is..");
		int index = 0;
		while(index < path.size()) {
			System.out.print(path.get(index) + " -> ");
			index++;
		}
	}

	public static void main(String[] args) {
		HamiltonianPath hPath = new HamiltonianPath();
		TwitterGraph tGraph = hPath.load(new TwitterGraph());
		List<Integer> path = hPath.getHamiltonianPath(tGraph, 1, 5);
		if(path != null) {
			hPath.printPath(path);
		}
		else {
			System.out.println("No hamiltonian path exists between the start and end vertex");
		}
	}

}
