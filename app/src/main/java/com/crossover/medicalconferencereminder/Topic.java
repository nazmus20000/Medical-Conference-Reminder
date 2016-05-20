package com.crossover.medicalconferencereminder;

/**
 * Created by nazmu on 5/8/2016.
 */
public class Topic {

    private String title;
    private String description;
    private String doctoremail;
    private String date;
    private int id;

    public Topic(int id, String title, String description, String doctoremail, String date) {
        this.title = title;
        this.description = description;
        this.doctoremail = doctoremail;
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

    public String getDoctoremail() {
        return doctoremail;
    }

    public void setDoctoremail(String doctoremail) {
        this.doctoremail = doctoremail;
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
