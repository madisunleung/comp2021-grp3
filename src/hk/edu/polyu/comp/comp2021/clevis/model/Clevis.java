package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;


/**
 *
 * Main application CLI for executing the commands
 *
 */


public class Clevis {

    /**
     *
     * @param hname The name of the html file
     * @param tname The name of the txt file
     * @throws IOException Exception thrown for Input output issues in writing the files
     */

    Scanner input = new Scanner(System.in);
    ArrayList<String> cmds = new ArrayList<String>();
    boolean invalid;
    String sinput;
    String fregex = "(-[0-9]+[.][0-9]+|-[0-9]+|[0-9]+[.][0-9]+|[0-9]+)";
    String nregex = "([a-zA-z0-9]+)";

    public Clevis(String hname, String tname) throws IOException {
        System.out.println("Welcome to CLEVIS!\n" +
                "Made by group 3\n");
        boolean cont = true;
        do{
            cont = CLI();
        }while(cont);
        File folder = new File("outputs");
        folder.mkdir();

        System.out.println("Creating logs...");
        File txt = new File("outputs\\"+tname);
        File html = new File("outputs\\"+hname);
        BufferedWriter tw = new BufferedWriter(new FileWriter(txt));
        BufferedWriter hw = new BufferedWriter(new FileWriter(html));
        for(int i = 0; i < cmds.size();i++){
            tw.write(cmds.get(i)+"\n");
            hw.write(i+1+"\t\t"+cmds.get(i)+"<br>");
        }
        tw.close();
        hw.close();
    }

    public boolean CLI() {            //Test comment message, hope you see this

        invalid = false;
        System.out.print("Please enter your command: ");
        sinput = input.nextLine();
        sinput = sinput.trim();
        if (sinput.matches("rectangle " + nregex + " " + fregex + " " + fregex + " " + fregex + " " + fregex)) {        //Rectangle construct, basically complete
            invalid = recConstruct(sinput);
        } else if (sinput.matches("line " + nregex + " " + fregex + " " + fregex + " " + fregex + " " + fregex)) {        //Line construct, basically complete
            invalid = lineConstruct(sinput);
        } else if (sinput.matches("circle " + nregex + " " + fregex + " " + fregex + " " + fregex)) {                 //Circle construct, basically complete
            invalid = cirConstruct(sinput);
        } else if (sinput.matches("square " + nregex + " " + fregex + " " + fregex + " " + fregex)) {                 //Square construct, basically complete
            invalid = squConstruct(sinput);
        } else if (sinput.startsWith("group ")) {                             //Group construct, basically complete
            invalid = grpConstruct(sinput);
        } else if (sinput.matches("ungroup " + nregex)) {                                                 //Ungroup action, basically complete
            invalid = ungrp(sinput);
        } else if (sinput.matches("delete " + nregex)) {                                                  //Delete on a certain shape, basically complete
            invalid = del(sinput);
        } else if (sinput.matches("boundingbox " + nregex)) {                                             //Bounding box, basically complete
            invalid = boundingbox(sinput);
        } else if (sinput.matches("move " + nregex + " " + fregex + " " + fregex)) {                             //Move a shape, basically complete
            invalid = move(sinput);
        } else if (sinput.matches("pick-and-move " + fregex + " " + fregex + " " + fregex + " " + fregex)) {
            System.out.println("pick-and-move command recognized");
            Shape.ClearRedo();
        } else if (sinput.matches("intersect " + nregex + " " + nregex)) {
            System.out.println("intersect command recognized");
        } else if (sinput.matches("list " + nregex)) {                                                //List a single shape, basically complete
            invalid = list(sinput);
        } else if (sinput.equals("listAll")) {                                                           //List all the shape, basically complete
            System.out.println("listAll command recognized");
            Shape.ListTest();
        } else if (sinput.equals("quit")) {                                                             //Quit the CLI, no need to check this right?
            System.out.println("Quitting...");
        } else if (sinput.equals("listHead")) {
            System.out.println("listHead test");
            Shape.ListFromHead();
        } else if (sinput.equals("undo")) {
            System.out.println("undo command recognised");
            Shape.Undos();
        } else if (sinput.equals("redo")) {
            System.out.println("redo command recognised");
            Shape.Redo();
        } else {                                                                                       //Happens when the command is not recognized
            System.out.println("Invalid command!");
            invalid = true;
        }
        if (!invalid) {
            cmds.add(sinput);
        }
        if(sinput.equals("quit")){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean recConstruct(String sinput){
        System.out.println("Rectangle command recognized");
        String[] cmd = sinput.split(" ");
        if (nameNotUsed(cmd[1])) {
            Shape.addShape(new Rectangle(cmd[1], Double.parseDouble(cmd[2]), Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4]), Double.parseDouble(cmd[5])));
            Shape.ClearRedo();
            return false;
        }
        else{
            return true;
        }
    }

