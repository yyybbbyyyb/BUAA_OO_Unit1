package entity;

import java.math.BigInteger;
import java.util.ArrayList;

public class Expr {

    private ArrayList<Term> terms;

    public Expr(ArrayList<Term> terms) {
        this.terms = terms;
    }

    public Poly toPoly() {
        Poly poly = new Poly();
        poly.addMono(new Mono(BigInteger.ZERO, 0));
        for (Term term : terms) {
            poly = Poly.addPoly(poly, term.toPloy());
        }
        return poly;
    }
}
