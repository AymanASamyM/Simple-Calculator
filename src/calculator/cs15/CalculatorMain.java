package calculator.cs15;


public class CalculatorMain {

	public static void main(String[] args) {
		TheCalculator calculator = new TheCalculator();
		calculator.input("3+5 * 4 / 2");
		System.out.println(calculator.getResult());
	}

}
