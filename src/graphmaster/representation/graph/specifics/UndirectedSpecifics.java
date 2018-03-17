package graphmaster.representation.graph.specifics;

import graphmaster.representation.edges.UndirectedEdge;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Texhnolyze
 */
public class UndirectedSpecifics<V, E extends UndirectedEdge<V>> implements Specifics<V, E> {

    private final Map<V, UndirectedEdgeContainer<E>> vertexMap = new HashMap<>();
    private final Set<V> unmodifiableVertexSet = Collections.unmodifiableSet(vertexMap.keySet());
    
    private int numEdges;
    
    @Override
    public int numEdges() {
        return numEdges;
    }
    
    @Override
    public int numVertices() {
        return vertexMap.size();
    }
    
    @Override
    public void removeAllEdges() {
        numEdges = 0;
        for (Map.Entry<V, UndirectedEdgeContainer<E>> e : vertexMap.entrySet())
            e.setValue(null);
    }
    
    @Override
    public void removeAllVertices() {
        numEdges = 0;
        vertexMap.clear();
    }
    
    @Override
    public boolean addEdge(E e) {
        V v1 = e.v1();
        Set<E> s1 = edgesOf0(v1);
        if (s1 == null)
            return false;
        V v2 = e.v2();
        Set<E> s2 = edgesOf0(v2);
        if (s2 == null)
            return false;
        if (s1.contains(e))
            return false;
        s1.add(e);
        s2.add(e);
        numEdges++;
        return true;
    }

    @Override
    public boolean removeEdge(E e) {
        UndirectedEdgeContainer<E> c1 = vertexMap.get(e.v1());
        if (c1 != null) {
            if (c1.edges.remove(e)) {
                numEdges--;
                Set<E> s2 = vertexMap.get(e.v2()).edges;
                s2.remove(e);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeEdge(V v1, V v2) {
        UndirectedEdgeContainer<E> c1 = vertexMap.get(v1);
        if (c1 != null) {
            for (Iterator<E> it = c1.edges.iterator(); it.hasNext();) {
                E e = it.next();
                if (e.touches(v2)) {
                    numEdges--;
                    it.remove();
                    vertexMap.get(v2).edges.remove(e);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addVertex(V v) {
        if (vertexMap.containsKey(v))
            return false;
        vertexMap.put(v, null);
        return true;
    }
    
    @Override
    public boolean removeVertex(V v) {
        UndirectedEdgeContainer<E> edges = vertexMap.get(v);
        if (edges == null)
            return false;
        for (E e : edges.edges) {
            V other = e.other(v);
            vertexMap.get(other).edges.remove(e);
        }
        numEdges -= edges.edges.size();
        vertexMap.remove(v);
        return true;
    }
    
    @Override
    public boolean containsVertex(V v) {
        return vertexMap.containsKey(v);
    }

    @Override
    public Set<V> vertexSet() {
        return unmodifiableVertexSet;
    }

    @Override
    public E getEdge(V v1, V v2) {
        UndirectedEdgeContainer<E> c1 = vertexMap.get(v1);
        if (c1 == null)
            return null;
        for (E e : c1.edges) {
            if (e.touches(v2))
                return e;
        }
        return null;
    }

    @Override
    public boolean containsEdge(E e) {
        UndirectedEdgeContainer<E> c1 = vertexMap.get(e.v1());
        if (c1 == null)
            return false;
        return c1.edges.contains(e);
    }

    @Override
    public Set<E> incomingEdgesOf(V v) {
        return edgesOf1(v);
    }

    @Override
    public Set<E> outgoingEdgesOf(V v) {
        return edgesOf1(v);
    }
    
    private Set<E> edgesOf0(V v) {
        if (!vertexMap.containsKey(v))
            return null;
        UndirectedEdgeContainer<E> edges = vertexMap.get(v);
        if (edges == null)
            vertexMap.put(v, edges = new UndirectedEdgeContainer<>());
        return edges.edges;
    }
    
    private Set<E> edgesOf1(V v) {
        if (!vertexMap.containsKey(v))
            return null;
        UndirectedEdgeContainer<E> edges = vertexMap.get(v);
        if (edges == null)
            return Collections.EMPTY_SET;
        Set<E> s = edges.unmodifiableEdgeSet;
        if (s == null)
            s = edges.unmodifiableEdgeSet = Collections.unmodifiableSet(edges.edges);
        return s;
    }
    
    private static class UndirectedEdgeContainer<E extends UndirectedEdge> {
        
        final Set<E> edges = new HashSet<>();
        Set<E> unmodifiableEdgeSet;
        
    }
    
}
