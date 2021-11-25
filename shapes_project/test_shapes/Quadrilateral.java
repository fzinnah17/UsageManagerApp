import java.awt.*;

public class Quadrilateral extends Shape2D {
    
    /**
     * Parameter constructor for the 2D Shape object. 
     * @param id
     * @param name
     * @param description
     * @param color
     * @param height
     * @param width
     */
    public Quadrilateral(int id, String name, String description, Color color, double height, double width) {
        super(id, name, description, color, height, width);
    }

     /**
     * Function to calculate area of the shape. 
     * 
     * @return area
     */
    public  double area() {
        return height * width; 
    }

    /**
     * Function to calculate teh perimeter of the shape. 
     * 
     * @return perimeter
     */
    public double perimeter() {
        return 2 * (height + width);
    }
}
