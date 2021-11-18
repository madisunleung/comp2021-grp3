package hk.edu.polyu.comp.comp2021.clevis;

import hk.edu.polyu.comp.comp2021.clevis.model.Clevis;

import java.io.IOException;


/**
 *
 * COMP2021 Group project
 *
 * @author grp38
 * @version 1.0
 *
 */

public class Application {

    /**
     *
     * @param args The argument contains names for the log files
     * @throws IOException throws expection on IO issues
     */

    public static void main(String[] args) throws IOException {
        if(args.length==0){
            Clevis clevis = new Clevis("log.html","log.txt");
        }
        else {
            Clevis clevis = new Clevis(args[1], args[3]);
        }
        // Initialize and utilize the system
    }
}
