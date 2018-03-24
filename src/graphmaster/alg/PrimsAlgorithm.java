package graphmaster.alg;

import graphmaster.representation.edges.UndirectedEdge;
import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lib.MinBinaryHeap;

/**
 *
 * @author Texhnolyze
 */
public class PrimsAlgorithm<V, E extends WeightedEdge<V> & UndirectedEdge<V>> {
    
    private final List<Double> weights = new ArrayList<>();
    private final Map<Integer, List<E>> spanningTrees = new HashMap<>();
    
    public PrimsAlgorithm(Graph<V, E> graph) {
        Collection<List<V>> components = ConnectedComponentsSearch.search(graph).components();
        Iterator<List<V>> it = components.iterator();
        while (it.hasNext()) {
            buildTree(graph, it.next());
            it.remove();
        }
    }
    
    private void buildTree(Graph<V, E> graph, List<V> component) {
        Map<V, Double> dist = new HashMap<>(component.size());
        Map<V, E> edgeTo = new HashMap<>(component.size() - 1);
        for (V v : component)
            dist.put(v, Double.POSITIVE_INFINITY);
        Set<V> tree = new HashSet<>(component.size());
        MinBinaryHeap<V> pq = new MinBinaryHeap<>((V v1, V v2) -> {
            return Double.compare(dist.get(v1), dist.get(v2));
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
    }
    
    public int numTrees() {
        return spanningTrees.size();
    }
    
    public Iterable<E> tree(int treeID) {
        return spanningTrees.getOrDefault(treeID, Collections.EMPTY_LIST);
    }
    
    public double weight(int treeID) {
        return weights.get(treeID);
    }
    
}
