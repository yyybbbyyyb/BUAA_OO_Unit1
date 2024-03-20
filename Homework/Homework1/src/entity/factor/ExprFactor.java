package entity.factor;

import entity.Expr;
import entity.Factor;
import entity.Poly;

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
    public Poly toPloy() {
        Poly poly = Poly.powerPoly(expr.toPoly(), Integer.parseInt(powerNum));
        if  (signal == -1) {
            Poly.negatePoly(poly);
        }
        return poly;
    }
}
