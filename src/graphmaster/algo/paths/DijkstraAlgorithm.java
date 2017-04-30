package graphmaster.algo.paths;

import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import graphmaster.representation.graph.WeightedGraph;
import java.util.TreeMap;

public class DijkstraAlgorithm<E extends WeightedEdge> extends BaseWeightedPathsSearch<E> {

    private TreeMap<Double, Integer> pQ;
    private boolean[] inTree;
    
    public DijkstraAlgorithm(Graph<E> g, int initialVertex) {
        this(g, initialVertex, -1);
    }
    
    public DijkstraAlgorithm(Graph<E> g, int initialVertex, int destinationVertex) {
        super(g, initialVertex, destinationVertex);
        requireNonNegativeWeights();
        pQ = new TreeMap<>();
        inTree = new boolean[graph.getVertexesCount()];
        compute();
        shutdown();
    }

    @Override
    protected final void compute() {
        pQ.put(0.0, _initialVertex);
        while (!pQ.isEmpty()) {
            int nextVertex = pQ.pollFirstEntry().getValue();
            if (!inTree[nextVertex]) {
                inTree[nextVertex] = true;
                if (!buildingTree && nextVertex == _destinationVertex) break;
                relax(nextVertex);
            }
        }
    }

    @Override
    protected void relax(int vertex) {
        for (E e : graph.outcomingEdgesFrom(vertex)) {
            int adjVert = e.getOther(vertex);
            double perhapsLessWeight = distanceTo[vertex] + e.getWeight();
            if (distanceTo[adjVert] > perhapsLessWeight) {
                edgeTo[adjVert] = e;
                distanceTo[adjVert] = perhapsLessWeight;
                pQ.put(perhapsLessWeight, adjVert);
            }
        }
    }
    
    @Override
    protected final void shutdown() {
        pQ = null;
        inTree = null;
        distanceTo = null;
    }
    
    private void requireNonNegativeWeights() {
        WeightedGraph<E> weighted = (WeightedGraph<E>) graph;
        if (weighted.hasNegativeWeight()) 
            throw new IllegalArgumentException("Does not work with negative weights!");
    }

    
    
}
