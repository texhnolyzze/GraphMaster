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
    protected void buildTree(Graph<V, E> graph, List<V> component, List<Double> weights, List<Iterable<E>> spanningTrees) {
        Set<V> tree = new HashSet<>();
        Map<V, E> edgeTo = new HashMap<>(component.size() - 1);
        IndexedMinPriorityQueue<V> pq = new IndexedMinPriorityQueue<>((V v1, V v2) -> {
            return Double.compare(edgeTo.get(v1).weight(), edgeTo.get(v2).weight());
        });
        V init = component.get(0);
        pq.add(init);
        while (!pq.isEmpty()) {
            V v = pq.pop();
            tree.add(v);
            for (E e : graph.outgoingEdgesOf(v)) {
                V adj = e.other(v);
                if (tree.contains(adj))
                    continue;
                E edge = edgeTo.get(adj);
                if (edge == null) {
                    edgeTo.put(adj, e);
                    pq.add(adj);
                } else {
                    if (e.weight() < edge.weight()) {
                        edgeTo.put(adj, e);
                        pq.priorityChanged(adj);
                    }
                }
            }
        }
        double weight = 0.0;
        for (E e : edgeTo.values())
            weight += e.weight();
        weights.add(weight);
        spanningTrees.add(edgeTo.values());
    }
    
}
