package graphmaster.representation.edges;

/**
 *
 * @author Texhnolyze
 */
public interface Edge<V> {

    V v1();
    V v2();
    
    default V other(V known) {
        if (v1().equals(known)) 
            return v2();
        else if (v2().equals(known)) 
            return v1();
        else 
            return null;
    }
    
    default boolean touches(V v) {return v1().equals(v) || v2().equals(v);}
    
}
