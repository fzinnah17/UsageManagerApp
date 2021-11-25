import java.awt.*;

public abstract class Shape implements Comparable<Shape> {

    public final int id;
    public final String name; 
    public final String description;
    public final Color color; 

    /**
     * Parameter constructor to create a Shape Object. 
     * 
     * @param id
     * @param name
     * @param description
     * @param color
     */
    public Shape(int id, String name, String description, Color color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public String getColorName() {
        if(color.equals(Color.black))
            return "black";
        else if (color.equals(Color.BLUE))
            return "blue";
        else if (color.equals(Color.GREEN))    
            return "green";
        else if (color.equals(Color.RED))
            return "red";
        else if (color.equals(Color.WHITE))
            return "white";
        else if (color.equals(Color.CYAN))
            return "cyan";
        else if (color.equals(Color.YELLOW))
            return "yellow";
        return "";
    }

    /**
     * Function to calculate area of the shape. 
     * 
     * @return area
     */
    public abstract double area();

    /**
     * Function to calculate teh perimeter of the shape. 
     * 
     * @return perimeter
     */
    public abstract double perimeter();

    @Override
    /**
     * Function to return the String representatin of the Shape. 
     * 
     * @return string representation
     */
    public String toString() {
        return String.format("%d %s %s %s", id, name, description, getColorName());
    }

	@Override
	public int compareTo(Shape o) {
        int result = name.compareTo(o.name);

        if(result == 0) {
            result = getColorName().compareTo(o.getColorName());
        }

        return result;
	}
    
}
