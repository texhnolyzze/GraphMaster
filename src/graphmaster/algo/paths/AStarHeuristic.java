package graphmaster.algo.paths;

public interface AStarHeuristic {
    
    double getEstimateCost(int from, int destination);
    
}
