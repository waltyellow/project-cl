package edu.cwru.eecs.clipper.DataManagers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import edu.cwru.eecs.clipper.Models.UserAccount;
import java.util.Objects;
import java.util.Optional;
import org.bson.Document;
import org.bson.types.ObjectId;


public class UserAccountDataManager {

  private final String DATABASE = "experimental";
  private final String COLLECTION = "user_accounts1";
  private MongoClient mongoClient;
  private MongoCollection mongoCollection;

  public UserAccountDataManager() {
    try {
      mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
      mongoCollection = mongoClient.getDatabase(DATABASE).getCollection(COLLECTION);
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  public UserAccount createUserAccount() {
    Document newUser = new Document();
    mongoCollection.insertOne(newUser);
    return convertToUserAccountForNotNullResult(newUser);
  }

  public Optional<UserAccount> findByFacebookID(String facebookId) {
    Document query = new Document("facebook_id", facebookId);
    Document result = (Document) mongoCollection.find(query).first();
    Optional<UserAccount> userAccountOptional = convertToUserAccount(result);
    return userAccountOptional;
  }


  public Optional<UserAccount> findByUserID(String userId) {
    Document query = new Document("_id", new ObjectId(userId));
    Document result = (Document) mongoCollection.find(query).first();
    Optional<UserAccount> userAccountOptional = convertToUserAccount(
        result);
    return userAccountOptional;
  }

  public void updateUserAccount(UserAccount userAccount) {
    Document query = new Document("_id", new ObjectId(userAccount.getUserId()));
    mongoCollection.findOneAndReplace(query, convertToDocument(userAccount));
  }

  public void deleteUserAccountById(UserAccount userAccount) {
    Document query = new Document("user_id", userAccount.getUserId());
    Document deleted = convertToDocument(userAccount).append("isDeleted", true);
    mongoCollection.findOneAndUpdate(query, convertToDocument(userAccount));
  }

  static Optional<UserAccount> convertToUserAccount(Document result) {
    Optional<UserAccount> userAccountOptional = Optional.empty();
    if ((result != null) && !result.getBoolean("isDeleted",false)) {
      UserAccount userAccount = convertToUserAccountForNotNullResult(result);
      userAccountOptional = Optional.of(userAccount);
    }
    return userAccountOptional;
  }

  static UserAccount convertToUserAccountForNotNullResult(Document result) {
    System.out.println(result.toJson());
    return new UserAccount(
        Objects.toString(result.get("_id")),
        Objects.toString(result.get("facebook_id")),
        Objects.toString(result.get("twitter_id")),
        Objects.toString(result.get("primary_organization")),
        Objects.toString(result.get("email")));
  }

  static Document convertToDocument(UserAccount userAccount) {
    Document result = new Document();
    result.put("_id", new ObjectId(userAccount.getUserId()));
    result.put("facebook_id", userAccount.getFacebookId());
    result.put("twitter_id", userAccount.getTwitterId());
    result.put("primary_organization", userAccount.getPrimaryOrganization());
    result.put("email", userAccount.getEmail());
    return result;
  }
}
