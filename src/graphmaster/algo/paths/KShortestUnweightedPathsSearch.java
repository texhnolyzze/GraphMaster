package graphmaster.algo.paths;

import graphmaster.algo.BaseGraphAlgorithm;
import graphmaster.representation.edges.Edge;
import graphmaster.representation.graph.Graph;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class KShortestUnweightedPathsSearch<E extends Edge> extends BaseGraphAlgorithm<E> {

    private final int _k;
    private final int source;
    private final int target;
    
    private final LinkedList<GraphPath<E>> kPaths;
    
    public KShortestUnweightedPathsSearch(Graph<E> g, int start, int dest, int k) {
        super(g);
        _k = k;
        source = start;
        target = dest;
        kPaths = new LinkedList<>();
        findFirstShortestPath();
        compute();
    }
    
    public Collection<GraphPath<E>> kPaths() {
        return kPaths;
    }

    @Override
    protected void compute() {
        GraphPath<E> shortestPath;
        if (!kPaths.isEmpty()) shortestPath = kPaths.getFirst();
        else return;
        int vertexToIgnoreFurther = -1;
        LinkedList<Integer> toIgnore = new LinkedList<>();
        for (int i = 0; i < _k - 1; i++) {
            int currentPathLength = graph.getEdgesCount();
            GraphPath<E> nextCandidate = null;
            List<Integer> vertexes = shortestPath.vertexes();
            for (int vert = 1; vert < vertexes.size() - 1; vert++) { //can't ignore start or end vertexes
                int currentIgnored = vertexes.get(vert);
                toIgnore.add(currentIgnored);
                UnweightedShortestPathSearch<E> search;
                search = new UnweightedShortestPathSearch<>(graph, source, target, toIgnore);
                toIgnore.removeLast();
                if (search.hasPathTo(target)) {
                    GraphPath<E> perhapsSmallerPath = search.pathTo(target);
                    if (perhapsSmallerPath.length() < currentPathLength) {
                        nextCandidate = perhapsSmallerPath;
                        currentPathLength = perhapsSmallerPath.length();
                        vertexToIgnoreFurther = currentIgnored;
                    }
                }
            }
            if (nextCandidate != null) {
                kPaths.add(nextCandidate);
                shortestPath = nextCandidate;
                toIgnore.add(vertexToIgnoreFurther);
            } else break;
        }
    }

    @Override
    protected void shutdown() {
        //NOTHING TODO.
    }

    private void findFirstShortestPath() {
        GraphPath<E> shortestPath;
        UnweightedShortestPathSearch<E> search;
            search = new UnweightedShortestPathSearch<>(graph, source, target);
            if (search.hasPathTo(target)) {
                shortestPath = search.pathTo(target);
                kPaths.add(shortestPath);
            }
    }
    
    
    
}
