package com.example.somnangrean.Models.User;

public class RegisterUser extends User {
    private String passwordConf;
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
