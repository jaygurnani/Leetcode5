import java.util.*;

public class AirportRoutes {

    // Function to add an edge into the graph
    private static void addEdge(Map<String, List<String>> graph, String src, String dest) {
        graph.putIfAbsent(src, new ArrayList<>());
        graph.get(src).add(dest);
    }

    // Function to find all paths from 'departure' to 'destination'
    private static void findAllPaths(Map<String, List<String>> graph, String departure, String destination,
                                     List<String> path, List<List<String>> allPaths) {
        path.add(departure);

        // If the current airport is the destination, add the path to the result
        if (departure.equals(destination)) {
            allPaths.add(new ArrayList<>(path));
        } else {
            // If the current airport has connections, explore each of them
            if (graph.containsKey(departure)) {
                for (String next : graph.get(departure)) {
                    findAllPaths(graph, next, destination, path, allPaths);
                }
            }
        }

        // Backtrack: remove the current airport from the path
        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();

        // Sample data: adding flights between airports
        addEdge(graph, "A", "B");
        addEdge(graph, "A", "C");
        addEdge(graph, "B", "C");
        addEdge(graph, "B", "D");
        addEdge(graph, "C", "D");
        addEdge(graph, "C", "E");
        addEdge(graph, "D", "E");

        String departure = "A";
        String destination = "E";

        List<List<String>> allPaths = new ArrayList<>();
        findAllPaths(graph, departure, destination, new ArrayList<>(), allPaths);

        // Print all paths
        System.out.println("All possible routes from " + departure + " to " + destination + ":");
        for (List<String> path : allPaths) {
            System.out.println(path);
        }
    }
}