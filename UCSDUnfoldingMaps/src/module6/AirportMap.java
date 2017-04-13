package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	public CommonMarker lastSelectedAirport;
	public CommonMarker lastClickedAirport;
	public int sourceCount = 0;
	public int DestinationCount = 0;
	List<String> destinations;
	
	public void setup() {
		// setting up PAppler
		size(800,600, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 750, 550);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
	
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
		
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
		
			System.out.println(sl.getProperties());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
		}
		
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		
		map.addMarkers(airportList);
		
	}
	
	@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelectedAirport != null) {
			lastSelectedAirport.setSelected(false);
			lastSelectedAirport = null;
		
		}
		selectMarkerIfHover(airportList);
		//selectMarkerIfHover(cityMarkers);
		//loop();
	}
	
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// Abort if there's already a marker selected
		if (lastSelectedAirport != null) {
			return;
		}
		
		for (Marker m : markers) 
		{
			CommonMarker marker = (CommonMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelectedAirport = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	
	@Override
	public void mouseClicked()
	{
		if (lastClickedAirport != null) {
			unhideMarkers();
			
			lastClickedAirport = null;
		}
		else if (lastClickedAirport == null) 
		{
			checkAirportsForClick();
		}
	}
	
	private void unhideMarkers() {
		for(Marker marker : routeList) {
			marker.setHidden(false);
		}
			
		for(Marker marker : airportList) {
			marker.setHidden(false);
		}
	}
	
	private void checkAirportsForClick()
	{
		if (lastClickedAirport != null) return;
		destinations = new ArrayList<String>();
		for(Marker m : airportList)
		{
			CommonMarker marker = (CommonMarker)m;
			if(!marker.isHidden() && marker.isInside(map, mouseX, mouseY))
			{
				lastClickedAirport = marker;
				String code = lastClickedAirport.getProperty("code").toString();
				code = code.substring(1, code.length() - 1);
				int count = 0;
				Object[] routesArray = routeList.toArray();
				for(int i = 0 ; i < routesArray.length; i++) {
					Marker mark = (Marker)routesArray[i];
					if(code.toLowerCase().equals(mark.getProperty("sourcecode").toString().toLowerCase()))
					{
						String destination = mark.getProperty("destinationcode").toString();
						destinations.add(destination);
					}
				}
				for(Marker mHide : airportList)
				{
					String airportCode = mHide.getProperty("code").toString();
					airportCode = airportCode.substring(1, airportCode.length() - 1);
					if (mHide != lastClickedAirport)
					{
						mHide.setHidden(true);
					}
					
					if(destinations.contains(airportCode))
					{
						mHide.setHidden(false);
					}
					
				}
				for(Marker mHide : routeList)
				{
					if(code.toLowerCase().equals(mHide.getProperty("sourcecode").toString().toLowerCase()))
					{
						mHide.setHidden(false);
					}
					else
					{
						mHide.setHidden(true);
					}
				}
			}
		}
	}
	
	
	public void draw() {
		background(0);
		map.draw();
		
	}
	

}
