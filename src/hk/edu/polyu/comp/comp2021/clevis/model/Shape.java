package hk.edu.polyu.comp.comp2021.clevis.model;

import org.w3c.dom.css.Rect;
import java.util.Arrays;
import java.util.Stack;

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

    public void regroup(){}

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
        SaveUndo(this,1);
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
    public void undelete() {
        SaveUndo(this,2);
        if (this.previous != null && this.next == null) {
            cur = this;
        }    else {
            this.next.previous = this;
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
    /**---------------[Undo related methods]------------------------------------------------------------*/
    static Stack<Shape> undo = new Stack<Shape>();
    static Stack<Integer> code = new Stack<Integer>();
    static Stack<Double> movx = new Stack<Double>();
    static Stack<Double> movy = new Stack<Double>();
    static Stack<Shape> redo = new Stack<Shape>();
    static Stack<Integer> recode = new Stack<Integer>();
    static Stack<Double> removx = new Stack<Double>();
    static Stack<Double> removy = new Stack<Double>();
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
        System.out.println(code.toString());
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
        System.out.println(recode.toString());
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


class Rectangle extends Shape {
    public double x, y, w, h;

    Rectangle(String name, double x, double y, double w, double h) {
        super(name);
        SaveUndo(this,2);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    public void getInfo(int n){
        System.out.println("[Shape type: Rectangle] " + " [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f",x) + "]  [y-coordinate: "+String.format("%.2f",y)+"]  [width: "+String.format("%.2f",w)+"]  [height: "+String.format("%.2f",h)+"]");
    }
    public void move(double x, double y){
        SaveUndo(this, 3);
        SaveMove(x, y);
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
        SaveUndo(this,2);
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    public void getInfo(int n){
        System.out.println("[Shape type: Line] "+ "  [Shape name: "+this.getName()+"]  [x-coordinate 1: "+ String.format("%.2f",x1) + "]  [y-coordinate 1: "+String.format("%.2f",y1)+"]  [x-coordinate 2: "+String.format("%.2f",x2)+"]  [y-coordinate 2: "+String.format("%.2f",y2)+"]");
    }

    public void move(double x, double y){
        SaveUndo(this, 3);
        SaveMove(x, y);
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
        SaveUndo(this,2);
        this.x = x;
        this.y = y;
        this.r = r;
    }
    public void getInfo(int n){
        System.out.println("[Shape type: Circle] "+ " [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f",x) + "]  [y-coordinate: "+String.format("%.2f",y)+"]  [radius: "+String.format("%.2f",r)+"]");
    }
    public void move(double x, double y){
        SaveUndo(this, 3);
        SaveMove(x, y);
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
        SaveUndo(this,2);
        this.x = x;
        this.y = y;
        this.l = l;
    }
    public void getInfo(int n){
        System.out.println("[Shape type: Square] "+" [Shape name: "+this.getName()+"]  [x-coordinate: "+ String.format("%.2f",x) + "]  [y-coordinate: "+String.format("%.2f",y)+"]  [side length: "+String.format("%.2f",l)+"]");
    }
    public void move(double x, double y){
        SaveUndo(this, 3);
        SaveMove(x, y);
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
        SaveUndo(this,5);
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
        SaveUndo(this,1);
        if (this.grouparent == null) {
            gdelete();
        } else {
            System.out.println("Deleting a component shape of a group is invalid!");
        }
    }

    public void move(double x, double y) {
        SaveUndo(this,3);
        SaveMove(x,y);
        for (int i=0; i<s1.length; i++){
            s1[i].move(x, y);
            PopUndo();
            PopMove();
        }
    }

    public void ungroup() {
        SaveUndo(this, 4);
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

    public void undelete() {
        SaveUndo(this, 2);
        if (this.previous != null && this.next == null) {
            cur = this;
        }    else {
            this.next.previous = this;
        }
    }

    public void regroup() {
        SaveUndo(this, 5);
        undelete();
        PopUndo();
        for (int i=0; i<s1.length; i++){
            this.s1[i].grouparent = this;
        }
    }

}
