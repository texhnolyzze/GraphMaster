package graphmaster.representation.edges.impls;

import graphmaster.representation.edges.DirectedEdge;
import graphmaster.representation.edges.WeightedEdge;
import java.util.Objects;

/**
 *
 * @author Texhnolyze
 */
public final class DirectedWeightedEdge<V> implements DirectedEdge<V>, WeightedEdge<V> {

    private final V source;
    private final V dest;
    
    private double weight;

    public DirectedWeightedEdge(V source, V dest, double weight) {
        this.source = Objects.requireNonNull(source);
        this.dest = Objects.requireNonNull(dest);
        this.weight = weight;
    }
    
    @Override public V source() {return source;}
    @Override public V dest() {return dest;}

    @Override public double weight() {return weight;}

    @Override public DirectedEdge<V> reverse() {return new DirectedWeightedEdge(dest, source, weight);}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + source.hashCode();
        hash = 89 * hash + dest.hashCode();
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.weight) ^ (Double.doubleToLongBits(this.weight) >>> 32));
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
        final DirectedWeightedEdge<?> other = (DirectedWeightedEdge<?>) obj;
        if (Double.doubleToLongBits(this.weight) != Double.doubleToLongBits(other.weight)) 
            return false;
        if (!this.source.equals(other.source)) 
            return false;
        return this.dest.equals(other.dest);
    }
    
}
