package calculator.cs54;

import calculator.Calculator;

import java.io.*;
import java.util.LinkedList;

/**
 * Calculator Class.
 */
public class MyCalculator implements Calculator {
    /**
     * current Formula Member.
     */
    private String currentFormula;
    /**
     * Holds formula History.
     */
    private LinkedList<String> formulas = new LinkedList<>();
    /**
     * Current Index Of Formulas History.
     */
    private int currentFormulaIndex = -1;
    /**
     * To Show When it's saved and not yet loaded.
     */
    private boolean newSave;

    /**
     * History maximum size.
     */
    private final int historySize = 5;

    /**
     * takes formula form user.
     *
     * @param s is the input
     */
    @Override
    public final void input(final String s) {
        currentFormula = s;
        if (formulas.size() == historySize) {
            formulas.removeFirst();
        }

        formulas.add(currentFormula);
        currentFormulaIndex = formulas.indexOf(currentFormula);
    }

    /**
     * takes formula.
     *
     * @return split formula
     */
    @Override
    public final String getResult() {
        char operator = ' ';
        int operatorIndex = 0;
        for (int i = 0; i < currentFormula.length(); i++) {
            operator = currentFormula.charAt(i);
            if (operator == '/' || operator == '*'
                    || operator == '+' || operator == '-') {
                operatorIndex = i;
                break;
            }
        }
        String firstTerm = currentFormula.substring(0, operatorIndex);
        String secondTerm = currentFormula.substring(operatorIndex + 1,
                currentFormula.length());

        if (!checkFormula(firstTerm, secondTerm)) {
            return "Invalid Formula";
        }

        return calculate(firstTerm, operator, secondTerm);
    }

    /**
     * calculate formula result.
     *
     * @param firstTerm  first operand
     * @param operator   operator
     * @param secondTerm second operand
     * @return calculation result
     */
    private String calculate(final String firstTerm,
                             final char operator, final String secondTerm) {
        Double value;
        switch (operator) {
            case '+':
                value = Double.valueOf(firstTerm) + Double.valueOf(secondTerm);
                break;
            case '-':
                value = Double.valueOf(firstTerm) - Double.valueOf(secondTerm);
                break;
            case '/':
                value = Double.valueOf(firstTerm) / Double.valueOf(secondTerm);
                break;
            case '*':
                value = Double.valueOf(firstTerm) * Double.valueOf(secondTerm);
                break;
            default:
                return null;
        }
        return String.valueOf(value);
    }

    /**
     * checks if formula is valid.
     *
     * @param firstTerm  first operand
     * @param secondTerm second operand
     * @return true if valid and false if invalid
     */
    private boolean checkFormula(final String firstTerm,
                                 final String secondTerm) {
        try {
            if (Double.valueOf(firstTerm).isNaN()
                    || Double.valueOf(secondTerm).isNaN()) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * @return current formula.
     */
    @Override
    public final String current() {
        if (formulas.isEmpty()) {
            return null;
        }
        return currentFormula;
    }

    /**
     * @return previous formula in history.
     */
    @Override
    public final String prev() {
        if (formulas.isEmpty()) {
            return null;
        }
        if (currentFormulaIndex == -1) {
            currentFormulaIndex = formulas.size() - 1;
        }
        if (currentFormulaIndex == 0) {
            return null;
        } else {
            currentFormulaIndex--;
            currentFormula = formulas.get(currentFormulaIndex);
            return currentFormula;
        }
    }

    /**
     * @return next formula in history.
     */
    @Override
    public final String next() {
        if (currentFormulaIndex == -1
                || currentFormulaIndex == formulas.size() - 1) {
            return null;
        } else {
            currentFormulaIndex++;
            currentFormula = formulas.get(currentFormulaIndex);
            return currentFormula;
        }
    }

    /**
     * save history.
     */
    @Override
    public final void save() {
        newSave = true;
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("savedCalculations.txt"));
            writer.write(String.valueOf(currentFormulaIndex));
            writer.newLine();
            for (String formula : formulas) {
                writer.write(formula + ',');
            }
            writer.close();
        } catch (IOException e) {
            throw null;
        }
    }

    /**
     * load history.
     */
    @Override
    public final void load() {
        if (newSave) {
            String[] savedFormulas = null;
            try {
                BufferedReader reader = new BufferedReader(
                        new FileReader("savedCalculations.txt"));
                currentFormulaIndex = Integer.valueOf(reader.readLine());
                savedFormulas = reader.readLine().split(",");
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
            formulas.clear();
            if (savedFormulas == null) {
                currentFormula = null;
            } else {
                for (int i = 0; i < savedFormulas.length; i++) {
                    formulas.add(savedFormulas[i]);
                }
                currentFormula = formulas.get(currentFormulaIndex);
            }
            newSave = false;
        }
    }
}
