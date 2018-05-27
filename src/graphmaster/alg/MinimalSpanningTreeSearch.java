package graphmaster.alg;

import graphmaster.representation.edges.UndirectedEdge;
import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Texhnolyze
 */
public abstract class MinimalSpanningTreeSearch<V, E extends WeightedEdge<V> & UndirectedEdge<V>> {
    
    private final Graph<V, E> graph;
    
    private final List<Double> weights = new ArrayList<>();
    private final List<Iterable<E>> spanningTrees = new ArrayList<>();
    
    protected MinimalSpanningTreeSearch(Graph<V, E> graph) {
        this.graph = graph;
        buildForest();
    }
    
    private void buildForest() {
        Collection<List<V>> components = ConnectedComponentsSearch.search(graph).components();
        Iterator<List<V>> it = components.iterator();
        while (it.hasNext()) {
            List<V> component = it.next();
            it.remove();
            buildTree(graph, component, weights, spanningTrees);
        }
    }
    
    protected abstract void buildTree(Graph<V, E> graph, List<V> component, List<Double> weights, List<Iterable<E>> spanningTrees);
    
    public int numTrees() {
        return spanningTrees.size();
    }
    
    public Iterable<E> tree(int treeID) {
        return spanningTrees.get(treeID);
    }
    
    public double weight(int treeID) {
        return weights.get(treeID);
    }
    
}
