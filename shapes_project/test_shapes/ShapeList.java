import java.util.TreeSet;

public class ShapeList extends TreeSet<Shape> {
  
    /**
     * Add new Shape the Collection. 
     */
    public boolean add(Shape shape) {
        if(!contains(shape)) {
            return super.add(shape);
        }

        return false; 
    }

    /**
     * Function to get the collection of 2D Shapes. 
     * 
     * @return list of 2D shapes. 
     */
    public ShapeList get2DShapes() {
        ShapeList list = new ShapeList();

        for(Shape shape: this) {
            if(shape instanceof Quadrilateral) {
                list.add(shape);
            }
        }

        return list;
    }

    /**
     * Function to get the collection of 3D Shapes. 
     * 
     * @return list of 3D shapes
     */
    public ShapeList get3DShapes() {
        ShapeList list = new ShapeList();

        for(Shape shape: this) {
            if(shape instanceof Quadrilateral3D) {
                list.add(shape);
            }
        }

        return list;
    }

    /**
     * Get the number of Shapes in this Collection. 
     * 
     * @return number of shapes. 
     */
    public int getSize() {
        return this.size();
    }

    /**
     * Print Formatted output of the Shape List. 
     */
    public void printFormatted() {
        
        String line = "+--------+-------------+--------+------------------------+----------------------";
        
        System.out.printf("%s%n", line);
        System.out.printf("| %-7s| %-12s| %-7s| %-23s| %-19s |%n", 
            "ID", "Name", "Color", "Dimensions", "Description");
        System.out.printf("%s%n", line);

        for(Shape shape: this) {

            String dimension = "";
            
            if(shape instanceof Quadrilateral) {
                Quadrilateral q = (Quadrilateral) shape;
                dimension = String.format("%.2f X %.2f", q.width, q.height);
            } else {
                Quadrilateral3D q = (Quadrilateral3D) shape;
                dimension = String.format("%.2f X %.2f X %.2f", q.width, q.height, q.length);
            }

            System.out.printf("| %-7d| %-12s| %-7s| %-23s| %-19s |%n", 
                     shape.id, shape.name, shape.getColorName(), dimension, shape.description);
            System.out.printf("%s%n", line);
        }
    }
}
