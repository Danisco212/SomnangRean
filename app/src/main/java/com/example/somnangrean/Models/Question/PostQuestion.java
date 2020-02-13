package com.example.somnangrean.Models.Question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostQuestion {
    private Integer id;
    private int user_id;
    private String body;

    @SerializedName("category_id")
    @Expose
    private int category_ID;

    public PostQuestion(int user_id, String body, int category_ID) {
        this.user_id = user_id;
        this.body = body;
        this.category_ID = category_ID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getCategory_ID() {
        return category_ID;
    }

    public void setCategory_ID(int category_ID) {
        this.category_ID = category_ID;
    }
}
