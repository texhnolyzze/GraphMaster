package graphmaster.representation.graph;

import graphmaster.representation.edges.Edge;
import java.util.Set;

/**
 *
 * @author Texhnolyze
 */
public class GraphDelegator<V, E extends Edge<V>> implements Graph<V, E> {

    protected final Graph<V, E> src;
    
    public GraphDelegator(Graph<V, E> src) {
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

    @Override public boolean addEdge(E e) {return src.addEdge(e);}
    @Override public boolean removeEdge(E e) {return src.removeEdge(e);}
    @Override public boolean removeEdge(V source, V dest) {return src.removeEdge(source, dest);}

    @Override public E getEdge(V source, V dest) {return src.getEdge(source, dest);}
    @Override public boolean containsEdge(E e) {return src.containsEdge(e);}
    @Override public boolean containsEdge(V source, V dest) {return src.containsEdge(source, dest);}

    @Override public Set<E> incomingEdgesOf(V v) {return src.incomingEdgesOf(v);}
    @Override public Set<E> outgoingEdgesOf(V v) {return src.outgoingEdgesOf(v);}
    
}
