package com.example.movieuitemplate.models;

import java.util.List;

public class Movie {

    private int id;
    private String title;
    private String description;
    private String thumbnail;
    private List<MovieCompanie> companies;
    private String rating;
    private String coverPhoto;

    public Movie() {
    }

    public Movie(String title, String thumbnail, String coverPhoto) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.coverPhoto = coverPhoto;
    }

    public Movie(String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Movie(String title, String description, String thumbnail, List<MovieCompanie> companies, String rating) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.companies = companies;
        this.rating = rating;
    }

    public Movie(int id, String title, String description, String thumbnail, List<MovieCompanie> companies, String rating, String coverPhoto) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.companies = companies;
        this.rating = rating;
        this.coverPhoto = coverPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<MovieCompanie> getCompanies() {
        return companies;
    }

    public void setCompanies(List<MovieCompanie> companies) {
        this.companies = companies;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
