package entity.factor;

import entity.Factor;
import entity.calc.Exp;
import entity.calc.Item;
import entity.calc.Mono;
import entity.calc.Poly;

import java.math.BigInteger;

public class NumFactor implements Factor {

    private final String num;

    private final int signal;

    public NumFactor(String num, int signal) {
        this.num = num;
        this.signal = signal;
    }

    @Override
    public Poly toPoly() {
        BigInteger numInt = new BigInteger(num);
        BigInteger result = numInt.multiply(BigInteger.valueOf(signal));
        Poly poly = new Poly();
        Mono mono = new Mono(BigInteger.ZERO);
        Exp exp = new Exp(new Poly());
        poly.addItem(new Item(result, mono, exp));
        return poly;
    }

    @Override
    public String toString() {
        if (signal == 1) {
            return "(" + num + ")";
        } else {
            return "(-" + num + ")";
        }
    }

}
