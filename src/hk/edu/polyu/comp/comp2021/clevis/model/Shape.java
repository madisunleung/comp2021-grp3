package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.Objects;

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
        // or its actually stated idk my brain is empty
        //whatever I'll leave the method here cuz I'm bored

        this.name = name;
        if (cur == null){
            head = this;
        }
        if(cur!=null){
            cur.next=this;
            this.previous=cur;
        }
        cur = this;
    }



    public String getName() {
        return name;
    }

    public void getInfo(){}

    public void move(double x, double y){}

    public void ungroup(){}

    public static Shape findAShape(String name) {        //finds and returns the shape with a name
        Shape temp = cur;
        while (temp != null) {
            if (temp.name.equals(name)) {
                break;
            }
            temp = temp.previous;
        }
        if (temp == null) {
            System.out.println("No shape with such name is found.");
        }
        return temp;
    }

    public Shape belongToGroup(){
        return grouparent;
    }

    /**-----------------[Delete related methods]------------------------------------------------------------**/

    public static void delete(String name){     //delete function prototype part 1
        Shape target = findAShape(name);
        if(target == null) return;
        else target.delete();
    }

    public void delete() {      //delete function prototype part 2 (v2)
        if (this.previous == null && this.next != null) {
            this.next.previous = null;
        } else if (this.next == null && this.previous != null) {
            cur = this.previous;
        } else if (this.previous != null && this.previous != null) {
            this.next.previous = this.previous;
        } else if (this.previous == null && this.next == null) {
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
                temp.getInfo();
            }
            temp = temp.previous;
        }
    }
    public static void ListFromHead(){   //might have to use this in redo steps, if you are having questions please ask Leo
        Shape temp = head;
        while (temp != null){
            temp.getInfo();
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
    public void getInfo(){
        System.out.println("[Shape type: Rectangle] " + " [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f",x) + "]  [y-coordinate: "+String.format("%.2f",y)+"]  [width: "+String.format("%.2f",w)+"]  [height: "+String.format("%.2f",h)+"]");
    }
    public void move(double x, double y){
        this.x = this.x + x;
        this.y = this.y + y;
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
    public void getInfo(){
        System.out.println("[Shape tpye: Line] "+ "]  [Shape name: "+this.getName()+"]  [x-coordinate 1: "+ String.format("%.2f",x1) + "]  [y-coordinate 1: "+String.format("%.2f",y1)+"]  [x-coordinate 2: "+String.format("%.2f",x2)+"]  [y-coordinate 2: "+String.format("%.2f",y2)+"]");
    }
    public void move(double x, double y){
        this.x1 = this.x1 + x;
        this.x2 = this.x2 + x;
        this.y1 = this.y1 + y;
        this.y2 = this.y2 + y;
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
    public void getInfo(){
        System.out.println("[Shape type: Circle] "+ " [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f",x) + "]  [y-coordinate: "+String.format("%.2f",y)+"]  [radius: "+String.format("%.2f",r)+"]");
    }
    public void move(double x, double y){
        this.x = this.x + x;
        this.y = this.y + y;
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
    public void getInfo(){
        System.out.println("[Shape type: Square] "+" [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f",x) + "]  [y-coordinate: "+String.format("%.2f",y)+"]  [side length: "+String.format("%.2f",l)+"]");
    }
    public void move(double x, double y){
        this.x = this.x + x;
        this.y = this.y + y;
    }
}

//==================[GROUP CLASS]=====================================================================

class Group extends Shape {
    public Shape s1, s2;

    Group(String name, Shape s1, Shape s2) {
        super(name);
        this.s1 = s1;
        this.s2 = s2;
        this.s1.grouparent = this;
        this.s2.grouparent = this;
    }

    public void getInfo() {
        System.out.println("[Type: Group] " + " [Group name: " + this.getName()+"]");
        System.out.println("previous: "+ this.previous + " next: "+ this.next + " GP: "+ this.grouparent);
        System.out.print("\t");
        s1.getInfo();
        System.out.print("\t");
        s2.getInfo();
    }

    public void delete() {
        s1.delete();
        s2.delete();
        gdelete();
    }

    public void move(double x, double y) {
        s1.move(x, y);
        s2.move(x, y);
    }

    public void ungroup(){
        s1.grouparent = null;
        s2.grouparent = null;
        gdelete();
    }

    private void gdelete(){
        if (this.previous == null && this.next != null) {
            this.next.previous = null;
        } else if (this.next == null && this.previous != null) {
            cur = this.previous;
        } else if (this.previous != null && this.previous != null) {
            this.next.previous = this.previous;
        } else if (this.previous == null && this.next == null) {
            cur = null;
        }
        if(cur == this){
            cur = this.previous;
        }
    }
}

