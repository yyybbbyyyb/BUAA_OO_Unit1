package entity.ast.factor;

import entity.ast.Factor;
import entity.calc.Exp;
import entity.calc.Item;
import entity.calc.Mono;
import entity.calc.Poly;

import java.math.BigInteger;
import java.util.Map;

public class ExpFactor implements Factor {

    private final int signal;

    private final String powerNum;

    private final Factor factor;

    public ExpFactor(Factor factor, String powerNum, int signal) {
        this.factor = factor;
        this.signal = signal;
        this.powerNum = powerNum;
    }

    @Override
    public Poly toPoly() {
        Poly poly = new Poly();
        Mono mono = new Mono(BigInteger.ZERO);
        Poly num = new Poly();
        Item item = new Item(new BigInteger(powerNum), new Mono(BigInteger.ZERO),
                             new Exp(new Poly()));
        num.addItem(item);
        Exp exp;
        if (powerNum.equals("1")) {
            exp = new Exp(factor.toPoly());
        } else {
            exp = new Exp(Poly.multiPoly(factor.toPoly(), num));
        }
        poly.addItem(new Item(BigInteger.valueOf(signal), mono, exp));
        return poly;
    }

    @Override
    public void formalParaReplace(Map<String, Factor> map) {
        factor.formalParaReplace(map);
    }

    @Override
    public Factor deepClone() {
        return new ExpFactor(factor.deepClone(), powerNum, signal);
    }

}
