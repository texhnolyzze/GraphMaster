package graphmaster.alg;

import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.HashMap;
import java.util.Map;
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
        Map<V, Double> dist = new HashMap<>();
        IndexedMinPriorityQueue<V> pq = new IndexedMinPriorityQueue<>((V v1, V v2) -> {
            return Double.compare(dist.get(v1), dist.get(v2));
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
