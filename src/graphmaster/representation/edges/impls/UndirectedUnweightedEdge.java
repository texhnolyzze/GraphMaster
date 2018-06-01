package graphmaster.representation.edges.impls;

import graphmaster.representation.edges.UndirectedEdge;
import graphmaster.representation.edges.UnweightedEdge;
import java.util.Objects;

/**
 *
 * @author Texhnolyze
 */
public final class UndirectedUnweightedEdge<V> implements UndirectedEdge<V>, UnweightedEdge<V> {

    private final V v1;
    private final V v2;
    
    public UndirectedUnweightedEdge(V v1, V v2) {
        this.v1 = Objects.requireNonNull(v1);
        this.v2 = Objects.requireNonNull(v2);
    }
    
    @Override public V v1() {return v1;}
    @Override public V v2() {return v2;}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + v1.hashCode();
        hash = 13 * hash + v2.hashCode();
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
        final UndirectedUnweightedEdge<?> other = (UndirectedUnweightedEdge<?>) obj;
        if (!this.v1.equals(other.v1))
            return false;
        return this.v2.equals(other.v2);
    }

    @Override
    public String toString() {
        return v1 + " <-> " + v2;
    }
    
}
