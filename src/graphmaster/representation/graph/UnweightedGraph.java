package graphmaster.representation.graph;

import graphmaster.representation.edges.UnweightedEdge;

public interface UnweightedGraph<E extends UnweightedEdge> extends Graph<E> {

    @Override
    default boolean isWeighted() {
        return false;
    }
    
}
