import java.util.Scanner;
public class Task3 {
	
	    public static void processInput() {
	        Scanner scanner = new Scanner(System.in);
	        try {
	            System.out.print("Enter a number: ");
	            double number = scanner.nextDouble();
	            if (number == 0) {
	                throw new ArithmeticException("Division by zero is not allowed.");
	            }
	            System.out.println("Reciprocal: " + (1 / number));
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input! Please enter a valid number.");
	        } catch (ArithmeticException e) {
	            System.out.println(e.getMessage());
	        } finally {
	            scanner.close();
	        }
	    }

	    public static void main(String[] args) {
	        processInput();
	    }
	}


