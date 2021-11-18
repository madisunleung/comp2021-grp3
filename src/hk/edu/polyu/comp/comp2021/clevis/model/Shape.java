package hk.edu.polyu.comp.comp2021.clevis.model;

import org.w3c.dom.css.Rect;
import java.util.ArrayList;
import java.util.Arrays;

public class Shape {
    /**                 Basic Settings              **/
    private final String name;
    Shape next;
    Shape previous;
    Shape grouparent = null;

    public static Shape cur=null;
    public static Shape head= null;

    public Shape(String name) {
        // DON'T FORGET VALIDATION
        // Should we consider that names of shapes should not be duplicated
        // Already implemented to check duplicated existing names of shapes, in Clevis.java using a boolean function nameNotUsed()

        this.name = name;
    }

    public static void addShape(Shape a){
        if (cur == null){
            head = a;
        }
        if(cur!=null){
            cur.next=a;
            a.previous=cur;
        }
        cur = a;
    }

    public String getName() {
        return name;
    }

    public void getInfo(int n){}

    public void move(double x, double y){}

    public double[] boundingbox(){
        return new double[4];
    }

    public void ungroup(){}

    public static Shape findAShape(String name) {        //finds and returns the shape with a name
        Shape temp = cur;
        while (temp != null) {
            if (temp.name.equals(name)) {
                break;
            }
            temp = temp.previous;
        }
        return temp;
    }

