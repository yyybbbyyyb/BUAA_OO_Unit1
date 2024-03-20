package entity;

import entity.calc.Poly;
import java.util.ArrayList;

public class Expr {

    private ArrayList<Term> terms;

    public Expr(ArrayList<Term> terms) {
        this.terms = terms;
    }

    public Poly toPoly() {
        Poly poly = new Poly();
        for (Term term : terms) {
            poly = Poly.addPoly(poly, term.toPoly());
        }
        return poly;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Term term : terms) {
            sb.append(term.toString());
        }
        sb.append(")");
        return sb.toString();
    }
}
