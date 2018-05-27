package graphmaster.alg;

import graphmaster.representation.GraphPath;
import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.HashMap;
import java.util.Map;
import lib.Stack;

/**
 *
 * @author Texhnolyze
 */
public final class NegativeCycleSearch<V, E extends WeightedEdge<V>> {
    
    private Graph<V, E> graph;
    private GraphPath<V, E> negativeCycle;
    
    public NegativeCycleSearch(Graph<V, E> graph) {
        this.graph = graph;
        search();
    }
    
    private void search() {
        Map<V, E> edgeTo = new HashMap<>();
        Map<V, Double> distTo = new HashMap<>();
        for (V v : graph.vertexSet())
            distTo.put(v, 0.0);
        for (int i = 0; i < graph.numVertices() - 1; i++) {
            boolean b = false;
            for (V v : graph.vertexSet()) {
                for (E e : graph.outgoingEdgesOf(v)) {
                    V adj = e.other(v);
                    double old = distTo.get(adj);
                    double curr = distTo.get(v) + e.weight();
                    if (old > curr) {
                        b = true;
                        edgeTo.put(adj, e);
                        distTo.put(adj, curr);
                    }
                }
            }
            if (!b) 
                return;
        }
        outer: for (V v : graph.vertexSet()) {
            for (E e : graph.outgoingEdgesOf(v)) {
                V adj = e.other(v);
                double old = distTo.get(adj);
                double curr = distTo.get(v) + e.weight();
                if (old > curr) {
                    for (int i = 0; i < graph.numVertices(); i++) 
                        adj = edgeTo.get(adj).other(adj);
                    V u = adj;
                    Stack<E> s = new Stack<>();
                    while (true) {
                        E edge = edgeTo.get(adj);
                        s.push(edge);
                        adj = edge.other(adj);
                        if (adj == u)
                            break;
                    }
                    negativeCycle = new GraphPath<>(u, u, s);
                    break outer;
                }
            }
        }
    }
    
    public boolean hasNegativeCycle() {
        return negativeCycle != null;
    }
    
    public GraphPath<V, E> negativeCycle() {
        return negativeCycle;
    }
    
}
