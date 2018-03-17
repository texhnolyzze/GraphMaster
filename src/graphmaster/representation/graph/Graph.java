package graphmaster.representation.graph;

import graphmaster.representation.edges.Edge;
import java.util.Set;

/**
 *
 * @author Texhnolyze
 */
public interface Graph<V, E extends Edge<V>> {
    
    boolean directed();
    boolean weighted();
    
    void removeAllEdges();
    void removeAllVertices();
   
    int numEdges();
    int numVertices();
    
    boolean addVertex(V v);
    boolean containsVertex(V v);
    boolean removeVertex(V v);
    
    Set<V> vertexSet();
    
    boolean addEdge(E e);
    boolean removeEdge(E e);
    boolean removeEdge(V source, V dest);
    
    E getEdge(V source, V dest);
    boolean containsEdge(E e);
    boolean containsEdge(V source, V dest);
    
    Set<E> incomingEdgesOf(V v);
    Set<E> outgoingEdgesOf(V v);
    
}
