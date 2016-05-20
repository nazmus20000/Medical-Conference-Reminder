package com.crossover.medicalconferencereminder;

/**
 * Created by acer on 2/13/2016.
 */
public class person {

    private String name;
    private String password;
    private String email;
    private String type;
    private String image;

    public person(String email, String password, String name, String image, String type) {
        this.image = image;
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
