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
     * Scanner for reading inputs from the user
     */
    protected Scanner input = new Scanner(System.in);
    /**
     * Array list for storing all the valid commands
     * for writing them into log files when the program is called to terminate
     */
    protected ArrayList<String> cmds = new ArrayList<String>();
    /**
     * boolean variable to check if the current command is valid or not
     */
    protected boolean invalid;
    /**
     * String to receive the input from the user
     */
    protected String sinput;
    /**
     * regex sequence to recognize numeric values
     */
    protected String fregex = "(-[0-9]+[.][0-9]+|-[0-9]+|[0-9]+[.][0-9]+|[0-9]+)";
    /**
     * regex sequence to recognize name parameters in a command
     */
    protected String nregex = "([a-zA-z0-9]+)";

    /**
     * Allows creation of clevis instance without parameters, but not automatically going into
     */
    public Clevis(){}
    /**
     * Clevis, keeps looping to read user input and do the commands until quit is called
     * @param hname     The name of the html log file
     * @param tname     The name of the txt log file
     *@throws IOException      Throws exception when there are issues with Input/Output
     */



    public Clevis(String hname, String tname) throws IOException {
        System.out.println("Welcome to CLEVIS!\n" +
                "Made by group 3\n");
        boolean cont = true;
        while(cont){
            System.out.print("Please enter your command: ");
            sinput = input.nextLine();
            cont = CLI(sinput);
        }
        writeLogs(hname,tname);
    }



    /**
     * The CLI to read user input, check if the command is valid, and calls corresponding methods to perform actions
     * @param s    The user's command to be recognized and executed
     * @return     a boolean, that indicates if the program will continue(true) or teminate(false)
     */

    public boolean CLI(String s) {
        String sinput;
        invalid = false;
        sinput = s.trim();
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
            invalid = pickandmove(sinput);
            Shape.ClearRedo();
        } else if (sinput.matches("intersect " + nregex + " " + nregex)) {
            invalid = intersect(sinput);
        } else if (sinput.matches("list " + nregex)) {                                                //List a single shape, basically complete
            invalid = list(sinput);
        } else if (sinput.equals("listAll")) {                                                           //List all the shape, basically complete
            System.out.println("listAll command recognized");
            Shape.List();
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

    /**
     * method recConstruct:
     * Checks if the command is valid, if yes then a rectangle is created according to the parameters and added to the doubly linked list
     * @param sinput      The user input
     * @return              A boolean indicating the command is checked to be invalid(True) or not(False)
     */
    private boolean recConstruct(String sinput) {
        System.out.println("Rectangle command recognized");
        String[] cmd = sinput.split(" ");
        if (nameNotUsed(cmd[1])) {
            Shape.addShape(new Rectangle(cmd[1], Double.parseDouble(cmd[2]), Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4]), Double.parseDouble(cmd[5])));
            Shape.ClearRedo();
            return false;
        } else {
            return true;
        }
    }

    /**
     * method lineConstruct:
     * Checks if the command is valid, if yes then a line is created according to the parameters and added to the doubly linked list
     * @param sinput        The user input
     * @return              A boolean indicating the command is checked to be invalid(True) or not(False)
     */
    private boolean lineConstruct(String sinput){
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

    /**
     * method cirConstruct:
     * Checks if the command is valid, if yes then a circle is created according to the parameters and added to the doubly linked list
     * @param sinput        The user input
     * @return              A boolean indicating the command is checked to be invalid(True) or not(False)
     */
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

    /**
     * method squConstruct:
     * Checks if the command is valid, if yes then a square is created according to the parameters and added to the doubly linked list
     * @param sinput        The user input
     * @return              A boolean indicating the command is checked to be invalid(True) or not(False)
     */
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

    /**
     * method grpConstruct:
     * Checks if the command is valid, if yes then a group is created according to the parameters and added to the doubly linked list
     * The grouped shapes are also going to have their grouparent pointer set pointing to this group instance created
     * @param sinput        The user input
     * @return              A boolean indicating the command is check to be invalid(True) or not(False)
     */
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
                    if(a[i-2] == null || a[i - 2].getGrouparent() != null){
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

    /**
     * method ungrp:
     * Check if the command is valid, if yes, then the group with the name stated in the command will be ungrouped
     * @param sinput        The user input
     * @return              A boolean indicating the command is check to be invalid(True) or not(False)
     * */
    public boolean ungrp(String sinput){
        System.out.println("ungroup command recognized");
        String[] cmd = sinput.split(" ");
        Shape temp = Shape.findAShape(cmd[1]);
        if (!(temp instanceof Group)&& temp != null){
            System.out.println("This is not a group.");
            return true;
        }
        else if(temp != null && temp.getGrouparent() == null) {
            temp.ungroup();
            Shape.ClearRedo();
            return false;
        }
        return true;
    }

    /**
     * method del:
     * Calls delete on the shape with the name indicated in the command, if the shape is not found/ it belongs to a group, the command is invalid
     * @param sinput       The user input
     * @return             A boolean indicating the command is check to be invalid(True) or not(False)
     */
    public boolean del(String sinput){
        System.out.println("delete command recognized");
        String[] cmd = sinput.split(" ");
        Shape.ClearRedo();
        return Shape.delete(cmd[1]);
    }

    /**
     * method boundingbox:
     * Check if the actions is valid, yes then returns the information about the bounding box that includes all shapes in it
     * @param sinput        The user input
     * @return              A boolean indicating the command is check to be invalid(True) or not(False)
     */
    public boolean boundingbox(String sinput){
        System.out.println("boundingbox command recognized");
        String[] cmd = sinput.split(" ");
        Shape temp = Shape.findAShape(cmd[1]);
        if(temp != null && temp.getGrouparent() == null) {
            double[] result =temp.boundingbox();
            System.out.println("Bounding box of "+cmd[1]+" is: [x: "+String.format("%.2f",result[0])+"]  [y: "+String.format("%.2f",result[1])+"]  [w: "+String.format("%.2f",result[2])+"]  [h: "+String.format("%.2f",result[3])+"]");
            return false;
        } else if(temp == null){
            System.out.println("No shape with such name is found.");
            return true;
        }else {
            System.out.println("Cannot perform action on group component!");
            return true;
        }
    }


     /**
      * method move:
      * Check if the action is valid, yes then calls the shape to move by the passed in parameters
     * @param sinput        The user input
     * @return              A boolean indicating the command is check to be invalid(True) or not(False)
     */
    public boolean move(String sinput){
        System.out.println("move command recognized");
        String[] cmd = sinput.split(" ");
        Shape temp =  Shape.findAShape(cmd[1]);
        if(temp != null && temp.getGrouparent() == null){
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

    /**
     * method pickandmove:
     * Check if the action is valid, yes then finds the shape to move by the passed in parameters
     * @param sinput        The user input
     * @return              A boolean indicating the command is check to be invalid(True) or not(False)
     */
    public boolean pickandmove(String sinput){
        System.out.println("pick-and-move command recognized");
        String[] cmd = sinput.split(" ");
        Shape.pickandmove(Double.parseDouble(cmd[1]), Double.parseDouble(cmd[2]), Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4]));
        return false;
    }

    /**
     * method intersect:
     * Check if the action is valid, yes then checks whether the inputted shapes intersect with each other
     * @param sinput        The user input
     * @return              A boolean indicating the command is check to be invalid(True) or not(False)
     */
    public boolean intersect(String sinput){
        System.out.println("intersect command recognized");
        String[] cmd = sinput.split(" ");
        Shape temp = Shape.findAShape(cmd[1]);
        Shape temp2 = Shape.findAShape(cmd[2]);
        if (temp == null || temp2 == null){
            System.out.println("At least one of the shapes are not found");
            return true;
        }
        else if (Shape.intersect(temp, temp2)){
            System.out.println("Shape " + cmd[1] + " and Shape " + cmd[2] + " intersects each other.");
            return false;
        }
        else{
            System.out.println("Shape " + cmd[1] + " and Shape " + cmd[2] + " does not intersect with each other.");
            return false;
        }
    }


    /**
     * method list:
     * Checks if the action is valid, yes then calls getInfo on the shape indicated in the command to show the information the shape contains
     * @param sinput        The user input
     * @return              A boolean indicating the command is check to be invalid(True) or not(False)
     */
    public boolean list(String sinput){
        System.out.println("list command recognized");
        String[] cmd = sinput.split(" ");
        Shape temp = Shape.findAShape(cmd[1]);
        if(temp != null && temp.getGrouparent() == null){
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
     * method nameNotUsed:
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

    /**
     * method writeLogs:
     * write and outputs the valid cmds
     * @param hname     The name of the html log
     * @param tname     The name of the txt log
     * @throws IOException      Exception thrown on Input output exception
     */
    public void writeLogs(String hname, String tname) throws IOException {
        File folder = new File("outputs");
        folder.mkdir();

        System.out.println("Creating logs...");
        File txt = new File("outputs\\" + tname);
        File html = new File("outputs\\" + hname);
        BufferedWriter tw = new BufferedWriter(new FileWriter(txt));
        BufferedWriter hw = new BufferedWriter(new FileWriter(html));
        for (int i = 0; i < cmds.size(); i++) {
            tw.write(cmds.get(i) + "\n");
            hw.write(i + 1 + "\t\t" + cmds.get(i) + "<br>");
        }
        tw.close();
        hw.close();
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