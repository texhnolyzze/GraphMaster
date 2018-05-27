package graphmaster.alg;

import graphmaster.representation.edges.DirectedEdge;
import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author Texhnolyze
 */
public class AcyclicGraphPathSearch<V, E extends WeightedEdge<V> & DirectedEdge<V>> extends PathSearch<V, E> {
    
    public static enum SearchMode {
        SHORTEST_PATH_SEARCH((WeightedEdge e) -> {return e.weight();}), 
        LONGEST_PATH_SEARCH((WeightedEdge e) -> {return -e.weight();});
        private SearchMode(Function<WeightedEdge, Double> w) {this.w = w;}
        private final Function<WeightedEdge, Double> w;
    } 
    
    private GraphOrders<V, E> orders;
    private final SearchMode mode;
    
    public AcyclicGraphPathSearch(Graph<V, E> graph, V source, SearchMode mode) {
        this(graph, source, null, mode);
    }
    
    public AcyclicGraphPathSearch(Graph<V, E> graph, V source, V dest, SearchMode mode) {
        super(graph, source, dest);
        this.mode = mode;
        orders = new GraphOrders<>(graph, false, false, true);
        if (orders.hasCycle())
            throw new IllegalArgumentException("Graph must be acyclic.");
        super.search();
    }

    @Override
    protected Map<V, E> search0(Graph<V, E> graph, V source, V dest) {
        Map<V, E> edgeTo = new HashMap<>();
        Map<V, Double> dist = new HashMap<>();
        for (V v : graph.vertexSet())
            dist.put(v, Double.POSITIVE_INFINITY);
        dist.put(source, 0.0);
        for (V v : orders.topologicalOrder()) {
            if (v == dest)
                break;
            for (E e : graph.outgoingEdgesOf(v)) {
                V adj = e.dest();
                double old = dist.get(adj);
                double curr = dist.get(v) + mode.w.apply(e);
                if (old > curr) {
                    edgeTo.put(adj, e);
                    dist.put(adj, curr);
                }
            }
        }
        return edgeTo;
    }
    
    public SearchMode searchMode() {
        return mode;
    }
    
}
