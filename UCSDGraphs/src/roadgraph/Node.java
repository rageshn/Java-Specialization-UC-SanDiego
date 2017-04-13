/**
 *
 */
package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;
/**
 * @author Ragesh
 * This represents a particular node in the graph and the Road to get there
 * This is used in BFS algorithm
 */
public class Node{
	public GeographicPoint vertex;
	public List<Road> paths = new ArrayList<Road>();

	public GeographicPoint getNodeValue()
	{
		return vertex;
	}

	public List<Road> getPaths()
	{
		return paths;
	}
}
