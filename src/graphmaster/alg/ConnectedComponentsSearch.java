package graphmaster.alg;

import graphmaster.GraphUtils;
import graphmaster.representation.edges.DirectedEdge;
import graphmaster.representation.edges.Edge;
import graphmaster.representation.edges.UndirectedEdge;
import graphmaster.representation.graph.Graph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lib.Stack;

/**
 *
 * @author Texhnolyze
 */
public abstract class ConnectedComponentsSearch<V, E extends Edge<V>> extends DepthFirstSearch<V, E>{
    
    private int numComponents;
    private Map<V, Integer> componentID = new HashMap<>();
    private Map<Integer, List<V>> components = new HashMap<>();
    
    private ConnectedComponentsSearch(Graph<V, E> graph) {
        super(graph);
        super.dfs();
    }
    
    @Override
    protected void dfs0(V v, Map<V, Iterator<E>> iterators, Set<V> visited) {
        visited.add(v);
        componentID.put(v, numComponents);
        List<V> component = new ArrayList<>();
        components.put(numComponents, component);
        Stack<V> stack = new Stack<>();
        stack.push(v);
        while (!stack.isEmpty()) {
            V vertex = stack.peek();
            Iterator<E> it = iterators.get(vertex);
            if (it.hasNext()) {
                E edge = it.next();
                V adj = edge.other(vertex);
                if (!visited.contains(adj)) {
                    visited.add(adj);
                    stack.push(adj);
                    component.add(adj);
                    componentID.put(adj, numComponents);
                }
            } else
                stack.pop();
        }
        numComponents++;
    }
    
    public int numComponents() {
        return numComponents;
    }
    
    public boolean isGraphConnected() {
        return numComponents == 1;
    }
    
    public int id(V v) {
        return componentID.getOrDefault(v, -1);
    }
    
    public boolean areConnected(V v1, V v2) {
        Integer id1 = componentID.get(v1);
        if (id1 == null)
            return false;
        Integer id2 = componentID.get(v2);
        if (id2 == null)
            return false;
        return id1.intValue() == id2.intValue();
    }
    
    public Iterable<V> componentByID(int id) {
        if (id < 0 || id >= numComponents)
            return Collections.EMPTY_LIST;
        return components.get(id);
    }
    
    public Collection<List<V>> components() {
        return components.values();
    }
    
    public static <V, E extends Edge<V>> ConnectedComponentsSearch<V, E> search(Graph<V, E> g) {
        if (g.directed()) 
            return new DirectedConnectedComponentsSearch(g);
        else 
            return new UndirectedConnectedComponentsSearch(g);
    }
    
    private static class UndirectedConnectedComponentsSearch<V, E extends UndirectedEdge<V>> extends ConnectedComponentsSearch<V, E> {
        UndirectedConnectedComponentsSearch(Graph<V, E> graph) {super(graph);}
        @Override protected Iterable<V> order() {return graph.vertexSet();}
    }
    
    private static class DirectedConnectedComponentsSearch<V, E extends DirectedEdge<V>> extends ConnectedComponentsSearch<V, E> {
        DirectedConnectedComponentsSearch(Graph<V, E> graph) {super(graph);}
        @Override protected Iterable<V> order() {return new GraphOrders<>(GraphUtils.reversedGraphDelegator(graph), false, false, true).reversePostOrder();}
    }
    
}
