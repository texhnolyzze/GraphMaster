package graphmaster.representation.graph;

import graphmaster.representation.edges.Edge;
import java.util.Iterator;

public interface Graph<E extends Edge> {
    
    void addVertex();
    
    boolean addEdge(E e);
    
    E getEdge(int source, int target);
    
    boolean containsVertex(int vertex);
    
    boolean containsEdge(E e);
    
    boolean isDirected();
    
    boolean isWeighted();
    
    int getVertexesCount();
    
    int getEdgesCount();
    
    Iterable<E> incomingEdgesTo(int vertex);
    
    int inDegreeOf(int vertex);
    
    Iterable<E> outcomingEdgesFrom(int vertex);
    
    int outDegreeOf(int vertex);
    
    Iterable<E> edges();
    
    Class<E> correspondingEdgeType();
    
    default Iterable<Integer> scrollVertexes() {
        return () -> new Iterator<Integer>() {
            
            private int counter = 0;
            private final int _vertexesCount = getVertexesCount();
            
            @Override
            public boolean hasNext() {
                return counter < _vertexesCount;
            }
            
            @Override
            public Integer next() {
                return counter++;
            }
        };
    }
    
}
