package graphmaster.representation.edges;

public interface Edge {
    
    int getEither();
    
    int getOther(int knownVertex);
    
    boolean isDirected();
    
    boolean isWeighted();
    
}
