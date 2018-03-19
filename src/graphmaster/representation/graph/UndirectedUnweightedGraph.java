package graphmaster.representation.graph;

import graphmaster.representation.edges.impls.UndirectedUnweightedEdge;

/**
 *
 * @author Texhnolyze
 */
public class UndirectedUnweightedGraph<V> extends AbstractBaseGraph<V, UndirectedUnweightedEdge<V>> {

    @Override
    public boolean directed() {
        return false;
    }

    @Override
    public boolean weighted() {
        return false;
    }
    
    public boolean addEdge(V v1, V v2) {
        return super.addEdge(new UndirectedUnweightedEdge(v1, v2));
    }
    
}
