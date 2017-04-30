package graphmaster.algo;

import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.Graph;
import graphmaster.utils.Stack;
import java.util.ArrayList;
import java.util.List;

public abstract class ConnectedComponentsSearch<E extends Edge> extends BaseDepthSearch<E> {
    
    private final int[] componentIdOf;
    private int connectedComponentsCount;
    
    public ConnectedComponentsSearch(Graph<E> g) {
        super(g);
        componentIdOf = new int[graph.getVertexesCount()];
        compute();
    }
    
    public int connectedComponentsCount() {
        return connectedComponentsCount;
    }
    
    public int componentIdOf(int vertex) {
        return componentIdOf[vertex];
    }
    
    public boolean areConnected(int firstVertex, int secondVertex) {
        return componentIdOf[firstVertex] == componentIdOf[secondVertex];
    }
    
    public boolean isGraphConnected() {
        return connectedComponentsCount == 1;
    }
    
    public Iterable<Integer> vertexesById(int componentId) {
        List<Integer> vertexes = new ArrayList<>();
        for (int vert = 0; vert < componentIdOf.length; vert++) 
            if (componentIdOf[vert] == componentId) vertexes.add(vert);
        return vertexes;
    }

    @Override
    protected final void compute() {
        for (int vertex : getSpecificOrder()) if (!beenIn[vertex]) startFrom(vertex);
    }
    
    private void startFrom(int vertex) {
        beenIn[vertex] = true;
        componentIdOf[vertex] = connectedComponentsCount;
        Stack<Integer> stack = new Stack<>();
        stack.push(vertex);
        while (!stack.isEmpty()) {
            int vert = stack.peek();
            if (adjVerts[vert].hasNext()) {
                int adjVert = adjVerts[vert].next().getOther(vert);
                if (!beenIn[adjVert]) {
                    beenIn[adjVert] = true;
                    componentIdOf[adjVert] = connectedComponentsCount;
                    stack.push(adjVert);
                }
            } else stack.pop();
        }
        connectedComponentsCount++;
    }
    
    protected abstract Iterable<Integer> getSpecificOrder();
    
    @Override
    protected final void shutdown() {
        beenIn = null;
        adjVerts = null;
    }
    
}
