package graphmaster.alg;

import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.Graph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Texhnolyze
 */
public abstract class DepthFirstSearch<V, E extends Edge<V>> {
    
    protected final Graph<V, E> graph;
    
    protected DepthFirstSearch(Graph<V, E> graph) {
        this.graph = graph;
    }
    
    protected final void dfs() {
        Map<V, Iterator<E>> iterators = new HashMap<>(graph.numVertices());
        for (V v : graph.vertexSet()) 
            iterators.put(v, graph.outgoingEdgesOf(v).iterator());   
        Set<V> visited = new HashSet<>();
        for (V v : this.order()) {
            if (!visited.contains(v)) 
               dfs0(v, iterators, visited);
        }
    }
    
    protected abstract Iterable<V> order();
    protected abstract void dfs0(V v, Map<V, Iterator<E>> iterators, Set<V> visited);
    
}