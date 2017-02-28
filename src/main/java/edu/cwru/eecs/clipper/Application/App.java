package edu.cwru.eecs.clipper.Application;

import edu.cwru.eecs.clipper.DataManagers.InitialDataManager;

import static spark.Spark.*;

public class App {
    public static void main(String[] args){
        get("/",(req,res)->generateFeedback());
    }

    private static String generateFeedback() {
        System.out.println("Generating feedback");
        String feedback = "Hello!";
        InitialDataManager initialDataManager = new InitialDataManager();
        int sequence = (int) (Math.random() * 1000);
        String id = initialDataManager.write(""+sequence);
        feedback = feedback + ("\nInserted SEQ# = " + sequence);
        feedback = feedback +("\nInserted @" + id);
        String seqout = initialDataManager.read(id);
        feedback = feedback +("\nReceived"+seqout);
        return feedback;
    }
}
