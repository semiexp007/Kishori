package com.uietsocial.kishori.model;

public class User {

    String name;
    String id;
    String issue;
    String profilePicUrl;
    String uid;
    String status;
    public User() {
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User(String name, String id, String issue, String profilePicUrl, String uid, String status) {
        this.name = name;
        this.id = id;
        this.issue = issue;
        this.profilePicUrl=profilePicUrl;
        this.uid=uid;
        this.status=status;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
