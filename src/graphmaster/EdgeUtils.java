package graphmaster;

import graphmaster.representation.edges.Edge;
import graphmaster.representation.edges.WeightedEdge;

/**
 *
 * @author Texhnolyze
 */
public class EdgeUtils {
    
    public static <V, E extends Edge<V>> double weight(E e) {
        if (e instanceof WeightedEdge)
            return ((WeightedEdge<V>) e).weight();
        else
            return 1.0;
    }
    
}
