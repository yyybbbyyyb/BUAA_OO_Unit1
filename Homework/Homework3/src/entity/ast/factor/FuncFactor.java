package entity.ast.factor;

import entity.ast.Expr;
import entity.ast.Factor;
import entity.calc.Poly;
import util.FuncDeal;

import java.util.ArrayList;
import java.util.Map;

public class FuncFactor implements Factor {

    private final int signal;

    private final Expr expr;

    private final ArrayList<Factor> actualParas;

    private final String name;

    public FuncFactor(String name, ArrayList<Factor> actualParas, int signal) {
        this.name = name;
        this.actualParas = actualParas;
        this.expr = FuncDeal.callDefFunc(name, actualParas);
        this.signal = signal;
    }

    @Override
    public Poly toPoly() {
        Poly poly = expr.toPoly();
        if  (signal == -1) {
            Poly.negatePoly(poly);
        }
        return poly;
    }

    @Override
    public void formalParaReplace(Map<String, Factor> map) {
        expr.formalParaReplace(map);
    }

    @Override
    public Factor deepClone() {
        return new FuncFactor(name, actualParas, signal);
    }
}
