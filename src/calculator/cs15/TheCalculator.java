package calculator.cs15;



import calculator.Calculator;

import java.io.*;
import java.util.LinkedList;


public class TheCalculator implements Calculator{
	private LinkedList<String> operations = new LinkedList<String>();
	private int currentOpt = -1;
	private String saveFileName = "Calculator Save File.txt";

	/* Take input from user */
	public void input(String s) {
		if(operations.size() >= 5) 
			operations.removeFirst();
		operations.addLast(s);
		currentOpt = operations.size()-1;
	}

	/*
	 * Return the result of the current operations or throws a runtime exception
	 */
	public String getResult() {
		ExpressionEvaluater expressionEvaluater = new ExpressionEvaluater();
		String postfix = expressionEvaluater.infixToPostfix(current());
		try {
			return String.valueOf(expressionEvaluater.evaluate(postfix));
		}catch (Exception e)
		{
			return "Invalid Formula";
		}
	}

	/* return the current formula */
	public String current() {
		if (currentOpt == -1)
			return null;
		else
			return operations.get(currentOpt);

	}

	/*
	 * return the last operation in String format, or Null if no more history
	 * available
	 */
	public String prev() {
		if (currentOpt <= 0)
			return null;
		else {
			return operations.get(--currentOpt);
		}
	}

	/*
	 * return the next operation in String format, or Null if no more history
	 * available
	 */
	public String next() {
		if (currentOpt + 1 >= operations.size() || currentOpt < 0)
			return null;
		else {
			return operations.get(++currentOpt);
		}
	}

	/* Save in file the last 5 done Operations */
	public void save() {
		BufferedWriter outstream = null;
		try {
			outstream = new BufferedWriter(new FileWriter(saveFileName));
			outstream.write(Integer.toString(currentOpt)+"\n");
			for (String operation : operations) {
				outstream.write(operation+"\n");
			}
		} catch (Exception e) {} 
		finally {
			if (outstream != null) {
				try {
					outstream.close();
				} catch (IOException e) {}
			}
		}
	}

	/* Load from file the saved operations */
	public void load() {
		operations.clear();
		currentOpt = -1;
		BufferedReader instream = null;
		try {
			instream = new BufferedReader(new FileReader(saveFileName));
			currentOpt = Integer.parseInt(instream.readLine());
			String line;
		    while ((line = instream.readLine()) != null) {
		    	operations.addLast(line);
		    }
		}
		catch (Exception e) {}
		finally {
			if (instream != null) {
				try {
					instream.close();
				} catch (IOException e) {}
			}
		}
	}

}
