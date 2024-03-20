package util;

public class PreProcess {

    public static String process(String initInput) {
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
}
