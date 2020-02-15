package com.example.somnangrean.Models.Answer;

public class Answer extends PostAnswer {
    private long rating;

    public Answer(int user_id, String body, int question_id, long rating) {
        super(user_id, body, question_id);
        this.rating = rating;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}
