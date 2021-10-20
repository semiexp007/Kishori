package com.uietsocial.kishori.model;

public class Imagepost {
    String name ,url, text,date,userpic,userUid;

    public Imagepost() {
    }

    public Imagepost(String name, String url, String text, String date,String userpic,String userUid) {
        this.name = name;
        this.url = url;
        this.text = text;
        this.date = date;
        this.userpic=userpic;
        this.userUid=userUid;

    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
