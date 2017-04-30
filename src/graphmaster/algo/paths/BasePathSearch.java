package graphmaster.algo.paths;

import graphmaster.algo.BaseGraphAlgorithm;
import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.Graph;
import graphmaster.utils.Stack;
import java.lang.reflect.Array;

public abstract class BasePathSearch<E extends Edge> extends BaseGraphAlgorithm<E> {
    
    protected final int _initialVertex;
    protected final int _destinationVertex;
    protected final boolean buildingTree;
    
    protected E[] edgeTo;
    
    public BasePathSearch(Graph<E> g, int initialVertex) {
        this(g, initialVertex, -1);
    }
    
    public BasePathSearch(Graph<E> g, int initialVertex, int destinationVertex) {
        super(g);
        _initialVertex = initialVertex;
        _destinationVertex = destinationVertex;
        buildingTree = _destinationVertex == -1;
        edgeTo = (E[]) Array.newInstance(graph.correspondingEdgeType(), graph.getVertexesCount());
    }
    
    public boolean hasPathTo(int vertex) {
        return edgeTo[vertex] != null;
    }
    
    public boolean hasPathToDest() {
        requireDestGiven();
        return hasPathTo(_destinationVertex);
    }
    
    public GraphPath<E> pathToDest() {
        requireDestGiven();
        return pathTo(_destinationVertex);
    }
    
    public GraphPath<E> pathTo(int vertex) {
        if (!hasPathTo(vertex)) return null;
        Stack<E> stack = new Stack<>();
        for (int vert = vertex; vert != _initialVertex; vert = edgeTo[vert].getOther(vert)) 
            stack.push(edgeTo[vert]);
        GraphPath<E> path = graph.isWeighted() ? new WeightedGraphPath(graph, stack, _initialVertex, vertex) 
                : new UnweightedGraphPath(graph, stack, _initialVertex, vertex);
        return path;
    }
    
    protected void requireDestGiven() {
        if (_destinationVertex == -1) throw new IllegalArgumentException("Target vertex is not specified");
    }
    
}
