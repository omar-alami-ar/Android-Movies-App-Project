package com.example.movieuitemplate.models;

public class Review {
    //public int id;
    public String username,reviewText;

    public Review() {
    }

    public Review(String username, String reviewText) {
        this.username = username;
        this.reviewText = reviewText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
