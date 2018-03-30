package graphmaster.alg;

import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lib.IndexedMinPriorityQueue;

/**
 *
 * @author Texhnolyze
 */
public class AStar<V, E extends WeightedEdge<V>> extends PathSearch<V, E> {
    
    private final Heuristic<V> h;
    
    public AStar(Graph<V, E> graph, V source, V dest, Heuristic<V> h) {
        super(graph, Objects.requireNonNull(source), Objects.requireNonNull(dest));
        this.h = h;
        super.search();
    }

    @Override
    protected Map<V, E> search0(Graph<V, E> graph, V source, V dest) {
        Map<V, E> edgeTo = new HashMap<>();
        Map<V, Double> dist = new HashMap<>();
        edgeTo.put(source, null);
        IndexedMinPriorityQueue<V> pq = new IndexedMinPriorityQueue<>((V v1, V v2) -> {
            return Double.compare(
                    dist.get(v1) + h.cost(v1, dest), 
                    dist.get(v2) + h.cost(v2, dest)
            );
        });
        pq.add(source);
        dist.put(source, 0.0);
        while (!pq.isEmpty()) {
            V v = pq.pop();
            if (v == dest)
                break;
            double w = dist.get(v);
            for (E e : graph.outgoingEdgesOf(v)) {
                V adj = e.other(v);
                double old = dist.getOrDefault(adj, Double.POSITIVE_INFINITY);
                double curr = w + e.weight();
                if (curr < old) {
                    edgeTo.put(adj, e);
                    dist.put(adj, curr);
                    if (!pq.contains(adj))
                        pq.add(adj);
                    else
                        pq.priorityChanged(adj);
                }
                
            }
        }
        return edgeTo;
    }
    
    
    
    public static interface Heuristic<V> {
        double cost(V v1, V v2);
    }
    
}
