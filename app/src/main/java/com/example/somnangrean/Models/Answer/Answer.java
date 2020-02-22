package com.example.somnangrean.Models.Answer;

public class Answer extends PostAnswer {
    private long rating;

    public Answer(String body, int question_id, long rating) {
        super(body, question_id);
        this.rating = rating;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}
