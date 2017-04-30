package graphmaster.algo.paths;

import graphmaster.representation.edges.UnweightedEdge;
import graphmaster.representation.graph.Graph;
import graphmaster.utils.Stack;

public class UnweightedGraphPath<E extends UnweightedEdge> extends GraphPath<E> {

    public UnweightedGraphPath(Graph<E> g, Stack<E> path, int pathSource, int pathTarget) {
        super(g, path, pathSource, pathTarget);
    }

    @Override
    protected void buildPath(Stack<E> path) {
        while (!path.isEmpty()) {
            _path.add(path.pop());
            pathLength++;
        }
    }
    
}
