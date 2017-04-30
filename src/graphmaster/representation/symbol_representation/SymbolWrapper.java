package graphmaster.representation.symbol_representation;

import graphmaster.Edges;
import graphmaster.Graphs;
import graphmaster.representation.edges.Edge;
import graphmaster.representation.edges.UnweightedEdge;
import graphmaster.representation.edges.WeightedEdge;
import graphmaster.representation.graph.AbstractGraph;
import graphmaster.representation.graph.Graph;
import graphmaster.representation.graph.ConstantVertexesCountGraph;
import graphmaster.utils.trie_map.Alphabet;
import graphmaster.utils.trie_map.TrieMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SymbolWrapper<E extends Edge> {
    
    private final Graph<E> realGraph;
    
    private final Map<String, Integer> map;
    private final List<String> invertedMap;
    
    public <G extends Graph> SymbolWrapper(Class<G> graphType, Alphabet alphabet, int approximateKeysLength) {
        if (approximateKeysLength <= 0) throw new IllegalArgumentException("Keys length must be > 0");
        realGraph = Graphs.createGraph(graphType, 0);
        invertedMap = new ArrayList<>();
        map = chooseMap(alphabet, approximateKeysLength);
    }
    
    public <G extends Graph> SymbolWrapper(Class<G> graphType) {
        this(graphType, null, 0);
    }
    
    public boolean addVertex(String vertexName) {
        if (containsVertex(vertexName)) return false;
        else {
            map.put(vertexName, realGraph.getVertexesCount());
            invertedMap.add(vertexName);
            realGraph.addVertex();
            return true;
        }
    }
    
    public ConstantVertexesCountGraph<E> getGraph() {
        return new ConstantVertexesCountGraph<>((AbstractGraph<E>) realGraph);
    }
    
    public boolean containsVertex(String vertexName) {
        return map.containsKey(vertexName);
    }
    
    public String vertexNameByIndex(int index) {
        return invertedMap.get(index);
    }
    
    public int vertexIndexByName(String vertexName) {
        return map.get(vertexName);
    }
    
    private Map<String, Integer> chooseMap(Alphabet alphabet, int approxKeysLength) {
        if (alphabet != null && alphabet.length() <= TrieMap.OPTIMAL_ALPHA_LENGTH 
                && approxKeysLength <= TrieMap.OPTIMAL_KEYS_LENGTH) return new TrieMap(alphabet);
        else return new HashMap<>();
    }
    
    
    
}
