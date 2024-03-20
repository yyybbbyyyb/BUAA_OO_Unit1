import entity.ast.Expr;

import entity.calc.Poly;
import util.FuncDeal;
import util.Lexer;
import util.Parser;
import util.SimplifyPrint;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. 从标准输入读取数据
        Scanner scanner = new Scanner(System.in);

        // 2. 读取定义函数的数量并添加定义函数
        int defFuncCount = readInteger(scanner);
        for (int i = 0; i < defFuncCount; i++) {
            String input = scanner.nextLine();
            FuncDeal.addDefFunc(input);
        }

        // 3. 读取初始输入并进行解析和计算
        String initInput = scanner.nextLine();
        Lexer lexer = new Lexer(initInput);
        Parser parser = new Parser(lexer, false);
        Expr expr = parser.parserExpr();
        Poly poly = expr.toPoly();

        // 4. 化简后输出表达式
        System.out.println(SimplifyPrint.process(poly));

        // 5. 关闭 Scanner
        scanner.close();
    }

    private static int readInteger(Scanner scanner) {
        return Integer.parseInt(scanner.nextLine());
    }
}