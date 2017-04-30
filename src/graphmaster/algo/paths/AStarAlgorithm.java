package graphmaster.algo.paths;

import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.TreeMap;

public class AStarAlgorithm<E extends WeightedEdge> extends BaseWeightedPathsSearch<E>  {

    private boolean[] inTree;
    private TreeMap<Double, Integer> pQ;
    private final AStarHeuristic _heuristic;
    
    public AStarAlgorithm(Graph<E> g, int initialVertex, int destinationVertex, AStarHeuristic heuristic) {
        super(g, initialVertex, destinationVertex);
        pQ = new TreeMap<>();
        _heuristic = heuristic;
        inTree = new boolean[graph.getVertexesCount()];
        compute();
        shutdown();
    }

    @Override
    protected void relax(int vertex) {
        for (E e : graph.outcomingEdgesFrom(vertex)) {
            int adjVertex = e.getOther(vertex);
            double tentative = distanceTo[vertex] + e.getWeight();
            if (tentative < distanceTo[adjVertex]) {
                edgeTo[adjVertex] = e;
                distanceTo[adjVertex] = tentative;
                pQ.put(distanceTo[adjVertex] 
                        + _heuristic.getEstimateCost(adjVertex, _destinationVertex), adjVertex);
            }
        }
    }

    @Override
    protected void compute() {
        pQ.put(0.0, _initialVertex);
        while (!pQ.isEmpty()) {
            int nextVertex = pQ.pollFirstEntry().getValue();
            if (!inTree[nextVertex]) {
                inTree[nextVertex] = true;
                if (nextVertex == _destinationVertex) break;
                relax(nextVertex);
            }
        }
    }

    @Override
    protected void shutdown() {
        pQ = null;
        inTree = null;
    }
    
    
    
}
