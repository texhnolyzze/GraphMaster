package graphmaster.alg;

import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.Graph;
import graphmaster.utils.Queue;
import graphmaster.utils.Stack;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Texhnolyze
 */
public final class GraphOrders<V, E extends Edge<V>> extends DepthFirstSearch<V, E> {
    
    private boolean hasCycle;    
    
    private Queue<V> pre;
    private Queue<V> post;
    private Stack<V> reversePost;
    
    private final boolean computePre;
    private final boolean computePost;
    private final boolean computeReversePost;
    
    public GraphOrders(Graph<V, E> graph, boolean computePre, boolean computePost, boolean computeReversePost) {
        super(graph);
        if (this.computePre = computePre) pre = new Queue<>();
        if (this.computePost = computePost) post = new Queue<>();
        if (this.computeReversePost = computeReversePost) reversePost = new Stack<>();
        super.dfs();
    }
    
    @Override
    protected Iterable<V> order() {
        return graph.vertexSet();
    }
    
    @Override
    protected void dfs0(V v, Set<V> visited) {
        visited.add(v);
        Stack<V> stack = new Stack<>();
        Set<V> onStack = new HashSet<>();
        stack.push(v);
        onStack.add(v);
        while (!stack.isEmpty()) {
            V vertex = stack.peek();
            Iterator<E> it = iterators.get(vertex);
            if (it.hasNext()) {
                V adj = it.next().other(vertex);
                if (!visited.contains(adj)) {
                    if (computePre) 
                        pre.add(vertex);
                    stack.push(adj);
                    visited.add(adj);
                    onStack.add(adj);
                } else {
                    if (onStack.contains(adj))
                        hasCycle = true;
                }
            } else {
                stack.pop();
                onStack.remove(vertex);
                if (computePost)
                    post.add(vertex);
                if (computeReversePost)
                    reversePost.push(vertex);
            }
        }
    }
    
    public boolean hasCycle() {
        return hasCycle;
    }
    
    public Iterable<V> preOrder() {
        return pre;
    }
    
    public Iterable<V> postOrder() {
        return post;
    }
    
    public Iterable<V> reversePostOrder() {
        return reversePost;
    }
    
    public Iterable<V> topologicalOrder() {
        return hasCycle ? null : reversePost;
    }
    
}
