package edu.cwru.eecs.clipper.Models

class UserAccount(userId: String, facebookId: String, twitterId: String, primaryOrganization: String, email: String) {
    var userId = ""
    var facebookId = ""
    var twitterId = ""
    var primaryOrganization = ""
    var email = ""

    init {
        this.userId = userId
        this.facebookId = facebookId
        this.twitterId = twitterId
        this.primaryOrganization = primaryOrganization
        this.email = email
    }


}
