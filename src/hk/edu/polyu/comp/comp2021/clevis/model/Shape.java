package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.Stack;
import java.util.ArrayList;


/**
 * The shape class, extended into subclasses of shapes (Rectangle, line, circle, square and group)
 * Each instance of shape contains the name of the shape for base, and all of them contains the property of a doubly linked list node
 * Along with methods to manipulate the doubly linked list of shapes or the shape themselves individually
 * Static methods are provided as well to access the doubly linked list easily
 */

public class Shape {
    private final String name;
    private Shape next;
    private Shape previous;
    private Shape grouparent = null;
    

    private static Shape cur=null;
    private static Shape head= null;

    /**
     *
     * @param name      The name of the shape, each subclass calls super(name) to ensure the name is passed into here and stored
     */

    public Shape(String name) {
        // DON'T FORGET VALIDATION
        // Should we consider that names of shapes should not be duplicated
        // Already implemented to check duplicated existing names of shapes, in Clevis.java using a boolean function nameNotUsed()

        this.name = name;
    }

    /**
     * Static method addShape:
     * called to add a shape into the doubly linked list of shapes,
     * as shapes are not added automatically into the list upon construction
     * @param a     The shape object to be added to the list
     */

    public static void addShape(Shape a){
        if (head == null){
            Shape.head = a;
        }
        if(getCur() !=null){
            getCur().setNext(a);
            a.setPrevious(getCur());
        }
        setCur(a);
        
    }

    /**
     * Static method getCur:
     * @return  the cur pointer, which usually points to the latest available constructed shape
     */
    public static Shape getCur() {
        return cur;
    }

    /**
     * Static method setCur
     * @param cur   Updating the cur pointer, when there are operations on the shape such that they are deleted or added
     */
    public static void setCur(Shape cur) {
        Shape.cur = cur;
    }

    /**                 Basic Settings              **/


    /**
     * method getName:
     * called directly by any instance of shape
     * @return  the name of the shape
     */
    public String getName() {
        return name;
    }

    /**
     * method getSubClass:
     * this method is overridden in every subclasses, returning the type of the subclass as a string
     * @return      the type of the subclass of a specific shape
     */
    public String getSubClass(){return "";}

    /**
     * method createShape:
     * this method is overridden in every subclasses, returning the shape in the type of the specified subclass
     * @return      the shape that was created
     */
    public Shape createShape(){return null;}

    /**
     * method getData:
     * this method is overridden in every subclasses, returning the parameters of the given shape
     * @return an array of double indicating different parameters of a given shape
     */
    public double[] getData(){return new double[0];};

    /**
     * method getInfo:
     * This method is overridden in every subclass, prints out the information of the shape (Type, name, x,y, etc.)
     * @param n indicates the indentation, should be 1 for most cases, as the overridden version in group shape would do recursion on it, increasing the indentation value on printing the group members
     */
    public void getInfo(int n){}

    /**
     * method getNext:
     * @return  The shape pointed by the "next" pointer of the current shape
     */
    public Shape getNext() {
        return next;
    }

    /**
     * method setNext:
     * @param next   Updates the "next" pointer of the current shape
     */
    public void setNext(Shape next) {
        this.next = next;
    }

    /**
     * method getPrevious:
     * @return The shape pointed by the "previous" pointer of the current shape
     */
    public Shape getPrevious() {
        return previous;
    }

    /**
     * method setPrevious:
     * @param previous updates the "previous" pointer of the current shape
     */
    public void setPrevious(Shape previous) {
        this.previous = previous;
    }

    /**
     * method getGrouparent:
     * The grouparent pointer indicates if the current shape belongs to a group, if it does it will point to the group shape that grouped them, else the grouparent is set to null
     * @return the group parent shape of the current shape, null if the current shape doesn't belong to a group
     */
    public Shape getGrouparent() {
        return grouparent;
    }

    /**
     * method setGrouparent:
     * @param grouparent updates the group parent pointer of the current shape, when the current shape is involved grouping related operations such as group constructing and ungrouping
     */
    public void setGrouparent(Shape grouparent) {
        this.grouparent = grouparent;
    }

    /**
     * method move:
     * "moves" the shape by adding the parameters passed into the method to the x and y coordinates of the shape respectively
     * This method is overridden in every subclass of shape as the moving operation of each type of shape may slightly alter from each other
     * In group, the method is recursed into the grouped shapes so every group member element is moved
     * @param x     Moves the x-coordinate by this value (add it to x)
     * @param y     Moves the y-coordinate by this value (add it to y)
     */
    public void move(double x, double y){}

    /**
     * method boundingbox:
     * This method is overridden in every subclass of shape, as the way to obtain the boundingbox of each shape is different from others
     * Recursed in group shapes, as it will obtain the bounding boxes returned by each group member shape and compare them to find the bounding box that includes all shapes in the group
     * @return      a double type array, in the indexes: 0 is the x, 1 is the y, 2 is the width, 3 is the height of the bounding box
     */
    public double[] boundingbox(){
        return new double[4];
    }

    /**
     * method ungroup:
     * The method is overridden in Group type subclass, it removes the group shape from the doubly linked list
     * and set all the member shape's grouparent pointer to null as they are ungrouped
     */
    public void ungroup(){}

    /**
     * method regroup:
     * The method is overridden in Group type subclass, it "redo"s the ungroup operation by recovering the group
     * and the member shape's grouparent pointer will point to the group again
     */
    public void regroup(){}

    /**
     * static method findAShape:
     * Takes a name as a parameter, returns the shape with the name if it exists in the doubly linked list
     * @param name      The name of the shape to find
     * @return          returns the shape object with the name, null if there is no such shape with the name
     */
    public static Shape findAShape(String name) {        //finds and returns the shape with a name
        Shape temp = getCur();
        while (temp != null) {
            if (temp.getName().equals(name)) {
                break;
            }
            temp = temp.getPrevious();
        }
        return temp;
    }

