package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class Clevis {

    public Clevis(String hname, String tname) throws IOException {            //Test comment message, hope you see this
        Scanner input = new Scanner(System.in);
        ArrayList<String> cmds = new ArrayList<String>();
        boolean invalid;
        String sinput;
        String fregex = "(-[0-9]+[.][0-9]+|-[0-9]+|[0-9]+[.][0-9]+|[0-9]+)";
        String nregex = "([a-zA-z0-9]+)";
        System.out.println("Welcome to CLEVIS!\n" +
                "Made by group 3\n");
        do{
            invalid = false;
            System.out.print("Please enter your command: ");
            sinput = input.nextLine();
            sinput= sinput.trim();
            if(sinput.matches("rectangle "+nregex+" "+fregex+" "+fregex+" "+fregex+" "+fregex)) {        //Rectangle construct, basically complete
                System.out.println("Rectangle command recognized");
                String[] cmd = sinput.split(" ");
                if (nameNotUsed(cmd[1])) {
                    Shape.addShape(new Rectangle(cmd[1], Double.parseDouble(cmd[2]), Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4]), Double.parseDouble(cmd[5])));
                }
                else{
                    invalid = true;
                }
            }
            else if(sinput.matches("line "+nregex+" "+fregex+" "+fregex+" "+fregex+" "+fregex)){        //Line construct, basically complete
                System.out.println("line command recognized");
                String[] cmd = sinput.split(" ");
                if (nameNotUsed(cmd[1])) {
                    Shape.addShape(new Line(cmd[1], Double.parseDouble(cmd[2]), Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4]), Double.parseDouble(cmd[5])));
                }
                else{
                    invalid = true;
                }
            }
            else if(sinput.matches("circle "+nregex+" "+fregex+" "+fregex+" "+fregex)){                 //Circle construct, basically complete
                System.out.println("circle command recognized");
                String[] cmd = sinput.split(" ");
                if (nameNotUsed(cmd[1])) {
                    Shape.addShape(new Circle(cmd[1], Double.parseDouble(cmd[2]), Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4])));
                }
                else{
                    invalid = true;
                }
            }
            else if(sinput.matches("square "+nregex+" "+fregex+" "+fregex+" "+fregex)){                 //Square construct, basically complete
                System.out.println("square command recognized");
                String[] cmd = sinput.split(" ");
                if (nameNotUsed(cmd[1])) {
                    Shape.addShape(new Square(cmd[1], Double.parseDouble(cmd[2]), Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4])));
                }
                else{
                    invalid = true;
                }
            }
            else if(sinput.startsWith("group ")){                             //Group construct, basically complete
                String[] cmd = sinput.split(" ");
                String cmdregex = "group";
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
                            if(a[i-2] == null || a[i-2].grouparent != null){
                                flag = false;
                            }
                        }
                        if (flag == true){
                            Shape.addShape(new Group(cmd[1], a));
                        }
                    }
                    else{
                        invalid = true;
                    }
                }
            }
            else if(sinput.matches("ungroup "+nregex)){                                                 //Ungroup action, basically complete
                System.out.println("ungroup command recognized");
                String[] cmd = sinput.split(" ");
                Shape temp = Shape.findAShape(cmd[1]);
                if (!(temp instanceof Group)&& temp != null){
                    System.out.println("This is not a group.");
                    invalid = true;
                }
                else if(temp != null) temp.ungroup();
            }
            else if(sinput.matches("delete "+nregex)){                                                  //Delete on a certain shape, basically complete
                System.out.println("delete command recognized");
                String[] cmd = sinput.split(" ");
                invalid = Shape.delete(cmd[1]);
            }
            else if(sinput.matches("boundingbox "+nregex)){                                             //Bounding box, basically complete
                System.out.println("boundingbox command recognized");
                String[] cmd = sinput.split(" ");
                Shape temp = Shape.findAShape(cmd[1]);
                if(temp != null && temp.grouparent == null) {
                    double[] result =temp.boundingbox();
                    System.out.println("Bounding box of "+cmd[1]+" is: x: "+String.format("%.2f",result[0])+" y: "+String.format("%.2f",result[1])+" w: "+String.format("%.2f",result[2])+" h: "+String.format("%.2f",result[3]));
                } else if(temp == null){
                    System.out.println("No shape with such name is found.");
                    invalid = true;
                }else {
                    System.out.println("Cannot perform action on group component!");
                    invalid = true;
                }
            }
            else if(sinput.matches("move "+nregex+" "+fregex+" "+fregex)){                             //Move a shape, basically complete
                System.out.println("move command recognized");
                String[] cmd = sinput.split(" ");
               Shape temp =  Shape.findAShape(cmd[1]);
               if(temp != null && temp.grouparent == null){
                   temp.move(Double.parseDouble(cmd[2]),Double.parseDouble(cmd[3]));
               }
               else if(temp == null){
                   System.out.println("No shape with such name is found.");
                   invalid = true;
               }else {
                   System.out.println("Cannot perform action on group component!");
                   invalid=true;
               }
            }
            else if(sinput.matches("pick-and-move "+fregex+" "+fregex+" "+fregex+" "+fregex)){
                System.out.println("pick-and-move command recognized");
                String[] cmd = sinput.split(" ");
                Shape.pickandmove(Double.parseDouble(cmd[1]), Double.parseDouble(cmd[2]), Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4]));
            }
            else if(sinput.matches("intersect "+nregex+" "+nregex)){
                System.out.println("intersect command recognized");
                String[] cmd = sinput.split(" ");
                Shape temp = Shape.findAShape(cmd[1]);
                Shape temp2 = Shape.findAShape(cmd[2]);
                if (temp == null || temp2 == null){
                    System.out.println("At least one of the shapes are not found");
                    invalid = true;
                }
                else if (Shape.intersect(temp, temp2)){
                    System.out.println("Shape " + cmd[1] + " and Shape " + cmd[2] + " intersects each other.");
                }
                else{
                    System.out.println("Shape " + cmd[1] + " and Shape " + cmd[2] + " does not intersect with each other.");
                }
            }
            else if(sinput.matches("list "+nregex)){                                                //List a single shape, basically complete
                System.out.println("list command recognized");
                String[] cmd = sinput.split(" ");
                Shape temp = Shape.findAShape(cmd[1]);
                if(temp != null && temp.grouparent == null){
                    temp.getInfo(1);
                }
                else if(temp == null){
                    System.out.println("No shape with such name is found.");
                    invalid = true;
                }
                else {
                    System.out.println("Cannot perform action on group component!");
                    invalid = true;
                }
            }
            else if(sinput.equals("listAll")){                                                           //List all the shape, basically complete
                System.out.println("listAll command recognized");
                Shape.ListTest();
            }
            else if(sinput.equals("quit")){                                                             //Quit the CLI, no need to check this right?
                System.out.println("Quitting...");
            }
            else if(sinput.equals("listHead")){
                System.out.println("listHead test");
                Shape.ListFromHead();
            }
            else{                                                                                       //Happens when the command is not recognized
                System.out.println("Invalid command!");
                invalid = true;
            }
            if(!invalid){
                cmds.add(sinput);
            }
        }while(!sinput.equals("quit"));

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


    public boolean nameNotUsed(String name){                  //method called when constructing new shapes, checks if the name is used or not
        if(Shape.findAShape(name)==null){
            return true;
        }
        else{
            System.out.println("The name ["+name+"] is already used for a shape.");
            return false;
        }
    }

    public static void main(String[] args){     //my test case
        Rectangle R = new Rectangle("A Rectangle", 0,0,10,20);
        Line L = new Line("A Line",0,0,3,3);
        //Group G = new Group("A Group", R);
        new Circle("A Circle",0,0,30);
        new Square("A Square",0,0,5);
        Shape.ListTest();
        Shape.delete("A Group");
        Shape.ListTest();
    }

}