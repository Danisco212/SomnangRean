package com.example.somnangrean.Models.Answer;

public class APIAnswers {
    private String next_page_url;
    private int total;
    private Answer[] data;
    private int current_page;

    public APIAnswers(String next_page_url, int total, Answer[] data, int current_page) {
        this.next_page_url = next_page_url;
        this.total = total;
        this.data = data;
        this.current_page = current_page;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Answer[] getData() {
        return data;
    }

    public void setData(Answer[] data) {
        this.data = data;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }
}
