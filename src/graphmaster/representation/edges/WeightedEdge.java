package graphmaster.representation.edges;

public interface WeightedEdge extends Edge {

    double getWeight();
    
    void setWeight(double weight);
    
    @Override
    default boolean isWeighted() {
        return true;
    }
    
    
    
}
