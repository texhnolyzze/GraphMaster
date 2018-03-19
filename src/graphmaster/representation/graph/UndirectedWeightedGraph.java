package graphmaster.representation.graph;

import graphmaster.representation.edges.impls.UndirectedWeightedEdge;

/**
 *
 * @author Texhnolyze
 */
public class UndirectedWeightedGraph<V> extends AbstractBaseGraph<V, UndirectedWeightedEdge<V>> {

    @Override
    public boolean directed() {
        return false;
    }

    @Override
    public boolean weighted() {
        return true;
    }
    
    public boolean addEdge(V v1, V v2, double weight) {
        return super.addEdge(new UndirectedWeightedEdge<>(v1, v2, weight));
    }
    
}
