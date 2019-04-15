package com.vivekvishwanath.tipsease;

public class Employee {

    private String firstName, lastName, bio, workplace
            , username, email, imageUrl;

    public Employee() {

    }

    public Employee(String firstName, String lastName, String bio, String workplace, String username, String email, String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.workplace = workplace;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
