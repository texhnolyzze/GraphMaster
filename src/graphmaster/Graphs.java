package graphmaster;

import graphmaster.representation.edges.Edge;
import graphmaster.representation.edges.UnweightedEdge;
import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.Graph;
import graphmaster.representation.graph.UnweightedGraph;
import graphmaster.representation.graph.WeightedGraph;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public final class Graphs {
    
    private Graphs() {}
    
    public static <G extends Graph> G createGraph(Class<G> graphType, int initVertsCount) {
        G graph = null;
        try {
            graph = graphType.getDeclaredConstructor(int.class).newInstance(initVertsCount);
        } catch (NoSuchMethodException | SecurityException | InstantiationException 
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException("Can't create graph of type: " + graphType, ex);
        }
        return graph;
    }
    
    public static <G extends UnweightedGraph> G randomUnweightedGraph(Class<G> graphType, int initVertsCount, int initEdgesCount) {
        Random random = new Random();
        G g = createGraph(graphType, initVertsCount);
        Class<? extends UnweightedEdge> edgeType = g.correspondingEdgeType();
        for (int i = 0; i < initEdgesCount; i++) {
            int firstVertex = random.nextInt(initVertsCount);
            int secondVertex = random.nextInt(initVertsCount);
            if (firstVertex != secondVertex) {
                Edge e = Edges.createEdge(edgeType, firstVertex, secondVertex);
                g.addEdge(e);
            }
        }
        return g;
    }
    
    public static <G extends WeightedGraph> G randomWeightedGraph(Class<G> graphType, int initVertsCount, int initEdgesCount) {
        Random random = new Random();
        G g = createGraph(graphType, initVertsCount);
        Class<? extends WeightedEdge> edgeType = g.correspondingEdgeType();
        for (int i = 0; i < initEdgesCount; i++) {
            int firstVertex = random.nextInt(initVertsCount);
            int secondVertex = random.nextInt(initVertsCount);
            if (firstVertex != secondVertex) {
                double weight = random.nextBoolean() ? (-1) * random.nextDouble() : random.nextDouble();
                Edge e = Edges.createEdge(edgeType, firstVertex, secondVertex, weight);
                g.addEdge(e);
            }
        }
        return g;
    } 
    
    public static <G extends UnweightedGraph, E extends UnweightedEdge> boolean addEdge(G g, int source, int target) {
        Class<E> edgeType = g.correspondingEdgeType();
        E e = Edges.createEdge(edgeType, source, target);
        return g.addEdge(e);
    }
    
    public static <G extends WeightedGraph, E extends WeightedEdge> boolean addEdge(G g, int source, int target, double weight) {
        Class<E> edgeType = g.correspondingEdgeType();
        E e = Edges.createEdge(edgeType, source, target, weight);
        return g.addEdge(e);
    }
    
}
