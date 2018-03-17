package graphmaster.representation.graph;

import graphmaster.representation.edges.DirectedEdge;
import graphmaster.representation.edges.Edge;
import java.util.Set;

/**
 *
 * @author Texhnolyze
 */
public class ReversedDelegateGraph<V, E extends Edge<V>> implements Graph<V, E> {

    private final Graph<V, E> src;
    
    ReversedDelegateGraph(Graph<V, E> src) {
        this.src = src;
    }
    
    @Override public boolean directed() {return true;}
    @Override public boolean weighted() {return src.weighted();}

    @Override public void removeAllEdges() {src.removeAllEdges();}
    @Override public void removeAllVertices() {src.removeAllVertices();}

    @Override public int numEdges() {return src.numEdges();}
    @Override public int numVertices() {return src.numVertices();}

    @Override public boolean addVertex(V v) {return src.addVertex(v);}
    @Override public boolean containsVertex(V v) {return src.containsVertex(v);}
    @Override public boolean removeVertex(V v) {return src.removeVertex(v);}
    
    @Override public Set<V> vertexSet() {return src.vertexSet();}

    @Override public boolean addEdge(E e) {return src.addEdge((E) ((DirectedEdge<V>) e).reverse());}
    @Override public boolean removeEdge(E e) {return src.removeEdge((E) ((DirectedEdge<V>) e).reverse());}
    @Override public boolean removeEdge(V source, V dest) {return src.removeEdge(dest, source);}

    @Override public E getEdge(V source, V dest) {return src.getEdge(dest, source);}
    @Override public boolean containsEdge(V source, V dest) {return src.containsEdge(dest, source);}
    @Override public boolean containsEdge(E e) {return src.containsEdge((E) ((DirectedEdge<V>) e).reverse());}
    
    @Override public Set<E> incomingEdgesOf(V v) {return src.outgoingEdgesOf(v);}
    @Override public Set<E> outgoingEdgesOf(V v) {return src.incomingEdgesOf(v);}
    
}
