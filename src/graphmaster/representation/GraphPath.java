package graphmaster.representation;

import graphmaster.representation.edges.Edge;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Texhnolyze
 */
public class GraphPath<V, E extends Edge<V>> {
    
    private final V source;
    private final V dest;
    
    private int numEdges;
    private final boolean isCycle;
    
    private Iterable<E> edges;
    
    public GraphPath(V source, V dest, Iterable<E> edges) {
        this.source = Objects.requireNonNull(source);
        this.dest = Objects.requireNonNull(dest);
        this.edges = edges;
        this.isCycle = source.equals(dest);
        V curr = source;
        Iterator<E> it = edges.iterator();
        while (it.hasNext()) {
            numEdges++;
            E e = it.next();
            if (!e.touches(curr))
                throw new IllegalArgumentException("Illegal path.");
            curr = e.other(curr);
        }
    }
    
    public V source() {
        return source;
    }
    
    public V dest() {
        return dest;
    }
    
    public Iterable<E> edges() {
        return edges;
    }
    
    public boolean isCycle() {
        return isCycle;
    }
    
    public int numEdges() {
        return numEdges;
    }
    
    public Iterable<V> vertices() {
        V curr = source;
        List<V> vertices = new ArrayList<>();
        Iterator<E> it = edges.iterator();
        while (it.hasNext()) {
            vertices.add(curr);
            curr = it.next().other(curr);
        }
        vertices.add(curr);
        return vertices;
    }
    
}
