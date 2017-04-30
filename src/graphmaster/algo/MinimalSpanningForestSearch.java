package graphmaster.algo;

import graphmaster.representation.edges.UndirectedWeightedEdge;
import graphmaster.representation.graph.Graph;
import graphmaster.utils.ArrayQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class MinimalSpanningForestSearch extends BaseGraphAlgorithm<UndirectedWeightedEdge> {

    static int counter[] = new int[100];
    
    private boolean[] inTree;
    
    private int treesCount;
    private double[] distanceTo;
    private UndirectedWeightedEdge[] edgeTo;
    
    private final ArrayList<Double> weights;
    private final Map<Integer, Queue<UndirectedWeightedEdge>> trees;
    
    private TreeMap<Double, Integer> pQ;
    
    public MinimalSpanningForestSearch(Graph<UndirectedWeightedEdge> g) {
        super(g);
        
        pQ = new TreeMap<>();
        trees = new HashMap<>();
        weights = new ArrayList<>();
        inTree = new boolean[graph.getVertexesCount()];
        distanceTo = new double[graph.getVertexesCount()];
        Arrays.fill(distanceTo, Double.POSITIVE_INFINITY);
        edgeTo = new UndirectedWeightedEdge[graph.getVertexesCount()];
        compute();
        shutdown();
    }

    public int treesCount() {
        return treesCount;
    }
    
    public Iterable<UndirectedWeightedEdge> getTree(int treeId) {
        return trees.get(treeId);
    }
    
    public double getTreeWeight(int treeId) {
        return weights.get(treeId);
    }
    
    @Override
    protected final void compute() {
        for (int vertex : graph.scrollVertexes()) 
            if (!inTree[vertex]) 
                buildTree(vertex);
        deleteNulls();
    }
    
    private void buildTree(int initVertex) {
        weights.add(0.0);
        pQ.put(0.0, initVertex);
        distanceTo[initVertex] = 0.0;
        trees.put(treesCount, new ArrayQueue<>());
        while (!pQ.isEmpty()) nextVertex(pQ.pollFirstEntry().getValue());
        treesCount++;
    }
    
    private void nextVertex(int vertex) {
        if (!inTree[vertex]) {
            inTree[vertex] = true;
            trees.get(treesCount).offer(edgeTo[vertex]);
            double currentTreeWeight = weights.get(treesCount);
            weights.set(treesCount, edgeTo[vertex] == null ? 
                    currentTreeWeight : currentTreeWeight + edgeTo[vertex].getWeight());
            edgeTo[vertex] = null;
            for (UndirectedWeightedEdge e : graph.outcomingEdgesFrom(vertex)) {
                int adjVert = e.getOther(vertex);
                if (!inTree[adjVert]) {
                    double currentDistance = distanceTo[adjVert];
                    double perhapsLess = distanceTo[vertex] + e.getWeight();
                    if (currentDistance > perhapsLess) {
                        edgeTo[adjVert] = e;
                        pQ.put(perhapsLess, adjVert);
                        distanceTo[adjVert] = perhapsLess;
                    }
                }
            }
        }
    } 
    
    private void deleteNulls() {
        for (Map.Entry<Integer, Queue<UndirectedWeightedEdge>> entry : trees.entrySet()) 
            entry.getValue().poll();
    }

    @Override
    protected final void shutdown() {
        pQ = null;
        inTree = null;
        edgeTo = null;
        distanceTo = null;
    }
    
}
