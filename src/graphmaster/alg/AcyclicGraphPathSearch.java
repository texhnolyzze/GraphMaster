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
public class AcyclicGraphPathSearch<V, E extends DirectedEdge<V> & WeightedEdge<V>> extends PathSearch<V, E> {
    
    public static enum SearchMode {
        SHORTEST_PATH_SEARCH((WeightedEdge e) -> {return e.weight();}), 
        LONGEST_PATH_SEARCH((WeightedEdge e) -> {return -e.weight();});
        private SearchMode(Function<WeightedEdge, Double> w) {this.w = w;}
        private final Function<WeightedEdge, Double> w;
    } 
    
    private GraphOrders<V, E> orders;
    private final SearchMode mode;
    
    public AcyclicGraphPathSearch(Graph<V, E> graph, SearchMode mode) {
        super(graph, null, null);
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
        for (V v : orders.topologicalOrder()) {
            for (E e : graph.outgoingEdgesOf(v)) {
                V adj = e.dest();
                WeightedEdge<V> we = (WeightedEdge<V>) e;
                double old = dist.get(adj);
                double curr = dist.get(v) + mode.w.apply(we);
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