    /**
     * method belongToGroup:
     * @return The grouparent pointer of the shape called this method
     */
    public Shape belongToGroup(){
        return getGrouparent();
    }
    /**-----------------[Intersect related methods]------------------------------------------------------------**/
    /**
     * The method that checks whether 2 shape intersects each other
     * @param n1    the first shape that needed to be checked
     * @param n2    the second shape that needed to be checked
     * @return      a boolean value indicating whether 2 shape intersects with each other
     */
    public static boolean intersect(Shape n1, Shape n2){
        ArrayList<Shape> list1 = subShapes(n1);
        ArrayList<Shape> list2 = subShapes(n2);
        Shape[] temp1 = list1.toArray(new Shape[list1.size()]);
        Shape[] temp2 = list2.toArray(new Shape[list2.size()]);
        double[] data = new double[4];
        double[] data2 = new double[4];
        for(int i=0; i<temp1.length; i++){
            for(int j=0; j<temp2.length; j++){
                if (containspoint(temp1[i], temp2[j])){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A method that checks whether the intercepting point exists in two shapes
     * @param temp      the first shape that needed to be checked
     * @param temp1     the second shape that needed to be checked
     * @return          a boolean value indicating whether the intercepting point exists
     */
    public static boolean containspoint(Shape temp, Shape temp1){
        boolean flag = false;
        Shape p1, p2, p3, p4;
        double[] data = new double[4];
        double[] data2 = new double[4];
        if (temp.getSubClass().equals("Square")){
            data = temp.getData();
            temp = new Rectangle("test", data[0], data[1], data[2], data[2]);
        }
        if (temp.getSubClass().equals("Rectangle")){
            data = temp.getData();
            p1 = new Line("p1", data[0], data[1], data[0] + data[2], data[1]);
            p2 = new Line("p2", data[0], data[1] - data[3], data[0] + data[2], data[1] - data[3]);
            p3 = new Line("p3", data[0], data[1], data[0], data[1] - data[3]);
            p4 = new Line("p4", data[0] + data[2], data[1], data[0] + data[2], data[1] - data[3]);
            flag = (containspoint(p1, temp1) || containspoint(p2, temp1) || containspoint(p3, temp1) || containspoint(p4, temp1));
        }
        if (temp1.getSubClass().equals("Square")){
            data = temp1.getData();
            temp1 = new Rectangle("test", data[0], data[1], data[2], data[2]);
        }
        if (temp1.getSubClass().equals("Rectangle")){
            data = temp1.getData();
            p1 = new Line("p1", data[0], data[1], data[0] + data[2], data[1]);
            p2 = new Line("p2", data[0], data[1] - data[3], data[0] + data[2], data[1] - data[3]);
            p3 = new Line("p3", data[0], data[1], data[0], data[1] - data[3]);
            p4 = new Line("p4", data[0] + data[2], data[1], data[0] + data[2], data[1] - data[3]);
            flag = (containspoint(temp, p1) || containspoint(temp, p2) || containspoint(temp, p3) || containspoint(temp, p4));
        }
        if (temp.getSubClass().equals("Circle") && temp1.getSubClass().equals("Circle")){
            flag = check2circle(temp, temp1);
        }
        else if (temp.getSubClass().equals("Circle") && temp1.getSubClass().equals("Line")){
            data = temp.getData();
            data2 = temp1.getData();
            flag = checkCircleLine(new Circle("test", data[0], data[1], data[2]), new Line("test",data2[0], data2[1], data2[2], data2[3]));
        }
        else if (temp.getSubClass().equals("Line") && temp1.getSubClass().equals("Circle")){
            data = temp.getData();
            data2 = temp1.getData();
            flag = checkCircleLine(new Circle("test",data2[0], data2[1], data2[2]), new Line("test", data[0], data[1], data[2], data[3]));
        }
        else if (temp.getSubClass().equals("Line") && temp1.getSubClass().equals("Line")){
            flag = check2Line(temp, temp1);
        }
        return flag;
    }

    /**
     * a method that checks whether 2 circles has intersecting points
     * @param n1    the parameters of the first circle
     * @param n2    the parameters of the second circle
     * @return      a boolean value indicating whether 2 circles intersect
     */
    public static boolean check2circle(Shape n1, Shape n2){
        double[] data1 = n1.getData();
        double[] data2 = n2.getData();
        double num = pythegorean(data1[0], data1[1], data2[0], data2[1]);
        if (num < Math.abs(data1[2] - data2[2]) || num > data1[2] + data2[2]){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * a method that checks whether a circle and a line has intersecting points
     * @param n1    the parameters of the circle
     * @param n2    the parameters of the line
     * @return      a boolean value indicating whether they intersect
     */
    public static boolean checkCircleLine(Shape n1, Shape n2){
        double[] data1 = n1.getData();
        double[] data2 = n2.getData();
        double x1Intersect, y1Intersect, x2Intersect, y2Intersect;
        final double DELTA = 0.00001;
        if (Math.abs(data2[2] - data2[0]) == 0){
            if (Math.abs(data2[0] - data1[0]) > data1[2]){
                return false;
            }
            else if (Math.abs(data2[0] - data1[0]) == data1[2]){
                x1Intersect = data2[0];
                y1Intersect = data1[1];
                if (Math.abs(pythegorean(data2[0], data2[1], x1Intersect, y1Intersect)) > Math.abs(pythegorean(data2[0], data2[1], data2[2], data2[3]))
                        || Math.abs(pythegorean(data2[2], data2[3], x1Intersect, y1Intersect)) > Math.abs(pythegorean(data2[0], data2[1], data2[2], data2[3]))){
                    return false;
                }
                return true;
            }
            else{
                x1Intersect = data2[0];
                x2Intersect = data2[0];
                double a = 1;
                double b = -2* data1[1];
                double c = Math.pow(x1Intersect- data1[0],2) + Math.pow(data1[1],2) - Math.pow(data1[2],2);
                y1Intersect = (-b + Math.sqrt(Math.pow(b,2) - 4*a*c)) / (2*a);
                y2Intersect = (-b - Math.sqrt(Math.pow(b,2) - 4*a*c)) / (2*a);
                if (Math.abs(pythegorean(data2[0], data2[1], x1Intersect, y1Intersect)) > Math.abs(pythegorean(data2[0], data2[1], data2[2], data2[3]))
                        || Math.abs(pythegorean(data2[2], data2[3], x1Intersect, y1Intersect)) > Math.abs(pythegorean(data2[0], data2[1], data2[2], data2[3]))){
                    if (Math.abs(pythegorean(data2[0], data2[1], x2Intersect, y2Intersect)) > Math.abs(pythegorean(data2[0], data2[1], data2[2], data2[3]))
                            || Math.abs(pythegorean(data2[2], data2[3], x2Intersect, y2Intersect)) > Math.abs(pythegorean(data2[0], data2[1], data2[2], data2[3]))){
                        return false;
                    }
                }
                return true;
            }
        }
        else{
            double m = (data2[3] - data2[1]) / (data2[2] - data2[0]);
            double temp = data2[1] - m* data2[0] - data1[1];
            double a = Math.pow(m,2) + 1;
            double b = 2*m*temp - 2* data1[0];
            double c = Math.pow(data1[0],2) + Math.pow(temp,2) - Math.pow(data1[2],2);
            double determinant = Math.pow(b,2) - (4*a*c);
            if (determinant < 0){
                return false;
            }
            else if (determinant == 0){
                x1Intersect = (-b) / (2*a);
                y1Intersect = m*x1Intersect + data2[1] - m* data2[0];
                if (Math.abs(pythegorean(data1[0], data1[1], x1Intersect, y1Intersect) - data1[2]) < DELTA){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                x1Intersect = (-b + Math.sqrt(determinant)) / (2*a);
                y1Intersect = m*x1Intersect + data2[1] - m* data2[0];
                x2Intersect = (-b - Math.sqrt(determinant)) / (2*a);
                y2Intersect = m*x2Intersect + data2[1] - m* data2[0];
                if (Math.abs(pythegorean(data2[0], data2[1], x1Intersect, y1Intersect)) > Math.abs(pythegorean(data2[0], data2[1], data2[2], data2[3]))
                        || Math.abs(pythegorean(data2[2], data2[3], x1Intersect, y1Intersect)) > Math.abs(pythegorean(data2[0], data2[1], data2[2], data2[3]))){
                    if (Math.abs(pythegorean(data2[0], data2[1], x2Intersect, y2Intersect)) > Math.abs(pythegorean(data2[0], data2[1], data2[2], data2[3]))
                            || Math.abs(pythegorean(data2[2], data2[3], x2Intersect, y2Intersect)) > Math.abs(pythegorean(data2[0], data2[1], data2[2], data2[3]))){
                        return false;
                    }
                }
                return true;
            }
        }
    }

    /**
     * a method that checks whether 2 lines has intersecting points
     * @param n1    the parameters of the first line
     * @param n2    the parameters of the second line
     * @return      a boolean value indicating whether 2 lines intersect
     */
    public static boolean check2Line (Shape n1, Shape n2){
        double xIntersect, yIntersect;
        double[] data1 = n1.getData();
        double[] data2 = n2.getData();
        if (data1[1] - data1[0] == 0 && data2[2] - data2[0] == 0){
            if (data1[0] != data2[0]){
                return false;
            }
            else if(Math.abs(data1[1] - data2[1]) > Math.abs(data2[1] - data2[3]) || Math.abs(data1[1] - data2[3]) > Math.abs(data2[1] - data2[3])){
                if(Math.abs(data1[3] - data2[1]) > Math.abs(data2[1] - data2[3]) || Math.abs(data1[3] - data2[3]) > Math.abs(data2[1] - data2[3])){
                    return false;
                }
            }
            return true;
        }
        if (data1[2] - data1[0] == 0){
            xIntersect = data1[0];
            double m2 = (data2[3] - data2[1])/(data2[2] - data2[0]);
            yIntersect = data2[1] -m2* data2[0] - (-m2*xIntersect);
            if(Math.abs(yIntersect- data1[1]) > Math.abs(data1[1] - data1[3]) || Math.abs(yIntersect- data1[3]) > Math.abs(data1[1] - data1[3])){
                return false;
            }
            else if(pythegorean(xIntersect,yIntersect, data2[0], data2[1]) > pythegorean(data2[0], data2[1], data2[2], data2[3])
                    || pythegorean(xIntersect,yIntersect, data2[2], data2[3]) > pythegorean(data2[0], data2[1], data2[2], data2[3])){
                return false;
            }
            else{
                return true;
            }
        }
        else if (data2[2] - data2[0] == 0){
            xIntersect = data2[0];
            double m = (data1[3] - data1[1])/(data1[2] - data1[0]);
            yIntersect = data1[1] -m* data1[0] - (-m*xIntersect);
            if(Math.abs(yIntersect- data2[1]) > Math.abs(data2[1] - data2[3]) || Math.abs(yIntersect- data2[3]) > Math.abs(data2[1] - data2[3])){
                return false;
            }
            else if(pythegorean(xIntersect,yIntersect, data1[0], data1[1]) > pythegorean(data1[0], data1[1], data1[2], data1[3])
                    || pythegorean(xIntersect,yIntersect, data1[2], data1[3]) > pythegorean(data1[0], data1[1], data1[2], data1[3])){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            double m = (data1[3] - data1[1])/(data1[2] - data1[0]); //slope
            double m2 = (data2[3] - data2[1])/(data2[2] - data2[0]); //another slope
            double a = -m, b = 1, c = data1[1] -m* data1[0];    //ax + by = c
            double d = -m2, e = 1, f = data2[1] -m2* data2[0];  //dx + ey = f
            //solving 2 equations with 2 unknowns (with matrix)
            double temp = b*d - a*e;
            if (m == m2){
                if (c != f){
                    return false;
                }
                else if(pythegorean(data1[0], data1[1], data2[0], data2[1]) > pythegorean(data2[0], data2[1], data2[2], data2[3]) ||
                        pythegorean(data1[0], data1[1], data2[2], data2[3]) > pythegorean(data2[0], data2[1], data2[2], data2[3])){
                    if(pythegorean(data1[2], data1[3], data2[0], data2[1]) > pythegorean(data2[0], data2[1], data2[2], data2[3]) ||
                            pythegorean(data1[2], data1[3], data2[2], data2[3]) > pythegorean(data2[0], data2[1], data2[2], data2[3])){
                        return false;
                    }
                }
                return true;
            }
            xIntersect = (b*f - c*e) / temp;
            yIntersect = (c*d - a*f) / temp;
            if(pythegorean(xIntersect,yIntersect, data1[0], data1[1]) > pythegorean(data1[0], data1[1], data1[2], data1[3])
                    || pythegorean(xIntersect,yIntersect, data1[2], data1[3]) > pythegorean(data1[0], data1[1], data1[2], data1[3])
                    || pythegorean(xIntersect,yIntersect, data2[0], data2[1]) > pythegorean(data2[0], data2[1], data2[2], data2[3])
                    || pythegorean(xIntersect,yIntersect, data2[2], data2[3]) > pythegorean(data2[0], data2[1], data2[2], data2[3])){
                return false;
            }
            else{
                return true;
            }
        }
    }

    /**
     * A method that checks the content of the shapes inside a group of shapes
     * @param n         the group of shapes
     * @return          an array of shapes inside that group of shapes
     */
    public static ArrayList<Shape> subShapes(Shape n){
        Shape temp = cur;
        ArrayList<Shape> ret = new ArrayList<>();
        if (n.getSubClass().equals("Group")){
            Shape[] test = new Shape[1];
            test[0] = n;
            for (int i = 0; i< (new Group("test", test)).getS1().length; i++){
                if ((new Group("test", test)).getS1()[i].getSubClass().equals("Group")){
                    ret.addAll(subShapes((new Group("test", test)).getS1()[i]));
                }
                else{
                    ret.add((new Group("test", test)).getS1()[i]);
                }
            }
            return ret;
        }
        else{
            ret.add(n);
            return ret;
        }
    }
    /**-----------------[pickandmove related methods]------------------------------------------------------------**/

    /**
     * a method that moves a certain shape to the desired place when given a point that intersects with the shape
     * @param x         the x coordinate of the shape that needed to be moved
     * @param y         the y coordinate of the shape that needed to be moved
     * @param dx        the change in x coordinate of the shape
     * @param dy        the change in y coordinate of the shape
     */
    public static void pickandmove(double x, double y, double dx, double dy){
        Shape temp = cur;
        while (temp != null) {
            if (containspoint(temp, x, y)) {
                break;
            }
            temp = temp.getPrevious();
        }
        if (temp == null){
            System.out.println("There isn't any shape satisfying the parameters.");
            return;
        }
        else{
            while (temp.belongToGroup() != null){
                temp = temp.belongToGroup();
            }
            temp.move(dx, dy);
        }
    }

    /**
     * a method that checks whether a shape contains with a point
     * @param temp      the shape that needed to be checked
     * @param x         the x coordinate of the point
     * @param y         the y coordinate of the point
     * @return          a boolean value that indicates whether they intersect
     */
    public static boolean containspoint(Shape temp, double x, double y){
        Shape temp2 = new Shape("test");
        Shape p1, p2, p3, p4;
        boolean flag = false;
        double[] data = new double[4];
        Shape[] test = new Shape[1];
        test[0] = temp;
        if (temp.getSubClass().equals("Group")){
            for (int i = 0; i< (new Group("test", test)).getS1().length; i++) {
                flag = (flag || containspoint((new Group("test", test)).getS1()[i], x, y));
            }
            return flag;
        }
        else if (temp.getSubClass().equals("Circle")){
            data = temp.getData();
            flag = minDistance(data[0], data[1], x, y, data[2]);
        }
        else if (temp.getSubClass().equals("Rectangle")){
            temp2 = temp;
        }
        else if (temp.getSubClass().equals("Square")){
            data = temp.getData();
            temp2 = new Rectangle("test", data[0], data[1], data[2], data[2]);
        }
        else if (temp.getSubClass().equals("Line")){
            data = temp.getData();
            flag = minDistance(data[0], data[1], data[2], data[3], x, y);
        }
        if (temp2.getSubClass().equals("Rectangle")){
            data = temp2.getData();
            p1 = new Line("p1", data[0], data[1], data[0] + data[2], data[1]);
            p2 = new Line("p2", data[0], data[1] - data[3], data[0] + data[2], data[1] - data[3]);
            p3 = new Line("p3", data[0], data[1], data[0], data[1] - data[3]);
            p4 = new Line("p4", data[0] + data[2], data[1], data[0] + data[2], data[1] - data[3]);
            flag = (containspoint(p1, x, y) || containspoint(p2, x, y) || containspoint(p3, x, y) || containspoint(p4, x, y));
        }
        return flag;
    }

    //Circle minDistance

    /**
     * a method that calculates the distance between a point and the centre of the circle, and checks whether the point lies on the circumference
     * @param x         the x coordinate of the circle
     * @param y         the y coordinate of the circle
     * @param dx        the x coordinate of the point
     * @param dy        the y coordinate of the point
     * @param r         the radius of the point
     * @return          whether the point lines on the circumference with the error of 0.05
     */
    public static boolean minDistance(double x, double y, double dx, double dy, double r){
        final double CONST = 0.05;
        double distance = pythegorean(x, y, dx, dy);
        if (Math.abs(distance - r) < CONST){
            return true;
        }
        return false;
    }

    //Rectangle, Square and Line minDistance

    /**
     * a method that checks whether a point lies within a line
     * @param x1        the x coordinate of the first point of a line
     * @param y1        the y coordinate of the first point of a line
     * @param x2        the x coordinate of the second point of a line
     * @param y2        the y coordinate of the second point of a line
     * @param dx        the x coordinate of the point
     * @param dy        the y coordinate of the point
     * @return          a boolean value indicating whether the point lies within a line
     */
    public static boolean minDistance(double x1, double y1, double x2, double y2, double dx, double dy){
        double xIntersect, yIntersect, distance;
        final double CONST = 0.05;
        if (y2-y1 == 0){
            xIntersect = dx;
            yIntersect = y1;
        }
        else if (x2-x1 == 0){
            xIntersect = x1;
            yIntersect = dy;
        }
        else{
            double m = (y2-y1)/(x2-x1); //slope
            double m2 = -1 / m; //perpendicular to slope
            double a = -m, b = 1, c = y1-m*x1;    //ax + by = c
            double d = -m2, e = 1, f = dy-m2*dx;  //dx + ey = f
            //solving 2 equations with 2 unknowns (with matrix)
            double temp = b*d - a*e;
            xIntersect = (b*f - c*e) / temp;
            yIntersect = (c*d - a*f) / temp;
        }
        if (pythegorean(xIntersect,yIntersect,x2,y2) > pythegorean(x1,y1,x2,y2) || pythegorean(xIntersect,yIntersect,x1,y1) > pythegorean(x1,y1,x2,y2)){
            if(pythegorean(xIntersect,yIntersect,x2,y2) > pythegorean(xIntersect,yIntersect,x1,y1)){
                distance = pythegorean(dx,dy,x1,y1);
            }
            else{
                distance = pythegorean(dx,dy,x2,y2);
            }
        }
        else{
            distance = pythegorean(dx,dy,xIntersect,yIntersect);
        }
        if (distance < CONST){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * a method that calculates the distance between 2 points using pythegorean's theorem
     * @param x     the x coordinate of the first point
     * @param y     the y coordinate of the first point
     * @param dx    the x coordinate of the second point
     * @param dy    the y coordinate of the second point
     * @return      the distance between those 2 points
     */
    public static double pythegorean(double x, double y, double dx, double dy){
        double xDif = x-dx;
        double yDif = y-dy;
        return Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
    }
    /**-----------------[Delete related methods]------------------------------------------------------------**/

    /**
     * static method delete
     * takes a name as the parameter, finds and deletes the shape if the shape exists in the doubly linked list and not a member of a group.
     * @param name  The name of the shape to be deleted
     * @return      nothing
     */
    public static boolean delete(String name){     //delete function prototype part 1
        Shape target = findAShape(name);
        if(target != null) {
            if(target.getGrouparent() == null) {
                target.delete();
                return false;
            }
            else{
                System.out.println("Deleting a component shape of a group is invalid!");
                return true;
            }
        }
        else{
            System.out.println("No shape with such name is found.");
            return true;
        }
    }

    /**
     * method delete:
     * deletes the shape that called this method
     */
    public void delete() {      //delete function prototype part 2
        SaveUndo(this,1);
        if (this.getPrevious() == null && this.getNext() != null) {
            this.getNext().setPrevious(null);
        } else if (this.getNext() == null && this.getPrevious() != null) {
            setCur(this.getPrevious());
        } else if (this.getPrevious() != null) {
            this.getNext().setPrevious(this.getPrevious());
        } else {
            setCur(null);
        }
        if(getCur() == this){
            setCur(this.getPrevious());
        }
    }

    /**
     * method undelete:
     * "undo"s the delete on a shape that is deleted
     */
    public void undelete() {
        SaveUndo(this,2);
        if (getCur() == null) {
            setCur(this);
        }   else if (this.getPrevious() == null && this.getNext() != null) {
            this.getNext().setPrevious(this);
        } else {
            setCur(this);
        }
    }

    

    /**---------------[List related methods]------------------------------------------------------------**/

    /**
     * static method List
     * Lists every available shape by the decreasing Z-order,
     * by looping through the doubly linked list from cur(usually the tail) to head
     * and calling getInfo on each shape
     * group member shapes will not be listed out twice
     */
    public static void List(){      //Lists every single shape, from the newest to the oldest
        Shape temp = getCur();
        while(temp != null){
            if(temp.belongToGroup() == null) {
                temp.getInfo(1);
            }
            temp = temp.getPrevious();
        }
    }

    /**
     * A Method made for debugging
     */
    public static void ListFromHead(){   //might have to use this in redo steps, if you are having questions please ask Leo
        Shape temp = head;
        while (temp != null){
            temp.getInfo(1);
            temp = temp.getNext();
        }
    }
    /**---------------[Undo related methods]------------------------------------------------------------*/
    protected static Stack<Shape> undo = new Stack<Shape>();
    protected static Stack<Integer> code = new Stack<Integer>();
    protected static Stack<Double> movx = new Stack<Double>();
    protected static Stack<Double> movy = new Stack<Double>();
    protected static Stack<Shape> redo = new Stack<Shape>();
    protected static Stack<Integer> recode = new Stack<Integer>();
    protected static Stack<Double> removx = new Stack<Double>();
    protected static Stack<Double> removy = new Stack<Double>();
    /**undoable methods:
     * re-add/delete 1,2
     * move 3
     * re-group/ungroup 4,5
     */
    public static void SaveUndo(Shape target,int number){//all the above methods use this to save to the undo list
        undo.push(target);
        code.push(number);
    }
    public static void SaveMove(Double x,Double y){//save the reverse action of the move
        movx.push(-x);
        movy.push(-y);
    }
    public static void PopUndo(){
        undo.pop();
        code.pop();
    }
    public static void PopMove(){
        movx.pop();
        movy.pop();
    }
    public static void ClearRedo(){
        redo.clear();
        recode.clear();
    }
    public static void SaveRedo(Shape target,int number){//same implementation for redo
        redo.push(target);
        recode.push(number);
    }
    public static void SavereMove(Double x,Double y){//use the reversed save of the move
        removx.push(x);
        removy.push(y);
    }

    public static void Undos(){
        //System.out.println(code.toString());
        if (!undo.empty()) {
            Shape target = undo.pop();
            switch(code.pop()){
                case 1:
                    target.undelete();
                    SaveRedo(undo.pop(),code.pop());
                    break;
                case 2:
                    target.delete();
                    SaveRedo(undo.pop(),code.pop());
                    break;
                case 3:
                    target.move(movx.pop(),movy.pop());
                    SaveRedo(undo.pop(),code.pop());
                    SavereMove(movx.pop(),movy.pop());
                    break;
                case 4:
                    target.regroup();
                    SaveRedo(undo.pop(),code.pop());
                    break;
                case 5:
                    target.ungroup();
                    SaveRedo(undo.pop(),code.pop());
                    break;
            }
        }
            
    }
    /*Redo command pair codes are reversed*/
    public static void Redo(){
        //System.out.println(recode.toString());
        if (!redo.empty()) {
            Shape target = redo.pop();
            switch(recode.pop()){
                case 1:
                    target.undelete();
                    break;
                case 2:
                    target.delete();
                    break;
                case 3:
                    target.move(removx.pop(),removy.pop());
                    break;
                case 4:
                    target.regroup();
                    break;
                case 5:
                    target.ungroup();
                    break;
            }
        }
            
    }


}


//===============[RECTANGLE CLASS]==========================================================================

/**
 * Rectangle class shape, extended from Shape class
 */
class Rectangle extends Shape {
    private double x;
    private double y;
    private double w;
    private double h;

    /**
     *
     * @param name      The name of the rectangle
     * @param x         The x-coordinate of the top-left corner of the rectangle
     * @param y         The y-coordinate of the top-left corner of the rectangle
     * @param w         The width of the rectangle
     * @param h         The height of the rectangle
     */
    Rectangle(String name, double x, double y, double w, double h) {
        super(name);
        SaveUndo(this,2);
        this.setX(x);
        this.setY(y);
        this.setW(w);
        this.setH(h);
    }
    @Override
    public Shape createShape(){
        Rectangle ret = new Rectangle(this.getName(), this.getX(), this.getY(), this.getW(), this.getH());
        return ret;
    }
    @Override
    public String getSubClass(){return "Rectangle";}
    @Override
    public double[] getData(){
        double[] ret = new double[4];
        ret[0] = this.getX();
        ret[1] = this.getY();
        ret[2] = this.getW();
        ret[3] = this.getH();
        return ret;
    }
    @Override
    public void getInfo(int n){
        System.out.println("[Shape type: Rectangle] " + " [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f", getX()) + "]  [y-coordinate: "+String.format("%.2f", getY())+"]  [width: "+String.format("%.2f", getW())+"]  [height: "+String.format("%.2f", getH())+"]");
    }
    @Override
    public void move(double x, double y){
        SaveUndo(this, 3);
        SaveMove(x, y);
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }

    @Override
    public double[] boundingbox() {
        double[] boxArr = new double[4];
        boxArr[0] = this.getX();
        boxArr[1] = this.getY();
        boxArr[2] = this.getW();
        boxArr[3] = this.getH();

        return boxArr;
    }

    /**
     * The 4 parameters needed for constructing a rectangle
     */

    /**
     * The method to get x coordinate of the top left corner of a rectangle
     * @return          the x coordinate of the top left corner of a rectangle
     */
    public double getX() {
        return x;
    }

    /**
     * The method to set x coordinate of the top left corner of a rectangle
     * @param x          the x coordinate of the top left corner of a rectangle
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * The method to get y coordinate of the top left corner of a rectangle
     * @return          the y coordinate of the top left corner of a rectangle
     */
    public double getY() {
        return y;
    }

    /**
     * The method to set y coordinate of the top left corner of a rectangle
     * @param y          the y coordinate of the top left corner of a rectangle
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * The method to get the width of a rectangle
     * @return          the width of a rectangle
     */
    public double getW() {
        return w;
    }

    /**
     * The method to set the width of a rectangle
     * @param w          the width of a rectangle
     */
    public void setW(double w) {
        this.w = w;
    }

    /**
     * The method to get the height of a rectangle
     * @return          the height of a rectangle
     */
    public double getH() {
        return h;
    }

    /**
     * The method to set the height of a rectangle
     * @param h          the height of a rectangle
     */
    public void setH(double h) {
        this.h = h;
    }
}


//===============[LINE CLASS]======================================================================================

/**
 * Line class shape, extended from Shape class
 */

class Line extends Shape {
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    /**
     *
     * @param name      The name of the line shape
     * @param x1        The x-coordinate of the first point
     * @param y1        The y-coordinate of the first point
     * @param x2        The x-coordinate of the second point
     * @param y2        The y-coordinate of the second point
     */

    Line(String name, double x1, double y1, double x2, double y2) {
        super(name);
        SaveUndo(this,2);
        this.setX1(x1);
        this.setX2(x2);
        this.setY1(y1);
        this.setY2(y2);
    }
    @Override
    public Shape createShape(){
        Line ret = new Line(this.getName(), this.getX1(), this.getY1(), this.getX2(), this.getY2());
        return ret;
    }
    @Override
    public String getSubClass(){return "Line";}
    @Override
    public double[] getData(){
        double[] ret = new double[4];
        ret[0] = this.getX1();
        ret[1] = this.getY1();
        ret[2] = this.getX2();
        ret[3] = this.getY2();
        return ret;
    }
    @Override
    public void getInfo(int n){
        System.out.println("[Shape type: Line] "+ "  [Shape name: "+this.getName()+"]  [x-coordinate 1: "+ String.format("%.2f", getX1()) + "]  [y-coordinate 1: "+String.format("%.2f", getY1())+"]  [x-coordinate 2: "+String.format("%.2f", getX2())+"]  [y-coordinate 2: "+String.format("%.2f", getY2())+"]");
    }

    @Override
    public void move(double x, double y){
        SaveUndo(this, 3);
        SaveMove(x, y);
        this.setX1(this.getX1() + x);
        this.setX2(this.getX2() + x);
        this.setY1(this.getY1() + y);
        this.setY2(this.getY2() + y);
    }

    @Override
    public double[] boundingbox() {
        double[] boxArr = new double[4];
        boxArr[0] = this.getX1() < this.getX2() ? this.getX1() : this.getX2(); //smaller x
        boxArr[1] = this.getY1() > this.getY2() ? this.getY1() : this.getY2(); //bigger y
        boxArr[2] = this.getX1() < this.getX2() ? this.getX2() - this.getX1() : this.getX1() - this.getX2(); // width = larger x - smaller x
        boxArr[3] = this.getY1() < this.getY2() ? this.getY2() - this.getY1() : this.getY1() - this.getY2(); // height = larger y - smaller x

        return boxArr;
    }

    /**
     * The variables of a line, 2 sets of x,y coordinates to indicate the two points that forms a line
     */

    /**
     * The method to get x coordinate of the first point from a line
     * @return          the x coordinate of the first point from a line
     */
    public double getX1() {
        return x1;
    }

    /**
     * set the x coordinate of the first point of a line
     * @param x1    the x coordinate that needed to be set
     */
    public void setX1(double x1) {
        this.x1 = x1;
    }

    /**
     * The method to get y coordinate of the first point from a line
     * @return          the y coordinate of the first point from a line
     */
    public double getY1() {
        return y1;
    }

    /**
     * set the y coordinate of the first point of a line
     * @param y1    the y coordinate that needed to be set
     */
    public void setY1(double y1) {
        this.y1 = y1;
    }

    /**
     * The method to get x coordinate of the second point from a line
     * @return          the x coordinate of the second point from a line
     */
    public double getX2() {
        return x2;
    }

    /**
     * set the x coordinate of the second point of a line
     * @param x2    the x coordinate that needed to be set
     */
    public void setX2(double x2) {
        this.x2 = x2;
    }

    /**
     * The method to get y coordinate of the second point from a line
     * @return          the y coordinate of the second point from a line
     */
    public double getY2() {
        return y2;
    }

    /**
     * set the y coordinate of the second point of a line
     * @param y2    the y coordinate that needed to be set
     */
    public void setY2(double y2) {
        this.y2 = y2;
    }
}


//======================[CIRCLE CLASS]=============================================================================


/**
 *
 * Circle class Shape, extended from class Shape
 *
 */

class  Circle extends Shape {

    private double x;
    private double y;
    private double r;

    /**
     *
     * @param name      The name of the shape
     * @param x         The x-coordinate of the circle's centre
     * @param y         The y-coordinate of the circle's centre
     * @param r         The radius of the circle
     */

    Circle(String name, double x, double y, double r) {
        super(name);
        SaveUndo(this,2);
        this.setX(x);
        this.setY(y);
        this.setR(r);
    }

    @Override
    public String getSubClass(){return "Circle";}
    @Override
    public double[] getData(){
        double[] ret = new double[3];
        ret[0] = this.getX();
        ret[1] = this.getY();
        ret[2] = this.getR();
        return ret;
    }
    @Override
    public void getInfo(int n){
        System.out.println("[Shape type: Circle] "+ " [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f", getX()) + "]  [y-coordinate: "+String.format("%.2f", getY())+"]  [radius: "+String.format("%.2f", getR())+"]");
    }
    @Override
    public void move(double x, double y){
        SaveUndo(this, 3);
        SaveMove(x, y);
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }

    @Override
    public double[] boundingbox() {
        double[] boxArr = new double[4];
        boxArr[0] = this.getX() - this.getR(); //upper left corner x
        boxArr[1] = this.getY() + this.getR(); //upper left corner y
        boxArr[2] = this.getR() * 2;  //width
        boxArr[3] = this.getR() * 2; // height

        return boxArr;
    }

    /**
     * The 3 parameters necessary for a circle
     */

    /**
     * @return          the x coordinate of the circle shape
     */
    public double getX() {
        return x;
    }

    /**
     * Set the x coordinate of a circle to a specific value
     * @param x             the x value that needed to be set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     *@return          the y coordinate of the circle shape
     */
    public double getY() {
        return y;
    }

    /**
     * Set the y coordinate of a circle to a specific value
     * @param y             the y value that needed to be set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     *@return          the radius of the circle shape
     */
    public double getR() {
        return r;
    }

    /**
     * Set the radius of a circle to a specific value
     * @param r             the radius value that needed to be set
     */
    public void setR(double r) {
        this.r = r;
    }
}


//==================[SQUARE CLASS]=================================================================================

/**
 * Square class shape, extended from class Shape
 */
class Square extends Shape {
    private double x;
    private double y;
    private double l;

    /**
     *
     * @param name      The name of the shape
     * @param x         The x-coordinate of the square's top left corner
     * @param y         The y-coordinate of the square's top left corner
     * @param l         The side length of the square
     */

    Square(String name, double x, double y, double l) {
        super(name);
        SaveUndo(this,2);
        this.setX(x);
        this.setY(y);
        this.setL(l);
    }
    @Override
    public Shape createShape(){
        Square ret = new Square(this.getName(), this.getX(), this.getY(), this.getL());
        return ret;
    }
    @Override
    public String getSubClass(){return "Square";}
    @Override
    public double[] getData(){
        double[] ret = new double[3];
        ret[0] = this.getX();
        ret[1] = this.getY();
        ret[2] = this.getL();
        return ret;
    }
    @Override
    public void getInfo(int n){
        System.out.println("[Shape type: Square] "+" [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f", getX()) + "]  [y-coordinate: "+String.format("%.2f", getY())+"]  [side length: "+String.format("%.2f", getL())+"]");
    }
    @Override
    public void move(double x, double y){
        SaveUndo(this, 3);
        SaveMove(x, y);
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }

    @Override
    public double[] boundingbox() {
        double[] boxArr = new double[4];
        boxArr[0] = this.getX();
        boxArr[1] = this.getY();
        boxArr[2] = this.getL();
        boxArr[3] = this.getL();

        return boxArr;
    }

    /**
     * The 3 parameters needed for a square
     */

    /**
     * a method that gets the x coordinate of the top left corner of a square
     * @return      the x coordinate of the top left corner of a square
     */
    public double getX() {
        return x;
    }

    /**
     * a method that sets the x coordinate of the top left corner of a square
     * @param x     the x coordinate that needed to be set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * a method that gets the y coordinate of the top left corner of a square
     * @return      the y coordinate of the top left corner of a square
     */
    public double getY() {
        return y;
    }

    /**
     * a method that sets the y coordinate of the top left corner of a square
     * @param y     the y coordinate that needed to be set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * a method that gets the length of a square
     * @return      the length of a square
     */
    public double getL() {
        return l;
    }

    /**
     * a method that sets the length of a square
     * @param l     the length that needed to be set
     */
    public void setL(double l) {
        this.l = l;
    }
}

//==================[GROUP CLASS]=====================================================================

/**
 * Group class, it is not exactly a shape itself
 * This class exists to facilitate the grouping of the normal shapes,
 * and the functions to be passed/recursed on the grouped shapes (e.g. moving a whole group of shapes)
 */

class Group extends Shape {
    private Shape[] s1;

    /**
     *
     * @param name      The name of the group
     * @param s1        The array of Shapes that are supposed to be grouped
     */

    Group(String name, Shape[] s1) {
        super(name);
        SaveUndo(this,5);
        this.setS1(s1);
        for (int i=0; i<s1.length; i++){
            this.getS1()[i].setGrouparent(this);
        }
    }

    @Override
    public String getSubClass(){return "Group";}
    @Override
    public double[] getData(){return new double[0];}
    @Override
    public void getInfo(int n) {
        System.out.println("[Type: Group] " + " [Group name: " + this.getName() + "]");
        //System.out.println("previous: "+ this.previous + " next: "+ this.next + " GP: "+ this.grouparent);
        for(int i = 0; i< getS1().length; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("\t");
            }
            getS1()[i].getInfo(n + 1);
        }
    }

    @Override
    public double[] boundingbox() {
        double[] s1boxArr = new double[4];
        int i, j;
        for (i=0; i<4; i++){
            s1boxArr[i] = getS1()[0].boundingbox()[i];
        }
        double[] s2boxArr = new double[4];
        double[] result = new double[4];
        for(i=1; i< getS1().length; i++) {
            for (j=0; j<4; j++){
                s2boxArr[j] = getS1()[i].boundingbox()[j];
            }
            if (s1boxArr[0] < s2boxArr[0]) {
                result[0] = s1boxArr[0];
                if (s1boxArr[0] + s1boxArr[2] > s2boxArr[0] + s2boxArr[2]) {
                    result[2] = s1boxArr[2];
                } else {
                    result[2] = s2boxArr[0] - s1boxArr[0] + s2boxArr[2];
                }
            } else {
                result[0] = s2boxArr[0];
                if (s2boxArr[0] + s2boxArr[2] > s1boxArr[0] + s1boxArr[2]) {
                    result[2] = s2boxArr[2];
                } else {
                    result[2] = s1boxArr[0] - s2boxArr[0] + s1boxArr[2];
                }
            }
            if (s1boxArr[1] > s2boxArr[1]) {
                result[1] = s1boxArr[1];
                if (s1boxArr[1] - s1boxArr[3] < s2boxArr[1] - s2boxArr[3]) {
                    result[3] = s1boxArr[3];
                } else {
                    result[3] = s1boxArr[1] - s2boxArr[1] + s2boxArr[3];
                }
            } else {
                result[1] = s2boxArr[1];
                if (s2boxArr[1] - s2boxArr[3] < s1boxArr[1] - s1boxArr[3]) {
                    result[3] = s2boxArr[3];
                } else {
                    result[3] = s2boxArr[1] - s1boxArr[1] + s1boxArr[3];
                }
            }
            for (j=0; j<4; j++){
                s1boxArr[j] = result[j];
            }
        }
        return result;
    }

    @Override
    public void delete() {
        SaveUndo(this,1);
        if (this.getGrouparent() == null) {
            gdelete();
        } else {
            System.out.println("Deleting a component shape of a group is invalid!");
        }
    }

    @Override
    public void move(double x, double y) {
        SaveUndo(this,3);
        SaveMove(x,y);
        for (int i = 0; i< getS1().length; i++){
            getS1()[i].move(x, y);
            PopUndo();
            PopMove();
        }
    }

    @Override
    public void ungroup() {
        SaveUndo(this, 4);
        for (int i = 0; i< getS1().length; i++){
            getS1()[i].setGrouparent(null);
        }
        gdelete();
    }

    private void gdelete() {
        if (this.getPrevious() == null && this.getNext() != null) {
            this.getNext().setPrevious(null);
        } else if (this.getNext() == null && this.getPrevious() != null) {
            setCur(this.getPrevious());
        } else if (this.getPrevious() != null) {
            this.getNext().setPrevious(this.getPrevious());
        } else {
            setCur(null);
        }
        if (getCur() == this) {
            setCur(this.getPrevious());
        }
    }

    @Override
    public void undelete() {
        SaveUndo(this,2);
        if (getCur() == null) {
            setCur(this);
        }   else if (this.getPrevious() == null && this.getNext() != null) {
            this.getNext().setPrevious(this);
        } else {
            setCur(this);
        }
    }

    @Override
    public void regroup() {
        SaveUndo(this, 5);
        undelete();
        PopUndo();
        for (int i = 0; i< getS1().length; i++){
            this.getS1()[i].setGrouparent(this);
        }
    }

    /**
     * The array to point to all existing children shapes that are commanded to be grouped together
     */

    /**
     * @return              the array of shapes contained inside the group of shapes
     */
    public Shape[] getS1() {
        return s1;
    }

    /**
     * setting the array of shapes
     * @param s1            the array of shapes that needed to be set
     */
    public void setS1(Shape[] s1) {
        this.s1 = s1;
    }
}
