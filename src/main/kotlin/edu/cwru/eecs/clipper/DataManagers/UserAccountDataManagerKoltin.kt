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
    private val COLLECTION = "user_accounts1"
    private var mongoClient = MongoClient(MongoClientURI("mongodb://localhost:27017"))
    private var mongoCollection = mongoClient.getDatabase(DATABASE).getCollection(COLLECTION)

    fun createUserAccount(): UserAccount {
        val newUser = Document()
        mongoCollection.insertOne(newUser)
        val user = convertToUserAccountForNotNullResult(result=newUser)
        user.email=""
        user.facebookId=""
        user.primaryOrganization=""
        user.twitterId=""
        mongoCollection.findOneAndReplace(newUser,convertToDocument(user))
        println("userCreated")
        return user
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
                result["_id"].toString(),
                result["facebook_id"].toString(),
                result["twitter_id"].toString(),
                result["primary_organization"].toString(),
                result["email"].toString())
    }

    internal fun convertToDocument(userAccount: UserAccount): Document {
        val result = Document()
        result["facebook_id"] = userAccount.facebookId
        result["twitter_id"] = userAccount.twitterId
        result["primary_organization"] = userAccount.primaryOrganization
        result["_id"] = ObjectId(userAccount.userId)
        result["email"] = userAccount.email
        return result
    }
}