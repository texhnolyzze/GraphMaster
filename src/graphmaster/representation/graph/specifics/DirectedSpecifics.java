package graphmaster.representation.graph.specifics;

import graphmaster.representation.edges.DirectedEdge;
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
public class DirectedSpecifics<V, E extends DirectedEdge<V>> implements Specifics<V, E> {
    
    private final Map<V, DirectedEdgeContainer<E>> vertexMap = new HashMap<>();
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
    public boolean addEdge(E e) {
        V source = e.source();
        if (!vertexMap.containsKey(source))
            return false;
        V dest = e.dest();
        if (!vertexMap.containsKey(dest))
            return false;
        DirectedEdgeContainer<E> sourceContainer = vertexMap.get(source);
        if (sourceContainer == null)
            vertexMap.put(source, sourceContainer = new DirectedEdgeContainer<>());
        else if (sourceContainer.outgoing.contains(e))
            return false;
        DirectedEdgeContainer<E> destContainer = vertexMap.get(dest);
        if (destContainer == null)
            vertexMap.put(dest, destContainer = new DirectedEdgeContainer<>());
        sourceContainer.outgoing.add(e);
        destContainer.incoming.add(e);
        numEdges++;
        return true;
    }

    @Override
    public boolean removeEdge(E e) {
        DirectedEdgeContainer<E> sourceContainer = vertexMap.get(e.source());
        if (sourceContainer == null)
            return false;
        else {
            if (sourceContainer.outgoing.remove(e)) {
                DirectedEdgeContainer<E> destContainer = vertexMap.get(e.dest());
                destContainer.incoming.remove(e);
                numEdges--;
                return true;
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
        if (vertexMap.containsKey(v)) {
            DirectedEdgeContainer<E> c = vertexMap.get(v);
            if (c != null) {
                for (Iterator<E> it = c.incoming.iterator(); it.hasNext();) {
                    E e = it.next();
                    vertexMap.get(e.source()).outgoing.remove(e);
                }
                for (Iterator<E> it = c.outgoing.iterator(); it.hasNext();) {
                    E e = it.next();
                    vertexMap.get(e.dest()).incoming.remove(e);
                }
                numEdges -= (c.incoming.size() + c.outgoing.size());
            }
            vertexMap.remove(v);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEdge(V source, V dest) {
        DirectedEdgeContainer<E> sourceContainer = vertexMap.get(source);
        if (sourceContainer == null)
            return false;
        for (Iterator<E> it = sourceContainer.outgoing.iterator(); it.hasNext();) {
            E e = it.next();
            if (e.dest().equals(dest)) {
                numEdges--;
                it.remove();
                vertexMap.get(dest).incoming.remove(e);
                return true;
            }
        }
        return false;
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
    public void removeAllEdges() {
		numEdges = 0;
        for (Map.Entry<V, DirectedEdgeContainer<E>> e : vertexMap.entrySet()) 
            e.setValue(null);
    }

    @Override
    public void removeAllVertices() {
		numEdges = 0;
        vertexMap.clear();
    }

    @Override
    public E getEdge(V source, V dest) {
        DirectedEdgeContainer<E> c = vertexMap.get(source);
        if (c == null)
            return null;
        for (E e : c.outgoing) {
            if (e.dest().equals(dest))
                return e;
        }
        return null;
    }

    @Override
    public boolean containsEdge(E e) {
        V source = e.source();
        DirectedEdgeContainer<E> c = vertexMap.get(source);
        if (c == null)
            return false;
        return c.outgoing.contains(e);
    }

    @Override
    public Set<E> incomingEdgesOf(V v) {
        if (!vertexMap.containsKey(v))
            return null;
        DirectedEdgeContainer<E> c = vertexMap.get(v);
        if (c == null)
            return Collections.EMPTY_SET;
        if (c.unmodifiableIncoming == null)
            c.unmodifiableIncoming = Collections.unmodifiableSet(c.incoming);
        return c.unmodifiableIncoming;
    }

    @Override
    public Set<E> outgoingEdgesOf(V v) {
        if (!vertexMap.containsKey(v))
            return null;
        DirectedEdgeContainer<E> c = vertexMap.get(v);
        if (c == null)
            return Collections.EMPTY_SET;
        if (c.unmodifiableOutgoing == null)
            c.unmodifiableOutgoing = Collections.unmodifiableSet(c.outgoing);
        return c.unmodifiableOutgoing;
    }
    
    private static class DirectedEdgeContainer<E extends DirectedEdge> {
    
        final Set<E> incoming = new HashSet<>();
        final Set<E> outgoing = new HashSet<>();

        Set<E> unmodifiableIncoming;
        Set<E> unmodifiableOutgoing;
    
    }
    
}
