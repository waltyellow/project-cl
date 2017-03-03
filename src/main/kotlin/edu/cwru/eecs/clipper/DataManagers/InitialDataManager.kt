package edu.cwru.eecs.clipper.DataManagers

import com.mongodb.Block
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.bson.conversions.Bson
import org.bson.types.ObjectId


class InitialDataManager {

    private val EXPERIMENTAL = "experimental"
    private var mongoClient: MongoClient? = null
    private var collection_name: String? = null

    init {
        try {
            mongoClient = MongoClient(MongoClientURI("mongodb://localhost:27017"))
            val mongoDatabase = mongoClient!!.getDatabase(EXPERIMENTAL)
            val mongoCollection = mongoDatabase.getCollection("metadata")
            collection_name = (mongoCollection.find().first() as Document).get("current_collection")
                    .toString()
            System.out.println("Reading from" + collection_name!!)
        } catch (ex: Exception) {
            System.out.println(ex)
        }

    }

    /**
     * This is an example of how to write in Mongo.
     */
    @SuppressWarnings("unchecked")
    fun write(writeString: String): String {
        val mongoDatabase = mongoClient!!.getDatabase(EXPERIMENTAL)
        val mongoCollection = mongoDatabase.getCollection(collection_name)
        val document = Document("seq", writeString)
        mongoCollection.insertOne(document)

        val id = document.get("_id").toString()
        System.out.println(id)
        return id
    }

    /**
     * This is an example of how to read in Mongo.
     */
    fun read(id: String): String {
        val db = mongoClient!!.getDatabase(EXPERIMENTAL)
        val col = db.getCollection(collection_name)
        val query = Document("_id", ObjectId(id))
        val o = col.find(query).first()
        return o.toString()
    }

    companion object {

        internal val printBlock: Block<Document> = object : Block<Document> {

            override fun apply(document: Document) {
                System.out.println(document.toJson())
            }
        }

        @SuppressWarnings("unchecked")
        fun CreateCollection() {
            val mongoClient = MongoClient(MongoClientURI("mongodb://localhost:27017"))
            val mongoDatabase = mongoClient.getDatabase("experimental")
            val mongoCollection = mongoDatabase.getCollection("metadata")
            val name = "experiment-" + java.util.Date().getTime()
            val collection_name = Document("current_collection", name)
            mongoCollection.findOneAndReplace(Document(), collection_name)
            mongoDatabase.createCollection(name)
            System.out.println(collection_name.toJson())
            mongoCollection.find().forEach(printBlock)
        }
    }

}
