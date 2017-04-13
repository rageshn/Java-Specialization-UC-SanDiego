/**
 *
 */
package roadgraph;

import geography.GeographicPoint;

/**
 * @author ragesh
 * This class is used by the Dijkstra algorithm for finding the shortest path
 */
public class MapNode implements Comparable<MapNode> {

	private GeographicPoint vertex;
	private double distance;
	private double distanceFromGoal;

	public MapNode(GeographicPoint point)
	{
		this.vertex = point;
		this.distance = Double.POSITIVE_INFINITY;
		this.distanceFromGoal = 0;
	}

	public MapNode(GeographicPoint point, Double dist)
	{
		this.vertex = point;
		this.distance = dist;
		this.distanceFromGoal = 0;
	}

	public MapNode(GeographicPoint point, Double dist, Double distFromGoal)
	{
		this.vertex = point;
		this.distance = dist;
		this.distanceFromGoal = dist;
	}

	@Override
	public int compareTo(MapNode other)
	{
		if(this.distance + this.distanceFromGoal > other.distance + other.distanceFromGoal)
		{
			return 1;
		}
		else if(this.distance + this.distanceFromGoal < other.distance + other.distanceFromGoal)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}

	public GeographicPoint getVertex()
	{
		return vertex;
	}

	public double getDistance()
	{
		return distance;
	}

}
