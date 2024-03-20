package entity.factor;

import entity.Factor;
import entity.Mono;
import entity.Poly;

import java.math.BigInteger;

public class NumFactor implements Factor {

    private final String num;

    private final int signal;

    public NumFactor(String num, int signal) {
        this.num = num;
        this.signal = signal;
    }

    @Override
    public Poly toPloy() {
        BigInteger numInt = new BigInteger(num);
        BigInteger result = numInt.multiply(BigInteger.valueOf(signal));
        Poly poly = new Poly();
        poly.addMono(new Mono(result, 0));
        return poly;
    }
}
