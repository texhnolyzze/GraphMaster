package graphmaster.representation.graph;

import graphmaster.representation.edges.DirectedEdge;
import java.util.Set;

/**
 *
 * @author Texhnolyze
 */
public class ReversedGraph<V, E extends DirectedEdge<V>> extends GraphDelegator<V, E> {
    
    public ReversedGraph(Graph<V, E> src) {
        super(src);
    }

    @Override public boolean addEdge(E e) {return src.addEdge((E) e.reverse());}
    @Override public boolean removeEdge(E e) {return src.removeEdge((E) e.reverse());}
    @Override public boolean removeEdge(V source, V dest) {return src.removeEdge(dest, source);}

    @Override public E getEdge(V source, V dest) {return src.getEdge(dest, source);}
    @Override public boolean containsEdge(V source, V dest) {return src.containsEdge(dest, source);}
    @Override public boolean containsEdge(E e) {return src.containsEdge((E) e.reverse());}
    
    @Override public Set<E> incomingEdgesOf(V v) {return src.outgoingEdgesOf(v);}
    @Override public Set<E> outgoingEdgesOf(V v) {return src.incomingEdgesOf(v);}
    
}
