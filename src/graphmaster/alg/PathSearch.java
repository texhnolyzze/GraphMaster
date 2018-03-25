package graphmaster.alg;

import graphmaster.representation.GraphPath;
import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.Graph;
import java.util.Collections;
import java.util.Map;
import lib.Stack;

/**
 *
 * @author Texhnolyze
 */
public abstract class PathSearch<V, E extends Edge<V>> {
 
    private final V source, dest;
    private final Graph<V, E> graph;
    private Map<V, E> edgeTo;
    
    protected PathSearch(Graph<V, E> graph, V source) {
        this(graph, source, null);
    }
    
    protected PathSearch(Graph<V, E> graph, V source, V dest) {
        if (source != null && !graph.containsVertex(source))
            throw new IllegalArgumentException("Source vertex must be in graph.");
        if (dest != null && !graph.containsVertex(dest))
            throw new IllegalArgumentException("Destination vertex must be in graph.");
        this.source = source;
        this.dest = dest;
        this.graph = graph;
    }
    
    protected final void search() {
        this.edgeTo = search0(graph, source, dest);
    }
    
    protected abstract Map<V, E> search0(Graph<V, E> graph, V source, V dest);
    
    public V source() {
        return source;
    }
    
    public V dest() {
        return dest;
    }
    
    public Graph<V, E> graph() {
        return graph;
    }
    
    public boolean hasPathTo(V v) {
        return edgeTo.containsKey(v);
    }
    
    public GraphPath<V, E> pathTo(V v) {
        if (!edgeTo.containsKey(v))
            return null;
        E e = edgeTo.get(v);
        if (e == null)
            return new GraphPath<>(source, source, Collections.EMPTY_LIST);
        V curr = v;
        Stack<E> path = new Stack<>();
        for (;;) {
            path.push(e);
            curr = e.other(curr);
            e = edgeTo.get(curr);
            if (e == null)
                break;
        }
        return new GraphPath<>(source, v, path);
    }
    
}
