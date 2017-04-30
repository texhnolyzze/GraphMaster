package graphmaster.representation.graph;

import graphmaster.representation.edges.UndirectedUnweightedEdge;

public class UndirectedUnweightedGraph extends UndirectedGraph<UndirectedUnweightedEdge> 
        implements UnweightedGraph<UndirectedUnweightedEdge> {

    public UndirectedUnweightedGraph(int initVertexesCount) {
        super(initVertexesCount);
    }

    @Override
    public Class<UndirectedUnweightedEdge> correspondingEdgeType() {
        return UndirectedUnweightedEdge.class;
    }
    
}
