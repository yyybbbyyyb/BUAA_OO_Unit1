package util;

import constants.TokenTypeEnum;
import entity.Expr;
import entity.Factor;
import entity.Term;
import entity.factor.DefFuncFactor;
import entity.factor.ExpFactor;
import entity.factor.ExprFactor;
import entity.factor.NumFactor;
import entity.factor.VarFactor;

import java.util.ArrayList;

public class Parser {

    private final Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Expr parserExpr() {
        ArrayList<Term> terms = new ArrayList<>();
        int signal = 1;
        if (lexer.notEnd() && lexer.getCurToken().getType() == TokenTypeEnum.SUB) {
            signal = -1;
            lexer.move();
        } else if (lexer.notEnd() && lexer.getCurToken().getType() == TokenTypeEnum.ADD) {
            lexer.move();
        }
        terms.add(parserTerm(signal));
        while (lexer.notEnd() && (lexer.getCurToken().getType() == TokenTypeEnum.ADD
                                 || lexer.getCurToken().getType() == TokenTypeEnum.SUB)) {
            if (lexer.getCurToken().getType() == TokenTypeEnum.ADD) {
                signal = 1;
            } else {
                signal = -1;
            }
            lexer.move();
            terms.add(parserTerm(signal));
        }
        return new Expr(terms);
    }

    private Term parserTerm(int signal) {
        ArrayList<Factor> factors = new ArrayList<>();
        factors.add(parserFactor());
        while (lexer.notEnd() && lexer.getCurToken().getType() == TokenTypeEnum.MUL) {
            lexer.move();
            factors.add(parserFactor());
        }
        return new Term(factors, signal);
    }

    private Factor parserFactor() {
        int signal = 1;
        if (lexer.notEnd() && lexer.getCurToken().getType() == TokenTypeEnum.SUB) {
            signal = -1;
            lexer.move();
        } else if (lexer.notEnd() && lexer.getCurToken().getType() == TokenTypeEnum.ADD) {
            lexer.move();
        }
        if (lexer.notEnd() && lexer.getCurToken().getType() == TokenTypeEnum.NUM) {
            return parserNumFactor(signal);
        } else if (lexer.notEnd() && lexer.getCurToken().getType() == TokenTypeEnum.Var) {
            return parserVarFactor(signal);
        } else if (lexer.notEnd() && lexer.getCurToken().getType() == TokenTypeEnum.EXP) {
            return parserExpFactor(signal);
        } else if (lexer.notEnd() && lexer.getCurToken().getType() == TokenTypeEnum.FUNCNAME) {
            return parserDefFuncFactor(signal);
        } else {
            return parserExprFactor(signal);
        }
    }

    private Factor parserNumFactor(int signal) {
        NumFactor numFactor = new NumFactor(lexer.getCurToken().getContent(), signal);
        lexer.move();
        return numFactor;
    }

    private Factor parserVarFactor(int signal) {
        lexer.move();
        if (lexer.notEnd() && lexer.getCurToken().getType() == TokenTypeEnum.POWER) {
            lexer.move();
            VarFactor varFactor = new VarFactor(lexer.getCurToken().getContent(), signal);
            lexer.move();
            return varFactor;
        } else {
            return new VarFactor("1", signal);
        }
    }

    private Factor parserExprFactor(int signal) {
        lexer.move();
        Expr expr = parserExpr();
        lexer.move();
        if (lexer.notEnd() && lexer.getCurToken().getType() == TokenTypeEnum.POWER) {
            lexer.move();
            ExprFactor exprFactor = new ExprFactor(expr, lexer.getCurToken().getContent(),
                    signal);
            lexer.move();
            return exprFactor;
        } else {
            return new ExprFactor(expr,"1", signal);
        }
    }

    private Factor parserExpFactor(int signal) {
        lexer.move();
        lexer.move();
        Factor factor = parserFactor();
        lexer.move();
        if (lexer.notEnd() && lexer.getCurToken().getType() == TokenTypeEnum.POWER) {
            lexer.move();
            ExpFactor expFactor = new ExpFactor(factor, lexer.getCurToken().getContent(),
                    signal);
            lexer.move();
            return expFactor;
        } else {
            return new ExpFactor(factor, "1", signal);
        }
    }

    private Factor parserDefFuncFactor(int signal) {
        final String name = lexer.getCurToken().getContent();
        lexer.move();
        lexer.move();
        ArrayList<Factor> actualParas = new ArrayList<>();
        actualParas.add(parserFactor());
        while (lexer.notEnd() && lexer.getCurToken().getType() != TokenTypeEnum.RP) {
            lexer.move();
            actualParas.add(parserFactor());
        }
        lexer.move();
        return new DefFuncFactor(name, actualParas, signal);
    }

}
