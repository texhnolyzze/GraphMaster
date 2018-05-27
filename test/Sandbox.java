
import java.util.PriorityQueue;

/**
 *
 * @author Texhnolyze
 */
public class Sandbox {
    public static void main(String[] args) {   
        PriorityQueue<Double> pq = new PriorityQueue<>((Double e1, Double e2) -> {
            return Double.compare(e1, e2);
        });
        pq.add(1.0);
        pq.add(2.0);
        pq.add(3.0);
        System.out.println(pq.poll());
        System.out.println(pq.poll());
        System.out.println(pq.poll());
    }
}
