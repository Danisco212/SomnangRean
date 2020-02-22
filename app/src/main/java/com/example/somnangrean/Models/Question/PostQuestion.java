package com.example.somnangrean.Models.Question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostQuestion {
    private Integer id;
    private String body;

    private int category_id;

    public PostQuestion(String body, int category_id) {
        this.body = body;
        this.category_id = category_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getCategory_ID() {
        return category_id;
    }

    public void setCategory_ID(int category_ID) {
        this.category_id = category_id;
    }
}
