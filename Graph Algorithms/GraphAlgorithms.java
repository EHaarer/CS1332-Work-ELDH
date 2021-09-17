import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.List;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Ethan Haarer
 * @userid ehaarer3
 * @GTID 903586678
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Cannot begin search at null Vertex");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Cannot perform Depth First Search on null graph");
        }
        List<Vertex<T>> outter = new LinkedList<>();
        HashSet<Vertex<T>> seen = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();

        seen.add(start);
        outter.add(start);
        queue.add(start);

        Vertex<T> currVer;
        while (!queue.isEmpty()) {
            currVer = queue.remove();
            List<VertexDistance<T>> surrounding = graph.getAdjList().get(currVer);
            for (VertexDistance<T> vD: surrounding) {
                if (!seen.contains(vD.getVertex())) {
                    seen.add(vD.getVertex());
                    outter.add(vD.getVertex());
                    queue.add(vD.getVertex());
                }
            }
        }
        return outter;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        List<Vertex<T>> outter = new LinkedList<>();
        if (start == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Cannot begin search at null Vertex");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Cannot perform Depth First Search on null graph");
        }
        dfsHelper(start, graph, outter);
        return outter;
    }

    /**
     * This is the helper function to the depth first search algorithm for graphs.
     * @param currVer Current vertex being assessed
     * @param graph The graph from which connections are sourced
     * @param visited List of all Vertices visited so far
     * @param <T> Generic type used in Data structures
     */
    private static <T> void dfsHelper(Vertex<T> currVer, Graph<T> graph, List<Vertex<T>> visited) {
        List<VertexDistance<T>> surrounding;
        visited.add(currVer);
        surrounding = graph.getAdjList().get(currVer);
        for (VertexDistance<T> vD : surrounding) {
            if (!visited.contains(vD.getVertex())) {
                dfsHelper(vD.getVertex(), graph, visited);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start, Graph<T> graph) {
        if (start == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Cannot begin search at null Vertex");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Cannot perform Depth First Search on null graph");
        }
        Set<Vertex<T>> allVer = graph.getAdjList().keySet();
        PriorityQueue<Edge<T>> queue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> lengths = new HashMap<>();
        Edge<T> filler;

        for (Vertex<T> currVer : allVer) {
            if (!currVer.equals(start)) {
                lengths.put(currVer, Integer.MAX_VALUE);
                filler = new Edge<>(start, currVer,  Integer.MAX_VALUE);
            } else {
                lengths.put(start, 0);
                filler = new Edge<>(start, currVer, 0);
            }
            queue.add(filler);
        }

        int minDist;
        Edge<T> minEdge;

        while (!queue.isEmpty()) {
            minEdge = queue.remove();
            List<VertexDistance<T>> surrounding = graph.getAdjList().get(minEdge.getV());
            for (VertexDistance<T> vD : surrounding) {
                minDist = vD.getDistance() + lengths.get(minEdge.getV());
                if (lengths.get(vD.getVertex()) > minDist && minDist >= 0) {
                    lengths.put(vD.getVertex(), minDist);
                    queue.remove(vD);
                    Edge<T> temp = new Edge<>(start, vD.getVertex(), minDist);
                    queue.add(temp);
                }
            }
        }
        return lengths;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Cannot begin search at null Vertex");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Cannot perform Depth First Search on null graph");
        }

        PriorityQueue<Edge<T>> pqueue = new PriorityQueue<>();
        Set<Vertex<T>> travelled = new HashSet<>();
        Set<Edge<T>> edges = new HashSet<>();

        List<VertexDistance<T>> connectedStartVert = graph.getAdjList().get(start);
        for (VertexDistance<T> verDist : connectedStartVert) {
            pqueue.add(new Edge<>(start, verDist.getVertex(), verDist.getDistance()));
        }
        travelled.add(start);

        while (!pqueue.isEmpty() && travelled.size() < graph.getVertices().size()) { //Once they become equal in length,
            // then we've traversed all possible paths.
            Edge<T> filler = pqueue.remove();

            if (!travelled.contains(filler.getV())) {
                travelled.add(filler.getV());
                edges.add(filler);
                Edge<T> rellif = new Edge<T>(filler.getV(), filler.getU(), filler.getWeight()); //reverse edge of filler
                edges.add(rellif);

                List<VertexDistance<T>> surrounding = graph.getAdjList().get(filler.getV());
                for (VertexDistance<T> vD : surrounding) {
                    if (!travelled.contains(vD.getVertex())) {
                        Edge<T> temp = new Edge<T>(filler.getV(), vD.getVertex(), vD.getDistance());
                        pqueue.add(temp);
                    }
                }
            }
        }
        return edges;
    }
}