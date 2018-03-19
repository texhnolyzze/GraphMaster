package graphmaster.representation.graph;

import graphmaster.representation.edges.impls.DirectedUnweightedEdge;

/**
 *
 * @author Texhnolyze
 */
public class DirectedUnweightedGraph<V> extends AbstractBaseGraph<V, DirectedUnweightedEdge<V>> {

    @Override
    public boolean directed() {
        return true;
    }

    @Override
    public boolean weighted() {
        return false;
    }
    
    public boolean addEdge(V source, V dest) {
        return super.addEdge(new DirectedUnweightedEdge<>(source, dest));
    }
    
}
