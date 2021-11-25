import java.util.Scanner;

/**
 * Client class to run the StepsToMiles class and test its functionality.
 */
public class TestStepsToMiles {

    public static void main(String [] args) {
        // Keyboard reader
        Scanner keyboard = new Scanner(System.in);


        // First Object create data as in the test.
        StepsToMiles firstStepsToMiles = new StepsToMiles();
        firstStepsToMiles.setName("John Doe");
        firstStepsToMiles.setFeet(5);
        firstStepsToMiles.setInches(4.5);

        // Prompt the user for the values of Second Object.
        System.out.printf("%24s: ", "Enter Name");
        String name = keyboard.nextLine();
        System.out.print("Enter Height (ft and in): ");
        double feet = keyboard.nextDouble();
        double inches = keyboard.nextDouble();

        // Create Second object
        StepsToMiles secondStepsToMiles = new StepsToMiles(name, feet, inches);

        // Display Today Date
        System.out.printf("%n%13s: %s%n%n", "Today's Date", firstStepsToMiles.currDate());

        // Print objects
        System.out.printf("%s%n", firstStepsToMiles.formatAsString(12345));
        System.out.printf("%s%n", secondStepsToMiles.formatAsString(1000));
    }
}
