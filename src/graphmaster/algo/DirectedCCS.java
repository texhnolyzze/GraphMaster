package graphmaster.algo;

import graphmaster.representation.edges.DirectedEdge;
import graphmaster.representation.graph.Graph;

public class DirectedCCS<E extends DirectedEdge> extends ConnectedComponentsSearch<E> {

    public DirectedCCS(Graph<E> g) {
        super(g);
        compute();
    }

    @Override
    protected Iterable<Integer> getSpecificOrder() {
        DirectedGraphOrders<E> orders = new DirectedGraphOrders<>(graph);
        return orders.reversePostOrder();
    }
    
}
