package entity;

import java.math.BigInteger;
import java.util.ArrayList;

public class Term {

    private final ArrayList<Factor> factors;

    private final int signal;

    public Term(ArrayList<Factor> factors, int signal) {
        this.factors = factors;
        this.signal = signal;
    }

    public Poly toPloy() {
        Poly poly = new Poly();
        poly.addMono(new Mono(BigInteger.ONE, 0));
        for (Factor factor : factors) {
            poly = Poly.multiPoly(poly, factor.toPloy());
        }
        if (signal == -1) {
            Poly.negatePoly(poly);
        }
        return poly;
    }
}
