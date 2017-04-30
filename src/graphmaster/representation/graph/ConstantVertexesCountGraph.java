package graphmaster.representation.graph;

import graphmaster.representation.edges.Edge;

public class ConstantVertexesCountGraph<E extends Edge> extends AbstractGraph<E> {

    private final Graph<E> _srcGraph;
    
    public ConstantVertexesCountGraph(AbstractGraph<E> srcGraph) {
        super(srcGraph);
        _srcGraph = srcGraph;
    }

    @Override
    public void addVertex() {
        throw new UnsupportedOperationException("Graph is unmodifiable.");
    }

    @Override
    public boolean addEdge(E e) {
        return _srcGraph.addEdge(e);
    }

    @Override
    public boolean containsEdge(E e) {
        return _srcGraph.containsEdge(e);
    }

    @Override
    public boolean isDirected() {
        return _srcGraph.isDirected();
    }

    @Override
    public boolean isWeighted() {
        return _srcGraph.isWeighted();
    }

    @Override
    public Iterable<E> incomingEdgesTo(int vertex) {
        return _srcGraph.incomingEdgesTo(vertex);
    }

    @Override
    public int inDegreeOf(int vertex) {
        return _srcGraph.inDegreeOf(vertex);
    }

    @Override
    public Class<E> correspondingEdgeType() {
        return _srcGraph.correspondingEdgeType();
    }
    
}
