package graphmaster;

import graphmaster.algo.DirectedCCS;
import graphmaster.algo.DirectedGraphOrders;
import graphmaster.algo.MetricProperties;
import graphmaster.algo.MinimalSpanningForestSearch;
import graphmaster.algo.UndirectedCCS;
import graphmaster.algo.paths.AStarAlgorithm;
import graphmaster.algo.paths.AStarHeuristic;
import graphmaster.algo.paths.AcyclicWeightedPathsSearch;
import graphmaster.algo.paths.BellmanFordAlgorithm;
import graphmaster.algo.paths.DijkstraAlgorithm;
import graphmaster.algo.paths.KShortestUnweightedPathsSearch;
import graphmaster.algo.paths.UnweightedShortestPathSearch;
import graphmaster.representation.edges.DirectedEdge;
import graphmaster.representation.edges.Edge;
import graphmaster.representation.edges.UndirectedEdge;
import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.DirectedWeightedGraph;
import graphmaster.representation.graph.Graph;
import graphmaster.representation.graph.UndirectedWeightedGraph;

public class Algorithms {

    public static <E extends DirectedEdge> DirectedCCS<E> getDirectedCSS(Graph<E> graph) {
        return new DirectedCCS<>(graph);
    } 
    
    public static <E extends UndirectedEdge> UndirectedCCS<E> getUndirectedCCS(Graph<E> graph) {
        return new UndirectedCCS<>(graph);
    }
    
    public static <E extends DirectedEdge> DirectedGraphOrders<E> getDirectedGraphOrders(Graph<E> graph) {
        return new DirectedGraphOrders<>(graph);
    }
    
    public static <E extends Edge> MetricProperties<E> getMetricProperties(Graph<E> graph) {
        return new MetricProperties<>(graph);
    }
    
    public static MinimalSpanningForestSearch getMinimalSpanningForestSearch(UndirectedWeightedGraph graph) {
        return new MinimalSpanningForestSearch(graph);
    }
    
    public static <E extends WeightedEdge> AStarAlgorithm<E> getAStarSearch(Graph<E> graph, int source, AStarHeuristic h) {
        return getAStarSearch(graph, source, -1, h);
    }
    
    public static <E extends WeightedEdge> AStarAlgorithm<E> getAStarSearch(Graph<E> graph, int source, int target, AStarHeuristic h) {
        return new AStarAlgorithm<>(graph, source, target, h);
    }
    
    public static AcyclicWeightedPathsSearch getAcyclicWeightedPathsSearch(DirectedWeightedGraph graph, int source) {
        return getAcyclicWeightedPathsSearch(graph, source, -1);
    }
    
    public static AcyclicWeightedPathsSearch getAcyclicWeightedPathsSearch(DirectedWeightedGraph graph, int source, int target) {
        return new AcyclicWeightedPathsSearch(graph, source, target);
    } 
    
    public static <E extends WeightedEdge> BellmanFordAlgorithm<E> getBellmanFordSearch(Graph<E> g, int source) {
        return getBellmanFordSearch(g, source, -1);
    }
    
    public static <E extends WeightedEdge> BellmanFordAlgorithm<E> getBellmanFordSearch(Graph<E> g, int source, int target) {
        return new BellmanFordAlgorithm<>(g, source, target);
    }
    
    public static <E extends WeightedEdge> DijkstraAlgorithm<E> getDijkstraSearch(Graph<E> g, int source) {
        return getDijkstraSearch(g, source, -1);
    }
    
    public static <E extends WeightedEdge> DijkstraAlgorithm<E> getDijkstraSearch(Graph<E> g, int source, int target) {
        return new DijkstraAlgorithm<>(g, source, target);
    }
    
    public static <E extends Edge> KShortestUnweightedPathsSearch<E> getKShortestUnweightedPathsSearch(Graph<E> g, int source, int target, int k) {
        return new KShortestUnweightedPathsSearch<>(g, source, target, k);
    }
    
    public static <E extends Edge> UnweightedShortestPathSearch<E> getUnweightedShortestPathSearch(Graph<E> g, int source) {
        return getUnweightedShortestPathSearch(g, source, -1);
    }
    
    public static <E extends Edge> UnweightedShortestPathSearch<E> getUnweightedShortestPathSearch(Graph<E> g, int source, int target) {
        return new UnweightedShortestPathSearch(g, source, target);
    }
    
}
