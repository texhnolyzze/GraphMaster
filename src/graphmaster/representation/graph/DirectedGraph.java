package graphmaster.representation.graph;

import graphmaster.representation.edges.DirectedEdge;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class DirectedGraph<E extends DirectedEdge> extends AbstractGraph<E> {
    
    public DirectedGraph(int initVertexesCount) {
        super(initVertexesCount);
    }

    @Override
    public boolean addEdge(E e) {
        if (containsEdge(e)) return false;
        vertexes.get(e.getSource()).add(e);
        edgesCount++;
        return true;
    }

    @Override
    public boolean containsEdge(E e) {
        return getEdge(e.getSource(), e.getTarget()) != null;
    }

    @Override
    public E getEdge(int source, int target) {
        for (E e : outcomingEdgesFrom(source)) 
            if (e.getTarget() == target) return e;
        return null;
    }

    @Override
    public final boolean isDirected() {
        return true;
    }

    @Override
    public int inDegreeOf(int vertex) {
        return reversed().outDegreeOf(vertex);
    }
    
    @Override
    public Iterable<E> incomingEdgesTo(int vertex) {
        return reversed().outcomingEdgesFrom(vertex);
    }
    
    public Iterable<Integer> getSinks() {
        List<Integer> sinks = new ArrayList<>();
        for (int vertex : scrollVertexes()) 
            if (outDegreeOf(vertex) == 0) sinks.add(vertex);
        return sinks;
    }
    
    public Iterable<Integer> getSources() {
        return reversed().getSinks();
    }
    
    public DirectedGraph reversed() {
        DirectedGraph<E> reversed;
        Class<? extends DirectedGraph> type = getClass();
        try {
            reversed = type.getDeclaredConstructor(int.class).newInstance(vertexesCount);
            for (int vertex : scrollVertexes()) 
                for (E e : outcomingEdgesFrom(vertex)) 
                    reversed.addEdge((E) e.getReversed());
        } catch (NoSuchMethodException | SecurityException | InstantiationException 
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException("Unknown type of graph: " + type);
        }
        return reversed;
    }
    
    
    
    
    
}
