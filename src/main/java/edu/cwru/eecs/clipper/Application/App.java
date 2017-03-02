package edu.cwru.eecs.clipper.Application;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.MoreObjects;
import edu.cwru.eecs.clipper.DataManagers.InitialDataManager;
import edu.cwru.eecs.clipper.DataManagers.UserAccountDataManager;
import edu.cwru.eecs.clipper.Models.UserAccount;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");
        get("/", (req, res) -> generateFeedback());
        get("/experiment_reset", (req, res) -> experimentalReset());
        get("/create_user_test", (req, res) -> createUser(res));
        get("/get_user_test", (req, res) -> getUser(req));
        get("/*", (req, res) -> render(new HashMap<>(), "/templates/" + req.splat()[0] + ".html"));

        //ajax requests from tyler (these will probably need changing as they are rudimentary)
        //TODO write general mongo functions (probably 2-3) to handle all of these routes


        //FOOD & ENTERTAINMENT ROUTES
        get("foodAndEntertainments/:id", (req, res) -> {
            //TODO hook this up to a mongo call for specific f&e obj
            return null;
        });

        get("foodAndEntertainments", (req, res) -> {
            //TODO hook up to mongo call for list of food/entertainment objects?
            return null;
        });

        post("foodAndEntertainments/search", (req, res) -> {
            //TODO hook up to mongo call to store for database?
            return null;
        });


        //EVENT ROUTES
        get("events/:id", (req, res) -> {
            //TODO hook this up to a mongo call for specific f&e obj
            return null;
        });

        get("events", (req, res) -> {
            //TODO hook up to mongo call for list of food/entertainment objects?
            return null;
        });

        post("events/search", (req, res) -> {
            //TODO hook up to mongo call to store for database?
            return null;
        });

        post("events/create", (req, res) -> {
            //TODO
            return null;
        });

        post("events/update", (req, res) -> {
            //TODO
            return null;
        });

        post("events/delete", (req, res) -> {
            //TODO
            return null;
        });


        //STUDY ROOM ROUTES
        get("studyRooms/:id", (req, res) -> {
            //TODO
            return null;
        });

        get("studyRooms", (req, res) -> {
            //TODO
            return null;
        });

        post("studyRooms/create", (req, res) -> {
            //TODO
            return null;
        });

        post("studyRooms/update", (req, res) -> {
            //TODO
            return null;
        });

        post("studyRooms/delete", (req, res) -> {
            //TODO
            return null;
        });

        post("studyRooms/search", (req, res) -> {
            //TODO
            return null;
        });


        //BUILDING ROUTES
        get("buildings/:id", (req, res) -> {
            //TODO
            return null;
        });

        get("buildings", (req, res) -> {
            //TODO
            return null;
        });

        post("buildings/create", (req, res) -> {
            //TODO
            return null;
        });

        post("buildings/update", (req, res) -> {
            //TODO
            return null;
        });

        post("buildings/delete", (req, res) -> {
            //TODO
            return null;
        });

        post("buildings/search", (req, res) -> {
            //TODO
            return null;
        });


        //QUESTION ROUTES
        get("questions/:id/bestAnswer", (req, res) -> {
            //TODO
            return null;
        });

        get("questions/:id", (req, res) -> {
            //TODO
            return null;
        });

        get("questions", (req, res) -> {
            //TODO
            return null;
        });

        post("questions/create", (req, res) -> {
            //TODO
            return null;
        });

        post("questions/update", (req, res) -> {
            //TODO
            return null;
        });

        post("questions/search", (req, res) -> {
            //TODO
            return null;
        });
    }

    private static String render(Map<String, Object> model, String template) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }

    private static String generateFeedback() {
        System.out.println("Generating feedback");
        String feedback = "Hello!";
        InitialDataManager initialDataManager = new InitialDataManager();
        int sequence = (int) (Math.random() * 1000);
        String id = initialDataManager.write("" + sequence);
        feedback = feedback + ("\nInserted SEQ# = " + sequence);
        feedback = feedback + ("\nInserted @" + id);
        String seqout = initialDataManager.read(id);
        feedback = feedback + ("\nSaved" + seqout);
        return feedback;
    }

    private static String experimentalReset() {
        InitialDataManager.CreateCollection();
        return "resetted";
    }

    private static String createUser(Response res) {
        UserAccountDataManager userAccountDataManager = new UserAccountDataManager();
        UserAccount account = userAccountDataManager.createUserAccount();
        System.out.println(MoreObjects.toStringHelper(account));
        account.setEmail("zxh108@test.case.edu");
        System.out.println(MoreObjects.toStringHelper(account));
        userAccountDataManager.updateUserAccount(account);
        res.redirect("/get_user_test?u=" + account.getUserId());
        return account.toString();
    }

    private static String getUser(Request request) {
        String userId = request.queryMap("u").value();
        return new UserAccountDataManager().findByUserID(userId).toString();
    }
}
