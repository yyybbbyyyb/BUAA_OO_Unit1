package entity.factor;

import entity.Factor;
import entity.calc.Exp;
import entity.calc.Item;
import entity.calc.Mono;
import entity.calc.Poly;

import java.math.BigInteger;

public class VarFactor implements Factor {

    private final int signal;

    private final String powerNum;

    public VarFactor(String powerNum, int signal) {
        this.powerNum = powerNum;
        this.signal = signal;
    }

    @Override
    public Poly toPoly() {
        Poly poly = new Poly();
        Mono mono = new Mono(BigInteger.valueOf(Long.parseLong(powerNum)));
        Exp exp = new Exp(new Poly());
        poly.addItem(new Item(BigInteger.valueOf(signal), mono, exp));
        return poly;
    }

    @Override
    public String toString() {
        if (powerNum.equals("1")) {
            if (signal == 1) {
                return "(x)";
            } else {
                return "(-x)";
            }
        } else if (signal == 1) {
            return "(x^" + powerNum + ")";
        } else {
            return "(-x^" + powerNum + ")";
        }
    }
}
