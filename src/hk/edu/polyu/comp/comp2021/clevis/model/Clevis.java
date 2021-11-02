package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.Scanner;

public class Clevis {

    public Clevis(){
        Scanner input = new Scanner(System.in);
        String sinput;
        String fregex = "([0-9]+[.][0-9]+|[0-9]+)";
        String nregex = "([a-zA-z0-9]+)";
        System.out.println("Welcome to CLEVIS!\n" +
                "Made by group 3\n");
        do{
            System.out.print("Please enter your command: ");
            sinput = input.nextLine();
            if(sinput.matches("rectangle "+nregex+" "+fregex+" "+fregex+" "+fregex+" "+fregex)){
                System.out.println("Rectangle command recognized");
            }
            else if(sinput.matches("line "+nregex+" "+fregex+" "+fregex+" "+fregex+" "+fregex)){
                System.out.println("line command recognized");
            }
            else if(sinput.matches("circle "+nregex+" "+fregex+" "+fregex+" "+fregex)){
                System.out.println("circle command recognized");
            }
            else if(sinput.matches("square "+nregex+" "+fregex+" "+fregex+" "+fregex)){
                System.out.println("square command recognized");
            }
            else if(sinput.matches("group "+nregex+" "+nregex+" "+nregex)){
                System.out.println("group command recognized");
            }
            else if(sinput.matches("ungroup "+nregex)){
                System.out.println("ungroup command recognized");
            }
            else if(sinput.matches("delete "+nregex)){
                System.out.println("delete command recognized");
            }
            else if(sinput.matches("boundingbox "+nregex)){
                System.out.println("boundingbox command recognized");
            }
            else if(sinput.matches("move "+nregex+" "+fregex+" "+fregex)){
                System.out.println("move command recognized");
            }
            else if(sinput.matches("pick-and-move "+fregex+" "+fregex+" "+fregex+" "+fregex)){
                System.out.println("pick-and-move command recognized");
            }
            else if(sinput.matches("intersect "+nregex+" "+nregex)){
                System.out.println("intersect command recognized");
            }
            else if(sinput.matches("list "+nregex)){
                System.out.println("list command recognized");
            }
            else if(sinput.equals("listAll")){
                System.out.println("listAll command recognized");
            }
            else if(sinput.equals("quit")){
                System.out.println("Quitting...");
                break;
            }
            else{
                System.out.println("Invalid command!");
            }
        }while(!sinput.equals("quit"));
    }

}