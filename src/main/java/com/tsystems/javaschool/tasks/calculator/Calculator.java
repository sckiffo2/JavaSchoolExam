package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
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
            List<String> expression = expressionParser(statement);
            Stack<Double> numbers = new Stack<>();
            Stack<String> operators = new Stack<>();

			for (int i = 0; i < expression.size(); i++) {
				String s = expression.get(i);
				if (isNumeric(s)) {
					numbers.push(Double.parseDouble(s));
				} else if (s.equals(OPEN_BRACKET)) {
					operators.push(s);
				} else if (s.equals(CLOSE_BRACKET)) {
					// calculate all inside brackets pair
					while (!operators.peek().equals(OPEN_BRACKET)) {
						double b = numbers.pop();
						double a = numbers.pop();
						String operator = operators.pop();
						numbers.push(evaluatePair(a, b, operator));
					}
					operators.pop();
				} else if (OPERATORS.contains(s)){
					while (operators.size() > 0 && operPriority(s) <= operPriority(operators.peek())) {
						double b = numbers.pop();
						double a = numbers.pop();
						String operator = operators.pop();
						numbers.push(evaluatePair(a, b, operator));
					}
					operators.push(s);
				}
			}
			while (!operators.isEmpty()) {
				double b = numbers.pop();
				double a = numbers.pop();
				String operator = operators.pop();
				numbers.push(evaluatePair(a, b, operator));
			}
            double doubleResult = numbers.pop();
			DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
			decimalFormatSymbols.setDecimalSeparator('.');
			DecimalFormat df = new DecimalFormat("#.####", decimalFormatSymbols);
			//df.getDecimalFormatSymbols();//todo something with 2,3123 output. We need 0.12 like here.
			String result = df.format(doubleResult);
            return result;
        } catch (NullPointerException | ArithmeticException | NumberFormatException e) {
            //TODO some error logging
            return null;
        }
    }
    
    private List<String> expressionParser(String exprString) throws NullPointerException, ArithmeticException {
        List<String> expression = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(exprString, OPERATORS + OPEN_BRACKET + CLOSE_BRACKET, true);
        String prevToken = "start";
        int bracketBalance = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();

            // todo else if statements
            if (OPERATORS.contains(token) && prevToken.equals("start")) { //expression starts with operator
            	if (token.equals("-")) { //first number is lower than zero
					expression.add("0");
				} else {
               		throw new ArithmeticException("Expression cannot start with operator(except -nubers)");
				}
            }
            if (OPERATORS.contains(token) && OPERATORS.contains(prevToken)) {
                throw new ArithmeticException("Expression cannot have two operators in a row.");
            }
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
    
    private boolean isNumeric(String str) throws ArithmeticException {
        if (OPERATORS.contains(str) || str.equals(OPEN_BRACKET) || str.equals(CLOSE_BRACKET)) {
            return false;
        }
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            throw new ArithmeticException("Wrong number format.");
        } catch (NullPointerException nfe) {
            return false;
        }
        return true;
    }
    
    private int operPriority(String str) {
        if (str.equals("-") || str.equals("+")) {
            return 1;
        }
        if (str.equals("*") || str.equals("/")) {
            return 2;
        }
        return 0; //
    }

    private double evaluatePair(double a, double b, String oper) {
		switch (oper) {
			case "+":
				return a + b;
			case "-":
				return a - b;
			case "*":
				return a * b;
			case "/":
				if ( b != 0) {
					return a/ b;
				} else {
					throw new ArithmeticException("Divide on zero");
				}
		}
		throw new ArithmeticException("wrong operator");
	}
}