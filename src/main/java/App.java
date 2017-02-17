/**
 * Created by David Merriman on 02/17/2017.
 */

import static spark.Spark.*;

public class App {
    public static void main(String[] args){
        get("/",(req,res)->"hello world");
    }
}
