/**
 * 
 */
package roadgraph;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

import geography.GeographicPoint;

/**
 * @author Ragesh
 * This class represents the directed road from start point to end point.
 * 
 */
public class Road {
	private GeographicPoint startingPoint;
	private GeographicPoint endPoint;
	private String roadName;
	private String roadType;
	private double length;
	private List<GeographicPoint> intersections;
	
	public Road()
	{
		startingPoint = new GeographicPoint(0,0);
		endPoint = new GeographicPoint(0,0);
		roadName = "";
		roadType = "";
		length = 0;
		intersections = new ArrayList<GeographicPoint>();
	}
	
	public GeographicPoint getStartingPoint()
	{
		return startingPoint;
	}
	
	public GeographicPoint getEndingPoint()
	{
		return endPoint;
	}
	
	public String getRoadName()
	{
		return roadName;
	}
	
	public String getRoadType()
	{
		return roadType;
	}
	
	public double getRoadLength()
	{
		return length;
	}
	
	public void setStartingPoint(GeographicPoint start)
	{
		startingPoint = start;
	}
	
	public void setEndingPoint(GeographicPoint end)
	{
		endPoint = end;
	}
	
	public void setRoadName(String name)
	{
		roadName = name;
	}
	
	public void setRoadType(String type)
	{
		roadType = type;
	}
	
	public void setRoadLength(double len)
	{
		length = len;
	}
	
	public void addIntersection(GeographicPoint intersection)
	{
		if(!intersections.contains(intersection))
		{
			intersections.add(intersection);
		}
	}
	
	public List<GeographicPoint> getIntersections(GeographicPoint start, GeographicPoint end)
	{
		List<GeographicPoint> tempList = new ArrayList<GeographicPoint>();
		tempList.add(start);
		tempList.addAll(intersections);
		tempList.add(end);
		return tempList;
	}

}
