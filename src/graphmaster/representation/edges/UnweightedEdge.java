package graphmaster.representation.edges;

public interface UnweightedEdge extends Edge {

    @Override
    default boolean isWeighted() {
        return false;
    }
    
}
