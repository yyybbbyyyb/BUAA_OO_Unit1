package entity;

import entity.calc.Exp;
import entity.calc.Item;
import entity.calc.Mono;
import entity.calc.Poly;

import java.math.BigInteger;
import java.util.ArrayList;

public class Term {

    private final ArrayList<Factor> factors;

    private final int signal;

    public Term(ArrayList<Factor> factors, int signal) {
        this.factors = factors;
        this.signal = signal;
    }

    public Poly toPoly() {
        Poly poly = new Poly();
        Mono mono = new Mono(BigInteger.ZERO);
        Exp exp = new Exp(new Poly());
        poly.addItem(new Item(BigInteger.ONE, mono, exp));
        for (Factor factor : factors) {
            poly = Poly.multiPoly(poly, factor.toPoly());
        }
        if (signal == -1) {
            poly = Poly.negatePoly(poly);
        }
        return poly;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (signal == -1) {
            sb.append("-");
        } else {
            sb.append("+");
        }
        for (Factor factor : factors) {
            sb.append(factor.toString());
            sb.append("*");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
