package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Calculator {
    
    private final String OPERATORS = "+-*/";
    private final String OPEN_BRACKET = "(";
    private final String CLOSE_BRACKET = ")";
    private boolean isExpressionComputable = true;
    
    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        isExpressionComputable = true;
        
        // TODO: Implement the logic here
        expressionParser(statement);
        int result = 0;
        return Integer.toString(result);
    }
    
    private List<String> expressionParser(String exprString) {
        List<String> expression = new ArrayList<>();
        exprString = exprString.replaceAll(" ", "");
        StringTokenizer st = new StringTokenizer(exprString, OPERATORS + OPEN_BRACKET + CLOSE_BRACKET, true);
        String prevToken = "start";
        int bracketBalance = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            
            //start with operator
            if (OPERATORS.contains(token) && prevToken.equals("start")) {
                isExpressionComputable = false;
                return expression;
            }
            
            //double operator
            if (OPERATORS.contains(token) && OPERATORS.contains(prevToken)) {
                isExpressionComputable = false;
                return expression;
            }
            
            //bracket balance
            if (token.equals(OPEN_BRACKET)) {
                bracketBalance++;
            }
            
            if (token.equals(CLOSE_BRACKET)) {
                if (bracketBalance <= 0) {
                    isExpressionComputable = false;
                    return expression;
                }
                bracketBalance--;
            }
            if (!st.hasMoreTokens() && bracketBalance > 0) {
                isExpressionComputable = false;
                return expression;
            }
            
            
            expression.add(token);
            prevToken = token;
        }
        expression.forEach(System.out::println);
        return expression;
    }
    
    private boolean isNumber() {
        //todo
        return false;
    }
    
    
}
