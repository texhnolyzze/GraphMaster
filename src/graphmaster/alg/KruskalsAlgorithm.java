package graphmaster.alg;

import graphmaster.representation.edges.UndirectedEdge;
import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import lib.UFS;

/**
 *
 * @author Texhnolyze
 */
public class KruskalsAlgorithm<V, E extends WeightedEdge<V> & UndirectedEdge<V>> extends MinimalSpanningTreeSearch<V, E> {

    public KruskalsAlgorithm(Graph<V, E> graph) {
        super(graph);
    }

    @Override
    protected void buildTree(Graph<V, E> graph, List<V> component, List<Double> weights, Map<Integer, Iterable<E>> spanningTrees) {
        UFS<V> ufs = new UFS<>();
        PriorityQueue<E> pq = new PriorityQueue<>((E e1, E e2) -> {
            if (e1.weight() < e2.weight())
                return 1;
            else if (e1.weight() > e2.weight())
                return -1;
            else
                return 0;
        });
        Set<V> viewed = new HashSet<>();
        for (V v : component) {
            viewed.add(v);
            for (E e : graph.outgoingEdgesOf(v)) {
                V adj = e.other(v);
                if (viewed.contains(adj))
                    continue;
                pq.add(e);
            }
        }
        viewed = null;
        double weight = 0.0;
        List<E> tree = new ArrayList<>();
        while (tree.size() < component.size() - 1) {
            E e = pq.poll();
            V v1 = e.v1();
            V v2 = e.v2();
            if (ufs.connected(v1, v2))
                continue;
            ufs.union(v1, v2);
            tree.add(e);
            weight += e.weight();
        }
        weights.add(weight);
        spanningTrees.put(spanningTrees.size(), tree);
    }
    
}
