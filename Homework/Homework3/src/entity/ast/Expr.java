package entity.ast;

import entity.calc.Poly;

import java.util.ArrayList;
import java.util.Map;

public class Expr implements Ast {

    private ArrayList<Term> terms;

    public Expr(ArrayList<Term> terms) {
        this.terms = terms;
    }

    public Poly toPoly() {
        if (terms.size() == 1) {
            return terms.get(0).toPoly();
        }
        Poly poly = new Poly();
        for (Term term : terms) {
            poly = Poly.addPoly(poly, term.toPoly());
        }
        return poly;
    }

    @Override
    public void formalParaReplace(Map<String, Factor> map) {
        for (Term term : terms) {
            term.formalParaReplace(map);
        }
    }

    public Expr deepClone() {
        ArrayList<Term> terms = new ArrayList<>();
        for (Term term : this.terms) {
            terms.add(term.deepClone());
        }
        return new Expr(terms);
    }
}
