package geometricshapes;

import java.io.File;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class GraphLoader {
	/*
	 * Loads the graph from the data file
	 * @param - graph.Graph: the graph object which will be loaded with the data.
	 * @param - filename: Location of the data set from which the graph will be loaded.
	 */
	public static void loadGraph(graph.Graph g, String filename) {
		Set<Integer> visited = new HashSet<Integer>();
		Scanner scan;
		try {
			scan = new Scanner(new File(filename));
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return;
		}
		// Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
		while(scan.hasNextInt()) {
			int vertex1 = scan.nextInt();
			int vertex2 = scan.nextInt();
			if(!visited.contains(vertex1)) {
				visited.add(vertex1);
				g.addVertex(vertex1);
			}
			if(!visited.contains(vertex2)) {
				visited.add(vertex2);
				g.addVertex(vertex2);
			}
			g.addEdge(vertex2, vertex1);
		}
		scan.close();
	}
}
