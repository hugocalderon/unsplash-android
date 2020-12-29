package com.example.myapplication.retrofit.models;

public class UserJson {
    private String name;
    private String username;
    private ProfileImagesJson profile_image;
    private String instagram_username;

    public UserJson() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileImagesJson getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(ProfileImagesJson profile_image) {
        this.profile_image = profile_image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInstagram_username() {
        return instagram_username;
    }

    public void setInstagram_username(String instagram_username) {
        this.instagram_username = instagram_username;
    }
}
