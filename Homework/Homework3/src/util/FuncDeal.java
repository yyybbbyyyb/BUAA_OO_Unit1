package util;

import entity.ast.Expr;
import entity.ast.Factor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FuncDeal {

    private static final HashMap<String, String> funcMap = new HashMap<>();

    private static final HashMap<String, ArrayList<String>> formalParasMap = new HashMap<>();

    public static void addDefFunc(String initInput) {
        String input = PreProcess.process(initInput);

        String regex = "([fgh])\\(([^,]+(?:,[^,]+){0,2})?\\)=(.+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            funcMap.put(matcher.group(1), matcher.group(3));
            String[] formalParas = matcher.group(2).split(",");
            ArrayList<String> formalParaList = new ArrayList<>(Arrays.asList(formalParas));
            formalParasMap.put(matcher.group(1), formalParaList);
        }
        else {
            System.out.println("Failed to match the input: " + input);
        }
    }

    public static Expr callDefFunc(String name,  ArrayList<Factor> actualParas) {
        Map<String, Factor> parasMap = new HashMap<>();
        ArrayList<String> formalParas = formalParasMap.get(name);

        for (int i = 0; i < formalParas.size(); i++) {
            parasMap.put(formalParas.get(i), actualParas.get(i));
        }

        Lexer lexer = new Lexer(funcMap.get(name));
        Parser parser = new Parser(lexer, true);
        Expr expr = parser.parserExpr();
        expr.formalParaReplace(parasMap);

        return expr;
    }
}
