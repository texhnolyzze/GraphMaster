
import graphmaster.Graphs;
import graphmaster.algo.paths.BellmanFordAlgorithm;
import graphmaster.algo.paths.WeightedGraphPath;
import graphmaster.representation.edges.DirectedWeightedEdge;
import graphmaster.representation.graph.DirectedWeightedGraph;

public class Tests {
    
    public static void main(String[] args) {
        DirectedWeightedGraph graph;
        graph = Graphs.randomWeightedGraph(DirectedWeightedGraph.class, 1000, 10000);
//        graph = new DirectedWeightedGraph(4);
        graph.addEdge(new DirectedWeightedEdge(0, 1, 0.38));
        graph.addEdge(new DirectedWeightedEdge(1, 2, 0.37));
        graph.addEdge(new DirectedWeightedEdge(2, 3, 0.28));
        graph.addEdge(new DirectedWeightedEdge(1, 3, 0.35));
        graph.addEdge(new DirectedWeightedEdge(3, 1, -0.66));
//        System.out.println(graph);
        BellmanFordAlgorithm<DirectedWeightedEdge> algo;
        algo = new BellmanFordAlgorithm<>(graph, 0);
        WeightedGraphPath<DirectedWeightedEdge> path = algo.negativeCycle();
        for (DirectedWeightedEdge e : path.edges()) {
            int from = e.getSource();
            int to = e.getTarget();
            System.out.println(from + " -> " + to + ", weight: " + e.getWeight());
        }
//                for (int vertex : path.vertexes()) System.out.println(vertex + " ");        
    }
    
}
