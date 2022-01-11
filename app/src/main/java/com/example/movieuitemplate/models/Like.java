package com.example.movieuitemplate.models;

public class Like {
    private String movieID;
    private String movieName;

    public Like() {
    }

    public Like(String movieID, String movieName) {
        this.movieID = movieID;
        this.movieName = movieName;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
