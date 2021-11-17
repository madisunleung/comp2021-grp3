package hk.edu.polyu.comp.comp2021.clevis;

import hk.edu.polyu.comp.comp2021.clevis.model.Clevis;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        if(args.length==0){
            //System.out.println("no args detected");
            Clevis clevis = new Clevis("log.html","log.txt");
        }
        else {
            /*for(int i = 0; i < args.length;i++){
                System.out.println("args["+i+"]: "+args[i]);
            }
             */
            Clevis clevis = new Clevis(args[1], args[3]);
        }
        // Initialize and utilize the system
    }
}
