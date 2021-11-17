package hk.edu.polyu.comp.comp2021.clevis.model;

import org.w3c.dom.css.Rect;

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
