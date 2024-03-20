package entity.ast.factor;

import entity.ast.Expr;
import entity.ast.Factor;
import entity.calc.Poly;

import java.util.Map;

public class ExprFactor implements Factor {

    private final Expr expr;

    private final int signal;

    private final String powerNum;

    public ExprFactor(Expr expr, String powerNum, int signal) {
        this.expr = expr;
        this.powerNum = powerNum;
        this.signal = signal;
    }

    @Override
    public Poly toPoly() {
        Poly poly = Poly.powerPoly(expr.toPoly(), Integer.parseInt(powerNum));
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
        return new ExprFactor(expr.deepClone(), powerNum, signal);
    }
}
