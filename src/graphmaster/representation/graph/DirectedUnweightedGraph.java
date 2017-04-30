package graphmaster.representation.graph;

import graphmaster.representation.edges.DirectedUnweightedEdge;

public class DirectedUnweightedGraph extends DirectedGraph<DirectedUnweightedEdge> 
        implements UnweightedGraph<DirectedUnweightedEdge> {

    public DirectedUnweightedGraph(int initVertexesCount) {
        super(initVertexesCount);
    }

    @Override
    public Class<DirectedUnweightedEdge> correspondingEdgeType() {
        return DirectedUnweightedEdge.class;
    }
    
}
