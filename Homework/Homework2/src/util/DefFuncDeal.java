package util;

import constants.TokenTypeEnum;
import entity.Factor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefFuncDeal {

    private static final HashMap<String, String> funcMap = new HashMap<>();
    private static final HashMap<String, ArrayList<String>> formalParasMap = new HashMap<>();

    public static void addDefFunc(String initInput) {
        String input = Lexer.preprocess(initInput);

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

    public static String callDefFunc(String name,  ArrayList<Factor> actualParas) {
        Map<String, Factor> parasMap = new HashMap<>();
        ArrayList<String> formalParas = formalParasMap.get(name);

        for (int i = 0; i < formalParas.size(); i++) {
            parasMap.put(formalParas.get(i), actualParas.get(i));
        }

        String expr = funcMap.get(name);
        Lexer lexer = new Lexer(expr);
        StringBuilder sb = new StringBuilder();

        while (lexer.notEnd()) {
            if (lexer.getCurToken().getType() == TokenTypeEnum.Var) {
                Factor factor = parasMap.get(lexer.getCurToken().getContent());
                sb.append(factor.toString());
            } else {
                sb.append(lexer.getCurToken().getContent());
            }
            lexer.move();
        }
        return sb.toString();
    }

}
