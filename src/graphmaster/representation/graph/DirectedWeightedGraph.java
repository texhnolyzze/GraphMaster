package graphmaster.representation.graph;

import graphmaster.representation.edges.DirectedWeightedEdge;

public class DirectedWeightedGraph extends DirectedGraph<DirectedWeightedEdge> 
        implements WeightedGraph<DirectedWeightedEdge> {
    
    public DirectedWeightedGraph(int initVertexesCount) {
        super(initVertexesCount);
    }

    @Override
    public Class<DirectedWeightedEdge> correspondingEdgeType() {
        return DirectedWeightedEdge.class;
    }
    
}
