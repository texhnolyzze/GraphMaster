package graphmaster.algo.paths;

import graphmaster.algo.DirectedGraphOrders;
import graphmaster.representation.edges.DirectedWeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.Iterator;

public class AcyclicWeightedPathsSearch extends BaseWeightedPathsSearch<DirectedWeightedEdge> {

    private Iterable<Integer> topologicalOrder;
    
    public AcyclicWeightedPathsSearch(Graph<DirectedWeightedEdge> g, int initialVertex, int destinationVertex) {
        super(g, initialVertex, destinationVertex);
        requireAcycle();
        compute();
        shutdown();
    }

    public AcyclicWeightedPathsSearch(Graph<DirectedWeightedEdge> g, int initialVertex) {
        this(g, initialVertex, -1);
    }

    @Override
    protected void relax(int vertex) {
        for (DirectedWeightedEdge e : graph.outcomingEdgesFrom(vertex)) {
            int adjVert = e.getOther(vertex);
            double perhapsLessWeight = distanceTo[vertex] + e.getWeight();
            if (distanceTo[adjVert] > perhapsLessWeight) {
                edgeTo[adjVert] = e;
                distanceTo[adjVert] = perhapsLessWeight;
            }
        }
    }

    @Override
    protected void compute() {
        Iterator<Integer> it = topologicalOrder.iterator();
        while (it.next() != _initialVertex) {} //flush unattainable vertexes
        relax(_initialVertex);
        while (it.hasNext()) {
            int nextVertex = it.next();
            if (!buildingTree && nextVertex == _destinationVertex) break;
            relax(nextVertex);        
        }
    }

    @Override
    protected void shutdown() {
        topologicalOrder = null;
        distanceTo = null;
    }
    
    private void requireAcycle() {
        DirectedGraphOrders<DirectedWeightedEdge> orders;
        orders = new DirectedGraphOrders<>(graph);
        if (orders.hasCycle()) throw new IllegalArgumentException("Does not work on the cyclic graph!");
        topologicalOrder = orders.topologicalOrder();
    }
    
}
