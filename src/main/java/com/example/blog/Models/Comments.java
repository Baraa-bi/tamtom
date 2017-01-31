package com.example.Models;

/**
 * Created by baraa on 12/12/2016.
 */
public class Comments {

    private String userName;
    private String comment;

    public Comments(String userName, String comment) {
        this.userName = userName;
        this.comment = comment;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        comment = comment;
    }
}
