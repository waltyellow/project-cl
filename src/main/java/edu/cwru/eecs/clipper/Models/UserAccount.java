package edu.cwru.eecs.clipper.Models;

import org.bson.Document;

public class UserAccount {

    public UserAccount(String userId, String facebookId, String twitterId, String primaryOrganization, String email) {
        this.userId = userId;
        this.facebookId = facebookId;
        this.twitterId = twitterId;
        this.primaryOrganization = primaryOrganization;
        this.email = email;
    }

//    UserAccount(Document dbRecord){
//        this(
//            dbRecord.get("user_id").toString(),
//            dbRecord.get("facebook_id").toString(),
//            dbRecord.get("twitter_id").toString(),
//            dbRecord.get("primary_organization").toString(),
//            dbRecord.get("email").toString()
//        );
//    }

    public String getUserId() {
        return userId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getPrimaryOrganization() {
        return primaryOrganization;
    }

    public void setPrimaryOrganization(String primaryOrganization) {
        this.primaryOrganization = primaryOrganization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String userId = "";
    private String facebookId = "";
    private String twitterId ="";
    private String primaryOrganization = "";
    private String email = "";

}
