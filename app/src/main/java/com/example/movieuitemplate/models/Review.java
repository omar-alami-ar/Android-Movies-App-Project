package com.example.movieuitemplate.models;

public class Review {
    //public int id;
    public String username,reviewText,movieId,userId;

    public Review() {
    }

    public Review(String username, String reviewText, String userId) {
        this.username = username;
        this.reviewText = reviewText;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
