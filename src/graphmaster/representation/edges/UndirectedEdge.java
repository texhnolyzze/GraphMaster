package graphmaster.representation.edges;

public abstract class UndirectedEdge implements Edge {
    
    private final int _firstVertex;
    private final int _secondVertex;
    
    public UndirectedEdge(int firstVertex, int secondVertex) {
        if (firstVertex == secondVertex) throw new IllegalArgumentException("Loops are not allowed");
        _firstVertex = firstVertex;
        _secondVertex = secondVertex;
    }

    public final int getFirstVertex() {
        return _firstVertex;
    }
    
    public final int getSecondVertex() {
        return _secondVertex;
    }

    @Override
    public int getEither() {
        return _firstVertex;
    }

    @Override
    public int getOther(int knownVertex) {
        if (knownVertex == _firstVertex) return _secondVertex;
        else if (knownVertex == _secondVertex) return _firstVertex;
        else throw new IllegalArgumentException("Unknown vertex: " + knownVertex);
    }
    
    @Override
    public boolean isDirected() {
        return false;
    }
    
}