    public Shape belongToGroup(){
        return grouparent;
    }
    /**-----------------[Intersect related methods]------------------------------------------------------------**/
    public static boolean intersect(Shape n1, Shape n2){
        ArrayList<Shape> list1 = subShapes(n1);
        ArrayList<Shape> list2 = subShapes(n2);
        Shape[] temp1 = list1.toArray(new Shape[list1.size()]);
        Shape[] temp2 = list2.toArray(new Shape[list2.size()]);

        for(int i=0; i<temp1.length; i++){
            for(int j=0; j<temp2.length; j++){
                if (containspoint(temp1[i], temp2[j])){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containspoint(Shape temp, Shape temp1){
        boolean flag = false;
        Line p1, p2, p3, p4;
        if (temp instanceof Square){
            temp = new Rectangle("test", ((Square) temp).x, ((Square) temp).y, ((Square) temp).l, ((Square) temp).l);
        }
        if (temp instanceof Rectangle){
            p1 = new Line("p1", ((Rectangle) temp).x, ((Rectangle) temp).y, ((Rectangle) temp).x+ ((Rectangle) temp).w, ((Rectangle) temp).y);
            p2 = new Line("p2", ((Rectangle) temp).x, ((Rectangle) temp).y- ((Rectangle) temp).h, ((Rectangle) temp).x+ ((Rectangle) temp).w, ((Rectangle) temp).y- ((Rectangle) temp).h);
            p3 = new Line("p3", ((Rectangle) temp).x, ((Rectangle) temp).y, ((Rectangle) temp).x, ((Rectangle) temp).y- ((Rectangle) temp).h);
            p4 = new Line("p4", ((Rectangle) temp).x+ ((Rectangle) temp).w, ((Rectangle) temp).y, ((Rectangle) temp).x+ ((Rectangle) temp).w, ((Rectangle) temp).y- ((Rectangle) temp).h);
            flag = (containspoint(p1, temp1) || containspoint(p2, temp1) || containspoint(p3, temp1) || containspoint(p4, temp1));
        }
        if (temp1 instanceof Square){
            temp1 = new Rectangle("test", ((Square) temp1).x, ((Square) temp1).y, ((Square) temp1).l, ((Square) temp1).l);
        }
        if (temp1 instanceof Rectangle){
            p1 = new Line("p1", ((Rectangle) temp1).x, ((Rectangle) temp1).y, ((Rectangle) temp1).x+ ((Rectangle) temp1).w, ((Rectangle) temp1).y);
            p2 = new Line("p2", ((Rectangle) temp1).x, ((Rectangle) temp1).y- ((Rectangle) temp1).h, ((Rectangle) temp1).x+ ((Rectangle) temp1).w, ((Rectangle) temp1).y- ((Rectangle) temp1).h);
            p3 = new Line("p3", ((Rectangle) temp1).x, ((Rectangle) temp1).y, ((Rectangle) temp1).x, ((Rectangle) temp1).y- ((Rectangle) temp1).h);
            p4 = new Line("p4", ((Rectangle) temp1).x+ ((Rectangle) temp1).w, ((Rectangle) temp1).y, ((Rectangle) temp1).x+ ((Rectangle) temp1).w, ((Rectangle) temp1).y- ((Rectangle) temp1).h);
            flag = (containspoint(temp, p1) || containspoint(temp, p2) || containspoint(temp, p3) || containspoint(temp, p4));
        }
        if (temp instanceof Circle && temp1 instanceof Circle){
            flag = check2circle((Circle) temp, (Circle) temp1);
        }
        else if (temp instanceof Circle && temp1 instanceof Line){
            flag = checkCircleLine((Circle) temp, (Line) temp1);
        }
        else if (temp instanceof Line && temp1 instanceof Circle){
            flag = checkCircleLine((Circle) temp1, (Line) temp);
        }
        else if (temp instanceof Line && temp1 instanceof Line){
            flag = check2Line((Line) temp, (Line) temp1);
        }
        return flag;
    }

    public static boolean check2circle(Circle n1, Circle n2){
        double num = pythegorean(n1.x, n1.y, n2.x, n2.y);
        if (num < Math.abs(n1.r - n2.r) || num > (n1.r + n2.r)){
            return false;
        }
        else{
            return true;
        }
    }
    public static boolean checkCircleLine(Circle n1, Line n2){
        double x1Intersect, y1Intersect, x2Intersect, y2Intersect;
        if (Math.abs(n2.x2 - n2.x1) == 0){
            if (Math.abs(n2.x1 - n1.x) > n1.r){
                return false;
            }
            else if (Math.abs(n2.x1 - n1.x) == n1.r){
                x1Intersect = n2.x1;
                y1Intersect = n1.y;
                if (Math.abs(pythegorean(n2.x1, n2.y1, x1Intersect, y1Intersect)) > Math.abs(pythegorean(n2.x1, n2.y1, n2.x2, n2.y2))
                        || Math.abs(pythegorean(n2.x2, n2.y2, x1Intersect, y1Intersect)) > Math.abs(pythegorean(n2.x1, n2.y1, n2.x2, n2.y2))){
                    return false;
                }
                return true;
            }
            else{
                x1Intersect = n2.x1;
                x2Intersect = n2.x1;
                double a = 1;
                double b = -2*n1.y;
                double c = Math.pow(x1Intersect-n1.x,2) + Math.pow(n1.y,2) - Math.pow(n1.r,2);
                y1Intersect = (-b + Math.sqrt(Math.pow(b,2) - 4*a*c)) / (2*a);
                y2Intersect = (-b - Math.sqrt(Math.pow(b,2) - 4*a*c)) / (2*a);
                if (Math.abs(pythegorean(n2.x1, n2.y1, x1Intersect, y1Intersect)) > Math.abs(pythegorean(n2.x1, n2.y1, n2.x2, n2.y2))
                        || Math.abs(pythegorean(n2.x2, n2.y2, x1Intersect, y1Intersect)) > Math.abs(pythegorean(n2.x1, n2.y1, n2.x2, n2.y2))){
                    if (Math.abs(pythegorean(n2.x1, n2.y1, x2Intersect, y2Intersect)) > Math.abs(pythegorean(n2.x1, n2.y1, n2.x2, n2.y2))
                            || Math.abs(pythegorean(n2.x2, n2.y2, x2Intersect, y2Intersect)) > Math.abs(pythegorean(n2.x1, n2.y1, n2.x2, n2.y2))){
                        return false;
                    }
                }
                return true;
            }
        }
        else{
            double m = (n2.y2 - n2.y1) / (n2.x2 - n2.x1);
            double temp = n2.y1 - m*n2.x1 - n1.y;
            double a = Math.pow(m,2) + 1;
            double b = 2*m*temp - 2*n1.x;
            double c = Math.pow(n1.x,2) + Math.pow(temp,2) - Math.pow(n1.r,2);
            double determinant = Math.pow(b,2) - (4*a*c);
            if (determinant < 0){
                return false;
            }
            else if (determinant == 0){
                x1Intersect = (-b) / (2*a);
                y1Intersect = m*x1Intersect + n2.y1 - m*n2.x1;
                if (Math.abs(pythegorean(n1.x, n1.y, x1Intersect, y1Intersect) - n1.r) < 0.00001){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                x1Intersect = (-b + Math.sqrt(determinant)) / (2*a);
                y1Intersect = m*x1Intersect + n2.y1 - m*n2.x1;
                x2Intersect = (-b - Math.sqrt(determinant)) / (2*a);
                y2Intersect = m*x2Intersect + n2.y1 - m*n2.x1;
                if (Math.abs(pythegorean(n2.x1, n2.y1, x1Intersect, y1Intersect)) > Math.abs(pythegorean(n2.x1, n2.y1, n2.x2, n2.y2))
                || Math.abs(pythegorean(n2.x2, n2.y2, x1Intersect, y1Intersect)) > Math.abs(pythegorean(n2.x1, n2.y1, n2.x2, n2.y2))){
                    if (Math.abs(pythegorean(n2.x1, n2.y1, x2Intersect, y2Intersect)) > Math.abs(pythegorean(n2.x1, n2.y1, n2.x2, n2.y2))
                            || Math.abs(pythegorean(n2.x2, n2.y2, x2Intersect, y2Intersect)) > Math.abs(pythegorean(n2.x1, n2.y1, n2.x2, n2.y2))){
                        return false;
                    }
                }
                return true;
            }
        }
    }
    public static boolean check2Line (Line n1, Line n2){
        double xIntersect, yIntersect;
        if (n1.x2-n1.x1 == 0 && n2.x2-n2.x1 == 0){
            if (n1.x1 != n2.x1){
                return false;
            }
            else if(Math.abs(n1.y1 - n2.y1) > Math.abs(n2.y1 - n2.y2) || Math.abs(n1.y1 - n2.y2) > Math.abs(n2.y1 - n2.y2)){
                if(Math.abs(n1.y2 - n2.y1) > Math.abs(n2.y1 - n2.y2) || Math.abs(n1.y2 - n2.y2) > Math.abs(n2.y1 - n2.y2)){
                    return false;
                }
            }
            return true;
        }
        if (n1.x2-n1.x1 == 0){
            xIntersect = n1.x1;
            double m2 = (n2.y2-n2.y1)/(n2.x2-n2.x1);
            yIntersect = n2.y1-m2*n2.x1 - (-m2*xIntersect);
            if(Math.abs(yIntersect-n1.y1) > Math.abs(n1.y1-n1.y2) || Math.abs(yIntersect-n1.y2) > Math.abs(n1.y1-n1.y2)){
                return false;
            }
            else if(pythegorean(xIntersect,yIntersect,n2.x1,n2.y1) > pythegorean(n2.x1,n2.y1,n2.x2,n2.y2)
                || pythegorean(xIntersect,yIntersect,n2.x2,n2.y2) > pythegorean(n2.x1,n2.y1,n2.x2,n2.y2)){
                return false;
            }
            else{
                return true;
            }
        }
        else if (n2.x2-n2.x1 == 0){
            xIntersect = n2.x1;
            double m = (n1.y2-n1.y1)/(n1.x2-n1.x1);
            yIntersect = n1.y1-m*n1.x1 - (-m*xIntersect);
            if(Math.abs(yIntersect-n2.y1) > Math.abs(n2.y1-n2.y2) || Math.abs(yIntersect-n2.y2) > Math.abs(n2.y1-n2.y2)){
                return false;
            }
            else if(pythegorean(xIntersect,yIntersect,n1.x1,n1.y1) > pythegorean(n1.x1,n1.y1,n1.x2,n1.y2)
                    || pythegorean(xIntersect,yIntersect,n1.x2,n1.y2) > pythegorean(n1.x1,n1.y1,n1.x2,n1.y2)){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            double m = (n1.y2-n1.y1)/(n1.x2-n1.x1); //slope
            double m2 = (n2.y2-n2.y1)/(n2.x2-n2.x1); //another slope
            double a = -m, b = 1, c = n1.y1-m*n1.x1;    //ax + by = c
            double d = -m2, e = 1, f = n2.y1-m2*n2.x1;  //dx + ey = f
            //solving 2 equations with 2 unknowns (with matrix)
            double temp = b*d - a*e;
            if (m == m2){
                if (c != f){
                    return false;
                }
                else if(pythegorean(n1.x1, n1.y1, n2.x1, n2.y1) > pythegorean(n2.x1, n2.y1, n2.x2, n2.y2) ||
                        pythegorean(n1.x1, n1.y1, n2.x2, n2.y2) > pythegorean(n2.x1, n2.y1, n2.x2, n2.y2)){
                    if(pythegorean(n1.x2, n1.y2, n2.x1, n2.y1) > pythegorean(n2.x1, n2.y1, n2.x2, n2.y2) ||
                            pythegorean(n1.x2, n1.y2, n2.x2, n2.y2) > pythegorean(n2.x1, n2.y1, n2.x2, n2.y2)){
                        return false;
                    }
                }
                return true;
            }
            xIntersect = (b*f - c*e) / temp;
            yIntersect = (c*d - a*f) / temp;
            if(pythegorean(xIntersect,yIntersect,n1.x1,n1.y1) > pythegorean(n1.x1,n1.y1,n1.x2,n1.y2)
                    || pythegorean(xIntersect,yIntersect,n1.x2,n1.y2) > pythegorean(n1.x1,n1.y1,n1.x2,n1.y2)
                    || pythegorean(xIntersect,yIntersect,n2.x1,n2.y1) > pythegorean(n2.x1,n2.y1,n2.x2,n2.y2)
                    || pythegorean(xIntersect,yIntersect,n2.x2,n2.y2) > pythegorean(n2.x1,n2.y1,n2.x2,n2.y2)){
                return false;
            }
            else{
                return true;
            }
        }
    }
    public static ArrayList<Shape> subShapes(Shape n){
        Shape temp = cur;
        ArrayList<Shape> ret = new ArrayList<>();
        if (n instanceof Group){
            for (int i=0; i<((Group) n).s1.length; i++){
                if (((Group) n).s1[i] instanceof Group){
                    ret.addAll(subShapes(((Group) n).s1[i]));
                }
                else{
                    ret.add(((Group) n).s1[i]);
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
    public static void pickandmove(double x, double y, double dx, double dy){
        Shape temp = cur;
        while (temp != null) {
            if (containspoint(temp, x, y)) {
                break;
            }
            temp = temp.previous;
        }
        if (temp == null){
            System.out.println("There isn't any shape satisfying the parameters.");
            return;
        }
        else{
            while (temp.grouparent != null){
                temp = temp.grouparent;
            }
            temp.move(dx, dy);
        }
    }

    public static boolean containspoint(Shape temp, double x, double y){
        Shape temp2 = new Shape("test");
        boolean flag = false;
        if (temp instanceof Group){
            for (int i=0; i< ((Group) temp).s1.length; i++) {
                flag = (flag || containspoint(((Group) temp).s1[i], x, y));
            }
            return flag;
        }
        else if (temp instanceof Circle){
            flag = minDistance(((Circle) temp).x, ((Circle) temp).y, x, y, ((Circle) temp).r);
        }
        else if (temp instanceof Rectangle){
            temp2 = (Rectangle) temp;
        }
        else if (temp instanceof Square){
            temp2 = new Rectangle("test", ((Square) temp).x, ((Square) temp).y, ((Square) temp).l, ((Square) temp).l);
        }
        else if (temp instanceof Line){
            flag = minDistance(((Line) temp).x1, ((Line) temp).y1, ((Line) temp).x2, ((Line) temp).y2, x, y);
        }
        if (temp2 instanceof Rectangle){
            Line p1 = new Line("p1", ((Rectangle) temp2).x, ((Rectangle) temp2).y, ((Rectangle) temp2).x+ ((Rectangle) temp2).w, ((Rectangle) temp2).y);
            Line p2 = new Line("p2", ((Rectangle) temp2).x, ((Rectangle) temp2).y- ((Rectangle) temp2).h, ((Rectangle) temp2).x+ ((Rectangle) temp2).w, ((Rectangle) temp2).y- ((Rectangle) temp2).h);
            Line p3 = new Line("p3", ((Rectangle) temp2).x, ((Rectangle) temp2).y, ((Rectangle) temp2).x, ((Rectangle) temp2).y- ((Rectangle) temp2).h);
            Line p4 = new Line("p4", ((Rectangle) temp2).x+ ((Rectangle) temp2).w, ((Rectangle) temp2).y, ((Rectangle) temp2).x+ ((Rectangle) temp2).w, ((Rectangle) temp2).y- ((Rectangle) temp2).h);
            flag = (containspoint(p1, x, y) || containspoint(p2, x, y) || containspoint(p3, x, y) || containspoint(p4, x, y));
        }
        return flag;
    }

    //Circle minDistance
    public static boolean minDistance(double x, double y, double dx, double dy, double r){
        double distance = pythegorean(x, y, dx, dy);
        if (Math.abs(distance - r) < 0.05){
            return true;
        }
        return false;
    }

    //Rectangle, Square and Line minDistance
    public static boolean minDistance(double x1, double y1, double x2, double y2, double dx, double dy){
        double xIntersect, yIntersect, distance;
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
        if (distance < 0.05){
            return true;
        }
        else{
            return false;
        }
    }
    public static double pythegorean(double x, double y, double dx, double dy){
        double xDif = x-dx;
        double yDif = y-dy;
        return Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
    }
    /**-----------------[Delete related methods]------------------------------------------------------------**/

    public static boolean delete(String name){     //delete function prototype part 1
        Shape target = findAShape(name);
        if(target != null) {
            if(target.grouparent == null) {
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

    public void delete() {      //delete function prototype part 2 (v2)
        if (this.previous == null && this.next != null) {
            this.next.previous = null;
        } else if (this.next == null && this.previous != null) {
            cur = this.previous;
        } else if (this.previous != null) {
            this.next.previous = this.previous;
        } else {
            cur = null;
        }
        if(cur == this){
            cur = this.previous;
        }
    }

    /**---------------[List related methods]------------------------------------------------------------**/

    public static void ListTest(){      //Lists every single shape, from the newest to the oldest
        Shape temp = cur;
        while(temp != null){
            if(temp.belongToGroup() == null) {
                temp.getInfo(1);
            }
            temp = temp.previous;
        }
    }
    public static void ListFromHead(){   //might have to use this in redo steps, if you are having questions please ask Leo
        Shape temp = head;
        while (temp != null){
            temp.getInfo(1);
            temp = temp.next;
        }
    }

}


//===============[RECTANGLE CLASS]==========================================================================


class Rectangle extends Shape {
    public double x, y, w, h;

    Rectangle(String name, double x, double y, double w, double h) {
        super(name);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    public void getInfo(int n){
        System.out.println("[Shape type: Rectangle] " + " [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f",x) + "]  [y-coordinate: "+String.format("%.2f",y)+"]  [width: "+String.format("%.2f",w)+"]  [height: "+String.format("%.2f",h)+"]");
    }
    public void move(double x, double y){
        this.x = this.x + x;
        this.y = this.y + y;
    }

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


class Line extends Shape {
    public double x1, y1, x2, y2;

    Line(String name, double x1, double y1, double x2, double y2) {
        super(name);
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    public void getInfo(int n){
        System.out.println("[Shape type: Line] "+ "  [Shape name: "+this.getName()+"]  [x-coordinate 1: "+ String.format("%.2f",x1) + "]  [y-coordinate 1: "+String.format("%.2f",y1)+"]  [x-coordinate 2: "+String.format("%.2f",x2)+"]  [y-coordinate 2: "+String.format("%.2f",y2)+"]");
    }

    public void move(double x, double y){
        this.x1 = this.x1 + x;
        this.x2 = this.x2 + x;
        this.y1 = this.y1 + y;
        this.y2 = this.y2 + y;
    }

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


class Circle extends Shape {
    public double x, y, r;

    Circle(String name, double x, double y, double r) {
        super(name);
        this.x = x;
        this.y = y;
        this.r = r;
    }
    public void getInfo(int n){
        System.out.println("[Shape type: Circle] "+ " [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f",x) + "]  [y-coordinate: "+String.format("%.2f",y)+"]  [radius: "+String.format("%.2f",r)+"]");
    }
    public void move(double x, double y){
        this.x = this.x + x;
        this.y = this.y + y;
    }

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


class Square extends Shape {
    public double x, y, l;

    Square(String name, double x, double y, double l) {
        super(name);
        this.x = x;
        this.y = y;
        this.l = l;
    }
    public void getInfo(int n){
        System.out.println("[Shape type: Square] "+" [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f",x) + "]  [y-coordinate: "+String.format("%.2f",y)+"]  [side length: "+String.format("%.2f",l)+"]");
    }
    public void move(double x, double y){
        this.x = this.x + x;
        this.y = this.y + y;
    }

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

class Group extends Shape {
    public Shape[] s1;

    Group(String name, Shape[] s1) {
        super(name);
        this.s1 = s1;
        for (int i=0; i<s1.length; i++){
            this.s1[i].grouparent = this;
        }
    }

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

    public void delete() {
        if (this.grouparent == null) {
            for (int i=0; i<s1.length; i++){
                s1[i].delete();
            }
            gdelete();
        } else {
            System.out.println("Deleting a component shape of a group is invalid!");
        }
    }

    public void move(double x, double y) {
        for (int i=0; i<s1.length; i++){
            s1[i].move(x, y);
        }
    }

    public void ungroup() {
        for (int i=0; i<s1.length; i++){
            s1[i].grouparent = null;
        }
        gdelete();
    }

    private void gdelete() {
        if (this.previous == null && this.next != null) {
            this.next.previous = null;
        } else if (this.next == null && this.previous != null) {
            cur = this.previous;
        } else if (this.previous != null) {
            this.next.previous = this.previous;
        } else {
            cur = null;
        }
        if (cur == this) {
            cur = this.previous;
        }
    }

}
