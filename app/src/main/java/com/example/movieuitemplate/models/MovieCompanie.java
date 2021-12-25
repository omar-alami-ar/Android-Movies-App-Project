package com.example.movieuitemplate.models;

public class MovieCompanie {

    private String name;
    private String img;
    private String country;

    public MovieCompanie(String name, String img, String country) {
        this.name = name;
        this.img = img;
        this.country = country;
    }

    public MovieCompanie() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
