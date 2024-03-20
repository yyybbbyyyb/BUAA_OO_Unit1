package entity.ast;

import entity.calc.Poly;

import java.util.Map;

public interface Ast {
    void formalParaReplace(Map<String, Factor> map);

    Poly toPoly();
}
