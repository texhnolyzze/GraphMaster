package graphmaster.representation.edges;

/**
 *
 * @author Texhnolyze
 */
public interface DirectedEdge<V> extends Edge<V> {
    
    V source();
    V dest();
    
    @Override default V v1() {return source();}
    @Override default V v2() {return dest();}
    
    DirectedEdge<V> reverse();
    
}
