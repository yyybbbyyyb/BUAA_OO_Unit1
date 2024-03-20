package entity.calc;

import java.math.BigInteger;
import java.util.Objects;

public class Item {

    private BigInteger coe;
    private final Mono mono;
    private final Exp exp

    public Item(BigInteger coe, Mono mono, Exp exp) {
        this.coe = coe;
        this.mono = mono;
        this.exp = exp;
    }

    public BigInteger getCoe() {
        return coe;
    }

    public void setCoe(BigInteger coe) {
        this.coe = coe;
    }

    public Mono getMono() {
        return mono;
    }

    public Exp getExp() {
        return exp;
    }

    public boolean isBaseItem() {
        if (coe.equals(BigInteger.ZERO)) {
            return true;
        }
        if (exp.getPoly().isZero()) {
            if (mono.getExp().equals(BigInteger.ZERO)) {
                return true;
            } else {
                return coe.equals(BigInteger.ZERO) || coe.equals(BigInteger.ONE);
            }
        } else {
            if (mono.getExp().equals(BigInteger.ZERO)) {
                return coe.equals(BigInteger.ZERO) || coe.equals(BigInteger.ONE);
            } else {
                return false;
            }
        }
    }

    public boolean isNormalItem() {
        if (coe.compareTo(BigInteger.ONE) > 0) {
            if (exp.getPoly().isZero()) {
                return true;
            } else {
                return mono.getExp().equals(BigInteger.ZERO);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(coe, item.coe) &&
                Objects.equals(mono, item.mono) &&
                Objects.equals(exp, item.exp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coe, mono, exp);
    }

    @Override
    public String toString() {
        if (coe.equals(BigInteger.ZERO)) {
            return "";
        }
        String monoString = mono.toString();
        String expString = exp.toString();
        if (monoString.isEmpty()) {
            if (expString.isEmpty()) {
                return coe.toString();
            } else {
                if (coe.equals(BigInteger.ONE)) {
                    return expString;
                } else if (coe.equals(new BigInteger("-1"))) {
                    return "-" + expString;
                } else {
                    return coe + "*" + expString;
                }
            }
        } else {
            if (expString.isEmpty()) {
                if (coe.equals(BigInteger.ONE)) {
                    return monoString;
                } else if (coe.equals(new BigInteger("-1"))) {
                    return "-" + monoString;
                } else {
                    return coe + "*" + monoString;
                }
            } else {
                if (coe.equals(BigInteger.ONE)) {
                    return monoString + "*" + expString;
                } else if (coe.equals(new BigInteger("-1"))) {
                    return "-" + monoString + "*" + expString;
                } else {
                    return coe + "*" + monoString + "*" + expString;
                }
            }
        }
    }
}
