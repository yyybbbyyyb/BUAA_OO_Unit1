package entity.ast.factor;

import entity.ast.Factor;
import entity.calc.Exp;
import entity.calc.Item;
import entity.calc.Mono;
import entity.calc.Poly;

import java.math.BigInteger;
import java.util.Map;

public class VarFactor implements Factor {

    private final int signal;

    private final String powerNum;

    private final boolean isFormalPara;

    private final String name;

    private Factor factor;

    public VarFactor(String powerNum, int signal, boolean isFormalPara, String name) {
        this.powerNum = powerNum;
        this.signal = signal;
        this.isFormalPara = isFormalPara;
        this.name = name;
        this.factor = null;
    }

    public void setFactor(Factor factor) {
        this.factor = factor;
    }

    public boolean isFormalPara() {
        return isFormalPara;
    }

    @Override
    public Poly toPoly() {
        if (isFormalPara) {
            Poly factorPoly = factor.toPoly();
            Poly powerPoly = Poly.powerPoly(factorPoly, Integer.parseInt(powerNum));
            if  (signal == -1) {
                Poly.negatePoly(powerPoly);
            }
            return powerPoly;
        } else {
            Poly poly = new Poly();
            Mono mono = new Mono(BigInteger.valueOf(Long.parseLong(powerNum)));
            Exp exp = new Exp(new Poly());
            poly.addItem(new Item(BigInteger.valueOf(signal), mono, exp));
            return poly;
        }
    }

    @Override
    public void formalParaReplace(Map<String, Factor> map) {
        if (!isFormalPara) {
            return;
        }
        if (factor instanceof VarFactor) {
            if (!((VarFactor) factor).isFormalPara()) {
                return;
            }
        }
        if (factor == null) {
            this.factor = map.get(name).deepClone();
        } else {
            factor.formalParaReplace(map);
        }
    }

    @Override
    public Factor deepClone() {
        return new VarFactor(powerNum, signal, isFormalPara, name);
    }
}
