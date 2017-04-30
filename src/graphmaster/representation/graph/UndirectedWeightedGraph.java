package graphmaster.representation.graph;

import graphmaster.representation.edges.UndirectedWeightedEdge;

public class UndirectedWeightedGraph extends UndirectedGraph<UndirectedWeightedEdge> 
        implements WeightedGraph<UndirectedWeightedEdge> {
    
    public UndirectedWeightedGraph(int initVertexesCount) {
        super(initVertexesCount);
    }

    @Override
    public Class<UndirectedWeightedEdge> correspondingEdgeType() {
        return UndirectedWeightedEdge.class;
    }
    
}
