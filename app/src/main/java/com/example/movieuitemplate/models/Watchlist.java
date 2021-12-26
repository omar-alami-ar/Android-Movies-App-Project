package com.example.movieuitemplate.models;

import java.util.List;

public class Watchlist {

    private String id,userId,name;
    List<String> movies;

    public Watchlist() {
    }

    public Watchlist(String id, String userId, String name, List<String> movies) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.movies = movies;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMovies() {
        return movies;
    }

    public void setMovies(List<String> movies) {
        this.movies = movies;
    }
}
