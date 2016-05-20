package com.crossover.medicalconferencereminder;

/**
 * Created by nazmu on 5/9/2016.
 */
public class Particularinvitation {
    private String title;
    private String description;
    private String location;
    private String date;
    private String status;

    public Particularinvitation(String title, String description, String location, String date, String status) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
