package com.example.Models;

import jdk.internal.util.xml.impl.Pair;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baraa on 12/11/2016.
 */

@Document
public class UserPosts {
    @Id
    private String postTitle;
    private String postText;
    private String userName;
    private ArrayList<Comments> comments;

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    @Override
    public String toString() {
        return "UserPosts{" +
                "postTitle='" + postTitle + '\'' +
                ", postText='" + postText + '\'' +
                '}';
    }
}
