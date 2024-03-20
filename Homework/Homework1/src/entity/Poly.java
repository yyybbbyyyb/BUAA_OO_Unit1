package entity;

import java.math.BigInteger;
import java.util.HashMap;

public class Poly {

    private final HashMap<Integer, Mono> monoMap;

    public Poly() {
        this.monoMap = new HashMap<>();
    }

    public void addMono(Mono mono) {
        BigInteger coe = mono.getCoe();
        int exp = mono.getExp();
        if (coe.equals(BigInteger.ZERO)) {
            return;
        }
        Mono oldMono = monoMap.getOrDefault(exp, new Mono(BigInteger.ZERO, exp));
        Mono newMono = new Mono(coe.add(oldMono.getCoe()), exp);
        monoMap.put(exp, newMono);
    }

    public static Poly addPoly(Poly poly1, Poly poly2) {
        Poly poly = new Poly();

        for (Integer exp: poly1.getMonoMap().keySet()) {
            poly.addMono(new Mono(poly1.getMonoMap().get(exp).getCoe(), exp));
        }

        for (Integer exp: poly2.getMonoMap().keySet()) {
            poly.addMono(new Mono(poly2.getMonoMap().get(exp).getCoe(), exp));
        }
        return poly;
    }

    public static Poly multiPoly(Poly poly1, Poly poly2) {
        Poly poly = new Poly();

        for (Integer exp1 : poly1.monoMap.keySet()) {
            for (Integer exp2 : poly2.monoMap.keySet()) {
                BigInteger coe1 = poly1.monoMap.get(exp1).getCoe();
                BigInteger coe2 = poly2.monoMap.get(exp2).getCoe();
                BigInteger newCoe = coe1.multiply(coe2);
                int newExp = exp1 + exp2;
                poly.addMono(new Mono(newCoe, newExp));
            }
        }
        return poly;
    }

    public static Poly powerPoly(Poly ploy, Integer power) {
        Poly result = new Poly();
        result.addMono(new Mono(BigInteger.ONE, 0));

        if (power == 0) {
            return result;
        }

        for (int i = 0; i < power; i++) {
            result = multiPoly(result, ploy);
        }
        return result;
    }

    public static void negatePoly(Poly poly) {
        for (Integer exp: poly.getMonoMap().keySet()) {
            BigInteger coe = poly.getMonoMap().get(exp).getCoe();
            if (!coe.equals(BigInteger.ZERO)) {
                poly.getMonoMap().put(exp, new Mono(coe.negate(), exp));
            }
        }
    }

    public HashMap<Integer, Mono> getMonoMap() {
        return monoMap;
    }
}
