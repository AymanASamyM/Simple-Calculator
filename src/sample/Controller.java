package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import calculator.cs15.TheCalculator;

public class Controller {

    private TheCalculator calculator = new TheCalculator();
    public Label inputFormela;
    public Label statusLabel;

    public void NumOnClick(ActionEvent e) {
        if (inputFormela.getText() == "Invalid Formula")
            inputFormela.setText("");
        inputFormela.setText(inputFormela.getText() + ((Button) e.getSource()).getText());
        statusLabel.setText("");
    }

    public void RemoveLastChar() {
        String currentInput = inputFormela.getText();
        int length = currentInput.length();
        if (length > 0)
            inputFormela.setText(currentInput.substring(0, length - 1));
        statusLabel.setText("");
    }

    public void ClearAll (){
        inputFormela.setText("");
        statusLabel.setText("");
    }

    public void getResult () {
        calculator.input(inputFormela.getText());
        inputFormela.setText(calculator.getResult());
        statusLabel.setText("");
    }

    public void current () {
        inputFormela.setText(calculator.current());
        statusLabel.setText("");
    }

    public void next () {
        calculator.next();
        inputFormela.setText(calculator.current());
        statusLabel.setText("");
    }

    public void prev () {
        calculator.prev();
        inputFormela.setText(calculator.current());
        statusLabel.setText("");
    }

    public void save () {
        calculator.save();
        statusLabel.setText("Successfully Formulas Saved");
    }

    public void load () {
        calculator.load();
        statusLabel.setText("Successfully Formulas Loaded");
    }
}
