package graphmaster.algo;

import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.Graph;
import java.util.Iterator;

public abstract class BaseDepthSearch<E extends Edge> extends BaseGraphAlgorithm<E> {
    
    protected boolean[] beenIn;
    protected Iterator<E>[] adjVerts;
    
    public BaseDepthSearch(Graph<E> g) {
        super(g);
        beenIn = new boolean[graph.getVertexesCount()];
        adjVerts = (Iterator<E>[]) new Iterator[graph.getVertexesCount()];
    }
    
}
