package graphmaster.algo.paths;

import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.Arrays;

public abstract class BaseWeightedPathsSearch<E extends WeightedEdge> extends BasePathSearch<E> {
    
    protected double[] distanceTo;
    
    public BaseWeightedPathsSearch(Graph<E> g, int initialVertex, int destinationVertex) {
        super(g, initialVertex, destinationVertex);
        distanceTo = new double[graph.getVertexesCount()];
        Arrays.fill(distanceTo, Double.POSITIVE_INFINITY);
        distanceTo[_initialVertex] = 0.0;
    }
    
    public double distanceTo(int vertex) {
        if (!hasPathTo(vertex)) return Double.POSITIVE_INFINITY;
        WeightedGraphPath<E> path = (WeightedGraphPath<E>) pathTo(vertex);
        return path.weight();
    }
    
    public double distanceToDest() {
        requireDestGiven();
        return distanceTo(_destinationVertex);
    }
    
    protected abstract void relax(int vertex);
    
}
