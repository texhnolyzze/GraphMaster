package graphmaster.algo;

import graphmaster.representation.edges.DirectedEdge;
import graphmaster.representation.graph.Graph;
import graphmaster.utils.Stack;
import java.util.Collections;

public class DirectedGraphOrders<E extends DirectedEdge> extends BaseDepthSearch<E> {

    private boolean hasCycle;
    private boolean[] onStack;
    private final Stack<Integer> reversePostOrder;
    
    public DirectedGraphOrders(Graph<E> g) {
        super(g);
        onStack = new boolean[graph.getVertexesCount()];
        reversePostOrder = new Stack<>();
        compute();
        shutdown();
    }
    
    public Iterable<Integer> reversePostOrder() {
        return reversePostOrder;
    }
    
    public Iterable<Integer> topologicalOrder() {
        return hasCycle ? Collections.EMPTY_LIST : reversePostOrder;
    }
    
    public boolean hasCycle() {
        return hasCycle;
    }
    
    @Override
    protected void compute() {
        for (int vertex : (Iterable<Integer>) graph.scrollVertexes()) 
            if (!beenIn[vertex]) startFrom(vertex);
    }

    @Override
    protected void shutdown() {
        beenIn = null;
        onStack = null;
        adjVerts = null;
    }
    
    private void startFrom(int initialVertex) {
        beenIn[initialVertex] = true;
        Stack<Integer> stack = new Stack<>();
        stack.push(initialVertex);
        while (!stack.isEmpty()) {
            int currVert = stack.peek();
            if (adjVerts[currVert].hasNext()) {
                int adjVert = adjVerts[currVert].next().getOther(currVert);
                if (!beenIn[adjVert]) {
                    stack.push(adjVert);
                    beenIn[adjVert] = true;
                    onStack[adjVert] = true;
                } else if (onStack[adjVert]) hasCycle = true;
            } else {
                stack.pop();
                onStack[currVert] = false;
                reversePostOrder.push(currVert);
            }
        }
    }
    
}
