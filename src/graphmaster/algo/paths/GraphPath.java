package graphmaster.algo.paths;

import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.Graph;
import graphmaster.utils.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class GraphPath<E extends Edge> {
    
    private final Graph<E> graph;
    
    protected int pathLength;
    
    private final int _pathSource;
    private final int _pathTarget;
    
    protected final List<E> _path;
    
    public GraphPath(Graph<E> g, Stack<E> path, int pathSource, int pathTarget) {
        graph = g;
        _path = new ArrayList<>();
        _pathSource = pathSource;
        _pathTarget = pathTarget;
        buildPath(path);
    }
    
    protected abstract void buildPath(Stack<E> path);
    
    public int length() {
        return pathLength;
    }
    
    public Graph<E> getGraph() {
        return graph;
    }
    
    public int pathSource() {
        return _pathSource;
    }
    
    public int pathTarget() {
        return _pathTarget;
    }
    
    public List<Integer> vertexes() {
        List<Integer> verts = new ArrayList<>();
        verts.add(_pathSource);
        int currentVert = _pathSource;
        for (E e : _path) {
            int adjVert = e.getOther(currentVert);
            verts.add(adjVert);
            currentVert = adjVert;
        }
        return verts;
    }
    
    public List<E> edges() {
        return Collections.unmodifiableList(_path);
    }
    
}
