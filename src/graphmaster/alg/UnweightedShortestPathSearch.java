package graphmaster.alg;

import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.Graph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lib.Queue;

/**
 *
 * @author Texhnolyze
 */
public class UnweightedShortestPathSearch<V, E extends Edge<V>> extends PathSearch<V, E> {
    
    public UnweightedShortestPathSearch(Graph<V, E> graph, V source) {
        this(graph, source, null);
    }
    
    public UnweightedShortestPathSearch(Graph<V, E> graph, V source, V dest) {
        super(graph, source, dest);
        super.search();
    }
    
    @Override
    protected Map<V, E> search0(Graph<V, E> graph, V source, V dest) {
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
    
}
