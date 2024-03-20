import entity.Expr;
import entity.Poly;
import util.Lexer;
import util.Parser;
import util.SimplifyPrint;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String initInput = sc.nextLine();
        Lexer lexer = new Lexer(initInput);


        //for (Token token: lexer.getTokenList()) {
        //    System.out.println(token.getContent() + " " + token.getType());
        //}
        //expr.printExpr();


        Parser parser = new Parser(lexer);
        Expr expr = parser.parserExpr();
        Poly poly = expr.toPoly();
        System.out.println(SimplifyPrint.process(poly));
    }
}