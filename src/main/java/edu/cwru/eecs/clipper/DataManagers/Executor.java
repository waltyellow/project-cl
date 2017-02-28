package edu.cwru.eecs.clipper.DataManagers;

public class Executor {
    public static void main(String[] args) {
        InitialDataManager initialDataManager = new InitialDataManager();
        int sequence = (int) (Math.random() * 10000);
        String id = initialDataManager.write(""+sequence);
        System.out.println("Inserted SEQ# = " + sequence);
        System.out.println("Inserted @" + id);
        String seqout = initialDataManager.read(id);
        System.out.println("Received"+seqout);
    }
}
