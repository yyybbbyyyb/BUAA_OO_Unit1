package entity.factor;

import entity.Expr;
import entity.Factor;
import entity.calc.Poly;
import util.DefFuncDeal;
import util.Lexer;
import util.Parser;

import java.util.ArrayList;

public class DefFuncFactor implements Factor {

    private final int signal;

    private final String calledFunc;

    private final Expr expr;

    public DefFuncFactor(String name, ArrayList<Factor> actualParas, int signal) {
        this.calledFunc = DefFuncDeal.callDefFunc(name, actualParas);
        this.expr = dealExpr();
        Poly poly = expr.toPoly();
        this.signal = signal;
    }

    private Expr dealExpr() {
        Lexer lexer = new Lexer(calledFunc);
        Parser parser = new Parser(lexer);
        return parser.parserExpr();
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
    public String toString() {
        if (signal == 1) {
            return expr.toString();
        } else {
            return "(-" + expr.toString() + ")";
        }
    }
}
