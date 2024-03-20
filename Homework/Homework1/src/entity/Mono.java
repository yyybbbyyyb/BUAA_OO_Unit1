package entity;

import java.math.BigInteger;

public class Mono {

    private BigInteger coe;

    private int exp;

    public Mono(BigInteger coe, int exp) {
        this.coe = coe;
        this.exp = exp;
    }

    @Override
    public String toString() {
        if (exp == 0) {
            return coe.toString();
        } else if (exp == 1) {
            if (coe.equals(BigInteger.ZERO)) {
                return "";
            } else if (coe.equals(BigInteger.ONE)) {
                return "x";
            } else if (coe.equals(BigInteger.valueOf(-1))) {
                return "-x";
            }
            return coe + "*x";
        } else if (coe.equals(BigInteger.ZERO)) {
            return "";
        } else if (coe.equals(BigInteger.ONE)) {
            return "x^" + exp;
        } else if (coe.equals(BigInteger.valueOf(-1))) {
            return "-x^" + exp;
        } else {
            return coe + "*x^" + exp;
        }
    }

    public BigInteger getCoe() {
        return coe;
    }

    public int getExp() {
        return exp;
    }
}
