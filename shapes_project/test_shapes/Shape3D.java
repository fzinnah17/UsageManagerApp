import java.awt.*;

public abstract class Shape3D extends Shape2D {
    public final double length;

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
    public Shape3D(int id, String name, String description, Color color, 
        double height, double width, double length) {
        super(id, name, description, color, height, width);
           this.length = length;
    }

    @Override
    /**
     * Function to return the String representatin of the Shape. 
     * 
     * @return string representation
     */
    public String toString() {
        return String.format("%s %.2f", super.toString(), 
            length);
    }

	@Override
	public int compareTo(Shape o) {
        
        // first id
        int result = super.compareTo(o);

        if(result == 0) {
            Shape3D s = (Shape3D) o;

            if(length > s.length) {
                result = 1;
            } else if(length < s.length) {
                result  = -1;
            } else {
                result = 0;
            }
        }
        
        return result;
	}
}
