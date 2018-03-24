



/**
 *
 * @author Texhnolyze
 */
public class Sandbox {
    
    public static void main(String[] args) {
        System.out.println(f());
    }
    
    static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }
    
    static int pow(int a, int n) {
        if (n == 0)
            return 1;
        else if (n % 2 == 0)
            return square(pow(a, n / 2));
        else 
            return a * pow(a, n - 1);
    }
    
    static int square(int n) {
        return n * n;
    }
    
    static double f() {
        double d = 0;
        for (double i = 1.0; i < 100000000.0; i = i + 4) {
            d = d + 1.0 / (i * (i + 2));
        }
        return d;
    }
    
    static double eval(double[] poly, double x0) {
        double x = x0;
        double res = poly[0];
        for (int i = 1; i < poly.length; i++) {
            res = res + poly[i] * x;
            x = x * x0;
        }
        return res;
    }
    
}
