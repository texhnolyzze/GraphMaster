package graphmaster;

import graphmaster.representation.edges.DirectedEdge;
import graphmaster.representation.edges.Edge;
import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.edges.impls.DirectedUnweightedEdge;
import graphmaster.representation.edges.impls.DirectedWeightedEdge;
import graphmaster.representation.edges.impls.UndirectedUnweightedEdge;
import graphmaster.representation.edges.impls.UndirectedWeightedEdge;
import graphmaster.representation.graph.Graph;
import graphmaster.representation.graph.ReversedGraph;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Texhnolyze
 */
public final class GraphUtils {
    
    private GraphUtils() {}
    
    public static <V, E extends Edge<V>> String graphToString(Graph<V, E> g) {
        final boolean weighted = g.weighted();
        StringBuilder sb = new StringBuilder();
        sb.append("DIRECTED: ").append(Boolean.toString(g.directed()).toUpperCase());
        sb.append("WEIGHTED: ").append(Boolean.toString(weighted).toUpperCase());
        sb.append("V: ").append(g.numVertices()).append("\n");
        sb.append("E: ").append(g.numEdges()).append("\n");
        for (V v : g.vertexSet()) {
            sb.append(v).append(" -> (");
            Iterator<E> it = g.outgoingEdgesOf(v).iterator();
            for (int i = 0; i < g.incomingEdgesOf(v).size() - 1; i++) {
                edgeToString(weighted, sb, it, v);
                sb.append(", ");
            }
            if (it.hasNext()) 
                edgeToString(weighted, sb, it, v);
            sb.append(")\n");
        }
        return sb.toString();
    }
    
    private static <V, E extends Edge<V>> void edgeToString(final boolean weighted, StringBuilder sb, Iterator<E> it, V v) {
        E e = it.next();
        sb.append("(").append(e.other(v));
        if (weighted)
            sb.append(", ").append(((WeightedEdge<V>) e).weight());
        sb.append(")");
    }
    
    public static <V, E extends Edge<V>> void fillGraph(int verticesNum, int edgesNum, Graph<V, E> g, RandomVertexGenerator<V> vertexGenerator) {
        int maxEdgesNum = g.directed() ? verticesNum * (verticesNum - 1) : verticesNum * (verticesNum - 1) / 2;
        if (edgesNum > maxEdgesNum)
            throw new IllegalArgumentException("Such a graph does not exist");
        int verticesToCreate = verticesNum - g.numVertices();
        int edgesToCreate = edgesNum - g.numEdges();
        while (verticesToCreate > 0) {
            V v = vertexGenerator.next();
            if (g.addVertex(v)) 
                verticesToCreate--;
        }
        if (edgesToCreate > 0) {
            double factor = (double) edgesToCreate / maxEdgesNum;
            if (factor < 0.75)
                fillGraphSparse(edgesToCreate, g);
            else
                fillGraphComplete(maxEdgesNum, edgesToCreate, g);
        }
    }
    
    private static <V, E extends Edge<V>> void fillGraphSparse(int edgesToCreate, Graph<V, E> g) {
        final boolean directed = g.directed(), weighted = g.weighted();
        List<V> list = new ArrayList<>(g.vertexSet());
        int V = list.size();
        do {
            V v1 = list.get((int) (Math.random() * V));
            V v2 = list.get((int) (Math.random() * V));
            E e = createEdge(directed, weighted, v1, v2);
            if (g.addEdge(e))
                edgesToCreate--;
        } while (edgesToCreate != 0);
    }
    
    private static <V, E extends Edge<V>> void fillGraphComplete(int maxEdgesNum, int edgesToCreate, Graph<V, E> g) {
        final boolean directed = g.directed(), weighted = g.weighted();
        List<Pair<V, V>> edges = new ArrayList<>(maxEdgesNum - g.numEdges()); 
        if (directed) {
            for (V v : g.vertexSet()) {
                for (V u : g.vertexSet()) {
                    if (v != u) {
                        if (!g.containsEdge(u, v)) {
                            edges.add(new Pair<>(u, v));
                        }
                    }
                }
            }
        } else {
            List<V> list = new ArrayList<>(g.vertexSet());
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    if (!g.containsEdge(list.get(i), list.get(j))) {
                        edges.add(new Pair<>(list.get(i), list.get(j)));
                    }
                }
            }
        }
        do {
            int edgeIdx = (int) (Math.random() * edges.size());
            Pair<V, V> edge = edges.get(edgeIdx);
            edges.set(edgeIdx, edges.get(edges.size() - 1));
            edges.remove(edges.size() - 1);
            V v1 = edge.getKey();
            V v2 = edge.getValue();
            g.addEdge(createEdge(directed, weighted, v1, v2));
            edgesToCreate--;
        } while (edgesToCreate > 0);
    }
    
    private static <V, E extends Edge<V>> E createEdge(final boolean directed, final boolean weighted, V v1, V v2) {
        Edge<V> e;
        if (directed) {
            if (weighted) 
                e = new DirectedWeightedEdge<>(v1, v2, Math.random());
            else
                e = new DirectedUnweightedEdge<>(v1, v2);
        } else {
            if (weighted)
                e = new UndirectedWeightedEdge<>(v1, v2, Math.random());
            else
                e = new UndirectedUnweightedEdge<>(v1, v2);
        }
        return (E) e;
    }
    
    public static <V, E extends DirectedEdge<V>> Graph<V, E> reversedGraphDelegator(Graph<V, E> src) {
        return new ReversedGraph<>(src);
    }
    
    public static <V, E extends DirectedEdge<V>> Graph<V, E> reversedNewGraph(Graph<V, E> src) {
        try {
            Graph<V, E> g = src.getClass().getConstructor().newInstance();
            for (V v : src.vertexSet())
                g.addVertex(v);
            for (V v : src.vertexSet()) {
                for (E e : src.outgoingEdgesOf(v)) {
                    g.addEdge((E) e.reverse());
                }
            }
            return g;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.err.println(ex);
        }
        return null;
    }
    
}
