package entity.factor;

import entity.Expr;
import entity.Factor;
import entity.calc.Poly;

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
    public String toString() {
        String string = expr.toString();
        if (powerNum.equals("1")) {
            if (signal == 1) {
                return "(" + string + ")";
            } else {
                return "(-" + string + ")";
            }
        } else if (powerNum.equals("0")) {
            return "(1)";
        } else {
            if (signal == 1) {
                return "(" + string + "^" + powerNum + ")";
            } else {
                return "(-" + string + "^" + powerNum + ")";
            }
        }
    }
}
