package entity.factor;

import entity.Factor;
import entity.calc.Exp;
import entity.calc.Item;
import entity.calc.Mono;
import entity.calc.Poly;

import java.math.BigInteger;

public class ExpFactor implements Factor {

    private final int signal;

    private final String powerNum;

    private final Factor factor;

    public ExpFactor(Factor factor, String powerNum, int signal) {
        this.factor = factor;
        this.signal = signal;
        this.powerNum = powerNum;
    }

    @Override
    public Poly toPoly() {
        Poly poly = new Poly();
        Mono mono = new Mono(BigInteger.ZERO);
        Poly num = new Poly();
        Item item = new Item(new BigInteger(powerNum), new Mono(BigInteger.ZERO),
                             new Exp(new Poly()));
        num.addItem(item);
        Exp exp = new Exp(Poly.multiPoly(factor.toPoly(), num));
        poly.addItem(new Item(BigInteger.valueOf(signal), mono, exp));
        return poly;
    }

    @Override
    public String toString() {
        String string = factor.toString();
        if (powerNum.equals("1")) {
            if (signal == 1) {
                return "(exp(" + string + "))";
            } else {
                return "(-exp(" + string + "))";
            }
        } else if (powerNum.equals("0")) {
            return "(1)";
        } else {
            if (signal == 1) {
                return "(exp(" + string + ")^" + powerNum + ")";
            } else {
                return "(-exp(" + string + ")^" + powerNum + ")";
            }
        }

    }

}
