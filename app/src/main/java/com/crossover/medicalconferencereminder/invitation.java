package com.crossover.medicalconferencereminder;

/**
 * Created by nazmu on 5/9/2016.
 */
public class invitation {
    private String title;
    private String description;
    private String location;
    private String date;
    private int id;

    public invitation(String title, String description, String location, String date, int id) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
