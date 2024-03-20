package entity.calc;

import java.math.BigInteger;
import java.util.Objects;

public class Mono {

    private final BigInteger exp;

    public Mono(BigInteger exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        if (exp.equals(BigInteger.ZERO)) {
            return "";
        } else if (exp.equals(BigInteger.ONE)) {
            return "x";
        } else {
            return "x^" + exp;
        }
    }

    public BigInteger getExp() {
        return exp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mono mono = (Mono) o;
        return exp.equals(mono.exp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp);
    }
}
