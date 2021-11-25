import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.concurrent.CancellationException;

/**
 * Class StepsToMiles that encapsulates data involved in calculating StepsToMiles.
 */
public class StepsToMiles {
	/** Name of the Person */
    private String name;

    /** Height in Feet */
    private double feet;

    /** Height In Inches */
    private double inches;

    /**
     * Default constructor to initialize the StepsToMiles class with default values.
     */
    public StepsToMiles() {
        this.name = null;
        this.feet = -1.0;
        this.inches = -1.0;
    }

    /**
     * Parameter constructor to initialize the StepToMales Class with given parameter values
     * for the Name, feet and inches of Person's height.
     *
     * @param name of the person.
     * @param feet of the person.
     * @param inches of the person.
     */
    public StepsToMiles(String name, double feet, double inches) {
        this.name = name;
        this.feet = feet;
        this.inches = inches;
    }

    /**
     * Function to convert complete height into corresponding inches. Each feet is 12 inches +
     * the inches part. Return the total inches in the height.
     *
     * @return height in inches
     */
    public double height_inches() {
        return feet * 12 + inches;
    }

    /**
     * Return the person's stride length in inches. Stride is calculated using
     *  height_inches() * 0.413
     *
     * @return stride length in inches
     */
    public double strideLength_inches() {
        return this.height_inches() * 0.413;
    }

    /**
     * miles(steps) returns the number of miles to walk given the desired number of steps. Remember to
     * invoke the previous methods:
     *
     * strideLength_inches() * steps / (inches P/Miles)
     *
     * inches per mile: 5280 * 12
     *
     * @return miles
     */
    public double miles(int steps) {
        int inchesPerMile = 5280 * 12;
        return (this.strideLength_inches() * steps) / inchesPerMile;
    }

    /**
     * Function to get the name of the Person.
     *
     * @return name of the Person.
     */
    public String getName() {
        return name;
    }

    /**
     * Function to set or update the name of the Person.
     *
     * @param name of the Person.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Function to get the Height in Feet of the Person.
     *
     * @return height in feet
     */
    public double getFeet() {
        return feet;
    }

    /**
     * Function to set or update the height in feet of the Person.
     *
     * @param feet in height
     */
    public void setFeet(double feet) {
        this.feet = feet;
    }

    /**
     * Function to get the Height in inches of the Person.
     *
     * @return height in inches
     */
    public double getInches() {
        return inches;
    }

    /**
     * Function to set or update the height in inches of the Person.
     *
     * @param inches of height
     */
    public void setInches(double inches) {
        this.inches = inches;
    }

    /**
     * Produce a formatted String representation of the Class object.
     *
     * @param steps used to calculate miles
     * @return formatted string representation
     */
    public String formatAsString(int steps) {
        String output = String.format("%13s: %s%n", "Name", name);
        output += String.format("%13s: %.2f' %.2f\"%n", "Height", feet, inches);
        output += String.format("%13s: %.2f%n", "Stride", strideLength_inches());
        output += String.format("%13s: %,d%n", "Steps", steps);
        output += String.format("%13s: %.2f miles%n", "Walk", miles(steps));
        return output;
    }

    /**
     * Generate Today's date and return as MM/DD/YYYY Date String.
     *
     * @return
     */
    public String currDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return String.format("%d/%d/%d", calendar.get(Calendar.MONTH)+1,
                calendar.get(Calendar.DATE), calendar.get(Calendar.YEAR));
    }
}