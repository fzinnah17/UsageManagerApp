import java.awt.*;

public class Quadrilateral3D extends Shape3D {
    /**
     * Parameter constructor for the 2D Shape object. 
     * @param id
     * @param name
     * @param description
     * @param color
     * @param height
     * @param width
     * @param length
     */
    public Quadrilateral3D(int id, String name, String description, Color color, 
            double height, double width, double length) {
        super(id, name, description, color, height, width, length);
    }

     /**
     * Function to calculate area of the shape. 
     * 
     * @return area
     */
    public  double area() {
        return 2 * (width * height + width * length + height * length); 
    }

    /**
     * Function to calculate teh perimeter of the shape. 
     * 
     * @return perimeter
     */
    public double perimeter() {
        return 4 * (height + width + length);
    }
}
