package graphmaster.representation.edges;

public class DirectedUnweightedEdge extends DirectedEdge implements UnweightedEdge {

    public DirectedUnweightedEdge(int source, int target) {
        super(source, target);
    }

    @Override
    public DirectedEdge getReversed() {
        return new DirectedUnweightedEdge(getTarget(), getSource());
    }
    
}
