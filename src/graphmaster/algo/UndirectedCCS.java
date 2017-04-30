package graphmaster.algo;

import graphmaster.representation.edges.UndirectedEdge;
import graphmaster.representation.graph.Graph;

public class UndirectedCCS<E extends UndirectedEdge> extends ConnectedComponentsSearch<E> {

    public UndirectedCCS(Graph<E> g) {
        super(g);
        compute();
        shutdown();
    }

    @Override
    protected Iterable<Integer> getSpecificOrder() {
        return graph.scrollVertexes();
    }
    
}
