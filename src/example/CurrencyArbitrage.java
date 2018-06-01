package example;

import graphmaster.alg.NegativeCycleSearch;
import graphmaster.representation.GraphPath;
import graphmaster.representation.edges.impls.DirectedWeightedEdge;
import graphmaster.representation.graph.DirectedWeightedGraph;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Texhnolyze
 */
public class CurrencyArbitrage {
    
    public static void main(String[] args) throws IOException {
        DirectedWeightedGraph<String> dGraph = new DirectedWeightedGraph<>(); // currency exchange graph
        Map<String, String> currencyNameToCurrencyAcronym = new HashMap<>();
        for (Element e : Jsoup.connect("https://www.x-rates.com/").get().getElementsByClass("currencyList ratestable").first().children()) {
            Element currencyElement = e.children().first();
            String currencyName = currencyElement.html();
            String href = currencyElement.attr("href");
            String currencyAcronym = href.substring(href.length() - 3);
            currencyNameToCurrencyAcronym.put(currencyName, currencyAcronym);
            dGraph.addVertex(currencyAcronym);
        }
        for (String currencyAcronym : dGraph.vertexSet()) {
            Document doc = Jsoup.connect("https://www.x-rates.com/table/?from=" + currencyAcronym + "&amount=1").get();
            for (Element e : doc.getElementsByClass("tablesorter ratesTable").first().children().last().children()) {
                Elements children = e.children();
                String currencyName = children.first().html();
                double rate = Double.parseDouble(children.get(1).children().first().html());
                dGraph.addEdge(currencyAcronym, currencyNameToCurrencyAcronym.get(currencyName), round((-Math.log(rate)), 4));
            }
        }
        NegativeCycleSearch<String, DirectedWeightedEdge<String>> search = new NegativeCycleSearch<>(dGraph);
        if (search.hasNegativeCycle()) {
            System.out.println("Possible currency arbitrage founded.");
            GraphPath<String, DirectedWeightedEdge<String>> cycle = search.negativeCycle();
            System.out.println("Proceeds is: " + Math.exp(-1 * cycle.weight()));
            for (DirectedWeightedEdge<String> e : cycle.edges()) {
                System.out.println(e.source() + " -> " + e.dest());
            }
        } else {
            System.out.println("There is no possibility for currency arbitrage");
        }
    }
    
    static double round(double x, int scale) {
        return new BigDecimal(x).setScale(scale, RoundingMode.UP).doubleValue();
    }
    
}
