package entity.ast.factor;

import entity.ast.Expr;
import entity.ast.Factor;
import entity.calc.Poly;

import java.util.Map;

public class DxFactor implements Factor {

    private final Expr expr;

    private final int signal;

    public DxFactor(Expr expr, int signal) {
        this.expr = expr;
        this.signal = signal;
    }

    @Override
    public void formalParaReplace(Map<String, Factor> map) {
        expr.formalParaReplace(map);
    }

    @Override
    public Poly toPoly() {
        Poly oriPoly = expr.toPoly();
        if (signal == -1) {
            Poly.negatePoly(oriPoly);
        }
        return Poly.derivatePoly(oriPoly);
    }

    @Override
    public Factor deepClone() {
        return new DxFactor(expr.deepClone(), signal);
    }
}
