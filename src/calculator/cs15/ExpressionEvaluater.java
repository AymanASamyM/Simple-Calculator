package calculator.cs15;


import java.util.LinkedList;
import java.util.Stack;

/**
 * @author Ayman
 * 
 */
public class ExpressionEvaluater implements IExpressionEvaluator {

	public String infixToPostfix(String expression) {
		StringBuilder result = new StringBuilder();
		String[] splitedExpression = splitExpression(expression);
		Stack<Character> operatorSt = new Stack<Character>();		
		for (int i = 0;i < splitedExpression.length;i++) {
			String element = splitedExpression[i];
			if(isOperatant(element.charAt(0))) {
				result.append(element);
				result.append(' ');
			}
			else if (isOperator(element.charAt(0))) {
				if(element.charAt(0) == ')') {
					while(operatorSt.peek() != '(') {
						result.append(operatorSt.pop());
						result.append(' ');
					}
					operatorSt.pop();
				}
				else if (element.charAt(0) == '(') {
					operatorSt.push(element.charAt(0));
				}
				else {
					while (!operatorSt.isEmpty() && operatordegree(operatorSt.peek()) >= operatordegree(element.charAt(0))) {
						result.append(operatorSt.pop());
						result.append(' ');
					}
					operatorSt.push(element.charAt(0));
				}
			}
		}
		while (!operatorSt.isEmpty()) {
			result.append(operatorSt.pop());
			result.append(' ');
		}
		result.deleteCharAt(result.length()-1);
		return result.toString();
	}

	public double evaluate(String expression) {
		String[] splitedExpression = splitExpression(expression);
		LinkedList<Double> operatants = new LinkedList<Double>();
		int ind = 0;
		while(ind != splitedExpression.length) {
			if(isOperatant(splitedExpression[ind])) {
				operatants.push(Double.valueOf(splitedExpression[ind]));
			} else if (isOperator(splitedExpression[ind])) {
				if (operatordegree(splitedExpression[ind]) < 1)
					throw new RuntimeException();
				double num1 = operatants.pop();
				double num2 = operatants.pop();
				double res = evaluateTwo(num2, num1, splitedExpression[ind].charAt(0));
				operatants.push(res);
			} else {
				throw new RuntimeException();
			}
			ind++;
		}
		double res = operatants.pop();
		if (!operatants.isEmpty()) {
			throw new RuntimeException();
		}
		return res;
	}
	private int operatordegree(String str) {
		return operatordegree(str.charAt(0));
	}
	private int operatordegree(char x) {
		if (x == '+' || x == '-') {
			return 1;
		} else if (x == '/' || x == '*') {
			return 2;
		} else if (x == '(') {
			return 0;
		} else {
			throw new RuntimeException();
		}
	}

	/**
	 * @return num1 [operator] num2
	 */
	private double evaluateTwo(double num1, double num2, char operator) {
		if (operator == '+') {
			return num1 + num2;
		} else if (operator == '-') {
			return num1 - num2;
		} else if (operator == '*') {
			return num1 * num2;
		} else if (operator == '/') {
			if (num2 == 0) {
				throw new RuntimeException();
			}
			else {
				return num1 / num2;
			}
		} else {
			throw new RuntimeException();
		}
	}
	
	private String[] splitExpression(String exression) {
		LinkedList<String> result = new LinkedList<String>();
		int ind = 0;
		char curChar = exression.charAt(ind); 
		while(ind != exression.length())
		{
			curChar = exression.charAt(ind);
			if(isOperator(curChar))
			{
				result.add(String.valueOf(curChar));
			}
			else if(isOperatant(curChar))
			{
				StringBuilder number = new StringBuilder();
				while(ind != exression.length() && isOperatant(exression.charAt(ind)))
				{
					curChar = exression.charAt(ind);
					number.append(curChar);
					ind++;
				}
				ind--;
				result.add(number.toString());
			}
			ind++;
		}
		return result.toArray(new String[result.size()]);
	}
	private boolean isOperator(String str) {
		return isOperator(str.charAt(0));
	}
	private boolean isOperator(char x) {
		return (x == '+' || x == '-' || x == '*' || x == '/' || x == '(' || x == ')');
	}
	private boolean isOperatant(String str) {
		return isOperatant(str.charAt(0));
	}
	private boolean isOperatant(char x) {
		return (Character.isDigit(x) || x == '.');
	}
}