package graphmaster.representation.graph;

import graphmaster.representation.edges.Edge;
import graphmaster.representation.edges.WeightedEdge;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGraph<E extends Edge> implements Graph<E> {
    
    protected final List<List<E>> vertexes;
    protected int vertexesCount;
    
    protected int edgesCount;
    
    public AbstractGraph(int initVertexesCount) {
        vertexes = new ArrayList<>(initVertexesCount);
        for (int i = 0; i < initVertexesCount; i++) vertexes.add(new ArrayList<>());
        vertexesCount = initVertexesCount;
    }
    
    protected AbstractGraph(AbstractGraph<E> srcGraph) {
        vertexes = srcGraph.vertexes;
        edgesCount = srcGraph.getEdgesCount();
        vertexesCount = srcGraph.vertexesCount;
    }
    
    @Override
    public int getVertexesCount() {
        return vertexesCount;
    }

    @Override
    public int getEdgesCount() {
        return edgesCount;
    }
    
    @Override
    public void addVertex() {
        vertexes.add(new ArrayList<>());
        vertexesCount++;
    }
    
    @Override
    public E getEdge(int source, int target) {
        for (E e : outcomingEdgesFrom(source)) if (e.getOther(source) == target) return e;
        return null;
    }

    @Override
    public boolean containsVertex(int vertex) {
        return vertex >= 0 && vertex <= vertexesCount - 1;
    }
    
    @Override
    public int outDegreeOf(int vertex) {
        return vertexes.get(vertex).size();
    }

    @Override
    public Iterable<E> outcomingEdgesFrom(int vertex) {
        return vertexes.get(vertex);
    }
    
    @Override
    public Iterable<E> edges() {
        final boolean directed = isDirected();
        List<E> edges = new ArrayList<>(directed ? edgesCount : edgesCount / 2);
        for (int vertex : scrollVertexes()) {
            for (E e : outcomingEdgesFrom(vertex))
                if (directed) edges.add(e);
                else if (e.getOther(vertex) > vertex) edges.add(e);
        }
        return edges;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        final boolean weighted = isWeighted();
        for (int vertex : scrollVertexes()) {
            builder.append(vertex).append(": ");
            for (E e : outcomingEdgesFrom(vertex)) {
                builder.append('[');
                builder.append(e.getOther(vertex));
                if (weighted) {
                    WeightedEdge w = (WeightedEdge) e;
                    builder.append(", " + w.getWeight());
                }
                builder.append("] ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    
    
}
