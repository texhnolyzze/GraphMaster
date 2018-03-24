package graphmaster.alg;

import graphmaster.representation.GraphPath;
import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.Graph;
import graphmaster.utils.Queue;
import graphmaster.utils.Stack;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Texhnolyze
 */
public class UnweightedShortestPathSearch<V, E extends Edge<V>> {
    
    private final V source, dest;
    private final Graph<V, E> graph;
    private final Map<V, E> edgeTo;
    
    public UnweightedShortestPathSearch(Graph<V, E> graph, V source) {
        this(graph, source, null);
    }
    
    public UnweightedShortestPathSearch(Graph<V, E> graph, V source, V dest) {
        if (!graph.containsVertex(source))
            throw new IllegalArgumentException("Graph must contains source vertex.");
        if (dest != null && !graph.containsVertex(dest))
            throw new IllegalArgumentException("Graph must contains destination vertex.");
        this.graph = graph;
        this.source = source;
        this.dest = dest;
        this.edgeTo = bfs();
    }
    
    private Map<V, E> bfs() {
        Queue<V> queue = new Queue<>();
        Set<V> visited = new HashSet<>();
        HashMap<V, E> edgeTo = new HashMap<>();
        queue.add(source);
        visited.add(source);
        edgeTo.put(source, null);
        while (!queue.isEmpty()) {
            V v = queue.poll();
            if (dest == v)
                break;
            for (E e : graph.outgoingEdgesOf(v)) {
                V adj = e.other(v);
                if (visited.contains(adj))
                    continue;
                queue.add(adj);
                visited.add(adj);
                edgeTo.put(adj, e);
            }
        }
        return edgeTo;
    }
    
    public boolean hasPathTo(V v) {
        return edgeTo.containsKey(v);
    }
    
    public GraphPath<V, E> pathTo(V v) {
        if (!edgeTo.containsKey(v))
            return null;
        E e = edgeTo.get(v);
        if (e == null)
            return new GraphPath<>(source, source, Collections.EMPTY_LIST);
        V curr = v;
        Stack<E> path = new Stack<>();
        for (;;) {
            path.push(e);
            curr = e.other(curr);
            e = edgeTo.get(curr);
            if (e == null)
                break;
        }
        return new GraphPath<>(source, v, path);
    }
    
}
