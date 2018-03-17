package graphmaster.graph_filler;

import graphmaster.representation.edges.Edge;
import graphmaster.representation.edges.impls.DirectedUnweightedEdge;
import graphmaster.representation.edges.impls.DirectedWeightedEdge;
import graphmaster.representation.edges.impls.UndirectedUnweightedEdge;
import graphmaster.representation.edges.impls.UndirectedWeightedEdge;
import graphmaster.representation.graph.Graph;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Texhnolyze
 */
public final class GraphFiller {
    
    private GraphFiller() {}
    
    public static <V, E extends Edge<V>> void fillGraph(int verticesNum, int edgesNum, Graph<V, E> g, RandomVertexGenerator<V> vertexGenerator) {
        int maxEdgesNum = g.directed() ? verticesNum * (verticesNum - 1) : verticesNum * (verticesNum - 1) / 2;
        if (edgesNum > maxEdgesNum)
            throw new IllegalArgumentException("Such a graph does not exist");
        int verticesToCreate = verticesNum - g.numVertices();
        int edgesToCreate = edgesNum - g.numEdges();
        while (verticesToCreate > 0) {
            V v = vertexGenerator.generate();
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
            E e = getEdge(directed, weighted, v1, v2);
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
            g.addEdge(getEdge(directed, weighted, v1, v2));
            edgesToCreate--;
        } while (edgesToCreate > 0);
    }
    
    private static <V, E extends Edge<V>> E getEdge(final boolean directed, final boolean weighted, V v1, V v2) {
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
    
}
