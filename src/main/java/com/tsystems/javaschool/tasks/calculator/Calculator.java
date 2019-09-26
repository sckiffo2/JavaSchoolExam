package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Calculator {
    
    private final String OPERATORS = "+-*/";
    private final String OPEN_BRACKET = "(";
    private final String CLOSE_BRACKET = ")";
    
    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        try {
            expressionParser(statement);
            // todo All calculations here
            int result = 0;
            return Integer.toString(result);
        } catch (NullPointerException | ArithmeticException e) {
            //some error logging
            return null;
        }
    }
    
    private List<String> expressionParser(String exprString) throws NullPointerException, ArithmeticException {
        List<String> expression = new ArrayList<>();

        exprString = exprString.replaceAll(" ", "");
        StringTokenizer st = new StringTokenizer(exprString, OPERATORS + OPEN_BRACKET + CLOSE_BRACKET, true);
        String prevToken = "start";
        int bracketBalance = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            
            if (OPERATORS.contains(token) && prevToken.equals("start")) {
                throw new ArithmeticException("Expression cannot start with operator");
            }
            if (OPERATORS.contains(token) && OPERATORS.contains(prevToken)) {
                throw new ArithmeticException("Expression cannot have two operators in a row.");
            }
            
            //bracket balance
            if (token.equals(OPEN_BRACKET)) {
                bracketBalance++;
            }
            if (token.equals(CLOSE_BRACKET)) {
                if (bracketBalance <= 0) {
                    throw new ArithmeticException("Close bracket cannot stay before open one.");
                }
                bracketBalance--;
            }
            if (!st.hasMoreTokens() && bracketBalance > 0) {
                throw new ArithmeticException("Brackets must be closed.");
            }
            expression.add(token);
            prevToken = token;
        }
        if (expression.size() == 0) {
            throw new ArithmeticException("Empty expression");
        }
        
        return expression;
    }
    
    private boolean isNumber() {
        //todo
        return false;
    }
    
    
}
