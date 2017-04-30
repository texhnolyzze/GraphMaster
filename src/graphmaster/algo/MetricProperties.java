package graphmaster.algo;

import graphmaster.algo.paths.UnweightedShortestPathSearch;
import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.DirectedGraph;
import graphmaster.representation.graph.Graph;
import graphmaster.representation.graph.UndirectedGraph;

public class MetricProperties<E extends Edge> extends BaseGraphAlgorithm<E> {

    private final int[] eccentricityOf;
    private int graphDiameter;
    private int graphRadius;
    private int graphCenterVertex;
    
    public MetricProperties(Graph<E> g) {
        super(g);
        requireConnected();
        eccentricityOf = new int[graph.getVertexesCount()];
        compute();
        shutdown();
    }
    
    public int eccentricityOf(int vertex) {
        return eccentricityOf[vertex];
    }
    
    public int graphDiameter() {
        return graphDiameter;
    }
    
    public int graphRadius() {
        return graphRadius;
    }
    
    public int graphCenter() {
        return graphCenterVertex;
    }

    @Override
    protected void compute() {
        computeEccentricities();
        graphDiameter = computeGraphDiameter();
        graphRadius = computeGraphRadius();
        graphCenterVertex = defineGraphCenter();
    }

    @Override
    protected void shutdown() {
        //NOTHING TODO.
    }
    
    private void computeEccentricities() {
        for (int vertex : (Iterable<Integer>) graph.scrollVertexes()) {
            UnweightedShortestPathSearch<E> search;
            search = new UnweightedShortestPathSearch<>(graph, vertex);
            for (int v : (Iterable<Integer>) graph.scrollVertexes()) {
                int pathLengthToV = search.pathTo(v).length();
                eccentricityOf[vertex] = Math.max(eccentricityOf[vertex], pathLengthToV);
            }
        }
    }
    
    private int computeGraphDiameter() {
        int diameter = 0;
        for (int vertex : (Iterable<Integer>) graph.scrollVertexes()) 
            diameter = Math.max(eccentricityOf[vertex], diameter);
        return diameter;
    }
    
    private int computeGraphRadius() {
        int radius = graphDiameter;
        for (int vertex : (Iterable<Integer>) graph.scrollVertexes())
            radius = Math.min(radius, eccentricityOf[vertex]);
        return radius;
    }
    
    private int defineGraphCenter() {
        int center = 0;
        for (int vertex : (Iterable<Integer>) graph.scrollVertexes())
            if (eccentricityOf[vertex] == graphRadius) {
                center = vertex;
                break;
            }
        return center;   
    }
    
    private void requireConnected() {
        ConnectedComponentsSearch<E> search;
        if (graph.isDirected()) search = new DirectedCCS<>((DirectedGraph) graph);
        else search = new UndirectedCCS<>((UndirectedGraph) graph);
        if (!search.isGraphConnected())
            throw new IllegalArgumentException("Graph is not connected.");
    }
    
}
