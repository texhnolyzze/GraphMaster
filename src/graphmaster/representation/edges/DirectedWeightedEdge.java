package graphmaster.representation.edges;

public class DirectedWeightedEdge extends DirectedEdge implements WeightedEdge {

    private double _weight;
    
    public DirectedWeightedEdge(int source, int target, double weight) {
        super(source, target);
        _weight = weight;
    }

    @Override
    public double getWeight() {
        return _weight;
    }

    @Override
    public void setWeight(double weight) {
        _weight = weight;
    }

    @Override
    public DirectedEdge getReversed() {
        return new DirectedWeightedEdge(getTarget(), getSource(), _weight);
    }
    
}
