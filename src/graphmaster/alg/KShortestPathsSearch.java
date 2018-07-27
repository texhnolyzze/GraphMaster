package graphmaster.alg;

import graphmaster.representation.GraphPath;
import graphmaster.representation.edges.Edge;
import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Texhnolyze
 */
public final class KShortestPathsSearch<V, E extends Edge<V>> {
    
    private final int k;
    private final Graph<V, E> g;
	
    private final boolean graphWeighted;

    private final V source, dest;
    private final List<GraphPath<V, E>> findedPaths;
    
    public KShortestPathsSearch(Graph<V, E> g, V source, V dest, int k) {
        if (k <= 0)
            throw new IllegalArgumentException("k must be positive");
        if (!g.containsVertex(source))
            throw new IllegalArgumentException("Graph doesn't contains source vertex.");
        if (!g.containsVertex(dest))
            throw new IllegalArgumentException("Graph doesn't contains dest vertex.");
        this.graphWeighted = g.weighted();
        this.g = g;
        this.k = k;
        this.source = source;
        this.dest = dest;
        this.findedPaths = new ArrayList<>();
        findPaths();
    }
	
    public Graph<V, E> getGraph() {
        return g;
    }

    public V getSource() {
        return source;
    }

    public V getDest() {
        return dest;
    }

    public int getNumFindedPaths() {
        return findedPaths.size();
    }

    public int getK() {
        return k;
    }

//  numbering from 0
    public GraphPath<V, E> getKthPath(int k) {
        if (k < 0 || k >= findedPaths.size()) 
            throw new IllegalArgumentException("Path with ordinal number " + k + " doesn't exists.");
        return findedPaths.get(k);
    }
	
//  Implementation of Yen's algorithm
//  Algorithm taken from: http://lotos-khv.narod.ru/algoritm/ien.htm
    private void findPaths() {
        PathSearch<V, E> search;
        search = graphWeighted ? new DijkstrasAlgorithm((Graph<V, WeightedEdge<V>>) g, source, dest) : new UnweightedShortestPathSearch<>(g, source, dest);
        if (!search.hasPathTo(dest))
            return;
        GraphPath<V, E> currShortestPath = search.pathTo(dest);
        findedPaths.add(currShortestPath);
        E edgeToRemove = null;
        List<E> removedEdges = new ArrayList<>();
        for (int i = 0; i < k - 1; i++) {
            int shortestPathLen = g.numEdges();
            GraphPath<V, E> nextShortestPath = null;
            for (E e : currShortestPath.edges()) {
                g.removeEdge(e);
                search.search();
                if (search.hasPathTo(dest)) {
                    GraphPath<V, E> candidate = search.pathTo(dest);
                    if (candidate.numEdges() < shortestPathLen) {
                        edgeToRemove = e;
                        nextShortestPath = candidate;
                        shortestPathLen = candidate.numEdges();
                    }
                }
                g.addEdge(e);
            }
            if (nextShortestPath == null) {
                for (E removedEdge : removedEdges) 
                    g.addEdge(removedEdge);
                return;
            }
            findedPaths.add(nextShortestPath);
            currShortestPath = nextShortestPath;
            g.removeEdge(edgeToRemove);
            removedEdges.add(edgeToRemove);
        }
        
    }
    
}
