package com.example.somnangrean.Models.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterUser extends User {
    @SerializedName("password_confirmation")
    @Expose
    private String passwordConf;

    @SerializedName("name")
    @Expose
    private String username;

    public RegisterUser(String email, String password, String passwordConf, String username) {
        super(email, password);
        this.passwordConf = passwordConf;
        this.username = username;
    }

    public String getPasswordConf() {
        return passwordConf;
    }

    public void setPasswordConf(String passwordConf){
        this.passwordConf = passwordConf;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
