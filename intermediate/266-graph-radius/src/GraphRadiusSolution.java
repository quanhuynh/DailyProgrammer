import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class GraphRadiusSolution {
	int radius;
	int diameter;
	int numEdges;
	HashMap<Integer, HashSet<Integer>> graph;
	
	/**
	 * Construct a solution object
	 * @param br BufferedReader object with information to build graph
	 * @throws IOException
	 */
	public GraphRadiusSolution(BufferedReader br) throws IOException {
		graph = new HashMap<Integer, HashSet<Integer>>();
		buildGraph(br);
	}
	
	/**
	 * Build graph
	 * @param br BufferedReader object with information to build graph
	 * @throws IOException
	 */
	private void buildGraph(BufferedReader br) throws IOException {
		numEdges = Integer.parseInt(br.readLine());
		String line = br.readLine();
		while (line != null) {
			String[] input = line.split(" ");
			int vertex1 = Integer.parseInt(input[0]);
			int vertex2 = Integer.parseInt(input[1]);
			addEdge(vertex1, vertex2);
		}
	}
	
	/**
	 * Eccentricity: greatest distance from v to any other node
	 * => 1 + greatest distance from one of v's neighbors to any other node
	 * @param v Vertex
	 * @return eccentricity of given vertex
	 */
	private int ecc(int v, HashSet<Integer> visited) {
		/* if (!graph.containsKey(v)) {
			return 0;
		}
		*/
		int maxDist = Integer.MIN_VALUE;
		if (visited.contains(v)) {
			return 0;
		}
		for (Integer neighbor : graph.get(v)) {
			int curDist = 1 + ecc(neighbor, visited);
			visited.add(neighbor);
			if (curDist > maxDist) {
				maxDist = curDist;
			}
		}
		return maxDist;
	}

	/**
 	 * Radius: minimum eccentricity of graph
 	 * Diameter: maximum eccentricity of graph
	 */
	private void calcRadDiam() {
		int minEcc = Integer.MAX_VALUE;
		int maxEcc = Integer.MIN_VALUE;
		HashSet<Integer> visited = new HashSet<Integer>();
		for (Integer v : graph.keys()) {
			curEcc = ecc(v, visited);
			if (curEcc < minEcc) {
				minEcc = curEcc;
			}
			if (curEcc > maxEcc) {
				maxEcc = curEcc;
			}
		}
		radius = minEcc;
		diameter = maxEcc;
	}
	
	private void addEdge(Integer vertex1, Integer vertex2) {
		if (!graph.containsKey(vertex1)) {
			graph.put(vertex1, new HashSet<Integer>());
		}
		graph.get(vertex1).add(vertex2);
	}
	
	public int rad() {
		return radius;
	}
	public int diam() {
		return diameter;
	}
	
	/**
	 * Main method reads in input file
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			File file = new File(args[0]);
			BufferedReader br = new BufferedReader(new FileReader(file));
			GraphRadiusSolution sol = new GraphRadiusSolution(br);
			sol.calcRadDiam();
			System.out.println("Radius: " + sol.rad());
			System.out.println("Diameter: " + sol.diam());
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
}
