package com.example.somnangrean.Models.Question;

public class Question {
    private int current_page;
    private ListQuestion[] data;
    private int total;
    private String next_page_url;

    public Question(int current_page, ListQuestion[] data, int total, String next_page_url) {
        this.current_page = current_page;
        this.data = data;
        this.total = total;
        this.next_page_url = next_page_url;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public ListQuestion[] getData() {
        return data;
    }

    public void setData(ListQuestion[] data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }
}
