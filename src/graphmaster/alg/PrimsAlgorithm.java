package graphmaster.alg;

import graphmaster.representation.edges.UndirectedEdge;
import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lib.IndexedMinPriorityQueue;

/**
 *
 * @author Texhnolyze
 */
public class PrimsAlgorithm<V, E extends WeightedEdge<V> & UndirectedEdge<V>> extends MinimalSpanningTreeSearch<V, E> {
    
    public PrimsAlgorithm(Graph<V, E> graph) {
        super(graph);
    }
    
    @Override
    protected void buildTree(Graph<V, E> graph, List<V> component, List<Double> weights, Map<Integer, Iterable<E>> spanningTrees) {
        Set<V> tree = new HashSet<>();
        Map<V, Double> dist = new HashMap<>(component.size());
        Map<V, E> edgeTo = new HashMap<>(component.size() - 1);
        IndexedMinPriorityQueue<V> pq = new IndexedMinPriorityQueue<>((V v1, V v2) -> {
            return Double.compare(dist.get(v1), dist.get(v2));
        });
        V init = component.get(0);
        pq.add(init);
        dist.put(init, 0.0);
        while (!pq.isEmpty()) {
            V v = pq.pop();
            tree.add(v);
            for (E e : graph.outgoingEdgesOf(v)) {
                V adj = e.other(v);
                if (tree.contains(adj))
                    continue;
                if (e.weight() < dist.getOrDefault(adj, Double.POSITIVE_INFINITY)) {
                    edgeTo.put(adj, e);
                    dist.put(adj, e.weight());
                    if (pq.contains(adj))
                        pq.priorityChanged(adj);
                    else
                        pq.add(adj);
                }
            }
        }
        double weight = 0.0;
        for (E e : edgeTo.values())
            weight += e.weight();
        weights.add(weight);
        spanningTrees.put(spanningTrees.size(), edgeTo.values());
    }
    
}
