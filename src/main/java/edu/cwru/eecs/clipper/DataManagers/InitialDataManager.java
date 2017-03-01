package edu.cwru.eecs.clipper.DataManagers;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.*;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;


public class InitialDataManager {
    private final String EXPERIMENTAL = "experimental";
    private MongoClient mongoClient;
    private String collection_name;

    static final Block<Document> printBlock = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            System.out.println(document.toJson());
        }
    };

    @SuppressWarnings("unchecked")
    public static void CreateCollection(){
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase mongoDatabase = mongoClient.getDatabase( "experimental" );
        MongoCollection mongoCollection = mongoDatabase.getCollection("metadata");
        String name = "experiment-"+new java.util.Date().getTime();
        Document collection_name = new Document("current_collection", name);
        mongoCollection.findOneAndReplace(new Document(), collection_name);
        mongoDatabase.createCollection(name);
        System.out.println(collection_name.toJson());
        mongoCollection.find().forEach(printBlock);
    }

    public InitialDataManager(){
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase mongoDatabase = mongoClient.getDatabase(EXPERIMENTAL);
            MongoCollection mongoCollection = mongoDatabase.getCollection("metadata");
            collection_name = ((Document) mongoCollection.find().first()).get("current_collection").toString();
            System.out.println("Reading from" + collection_name);
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    /**
     * This is an example of how to write in Mongo.
     */
    @SuppressWarnings("unchecked")
    public String write(String writeString){
        MongoDatabase mongoDatabase = mongoClient.getDatabase(EXPERIMENTAL);
        MongoCollection mongoCollection = mongoDatabase.getCollection(collection_name);
        Document document = new Document("seq", writeString);
        mongoCollection.insertOne(document);

        String id = document.get("_id").toString();
        System.out.println(id);
        return id;
    }

    /**
     * This is an example of how to read in Mongo.
     */
    public String read(String id){
        MongoDatabase db = mongoClient.getDatabase(EXPERIMENTAL);
        MongoCollection col = db.getCollection(collection_name);
        Bson query = new Document("_id", new ObjectId(id));
        Object o = col.find(query).first();
        return o.toString();
    }

}
