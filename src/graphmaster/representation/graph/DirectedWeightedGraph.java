package graphmaster.representation.graph;

import graphmaster.representation.edges.impls.DirectedWeightedEdge;

/**
 *
 * @author Texhnolyze
 */
public class DirectedWeightedGraph<V> extends AbstractBaseGraph<V, DirectedWeightedEdge<V>> {

    @Override
    public boolean directed() {
        return true;
    }

    @Override
    public boolean weighted() {
        return true;
    }
    
    public boolean addEdge(V source, V dest, double weight) {
        return super.addEdge(new DirectedWeightedEdge<>(source, dest, weight));
    }
    
}
