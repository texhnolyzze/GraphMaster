package graphmaster.alg;

import graphmaster.representation.GraphPath;
import graphmaster.representation.edges.DirectedEdge;
import graphmaster.representation.graph.Graph;
import graphmaster.utils.Stack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Texhnolyze
 */
public class DirectedCycleFinding<V, E extends DirectedEdge<V>> extends DepthFirstSearch<V, E> {

    private GraphPath<V, E> cycle;
    
    public DirectedCycleFinding(Graph<V, E> graph) {
        super(graph);
        super.dfs();
    }

    @Override
    protected Iterable<V> order() {
        return graph.vertexSet();
    }

    @Override
    protected void dfs0(V v, Set<V> visited) {
        if (cycle != null)
            return;
        visited.add(v);
        Stack<V> stack = new Stack<>();
        Set<V> onStack = new HashSet<>();
        stack.push(v);
        onStack.add(v);
        Map<V, E> edgeTo = new HashMap<>();
        while (!stack.isEmpty()) {
            V vertex = stack.peek();
            Iterator<E> it = iterators.get(vertex);
            if (it.hasNext()) {
                E e = it.next();
                V adj = e.dest();
                if (!visited.contains(adj)) {
                    edgeTo.put(adj, e);
                    stack.push(adj);
                    onStack.add(adj);
                } else {
                    if (onStack.contains(adj)) {
                        Stack<E> edges = new Stack<>();
                        E eTemp = e;
                        V vTemp = adj;
                        edges.push(eTemp);
                        for (;;) {
                            edges.push(eTemp);
                            vTemp = eTemp.source();
                            if (vTemp == adj)
                                break;
                            eTemp = edgeTo.get(vTemp);
                        }
                        cycle = new GraphPath<>(adj, adj, edges);
                    }
                }
            } else 
                stack.pop();
        }
    }
    
    public boolean hasCycle() {
        return cycle != null;
    }
    
    public GraphPath<V, E> cycle() {
        return cycle;
    }
    
}
