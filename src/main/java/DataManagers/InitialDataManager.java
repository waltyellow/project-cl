package DataManagers;

import com.mongodb.*;
import org.bson.types.ObjectId;

/**
 * Created by zh on 2/27/17.
 */
public class InitialDataManager {
    MongoClient mongoClient;

    public InitialDataManager(){
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    public String write(String writeString){
        DB db = mongoClient.getDB( "test" );
        DBCollection col = db.getCollection("test1");
        BasicDBObject doc = new BasicDBObject("seq", writeString);
        WriteResult result = col.insert(doc);
        return doc.get("_id").toString();
    }

    public String read(String id){
        DB db = mongoClient.getDB( "test" );
        DBCollection col = db.getCollection("test1");
        BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
        DBCursor cursor = col.find(query);
        try {
            while(cursor.hasNext()) {
                return (cursor.next().toString());
            }
        } finally {
            cursor.close();
        }
        return "";
    }

}
