package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.Scanner;

public class Clevis {

    public Clevis(){            //Test comment message, hope you see this
        Scanner input = new Scanner(System.in);
        String sinput;
        String fregex = "(-[0-9]+[.][0-9]+|-[0-9]+|[0-9]+[.][0-9]+|[0-9]+)";
        String nregex = "([a-zA-z0-9]+)";
        System.out.println("Welcome to CLEVIS!\n" +
                "Made by group 3\n");
        do{
            System.out.print("Please enter your command: ");
            sinput = input.nextLine();
            if(sinput.matches("rectangle "+nregex+" "+fregex+" "+fregex+" "+fregex+" "+fregex)){        //Rectangle construct, basically complete
                System.out.println("Rectangle command recognized");
                String[] cmd = sinput.split(" ");
                new Rectangle(cmd[1],Double.parseDouble(cmd[2]),Double.parseDouble(cmd[3]),Double.parseDouble(cmd[4]),Double.parseDouble(cmd[5]));
            }
            else if(sinput.matches("line "+nregex+" "+fregex+" "+fregex+" "+fregex+" "+fregex)){        //Line construct, basically complete
                System.out.println("line command recognized");
                String[] cmd = sinput.split(" ");
                new Line(cmd[1],Double.parseDouble(cmd[2]),Double.parseDouble(cmd[3]),Double.parseDouble(cmd[4]),Double.parseDouble(cmd[5]));
            }
            else if(sinput.matches("circle "+nregex+" "+fregex+" "+fregex+" "+fregex)){                 //Circle construct, basically complete
                System.out.println("circle command recognized");
                String[] cmd = sinput.split(" ");
                new Circle(cmd[1],Double.parseDouble(cmd[2]),Double.parseDouble(cmd[3]),Double.parseDouble(cmd[4]));
            }
            else if(sinput.matches("square "+nregex+" "+fregex+" "+fregex+" "+fregex)){                 //Square construct, basically complete
                System.out.println("square command recognized");
                String[] cmd = sinput.split(" ");
                new Square(cmd[1],Double.parseDouble(cmd[2]),Double.parseDouble(cmd[3]),Double.parseDouble(cmd[4]));
            }
            else if(sinput.matches("group "+nregex+" "+nregex+" "+nregex)){                             //Group construct, basically complete
                System.out.println("group command recognized");
                String[] cmd = sinput.split(" ");
                Shape a = Shape.findAShape(cmd[2]);
                Shape b = Shape.findAShape(cmd[3]);
                if(a != null && b != null) {
                    new Group(cmd[1],a,b);
                }
            }
            else if(sinput.matches("ungroup "+nregex)){                                                 //Ungroup action, basically complete
                System.out.println("ungroup command recognized");
                String[] cmd = sinput.split(" ");
                Shape temp = Shape.findAShape(cmd[1]);
                if (!(temp instanceof Group)&& temp != null){
                    System.out.println("This is not a group.");
                }
                else if(temp != null) temp.ungroup();
            }
            else if(sinput.matches("delete "+nregex)){                                                  //Delete on a certain shape, I have questions for this
                System.out.println("delete command recognized");
                String[] cmd = sinput.split(" ");
                Shape.delete(cmd[1]);
            }
            else if(sinput.matches("boundingbox "+nregex)){
                System.out.println("boundingbox command recognized");
            }
            else if(sinput.matches("move "+nregex+" "+fregex+" "+fregex)){                             //Move a shape, basically complete
                System.out.println("move command recognized");
                String[] cmd = sinput.split(" ");
               Shape temp =  Shape.findAShape(cmd[1]);
               if(temp != null){
                   temp.move(Double.parseDouble(cmd[2]),Double.parseDouble(cmd[3]));
               }
            }
            else if(sinput.matches("pick-and-move "+fregex+" "+fregex+" "+fregex+" "+fregex)){
                System.out.println("pick-and-move command recognized");
            }
            else if(sinput.matches("intersect "+nregex+" "+nregex)){
                System.out.println("intersect command recognized");
            }
            else if(sinput.matches("list "+nregex)){                                                //List a single shape, basically complete
                System.out.println("list command recognized");
                String[] cmd = sinput.split(" ");
                Shape temp = Shape.findAShape(cmd[1]);
                if(temp != null){
                    temp.getInfo();
                }
            }
            else if(sinput.equals("listAll")){                                                           //List all the shape, basically complete, but I have questions too
                System.out.println("listAll command recognized");
                Shape.ListTest();
            }
            else if(sinput.equals("quit")){                                                             //Quit the CLI, no need to check this right?
                System.out.println("Quitting...");
                break;
            }
            else if(sinput.equals("listHead")){
                System.out.println("listHead test");
                Shape.ListFromHead();
            }
            else{                                                                                       //Happens when the command is not recognized
                System.out.println("Invalid command!");
            }
        }while(!sinput.equals("quit"));
    }

    public static void main(String[] args){     //my test case
        Rectangle R = new Rectangle("A Rectangle", 0,0,10,20);
        Line L = new Line("A Line",0,0,3,3);
        Group G = new Group("A Group", R,L);
        new Circle("A Circle",0,0,30);
        new Square("A Sqaure",0,0,5);
        Shape.ListTest();
        Shape.delete("A Group");
        Shape.ListTest();
    }

}