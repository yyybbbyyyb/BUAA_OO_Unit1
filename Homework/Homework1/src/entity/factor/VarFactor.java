package entity.factor;

import entity.Factor;
import entity.Mono;
import entity.Poly;

import java.math.BigInteger;

public class VarFactor implements Factor {

    private final int signal;

    private final String powerNum;

    public VarFactor(String powerNum, int signal) {
        this.powerNum = powerNum;
        this.signal = signal;
    }

    @Override
    public Poly toPloy() {
        Mono mono = new Mono(BigInteger.valueOf(1), Integer.parseInt(powerNum));
        Poly poly = new Poly();
        poly.addMono(mono);
        return poly;
    }
}
