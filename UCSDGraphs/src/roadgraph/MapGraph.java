/**
 * @author UCSD MOOC development team and YOU
 *
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.PriorityQueue;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 *
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 2

	//This is in the format as <Source, <Destination, Path>>
	HashMap<GeographicPoint, ArrayList<Node>> paths = null;
	private int numberOfEdges;
	Map<GeographicPoint, ArrayList<GeographicPoint>> adjList = null;
	HashMap<GeographicPoint, Node> nodes = null;
	static int nodesSearched_Dijkstra = 0;
	static int nodesSearched_AStar = 0;

	/**
	 * Create a new empty MapGraph
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2
		nodes = new HashMap<GeographicPoint, Node>();

	}

	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		return nodes.size();
	}

	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		return nodes.keySet();
	}

	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		for(GeographicPoint g: nodes.keySet())
		{
			Node n = nodes.get(g);
			numberOfEdges += n.paths.size();
		}
		return numberOfEdges;
	}



	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
		if(location == null || nodes.containsKey(location))
		{
			return false;
		}
		else
		{
			nodes.put(location,  new Node());
			return true;
		}
	}

	/**
	 * Adds a directed edge to the graph from pt1 to pt2.
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 2

		if(!nodes.containsKey(from) || !nodes.containsKey(to) || length < 0){
			throw new java.lang.IllegalArgumentException("Please provide valid parameters");
		}

		Road rd = createPath(from, to, roadName, roadType, length);

		Node currentNode = nodes.get(from);
		currentNode.vertex = from;
		currentNode.paths.add(rd);
		nodes.put(from, currentNode);
	}

	/**
	 * Creates the path from the source to destination with the properties provided
	 * @param from - Starting point
	 * @param to - Ending point
	 * @param roadName - Name of the road
	 * @param roadType - Type of the road
	 * @param length - Length in kilometers
	 * @return The Road object
	 */
	private Road createPath(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length)
	{
		Road rd = new Road();
		rd.setStartingPoint(from);
		rd.setEndingPoint(to);
		rd.setRoadName(roadName);
		rd.setRoadType(roadType);
		rd.setRoadLength(length);
		return rd;
	}


	/** Find the path from start to goal using breadth first search
	 *
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}

	/** Find the path from start to goal using breadth first search
	 *
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start,
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 2

		Queue<GeographicPoint> nodeQueue = new LinkedList<>();
		HashSet<GeographicPoint> visited = new HashSet<>();
		List<GeographicPoint> parent = new LinkedList<>();
		//<current_Node, parent_Node>
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<>();
		System.out.println("Start: " + start.getX()+","+start.getY());
		nodeQueue.add(start);
		visited.add(start);
		while(!nodeQueue.isEmpty())
		{
			GeographicPoint current = nodeQueue.remove();
			// Hook for visualization.  See writeup.
			nodeSearched.accept(current);
			System.out.println("Current: " + current.getX()+","+current.getY());
			if(current.equals(goal))
			{
				return getPath(parentMap, start, goal);
			}

			Node n = nodes.get(current);
			for(Road rd : n.paths)
			{
				GeographicPoint neighbor = rd.getEndingPoint();
				if(!visited.contains(neighbor))
				{
					visited.add(neighbor);
					parentMap.put(neighbor, current);
					nodeQueue.add(neighbor);
				}
			}
		}
		return null;

	}

	private List<GeographicPoint> getPath(HashMap<GeographicPoint, GeographicPoint> parentMap,
			GeographicPoint start, GeographicPoint goal)
	{
		List<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint current = goal;
		while(current != start)
		{
			path.add(current);
			current = parentMap.get(current);
		}
		path.add(current);
		Collections.reverse(path);
		printPath(path);
		return path;
	}

	private void printPath(List<GeographicPoint> path)
	{
		for(GeographicPoint gp : path)
		{
			System.out.print(gp.getX()+ " " + gp.getY() + " -> ");
		}

	}



	/** Find the path from start to goal using Dijkstra's algorithm
	 *
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 *
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start,
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		PriorityQueue<MapNode> pq = new PriorityQueue<MapNode>();
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		HashMap<GeographicPoint, GeographicPoint> parent = new HashMap<GeographicPoint, GeographicPoint>();
		HashMap<GeographicPoint, Double> distances = new HashMap<GeographicPoint, Double>();
		Set<GeographicPoint> vertices = getVertices();
		for(GeographicPoint vertex : vertices)
		{
			distances.put(vertex, Double.POSITIVE_INFINITY);
		}
		pq.add(new MapNode(start, 0.0));
		while(!pq.isEmpty())
		{
			nodesSearched_Dijkstra++;
			MapNode curr = pq.remove();
			GeographicPoint currentVertex = curr.getVertex();
			// Hook for visualization.  See writeup.
			nodeSearched.accept(currentVertex);
			if(!visited.contains(currentVertex))
			{
				visited.add(currentVertex);
				if(currentVertex.equals(goal))
				{
					return getPath(parent, start, goal);
				}

				Node n = nodes.get(currentVertex);
				for(Road rd : n.paths)
				{
					GeographicPoint neighbor = rd.getEndingPoint();

					if(!visited.contains(neighbor))
					{
						double result = curr.getDistance() + rd.getRoadLength();
						Double neighborDist = distances.get(neighbor);
						if(result < neighborDist)
						{
							distances.put(neighbor, result);
							parent.put(neighbor, currentVertex);
							pq.add(new MapNode(neighbor, result));
						}
					}
				}
			}
		}

		return null;
	}

	/** Find the path from start to goal using A-Star search
	 *
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}

	/** Find the path from start to goal using A-Star search
	 *
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start,
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		PriorityQueue<MapNode> pq = new PriorityQueue<MapNode>();
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		HashMap<GeographicPoint, GeographicPoint> parent = new HashMap<GeographicPoint, GeographicPoint>();
		HashMap<GeographicPoint, Double> distances = new HashMap<GeographicPoint, Double>();
		Set<GeographicPoint> vertices = getVertices();
		for(GeographicPoint vertex : vertices)
		{
			distances.put(vertex, Double.POSITIVE_INFINITY);
		}
		pq.add(new MapNode(start, 0.0, 0.0));
		while(!pq.isEmpty())
		{
			nodesSearched_AStar++;
			MapNode curr = pq.remove();
			GeographicPoint currentVertex = curr.getVertex();
			// Hook for visualization.  See writeup.
			nodeSearched.accept(currentVertex);
			if(!visited.contains(currentVertex))
			{
				visited.add(currentVertex);
				if(currentVertex.equals(goal))
				{
					return getPath(parent, start, goal);
				}

				Node n = nodes.get(currentVertex);
				for(Road rd : n.paths)
				{
					GeographicPoint neighbor = rd.getEndingPoint();

					if(!visited.contains(neighbor))
					{
						double distFromGoal = neighbor.distance(goal);
						double result = curr.getDistance() + rd.getRoadLength() + distFromGoal;
						Double neighborDist = distances.get(neighbor);
						if(result < neighborDist)
						{
							distances.put(neighbor, result);
							parent.put(neighbor, currentVertex);
							pq.add(new MapNode(neighbor, result, distFromGoal));
						}
					}
				}
			}
		}

		return null;
	}



	public static void main(String[] args)
	{
		/* System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(5,1);
		GeographicPoint end = new GeographicPoint(8,-1);

		List<GeographicPoint> route = theMap.bfs(start,end);
		*/

		// You can use this method for testing.

		/* Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);


		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/

		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);

		List<GeographicPoint> route = theMap.dijkstra(start,end);
		System.out.println("\n Dijkstra: " + nodesSearched_Dijkstra + " nodes searched");
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		System.out.println("\n A*: " + nodesSearched_AStar + " nodes searched");
	}

}
