package edu.cwru.eecs.clipper.DataManagers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import edu.cwru.eecs.clipper.Models.UserAccount;
import java.util.Optional;
import org.bson.Document;


public class UserAccountDataManager {

  private final String DATABASE = "experimental";
  private final String COLLECTION = "user_accounts";
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
    Document newUser = new Document("userId", "Placeholder");
    return convertToUserAccountForNotNullResult(newUser);
  }

  public Optional<UserAccount> findByFacebookID(String facebookId) {
    Document query = new Document("facebook_id", facebookId);
    Document result = (Document) mongoCollection.find(query).first();
    Optional<UserAccount> userAccountOptional = convertToUserAccount(result);
    return userAccountOptional;
  }


  public Optional<UserAccount> findByUserID(String userId) {
    Document query = new Document("user_id", userId);
    Document result = (Document) mongoCollection.find(query).first();
    Optional<UserAccount> userAccountOptional = convertToUserAccount(
        result);
    return userAccountOptional;
  }

  public void updateUserAccount(UserAccount userAccount) {
    Document query = new Document("user_id", userAccount.getUserId());
    mongoCollection.findOneAndUpdate(query, convertToDocument(userAccount));
  }

  public void deleteUserAccountById(UserAccount userAccount) {
    Document query = new Document("user_id", userAccount.getUserId());
    Document deleted = convertToDocument(userAccount).append("isDeleted", true);
    mongoCollection.findOneAndUpdate(query, convertToDocument(userAccount));
  }

  private static Optional<UserAccount> convertToUserAccount(Document result) {
    Optional<UserAccount> userAccountOptional = Optional.empty();
    if ((result != null) && !result.getBoolean("isDeleted")) {
      UserAccount userAccount = convertToUserAccountForNotNullResult(result);
      userAccountOptional = Optional.of(userAccount);
    }
    return userAccountOptional;
  }

  static UserAccount convertToUserAccountForNotNullResult(Document result) {
    return new UserAccount(
        result.get("user_id").toString(),
        result.get("facebook_id").toString(),
        result.get("twitter_id").toString(),
        result.get("primary_organization").toString(),
        result.get("email").toString());
  }

  static Document convertToDocument(UserAccount userAccount) {
    Document result = new Document();
    result.put("user_id", userAccount.getUserId());
    result.put("facebook_id", userAccount.getFacebookId());
    result.put("twitter_id", userAccount.getTwitterId());
    result.put("primary_organization", userAccount.getPrimaryOrganization());
    result.put("email", userAccount.getEmail());
    return result;
  }
}
