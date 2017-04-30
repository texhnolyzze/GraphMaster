package graphmaster.representation.graph;

import graphmaster.representation.edges.WeightedEdge;

public interface WeightedGraph<E extends WeightedEdge> extends Graph<E> {

    @Override
    default boolean isWeighted() {
        return true;
    }
    
    default boolean hasNegativeWeight() {
        for (E e : edges()) if (e.getWeight() < 0) return true;
        return false;
    }
    
}
