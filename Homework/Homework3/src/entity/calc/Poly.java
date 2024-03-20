package entity.calc;

import util.SimplifyPrint;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;

public class Poly {

    private final HashMap<BigInteger, HashMap<Poly, Item>> itemMap;

    private int cachedHashCode = 0;

    public Poly() {
        this.itemMap = new HashMap<>();
    }

    public void addItem(Item item) {
        BigInteger coe = item.getCoe();
        BigInteger exp = item.getMono().getExp();
        if (coe.equals(BigInteger.ZERO)) {
            return;
        }
        if (itemMap.containsKey(exp)) {
            Poly poly = item.getExp().getPoly();
            if (itemMap.get(exp).containsKey(poly)) {
                Item existingItem = itemMap.get(exp).get(poly);
                BigInteger existingCoe = existingItem.getCoe();
                BigInteger newCoe = existingCoe.add(coe);
                if (newCoe.equals(BigInteger.ZERO)) {
                    itemMap.get(exp).remove(poly);
                    if (itemMap.get(exp).isEmpty()) {
                        itemMap.remove(exp);
                    }
                } else {
                    existingItem.setCoe(newCoe);
                }
            } else {
                itemMap.get(exp).put(poly, item);
            }
        } else {
            HashMap<Poly, Item> expMap = new HashMap<>();
            expMap.put(item.getExp().getPoly(), item);
            itemMap.put(exp, expMap);
        }
    }

    public static Poly addPoly(Poly poly1, Poly poly2) {
        Poly poly = new Poly();
        deepCopyPoly(poly1, poly);
        deepCopyPoly(poly2, poly);
        return poly;
    }

    private static void deepCopyPoly(Poly srcPoly, Poly destPoly) {
        for (BigInteger exp: srcPoly.getItemMap().keySet()) {
            for (Poly tempPoly : srcPoly.getItemMap().get(exp).keySet()) {
                Item item = srcPoly.getItemMap().get(exp).get(tempPoly);
                Item newItem = new Item(item.getCoe(), item.getMono(), item.getExp());
                destPoly.addItem(newItem);
            }
        }
    }

    public static Poly multiPoly(Poly poly1, Poly poly2) {
        Poly poly = new Poly();

        if (poly1.isZero() || poly2.isZero()) {
            return poly;
        }

        for (BigInteger exp1 : poly1.itemMap.keySet()) {
            for (Poly tempPoly1 : poly1.itemMap.get(exp1).keySet()) {
                Item item1 = poly1.itemMap.get(exp1).get(tempPoly1);
                BigInteger coe1 = item1.getCoe();
                for (BigInteger exp2 : poly2.itemMap.keySet()) {
                    for (Poly tempPoly2 : poly2.itemMap.get(exp2).keySet()) {
                        Item item2 = poly2.itemMap.get(exp2).get(tempPoly2);
                        BigInteger coe2 = item2.getCoe();
                        BigInteger newCoe = coe1.multiply(coe2);
                        BigInteger newExp = exp1.add(exp2);
                        Poly newPoly = Poly.addPoly(item1.getExp().getPoly(),
                                                    item2.getExp().getPoly());
                        poly.addItem(new Item(newCoe, new Mono(newExp), new Exp(newPoly)));
                    }
                }
            }
        }
        return poly;
    }

    public static Poly powerPoly(Poly poly, Integer power) {
        Poly result = new Poly();
        result.addItem(new Item(BigInteger.ONE, new Mono(BigInteger.ZERO), new Exp(new Poly())));

        if (power == 0) {
            return result;
        }

        for (int i = 0; i < power; i++) {
            result = multiPoly(result, poly);
        }
        return result;
    }

    public static Poly derivatePoly(Poly poly) {
        if (poly.isZero()) {
            return poly;
        }
        Poly result = new Poly();
        for (BigInteger exp : poly.itemMap.keySet()) {
            for (Poly tempPoly : poly.itemMap.get(exp).keySet()) {
                Item item = poly.itemMap.get(exp).get(tempPoly);
                Poly derItem = item.derivateItem();
                result = addPoly(result, derItem);
            }
        }
        return result;
    }

    public static Poly negatePoly(Poly poly) {
        Poly result = new Poly();
        for (BigInteger exp: poly.getItemMap().keySet()) {
            for (Poly tempPoly : poly.getItemMap().get(exp).keySet()) {
                Item item = poly.getItemMap().get(exp).get(tempPoly);
                BigInteger coe = item.getCoe();
                if (!coe.equals(BigInteger.ZERO)) {
                    Item tempItem = new Item(coe.negate(), new Mono(exp), new Exp(tempPoly));
                    result.addItem(tempItem);
                }
            }
        }
        return result;
    }

    public HashMap<BigInteger, HashMap<Poly, Item>> getItemMap() {
        return itemMap;
    }

    public boolean isZero() {
        return itemMap.isEmpty();
    }

    public boolean hasOneItemOfType(boolean baseItem) {
        int itemCountOfType = 0;
        int itemCount = 0;
        for (BigInteger exp : itemMap.keySet()) {
            for (Poly tempPoly : itemMap.get(exp).keySet()) {
                if (baseItem && itemMap.get(exp).get(tempPoly).isBaseItem()) {
                    itemCountOfType++;
                } else if (!baseItem && itemMap.get(exp).get(tempPoly).isNormalItem()) {
                    itemCountOfType++;
                }
                itemCount++;
            }
        }
        return itemCountOfType == 1 && itemCount == 1;
    }

    public boolean hasSameCoe() {
        BigInteger coe = null;
        for (BigInteger exp : itemMap.keySet()) {
            for (Poly tempPoly : itemMap.get(exp).keySet()) {
                Item item = itemMap.get(exp).get(tempPoly);
                if (coe == null) {
                    coe = item.getCoe();
                } else {
                    if (!item.getCoe().equals(coe)) {
                        return false;
                    }
                }
            }
        }
        return coe.compareTo(BigInteger.ONE) > 0;
    }

    @Override
    public String toString() {
        return SimplifyPrint.process(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Poly poly = (Poly) o;
        return Objects.equals(itemMap, poly.itemMap);
    }

    @Override
    public int hashCode() {
        if (cachedHashCode == 0) {
            cachedHashCode = Objects.hash(itemMap);
        }
        return cachedHashCode;
    }
}
