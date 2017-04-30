package graphmaster.representation.edges;

public class UndirectedWeightedEdge extends UndirectedEdge implements WeightedEdge {

    private double _weight;
    
    public UndirectedWeightedEdge(int firstVertex, int secondVertex, double weight) {
        super(firstVertex, secondVertex);
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
    
}
