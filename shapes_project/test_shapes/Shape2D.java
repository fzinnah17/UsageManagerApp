import java.awt.*;

public abstract class Shape2D extends Shape {
    public final double height;
    public final double width;

    /**
     * Parameter constructor for the 2D Shape object. 
     * @param id
     * @param name
     * @param description
     * @param color
     * @param height
     * @param width
     */
    public Shape2D(int id, String name, String description, Color color, double height, double width) {
        super(id, name, description, color);
        this.height = height;
        this.width = width;
    }

    @Override
    /**
     * Function to return the String representatin of the Shape. 
     * 
     * @return string representation
     */
    public String toString() {
        return String.format("%s %.2f %.2f", super.toString(), 
            height, width);
    }

	@Override
	public int compareTo(Shape o) {
        
        // name and color
        int result = super.compareTo(o);

        if(result == 0) {
            // width and height
            Shape2D s = (Shape2D) o;

            if(height > s.height) {
                result = 1;
            } else if(height < s.height) {
                result = -1;
            } else {
                if(width > s.width) {
                    result = 1;
                } else if(width < s.width) {
                    result = -1;
                } else {
                    result = 0; 
                }
            }
        }
        
        return result;
	}
}
