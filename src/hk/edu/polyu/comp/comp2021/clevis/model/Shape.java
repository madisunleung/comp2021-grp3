package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.Stack;


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
    /* public static boolean intersect(Shape n1, Shape n2){
        Shape[] temp1 = new subShapes(n1);
        Shape[] temp2 = new subShapes(n2);
        Shape temp = cur;
        while(temp != null){
            if(temp instanceof Group == false) {
                temp.getInfo(1);
            }
            temp = temp.previous;
        }
        return true;
    }

    public static Shape[] subShapes(Shape n){

    }*/
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
    /**
     * The 4 parameters needed for constructing a rectangle
     */
    protected double x;
    /**
     *
     */
    protected double y;
    /**
     *
     */
    protected double w;
    /**
     *
     */
    protected double h;

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
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    @Override
    public void getInfo(int n){
        System.out.println("[Shape type: Rectangle] " + " [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f",x) + "]  [y-coordinate: "+String.format("%.2f",y)+"]  [width: "+String.format("%.2f",w)+"]  [height: "+String.format("%.2f",h)+"]");
    }
    @Override
    public void move(double x, double y){
        SaveUndo(this, 3);
        SaveMove(x, y);
        this.x = this.x + x;
        this.y = this.y + y;
    }

    @Override
    public double[] boundingbox() {
        double[] boxArr = new double[4];
        boxArr[0] = this.x;
        boxArr[1] = this.y;
        boxArr[2] = this.w;
        boxArr[3] = this.h;

        return boxArr;
    }
}


//===============[LINE CLASS]======================================================================================

/**
 * Line class shape, extended from Shape class
 */

class Line extends Shape {
    /**
     * The variables of a line, 2 sets of x,y coordinates to indicate the two points that forms a line
     */
    protected double x1;
    /**
     *
     */
    protected double y1;
    /**
     *
     */
    protected double x2;
    /**
     *
     */
    protected double y2;

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
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    @Override
    public void getInfo(int n){
        System.out.println("[Shape type: Line] "+ "  [Shape name: "+this.getName()+"]  [x-coordinate 1: "+ String.format("%.2f",x1) + "]  [y-coordinate 1: "+String.format("%.2f",y1)+"]  [x-coordinate 2: "+String.format("%.2f",x2)+"]  [y-coordinate 2: "+String.format("%.2f",y2)+"]");
    }

    @Override
    public void move(double x, double y){
        SaveUndo(this, 3);
        SaveMove(x, y);
        this.x1 = this.x1 + x;
        this.x2 = this.x2 + x;
        this.y1 = this.y1 + y;
        this.y2 = this.y2 + y;
    }

    @Override
    public double[] boundingbox() {
        double[] boxArr = new double[4];
        boxArr[0] = this.x1 < this.x2 ? this.x1 : this.x2; //smaller x
        boxArr[1] = this.y1 > this.y2 ? this.y1 : this.y2; //bigger y
        boxArr[2] = this.x1 < this.x2 ? this.x2 - this.x1 : this.x1 - this.x2; // width = larger x - smaller x
        boxArr[3] = this.y1 < this.y2 ? this.y2 - this.y1 : this.y1 - this.y2; // height = larger y - smaller x

        return boxArr;
    }
}


//======================[CIRCLE CLASS]=============================================================================


/**
 *
 * Circle class Shape, extended from class Shape
 *
 */

class  Circle extends Shape {

    /**
     * The 3 parameters necessary for a circle
     */
    protected double x;
    /**
     *
     */
    protected double y;
    /**
     *
     */
    protected double r;

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
        this.x = x;
        this.y = y;
        this.r = r;
    }
    @Override
    public void getInfo(int n){
        System.out.println("[Shape type: Circle] "+ " [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f",x) + "]  [y-coordinate: "+String.format("%.2f",y)+"]  [radius: "+String.format("%.2f",r)+"]");
    }
    @Override
    public void move(double x, double y){
        SaveUndo(this, 3);
        SaveMove(x, y);
        this.x = this.x + x;
        this.y = this.y + y;
    }

    @Override
    public double[] boundingbox() {
        double[] boxArr = new double[4];
        boxArr[0] = this.x - this.r; //upper left corner x
        boxArr[1] = this.y + this.r; //upper left corner y
        boxArr[2] = this.r * 2;  //width
        boxArr[3] = this.r * 2; // height

        return boxArr;
    }
}


//==================[SQUARE CLASS]=================================================================================

/**
 * Square class shape, extended from class Shape
 */
class Square extends Shape {
    /**
     * The 3 parameters needed for a square
     */
    protected double x;
    /**
     *
     */
    protected double y;
    /**
     *
     */
    protected double l;

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
        this.x = x;
        this.y = y;
        this.l = l;
    }
    @Override
    public void getInfo(int n){
        System.out.println("[Shape type: Square] "+" [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f",x) + "]  [y-coordinate: "+String.format("%.2f",y)+"]  [side length: "+String.format("%.2f",l)+"]");
    }
    @Override
    public void move(double x, double y){
        SaveUndo(this, 3);
        SaveMove(x, y);
        this.x = this.x + x;
        this.y = this.y + y;
    }

    @Override
    public double[] boundingbox() {
        double[] boxArr = new double[4];
        boxArr[0] = this.x;
        boxArr[1] = this.y;
        boxArr[2] = this.l;
        boxArr[3] = this.l;

        return boxArr;
    }
}

//==================[GROUP CLASS]=====================================================================

/**
 * Group class, it is not exactly a shape itself
 * This class exists to facilitate the grouping of the normal shapes,
 * and the functions to be passed/recursed on the grouped shapes (e.g. moving a whole group of shapes)
 */

class Group extends Shape {
    /**
     * The array to point to all existing children shapes that are commanded to be grouped together
     */
    protected Shape[] s1;

    /**
     *
     * @param name      The name of the group
     * @param s1        The array of Shapes that are supposed to be grouped
     */

    Group(String name, Shape[] s1) {
        super(name);
        SaveUndo(this,5);
        this.s1 = s1;
        for (int i=0; i<s1.length; i++){
            this.s1[i].setGrouparent(this);
        }
    }

    @Override
    public void getInfo(int n) {
        System.out.println("[Type: Group] " + " [Group name: " + this.getName() + "]");
        //System.out.println("previous: "+ this.previous + " next: "+ this.next + " GP: "+ this.grouparent);
         for(int i=0; i<s1.length; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("\t");
            }
            s1[i].getInfo(n + 1);
        }
    }

    @Override
    public double[] boundingbox() {
        double[] s1boxArr = new double[4];
        int i, j;
        for (i=0; i<4; i++){
            s1boxArr[i] = s1[0].boundingbox()[i];
        }
        double[] s2boxArr = new double[4];
        double[] result = new double[4];
        for(i=1; i<s1.length; i++) {
            for (j=0; j<4; j++){
                s2boxArr[j] = s1[i].boundingbox()[j];
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
        for (int i=0; i<s1.length; i++){
            s1[i].move(x, y);
            PopUndo();
            PopMove();
        }
    }

    @Override
    public void ungroup() {
        SaveUndo(this, 4);
        for (int i=0; i<s1.length; i++){
            s1[i].setGrouparent(null);
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
        for (int i=0; i<s1.length; i++){
            this.s1[i].setGrouparent(this);
        }
    }

}
