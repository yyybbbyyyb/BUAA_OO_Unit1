package util;

import entity.calc.Item;
import entity.calc.Poly;

import java.math.BigInteger;
import java.util.HashMap;

public class SimplifyPrint {

    public static String process(Poly poly) {
        HashMap<BigInteger, HashMap<Poly, Item>> itemMap = poly.getItemMap();
        StringBuilder sb = new StringBuilder();

        if (zeroPrint(itemMap)) {
            return "0";
        }

        appendFirstPosTerm(itemMap, sb);

        return sb.toString();
    }

    private static Boolean zeroPrint(HashMap<BigInteger, HashMap<Poly, Item>> itemMap) {
        return itemMap.isEmpty();
    }

    private static void appendFirstPosTerm(HashMap<BigInteger, HashMap<Poly, Item>> itemMap,
                                           StringBuilder sb) {
        BigInteger tipsExp = BigInteger.valueOf(-1);
        Poly tipsPoly = null;
        for (BigInteger exp : itemMap.keySet()) {
            for (Poly tempPoly : itemMap.get(exp).keySet()) {
                if (itemMap.get(exp).get(tempPoly).getCoe().compareTo(BigInteger.ZERO) > 0) {
                    tipsExp = exp;
                    tipsPoly = tempPoly;
                    break;
                }
            }
        }
        if (!tipsExp.equals(BigInteger.valueOf(-1)) && tipsPoly != null) {
            sb.append(itemMap.get(tipsExp).get(tipsPoly).toString());
        }
        appendOtherTerms(itemMap, sb, tipsExp, tipsPoly);
    }

    private static void appendOtherTerms(HashMap<BigInteger, HashMap<Poly, Item>> itemMap,
                                         StringBuilder sb, BigInteger tipsExp, Poly tipsPoly) {
        for (BigInteger exp : itemMap.keySet()) {
            for (Poly tempPoly : itemMap.get(exp).keySet()) {
                Item item = itemMap.get(exp).get(tempPoly);
                if (exp.equals(tipsExp) && tempPoly == tipsPoly) {
                    continue;
                }
                if (item.getCoe().compareTo(BigInteger.ZERO) > 0) {
                    sb.append('+').append(item);
                } else {
                    sb.append(item);
                }
            }
        }
    }
}
