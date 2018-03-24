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
        Map<V, Double> dist = new HashMap<>(component.size());
        Map<V, E> edgeTo = new HashMap<>(component.size() - 1);
        for (V v : component)
            dist.put(v, Double.POSITIVE_INFINITY);
        Set<V> tree = new HashSet<>(component.size());
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
        V init = component.get(0);
        dist.put(init, 0.0);
        pq.add(init);
        double weight = 0.0;
        while (!pq.isEmpty()) {
            V v = pq.pop();
            tree.add(v);
            for (E e : graph.outgoingEdgesOf(v)) {
                V adj = e.other(v);
                if (tree.contains(adj))
                    continue;
                double d = dist.get(adj);
                if (e.weight() < d) {
                    if (d != Double.POSITIVE_INFINITY)
                        weight -= d;
                    weight += e.weight();
                    edgeTo.put(adj, e);
                    dist.put(adj, e.weight());
                    if (pq.contains(adj))
                        pq.priorityChanged(adj);
                    else
                        pq.add(adj);
                }
            }
        }
        weights.add(weight);
        spanningTrees.put(spanningTrees.size(), edgeTo.values());
    }
    
}
