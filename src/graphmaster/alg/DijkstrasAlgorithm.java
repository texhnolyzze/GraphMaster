package graphmaster.alg;

import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lib.IndexedMinPriorityQueue;

/**
 *
 * @author Texhnolyze
 */
public class DijkstrasAlgorithm<V, E extends WeightedEdge<V>> extends PathSearch<V, E> {
    
    public DijkstrasAlgorithm(Graph<V, E> graph, V source) {
        this(graph, source, null);
    }
    
    public DijkstrasAlgorithm(Graph<V, E> graph, V source, V dest) {
        super(graph, source, dest);
        for (V v : graph.vertexSet()) {
            for (E e : graph.outgoingEdgesOf(v)) {
                if (e.weight() < 0.0)
                    throw new IllegalArgumentException("Weights must be >= 0.");
            }
        }
        super.search();
    }
    
    @Override
    protected Map<V, E> search0(Graph<V, E> graph, V source, V dest) {
        Map<V, E> edgeTo = new HashMap<>();
        edgeTo.put(source, null);
        Set<V> tree = new HashSet<>();
        Map<V, Double> dist = new HashMap<>();
        for (V v : graph.vertexSet())
            dist.put(v, Double.POSITIVE_INFINITY);
        IndexedMinPriorityQueue<V> pq = new IndexedMinPriorityQueue<>((V v1, V v2) -> {
            double d1 = dist.get(v1);
            double d2 = dist.get(v2);
            if (d1 < d2)
                return -1;
            else if (d1 > d2)
                return 1;
            else
                return 0;
        });
        pq.add(source);
        dist.put(source, 0.0);
        while (!pq.isEmpty()) {
            V v = pq.pop();
            if (v == dest)
                break;
            tree.add(v);
            for (E e : graph.outgoingEdgesOf(v)) {
                V adj = e.other(v);
                if (tree.contains(adj))
                    continue;
                double old = dist.get(adj);
                double curr = dist.get(v) + e.weight();
                if (old > curr) {
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
    
}
