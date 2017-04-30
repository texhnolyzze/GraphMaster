package graphmaster.algo;

import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.Graph;

public abstract class BaseGraphAlgorithm<E extends Edge> {
    
    protected final Graph<E> graph;
    
    public BaseGraphAlgorithm(Graph<E> g) {
        graph = g;
    }
    
    protected abstract void compute();
    
    protected abstract void shutdown();
    
}
