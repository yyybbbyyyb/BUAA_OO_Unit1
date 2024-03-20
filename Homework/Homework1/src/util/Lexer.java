package util;

import constants.TokenTypeEnum;
import entity.Token;

import java.util.ArrayList;

public class Lexer {

    private int pos;

    private final ArrayList<Token> tokenList = new ArrayList<>();

    public Lexer(String initInput) {
        String input = preprocess(initInput);
        int i = 0;
        while (i < input.length()) {
            if (input.charAt(i) == '(') {
                tokenList.add(new Token("(", TokenTypeEnum.LP));
            } else if (input.charAt(i) == ')') {
                tokenList.add(new Token(")", TokenTypeEnum.RP));
            } else if (input.charAt(i) == '+') {
                tokenList.add(new Token("+", TokenTypeEnum.ADD));
            } else if (input.charAt(i) == '-') {
                tokenList.add(new Token("-", TokenTypeEnum.SUB));
            } else if (input.charAt(i) == '*') {
                tokenList.add(new Token("*", TokenTypeEnum.MUL));
            } else if (input.charAt(i) == '^') {
                tokenList.add(new Token("^", TokenTypeEnum.POWER));
            } else if (input.charAt(i) == 'x') {
                tokenList.add(new Token("x", TokenTypeEnum.X));
            } else if (Character.isDigit(input.charAt(i))) {
                int start = i;
                while (i < input.length() && Character.isDigit(input.charAt(i))) {
                    i++;
                }
                String numStr = input.substring(start, i);
                if (numStr.length() > 1 && numStr.startsWith("0")) {
                    numStr = numStr.replaceFirst("^0+(?!$)", "");   //删除前导0
                }
                tokenList.add(new Token(numStr, TokenTypeEnum.NUM));
                i--;
            } else {
                System.out.println("Dont know what to do with " + input.charAt(i));
            }
            i++;
        }

        this.pos = 0;
    }

    // 预处理函数，用于删除空格和水平制表符，删除前导"+"号，合并重复运算符等
    private String preprocess(String initInput) {
        String input = initInput.replaceAll("[ |\\t]", "");

        StringBuilder sb = new StringBuilder();
        char lastC = '+';
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (lastC == '+' && c == '+') {
                continue;
            } else if (lastC == '+' && c == '-') {
                lastC = '-';
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append('-');
                continue;
            } else if (lastC == '-' && c == '-') {
                lastC = '+';
                sb.deleteCharAt(sb.length() - 1);
                sb.append('+');
                continue;
            } else if (lastC == '-' && c == '+') {
                continue;
            } else if (lastC == '^' && c == '+') {
                continue;
            } else if (lastC == '*' && c == '+') {
                continue;
            } else if (lastC == '(' && c == '+') {
                continue;
            }
            lastC = c;
            sb.append(c);
        }
        return sb.toString();
    }

    public Token getCurToken() {
        return tokenList.get(pos);
    }

    public boolean notEnd() {
        return pos < tokenList.size();
    }

    public void move() {
        if (notEnd()) {
            pos++;
        } else {
            System.out.println("pos is more than token list");
        }
    }
}
