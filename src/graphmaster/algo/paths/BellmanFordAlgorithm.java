package graphmaster.algo.paths;

import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import graphmaster.utils.Stack;

public class BellmanFordAlgorithm<E extends WeightedEdge> extends BaseWeightedPathsSearch<E> {

    private WeightedGraphPath<E> negativeCycle;
    
    public BellmanFordAlgorithm(Graph<E> g, int initialVertex) {
        this(g, initialVertex, -1);
    }

    public BellmanFordAlgorithm(Graph<E> g, int initialVertex, int destinationVertex) {
        super(g, initialVertex, destinationVertex);
        compute();
        shutdown();
    }
    
    public boolean hasNegativeCycle() {
        return negativeCycle != null;
    }
    
    public WeightedGraphPath<E> negativeCycle() {
        return negativeCycle;
    }

    @Override
    public boolean hasPathTo(int vertex) {
        if (hasNegativeCycle()) return false;
        return super.hasPathTo(vertex);
    }

    @Override
    public GraphPath<E> pathTo(int vertex) {
        if (hasNegativeCycle()) return null;
        return super.pathTo(vertex);
    }

    @Override
    public double distanceTo(int vertex) {
        if (hasNegativeCycle()) return Double.NEGATIVE_INFINITY;
        return super.distanceTo(vertex); 
    }
    
    @Override
    protected void relax(int vertex) {
        throw new UnsupportedOperationException("Not supported."); 
    }
    
    private boolean relax(E e, int srcVertex) {
        int dest = e.getOther(srcVertex);
        if (distanceTo[dest] > distanceTo[srcVertex] + e.getWeight()) {
            edgeTo[dest] = e;
            distanceTo[dest] = distanceTo[srcVertex] + e.getWeight();
            return true;
        }
        return false;
    }

    @Override
    protected void compute() {
        boolean changed = false;
        for (int i = 0; i < graph.getVertexesCount() - 1; i++) {
            changed = false;
            for (int j = 0; j < graph.getVertexesCount(); j++) {
                for (E e : graph.outcomingEdgesFrom(j)) if (relax(e, j)) changed = true;
            }
            if (!changed) break;
        }
        if (changed) {
            outer: for (int j = 0; j < graph.getVertexesCount(); j++) {
                for (E e : graph.outcomingEdgesFrom(j)) {
                    if (relax(e, j)) {
                        negativeCycle = findNegativeCycle(j);
                        break outer;
                    }
                }
            }
        }
    }
    
    private WeightedGraphPath<E> findNegativeCycle(int initVertex) {
        Stack<E> cycle = new Stack<>();
        int cycleVertex = initVertex;
        for (int i = 0; i < graph.getVertexesCount(); i++) 
            cycleVertex = edgeTo[cycleVertex].getOther(cycleVertex);
        int currentVertex = edgeTo[cycleVertex].getOther(cycleVertex);
        int cycleBegin = currentVertex;
        while (currentVertex != cycleVertex) {
            E e = edgeTo[currentVertex];
            cycle.push(e);
            currentVertex = e.getOther(currentVertex);
        }
        cycle.push(edgeTo[currentVertex]);
        return new WeightedGraphPath<>(graph, cycle, cycleBegin, cycleVertex);
    }

    @Override
    protected void shutdown() {
        if (hasNegativeCycle()) {
            edgeTo = null;
            distanceTo = null;
        }
    }
    
}
