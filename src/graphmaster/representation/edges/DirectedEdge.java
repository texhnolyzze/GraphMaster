package graphmaster.representation.edges;

public abstract class DirectedEdge implements Edge {

    private final int _source;
    private final int _target;
    
    public DirectedEdge(int source, int target) {
        if (source == target) throw new IllegalArgumentException("Loops are not allowed");
        _source = source;
        _target = target;
    }
    
    public final int getSource() {
        return _source;
    }
    
    public final int getTarget() {
        return _target;
    }

    @Override
    public int getEither() {
        return _source;
    }

    @Override
    public int getOther(int knownVertex) {
        if (knownVertex == _source) return _target;
        else if (knownVertex == _target) return _source;
        else throw new IllegalArgumentException("Unknown vertex: " + knownVertex);
    }
    
    @Override
    public boolean isDirected() {
        return true;
    }
    
    public abstract DirectedEdge getReversed();
    
}
