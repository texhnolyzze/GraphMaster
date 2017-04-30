package graphmaster.algo.paths;

import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.Graph;
import graphmaster.utils.ArrayQueue;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class UnweightedShortestPathSearch<E extends Edge> extends BasePathSearch<E> {
    
    private boolean[] beenIn;
    
    public UnweightedShortestPathSearch(Graph<E> g, int start) {
        this(g, start, -1);
    }
    
    public UnweightedShortestPathSearch(Graph<E> g, int start, int dest) {
        this(g, start, dest, Collections.EMPTY_LIST);
    }
    
    public UnweightedShortestPathSearch(Graph<E> g, int start, int dest, List<Integer> vertexesToIgnore) {
        super(g, start, dest);
        if (start == dest) throw new IllegalArgumentException("start must be different from dest!");
        beenIn = new boolean[graph.getVertexesCount()];
        for (int vertexToIgnore : vertexesToIgnore) {
            if (vertexToIgnore == start || vertexToIgnore == dest)
                throw new IllegalArgumentException("Can't ignore start vertex or destination vertex.");
            beenIn[vertexToIgnore] = true;
        } 
        compute();
        shutdown();
    }

    @Override
    protected void compute() {
        beenIn[_initialVertex] = true;
        Queue<Integer> q = new ArrayQueue<>();
        q.offer(_initialVertex);
        while (!q.isEmpty()) {
            int currVert = q.poll();
            for (E e : graph.outcomingEdgesFrom(currVert)) {
                int adjVert = e.getOther(currVert);
                if (!beenIn[adjVert]) {
                    beenIn[adjVert] = true;
                    edgeTo[adjVert] = e;
                    q.offer(adjVert);
                    if (!buildingTree && adjVert == _destinationVertex) {
                        q.clear();
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void shutdown() {
        beenIn = null;
    }
    
}
