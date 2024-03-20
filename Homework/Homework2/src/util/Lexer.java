package util;

import constants.TokenMap;
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
            if (isBaseToken(input.charAt(i))) {
                String content = String.valueOf(input.charAt(i));
                tokenList.add(new Token(content, TokenMap.TOKEN_MAP.get(content)));
            } else if (input.charAt(i) == 'f' || input.charAt(i) == 'g'
                                              || input.charAt(i) == 'h') {
                tokenList.add(new Token(String.valueOf(input.charAt(i)), TokenTypeEnum.FUNCNAME));
            } else if (input.charAt(i) == 'e') {
                if (input.charAt(i + 1) == 'x' && input.charAt(i + 2) == 'p') {
                    tokenList.add(new Token("exp", TokenTypeEnum.EXP));
                    i += 2;
                } else {
                    System.out.println("Dont know what to do with " +
                                        input.charAt(i + 1) + " after e");
                    break;
                }
            } else if (Character.isDigit(input.charAt(i))) {
                int start = i;
                while (i < input.length() && Character.isDigit(input.charAt(i))) {
                    i++;
                }
                String numStr = input.substring(start, i);
                numStr = removeLeadingZeros(numStr);
                tokenList.add(new Token(numStr, TokenTypeEnum.NUM));
                i--;
            } else {
                System.out.println("Dont know what to do with " + input.charAt(i));
                break;
            }
            i++;
        }

        this.pos = 0;
    }

    private boolean isBaseToken(char ch) {
        String baseTokens = "()+-*^xyz,";
        return baseTokens.indexOf(ch) != -1;
    }

    private String removeLeadingZeros(String numStr) {
        String result = numStr;
        if (numStr.length() > 1 && numStr.startsWith("0")) {
            result = numStr.replaceFirst("^0+(?!$)", "");   //删除前导0
        }
        return result;
    }

    public static String preprocess(String initInput) {
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
            } else if (isCalcToken(lastC) && c == '+') {
                continue;
            }
            lastC = c;
            sb.append(c);
        }
        return sb.toString();
    }

    private static boolean isCalcToken(char ch) {
        String baseTokens = "(+-*^,";
        return baseTokens.indexOf(ch) != -1;
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
