package com.example.movieuitemplate.models;

public class Genre {
    private String title;
    private int img;

    public Genre(String title, int img) {
        this.title = title;
        this.img = img;
    }

    public Genre() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
