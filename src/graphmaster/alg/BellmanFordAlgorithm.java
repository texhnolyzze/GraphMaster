package graphmaster.alg;

import graphmaster.representation.GraphPath;
import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Texhnolyze
 */
public class BellmanFordAlgorithm<V, E extends WeightedEdge<V>> extends PathSearch<V, E> {

    private boolean hasNegativeCycle;
    
    public BellmanFordAlgorithm(Graph<V, E> graph, V source) {
        super(graph, source);
    }

    @Override
    protected Map<V, E> search0(Graph<V, E> graph, V source, V dest) {
        Map<V, E> edgeTo = new HashMap<>();
        Map<V, Double> distTo = new HashMap<>();
        for (V v : graph.vertexSet()) {
            distTo.put(v, Double.POSITIVE_INFINITY);
        }
        distTo.put(source, 0.0);
        edgeTo.put(source, null);
        for (int i = 0; i < graph.numVertices() - 1; i++) {
            boolean b = false;
            for (V v : graph.vertexSet()) {
                for (E e : graph.outgoingEdgesOf(v)) {
                    V adj = e.other(v);
                    double d = distTo.get(v) + e.weight();
                    if (distTo.get(adj) > d) {
                        b = true;
                        distTo.put(adj, d);
                        edgeTo.put(adj, e);
                    }
                }
            }
            if (!b) 
                break;
        }
        outer: for (V v : graph.vertexSet()) {
            for (E e : graph.outgoingEdgesOf(v)) {
                V adj = e.other(v);
                double d = distTo.get(v) + e.weight();
                if (distTo.get(adj) > d) {
                    hasNegativeCycle = true;
                    break outer;
                }
            }
        }
        return edgeTo;
    }
    
    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

    @Override
    public GraphPath<V, E> pathTo(V v) {
        if (hasNegativeCycle) 
            throw new RuntimeException("Shortest path to " + v + " doesn't exist.");
        return super.pathTo(v);
    }

    @Override
    public boolean hasPathTo(V v) {
        if (hasNegativeCycle)
            return false;
        return super.hasPathTo(v); 
    }
    
}
