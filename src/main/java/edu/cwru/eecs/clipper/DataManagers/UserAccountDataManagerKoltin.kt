package edu.cwru.eecs.clipper.DataManagers

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import edu.cwru.eecs.clipper.Models.UserAccount
import org.bson.Document
import org.bson.types.ObjectId
import java.util.*


class UserAccountDataManagerKoltin constructor(){

    private val DATABASE = "experimental"
    private val COLLECTION = "user_accounts"
    private var mongoClient = MongoClient(MongoClientURI("mongodb://localhost:27017"))
    private var mongoCollection = mongoClient.getDatabase(DATABASE).getCollection(COLLECTION)

    fun createUserAccount(): UserAccount {
        val newUser = Document()
        mongoCollection?.insertOne(newUser)
        return UserAccountDataManager.convertToUserAccountForNotNullResult(newUser);
    }

    fun findByFacebookID(facebookId: String): Optional<UserAccount> {
        val query = Document("facebook_id", facebookId)
        val userAccountOptional = convertToUserAccount(mongoCollection.find(query).first())
        return userAccountOptional
    }


    fun findByUserID(userId: String): Optional<UserAccount> {
        val query = Document("_id", ObjectId(userId))
        val userAccountOptional = convertToUserAccount(
                mongoCollection.find(query).first())
        return userAccountOptional
    }

    fun updateUserAccount(userAccount: UserAccount) {
        val query = Document("_id", ObjectId(userAccount.userId))
        mongoCollection.findOneAndReplace(query, convertToDocument(userAccount))
    }

    fun deleteUserAccountById(userAccount: UserAccount) {
        val query = Document("user_id", userAccount.userId)
        val deleted = convertToDocument(userAccount).append("isDeleted", true)
        mongoCollection.findOneAndUpdate(query, convertToDocument(userAccount))
    }

    internal fun convertToUserAccount(result: Document?): Optional<UserAccount> {
        var userAccountOptional = Optional.empty<UserAccount>()
        if (result != null && !result.getBoolean("isDeleted", false)) {
            val userAccount = convertToUserAccountForNotNullResult(result)
            userAccountOptional = Optional.of(userAccount)
        }
        return userAccountOptional
    }

    internal fun convertToUserAccountForNotNullResult(result: Document): UserAccount {
        println(result.toJson())
        return UserAccount(
                Objects.toString(result["_id"]),
                Objects.toString(result["facebook_id"]),
                Objects.toString(result["twitter_id"]),
                Objects.toString(result["primary_organization"]),
                Objects.toString(result["email"]))
    }

    internal fun convertToDocument(userAccount: UserAccount): Document {
        val result = Document()
        result.put("_id", ObjectId(userAccount.userId))
        result.put("facebook_id", userAccount.facebookId)
        result.put("twitter_id", userAccount.twitterId)
        result.put("primary_organization", userAccount.primaryOrganization)
        result.put("email", userAccount.email)
        return result
    }
}