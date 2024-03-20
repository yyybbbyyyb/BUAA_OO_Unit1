package entity.ast;

import entity.calc.Exp;
import entity.calc.Item;
import entity.calc.Mono;
import entity.calc.Poly;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

public class Term implements Factor {

    private final ArrayList<Factor> factors;

    private final int signal;

    public Term(ArrayList<Factor> factors, int signal) {
        this.factors = factors;
        this.signal = signal;
    }

    public Poly toPoly() {
        Poly poly = new Poly();
        if (factors.size() == 1) {
            poly = factors.get(0).toPoly();
        } else {
            Mono mono = new Mono(BigInteger.ZERO);
            Exp exp = new Exp(new Poly());
            poly.addItem(new Item(BigInteger.ONE, mono, exp));
            for (Factor factor : factors) {
                poly = Poly.multiPoly(poly, factor.toPoly());
            }
        }
        if (signal == -1) {
            poly = Poly.negatePoly(poly);
        }
        return poly;
    }

    @Override
    public void formalParaReplace(Map<String, Factor> map) {
        for (Factor factor : factors) {
            factor.formalParaReplace(map);
        }
    }

    @Override
    public Term deepClone() {
        ArrayList<Factor> factors = new ArrayList<>();
        for (Factor factor : this.factors) {
            factors.add(factor.deepClone());
        }
        return new Term(factors, signal);
    }
}
