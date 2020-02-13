package com.example.somnangrean.Models.Question;

public class ListQuestion {
    private int id;
    private String body;
    private String category_name;
    private String user_name;

    public ListQuestion(int id, String body, String category_name, String user_name) {
        this.id = id;
        this.body = body;
        this.category_name = category_name;
        this.user_name = user_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
