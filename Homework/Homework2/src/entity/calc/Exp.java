package entity.calc;

import java.math.BigInteger;
import java.util.Objects;

public class Exp {

    private final Poly poly;

    public Exp(Poly poly) {
        this.poly = poly;
    }

    public Poly getPoly() {
        return poly;
    }

    @Override
    public String toString() {
        if (poly.isZero()) {
            return "";
        } else if (poly.hasOneItemOfType(true)) {
            return "exp(" + poly + ")";
        } else if (poly.hasOneItemOfType(false)) {
            Poly dealPoly = new Poly();
            BigInteger coe = gatherCoe(dealPoly);
            return "exp(" + dealPoly + ")^" + coe;
        } else if (poly.hasSameCoe()) {
            Poly dealPoly = new Poly();
            BigInteger coe = gatherCoe(dealPoly);
            return "exp((" + dealPoly + "))^" + coe;
        } else {
            return "exp((" + poly + "))";
        }
    }

    public BigInteger gatherCoe(Poly dealPoly) {
        BigInteger coe = null;
        for (BigInteger exp: poly.getItemMap().keySet()) {
            for (Poly tempPoly : poly.getItemMap().get(exp).keySet()) {
                Item item = poly.getItemMap().get(exp).get(tempPoly);
                Item tempItem = new Item(BigInteger.ONE, item.getMono(), item.getExp());
                dealPoly.addItem(tempItem);
                coe = item.getCoe();
            }
        }
        return coe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exp exp = (Exp) o;
        return Objects.equals(poly, exp.poly);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poly);
    }
}
