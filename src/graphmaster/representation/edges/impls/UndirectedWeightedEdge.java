package graphmaster.representation.edges.impls;

import graphmaster.representation.edges.UndirectedEdge;
import graphmaster.representation.edges.WeightedEdge;
import java.util.Objects;

/**
 *
 * @author Texhnolyze
 */
public final class UndirectedWeightedEdge<V> implements UndirectedEdge<V>, WeightedEdge<V> {

    private final V v1;
    private final V v2;
    
    private double weight;
    
    public UndirectedWeightedEdge(V v1, V v2, double weight) {
        this.v1 = Objects.requireNonNull(v1);
        this.v2 = Objects.requireNonNull(v2);
        this.weight = weight;
    }
    
    @Override public V v1() {return v1;}
    @Override public V v2() {return v2;}

    @Override public double weight() {return weight;}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + v1.hashCode();
        hash = 17 * hash + v2.hashCode();
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.weight) ^ (Double.doubleToLongBits(this.weight) >>> 32));
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
        final UndirectedWeightedEdge<?> other = (UndirectedWeightedEdge<?>) obj;
        if (Double.doubleToLongBits(this.weight) != Double.doubleToLongBits(other.weight)) 
            return false;
        if (!this.v1.equals(other.v1)) 
            return false;
        return this.v2.equals(other.v2);
    }

    @Override
    public String toString() {
        return v1 + " <-> " + v2 + ", w=" + weight;
    }
    
}
