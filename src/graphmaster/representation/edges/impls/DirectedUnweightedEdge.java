package graphmaster.representation.edges.impls;

import graphmaster.representation.edges.DirectedEdge;
import graphmaster.representation.edges.UnweightedEdge;
import java.util.Objects;

/**
 *
 * @author Texhnolyze
 */
public final class DirectedUnweightedEdge<V> implements DirectedEdge<V>, UnweightedEdge<V> {

    private final V source;
    private final V dest;
    
    public DirectedUnweightedEdge(V source, V dest) {
        this.source = Objects.requireNonNull(source);
        this.dest = Objects.requireNonNull(dest);
    }
    
    @Override public V source() {return source;}
    @Override public V dest() {return dest;}

    @Override public DirectedEdge<V> reverse() {return new DirectedUnweightedEdge<>(dest, source);}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + source.hashCode();
        hash = 79 * hash + dest.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        if (obj == null) 
            return false;
        if (getClass() != obj.getClass()) 
            return false;
        final DirectedUnweightedEdge<?> other = (DirectedUnweightedEdge<?>) obj;
        if (!this.source.equals(other.source)) 
            return false;
        return this.dest.equals(other.dest);
    }

    @Override
    public String toString() {
        return source + " -> " + dest;
    }
    
}
