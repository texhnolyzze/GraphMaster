package graphmaster.representation.graph;

import graphmaster.representation.edges.DirectedEdge;
import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.specifics.DirectedSpecifics;
import graphmaster.representation.graph.specifics.Specifics;
import graphmaster.representation.graph.specifics.UndirectedSpecifics;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public abstract class AbstractBaseGraph<V, E extends Edge<V>> implements Graph<V, E> {
	
    private final Specifics<V, E> specifics;
    
    AbstractBaseGraph() {
        if (directed())
            specifics = new DirectedSpecifics();
        else 
            specifics = new UndirectedSpecifics();
    }
    
    @Override public abstract boolean directed();
    @Override public abstract boolean weighted();
    
    @Override public void removeAllEdges() {specifics.removeAllEdges();}
    @Override public void removeAllVertices() {specifics.removeAllVertices();}
    
    @Override public int numEdges() {return specifics.numEdges();}
    @Override public int numVertices() {return specifics.numVertices();}
    
    @Override public boolean addVertex(V v) {return specifics.addVertex(v);}
    @Override public boolean containsVertex(V v) {return specifics.containsVertex(v);}
    @Override public boolean removeVertex(V v) {return specifics.removeVertex(v);}
    
    @Override public Set<V> vertexSet() {return specifics.vertexSet();}
    
    @Override public boolean addEdge(E e) {return specifics.addEdge(e);}
    @Override public boolean removeEdge(E e) {return specifics.removeEdge(e);}
    @Override public boolean removeEdge(V source, V dest) {return specifics.removeEdge(source, dest);}
    
    @Override public E getEdge(V source, V dest) {return specifics.getEdge(source, dest);}
    @Override public boolean containsEdge(E e) {return specifics.containsEdge(e);}
    @Override public boolean containsEdge(V source, V dest) {return specifics.containsEdge(source, dest);}
    
    @Override public Set<E> incomingEdgesOf(V v) {return specifics.incomingEdgesOf(v);}
    @Override public Set<E> outgoingEdgesOf(V v) {return specifics.outgoingEdgesOf(v);}
    
    public Graph<V, E> reversedDelegate() {
        if (!directed())
            return null;
        return new ReversedDelegateGraph<>(this);
    }
    
    public Graph<V, E> reversed() {
        if (!directed())
            return null;
        try {
            Graph<V, E> g = getClass().getConstructor().newInstance();
            for (V v : this.vertexSet())
                g.addVertex(v);
            for (V v : this.vertexSet()) {
                for (E e : this.outgoingEdgesOf(v)) {
                    DirectedEdge<V> de = (DirectedEdge<V>) e;
                    g.addEdge((E) de.reverse());
                }
            }
            return g;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.err.println(ex);
        }
        return null;
    }
    
}