    public boolean lineConstruct(String sinput){
        System.out.println("line command recognized");
        String[] cmd = sinput.split(" ");
        if (nameNotUsed(cmd[1])) {
            Shape.addShape(new Line(cmd[1], Double.parseDouble(cmd[2]), Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4]), Double.parseDouble(cmd[5])));
            Shape.ClearRedo();
            return false;
        }
        else{
            return true;
        }
    }

    public boolean cirConstruct(String sinput){
        System.out.println("circle command recognized");
        String[] cmd = sinput.split(" ");
        if (nameNotUsed(cmd[1])) {
            Shape.addShape(new Circle(cmd[1], Double.parseDouble(cmd[2]), Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4])));
            Shape.ClearRedo();
            return false;
        }
        else{
            return true;
        }
    }
    public boolean squConstruct(String sinput){
        System.out.println("square command recognized");
        String[] cmd = sinput.split(" ");
        if (nameNotUsed(cmd[1])) {
            Shape.addShape(new Square(cmd[1], Double.parseDouble(cmd[2]), Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4])));
            Shape.ClearRedo();
            return false;
        }
        else{
            return true;
        }
    }

    public boolean grpConstruct(String sinput){
        String[] cmd = sinput.split(" ");
        String cmdregex = "group";
        String nregex = "([a-zA-z0-9]+)";
        boolean flag = true;
        for(int i=1; i<cmd.length; i++){
            cmdregex = cmdregex + " " + nregex;
        }
        if (sinput.matches(cmdregex) && cmd.length >= 4) {
            System.out.println("group command recognized");
            Shape[] a = new Shape[cmd.length-2];
            for(int i=2; i<cmd.length; i++){
                a[i-2] = Shape.findAShape(cmd[i]);
            }
            if (nameNotUsed(cmd[1])) {
                for(int i=2; i<cmd.length; i++){
                    if(a[i-2] == null || a[i - 2].grouparent != null){
                        return true;
                    }
                }
                if (flag == true){
                    Shape.addShape(new Group(cmd[1], a));
                    Shape.ClearRedo();
                    return false;
                }
            }
            else{
                return true;
            }
        }
        return true;
    }

    public boolean ungrp(String sinput){
        System.out.println("ungroup command recognized");
        String[] cmd = sinput.split(" ");
        Shape temp = Shape.findAShape(cmd[1]);
        if (!(temp instanceof Group)&& temp != null){
            System.out.println("This is not a group.");
            return true;
        }
        else if(temp != null && temp.grouparent == null) {
            temp.ungroup();
            Shape.ClearRedo();
            return false;
        }
        return true;
    }

    public boolean del(String sinput){
        System.out.println("delete command recognized");
        String[] cmd = sinput.split(" ");
        Shape.ClearRedo();
        return Shape.delete(cmd[1]);
    }

    public boolean boundingbox(String sinput){
        System.out.println("boundingbox command recognized");
        String[] cmd = sinput.split(" ");
        Shape temp = Shape.findAShape(cmd[1]);
        if(temp != null && temp.grouparent == null) {
            double[] result =temp.boundingbox();
            System.out.println("Bounding box of "+cmd[1]+" is: x: "+String.format("%.2f",result[0])+" y: "+String.format("%.2f",result[1])+" w: "+String.format("%.2f",result[2])+" h: "+String.format("%.2f",result[3]));
            return false;
        } else if(temp == null){
            System.out.println("No shape with such name is found.");
            return true;
        }else {
            System.out.println("Cannot perform action on group component!");
            return true;
        }
    }

    public boolean move(String sinput){
        System.out.println("move command recognized");
        String[] cmd = sinput.split(" ");
        Shape temp =  Shape.findAShape(cmd[1]);
        if(temp != null && temp.grouparent == null){
            temp.move(Double.parseDouble(cmd[2]),Double.parseDouble(cmd[3]));
            Shape.ClearRedo();
            return false;
        }
        else if(temp == null){
            System.out.println("No shape with such name is found.");
            return true;
        }else {
            System.out.println("Cannot perform action on group component!");
            return true;
        }
    }

    public boolean list(String sinput){
        System.out.println("list command recognized");
        String[] cmd = sinput.split(" ");
        Shape temp = Shape.findAShape(cmd[1]);
        if(temp != null && temp.grouparent == null){
            temp.getInfo(1);
            return false;
        }
        else if(temp == null){
            System.out.println("No shape with such name is found.");
            return true;
        }
        else {
            System.out.println("Cannot perform action on group component!");
            return true;
        }
    }



    /**
     *
     * @param name  Finds a shape with that name, to see if it is used
     * @return      give false when the name is used by a shape in the list, else true
     */
    public boolean nameNotUsed(String name){                  //method called when constructing new shapes, checks if the name is used or not
        if(Shape.findAShape(name)==null){
            return true;
        }
        else{
            System.out.println("The name ["+name+"] is already used for a shape.");
            return false;
        }
    }






    /*
    public static void main(String[] args){     //my test case
        Shape.addShape(new Rectangle("A", 0, 0, 10, 20));
        Shape.addShape(new Rectangle("B", 0, 0, 10, 20));
        Shape.ListTest();
        Shape.Undos();
        Shape.ListTest();
        Shape.Redo();
        Shape.ListTest();
        Shape[] temp = {Shape.findAShape("A"), Shape.findAShape("B")};
        Shape.addShape(new Group("group",temp));
        Shape.ListTest();
        Shape.Undos();
        Shape.ListTest();
        Shape.Redo();
        Shape.ListTest();
        Shape.findAShape("group").move(100,100);
        Shape.ListTest();
        Shape.Undos();
        Shape.ListTest();
        Shape.Redo();
        Shape.ListTest();
        Shape.findAShape("group").ungroup();
        Shape.ListTest();
        Shape.Undos();
        Shape.ListTest();
        Shape.Redo();
        Shape.ListTest();
        Shape.findAShape("A").delete();
        Shape.ListTest();
        Shape.Undos();
        Shape.ListTest();
        Shape.Redo();
        Shape.ListTest();
    }

     */

}