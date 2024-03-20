package util;

import entity.Mono;
import entity.Poly;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;

public class SimplifyPrint {

    public static String process(Poly poly) {
        HashMap<Integer, Mono> monoMap = poly.getMonoMap();

        Iterator<Integer> iterator = monoMap.keySet().iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            if (monoMap.get(i).getCoe().equals(BigInteger.ZERO)) {
                iterator.remove();
            }
        }

        if (monoMap.isEmpty()) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        int tips = -1;
        for (Integer exp: poly.getMonoMap().keySet()) {
            if (monoMap.get(exp).getCoe().compareTo(BigInteger.ZERO) > 0) {
                tips = exp;
                break;
            }
        }

        if (tips != -1) {
            sb.append(monoMap.get(tips).toString());
        }

        for (Integer exp: poly.getMonoMap().keySet()) {
            if (exp == tips) {
                continue;
            }
            if (monoMap.get(exp).getCoe().compareTo(BigInteger.ZERO) > 0) {
                sb.append('+').append(monoMap.get(exp).toString());
            } else {
                sb.append(monoMap.get(exp).toString());
            }
        }

        return sb.toString();
    }

}
