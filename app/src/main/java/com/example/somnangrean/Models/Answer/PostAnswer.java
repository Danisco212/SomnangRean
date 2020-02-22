package com.example.somnangrean.Models.Answer;

public class PostAnswer {
    private String body;
    private int question_id;

    public PostAnswer(String body, int question_id) {
        this.body = body;
        this.question_id = question_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
