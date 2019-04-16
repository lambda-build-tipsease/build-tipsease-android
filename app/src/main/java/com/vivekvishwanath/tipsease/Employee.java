package com.vivekvishwanath.tipsease;

public class Employee {

    private String firstName, lastName, bio, tagline, workplace, serviceType
            , username, password, email, imageUrl, timeAtJob;
    private int id, numOfRatings;
    private double accountBalance, rating;

    public Employee() {

    }

    public Employee(String firstName, String lastName, String bio, String workplace, String username, String email, String imageUrl,
                    String tagline, String serviceType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.workplace = workplace;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.tagline = tagline;
        this.serviceType = serviceType;
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

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getTimeAtJob() {
        return timeAtJob;
    }

    public void setTimeAtJob(String timeAtJob) {
        this.timeAtJob = timeAtJob;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumOfRatings() {
        return numOfRatings;
    }

    public void setNumOfRatings(int numOfRatings) {
        this.numOfRatings = numOfRatings;
    }
}
