package graphmaster.representation.graph.specifics;

import graphmaster.representation.edges.Edge;
import java.util.Set;

/**
 *
 * @author Texhnolyze
 */
public interface Specifics<V, E extends Edge<V>> {
    
    int numEdges();
    int numVertices();
    
    boolean addEdge(E e);
    boolean removeEdge(E e);
    boolean removeEdge(V source, V dest);
    
    boolean addVertex(V v);
    boolean removeVertex(V v);
    boolean containsVertex(V v);
    Set<V> vertexSet();

    void removeAllEdges();
    void removeAllVertices();
    
    boolean containsEdge(E e);
    E getEdge(V source, V dest);
    
    default boolean containsEdge(V source, V dest) {
        return getEdge(source, dest) != null;
    }
    
    Set<E> incomingEdgesOf(V v);
    Set<E> outgoingEdgesOf(V v);
    
}
