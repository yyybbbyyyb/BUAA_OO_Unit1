package constants;

import java.util.HashMap;
import java.util.Map;

public class TokenMap {

    public static final Map<String, TokenTypeEnum> TOKEN_MAP = new HashMap<>();

    static {
        TOKEN_MAP.put("(", TokenTypeEnum.LP);
        TOKEN_MAP.put(")", TokenTypeEnum.RP);
        TOKEN_MAP.put("+", TokenTypeEnum.ADD);
        TOKEN_MAP.put("-", TokenTypeEnum.SUB);
        TOKEN_MAP.put("*", TokenTypeEnum.MUL);
        TOKEN_MAP.put("^", TokenTypeEnum.POWER);
        TOKEN_MAP.put("x", TokenTypeEnum.Var);
        TOKEN_MAP.put("y", TokenTypeEnum.Var);
        TOKEN_MAP.put("z", TokenTypeEnum.Var);
        TOKEN_MAP.put(",", TokenTypeEnum.COMMA);
    }
}