package com.example.movieuitemplate.models;

public class WatchListItem {
    private String name;
    private String id;

    public WatchListItem() {
    }

    public WatchListItem(String name) {
        this.name = name;
    }

    public WatchListItem(String id,String name) {
        this.name=name;
        this.id =id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
