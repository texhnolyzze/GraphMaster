package graphmaster;

import graphmaster.representation.edges.UnweightedEdge;
import graphmaster.representation.edges.WeightedEdge;
import java.lang.reflect.InvocationTargetException;

public final class Edges {
    
    private Edges() {}
    
    public static <E extends UnweightedEdge> E createEdge(Class<E> type, int source, int target) {
        E e = null;
        try {
            e = type.getDeclaredConstructor(int.class, int.class).newInstance(source, target);
        } catch (NoSuchMethodException | SecurityException | InstantiationException 
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException("Can't create edge of type: " + type, ex);
        }
        return e;
    }
    
    public static <E extends WeightedEdge> E createEdge(Class<E> type, int source, int target, Double weight) {
        E e = null;
        try {
            e = type.getDeclaredConstructor(int.class, int.class, double.class).newInstance(source, target, weight);
        } catch (NoSuchMethodException | SecurityException | InstantiationException 
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException("Can't create edge of type: " + type, ex);
        }
        return e;
    }
    
}
