package com.example.movieuitemplate.models;

public class Cast {

    String name;
    String character;
    String imgLink;

    public Cast() {
    }

    public Cast(String name, String character, String imgLink) {
        this.name = name;
        this.character = character;
        this.imgLink = imgLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
