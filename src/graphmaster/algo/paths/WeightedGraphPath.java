package graphmaster.algo.paths;

import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import graphmaster.utils.Stack;

public class WeightedGraphPath<E extends WeightedEdge> extends GraphPath<E> {

    private double pathWeight;
    
    public WeightedGraphPath(Graph<E> g, Stack<E> path, int pathSource, int pathTarget) {
        super(g, path, pathSource, pathTarget);
    }
    
    public double weight() {
        return pathWeight;
    }

    @Override
    protected void buildPath(Stack<E> path) {
        while (!path.isEmpty()) {
            E e = path.pop();
            _path.add(e);
            pathLength++;
            pathWeight += e.getWeight();
        }
    }
    
}
