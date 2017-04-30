package graphmaster.representation.graph;

import graphmaster.representation.edges.UndirectedEdge;

public abstract class UndirectedGraph<E extends UndirectedEdge> extends AbstractGraph<E> {

    public UndirectedGraph(int initVertexesCount) {
        super(initVertexesCount);
    }

    @Override
    public boolean addEdge(E e) {
        if (containsEdge(e)) return false;
        vertexes.get(e.getFirstVertex()).add(e);
        vertexes.get(e.getSecondVertex()).add(e);
        edgesCount += 2;
        return true;
    }

    @Override
    public boolean containsEdge(E e) {
        int firstVertex = e.getEither();
        int secondVertex = e.getOther(firstVertex);
        return getEdge(firstVertex, secondVertex) != null;
    }

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public Iterable<E> incomingEdgesTo(int vertex) {
        return outcomingEdgesFrom(vertex);
    }

    @Override
    public int inDegreeOf(int vertex) {
        return outDegreeOf(vertex);
    }
    
}
