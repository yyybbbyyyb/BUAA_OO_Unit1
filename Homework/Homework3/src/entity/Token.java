package entity;

import constants.TokenTypeEnum;

public class Token {
    private final String content;

    private final TokenTypeEnum type;

    public Token(String content, TokenTypeEnum type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public TokenTypeEnum getType() {
        return type;
    }
}
