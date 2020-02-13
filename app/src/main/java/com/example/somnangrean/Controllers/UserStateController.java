package com.example.somnangrean.Controllers;

import com.example.somnangrean.Models.User.ActivityUser;

public class UserStateController {
    public enum userState {loggedin, loggedout};

    private userState userState;

    public static UserStateController userStateController;
    public static ActivityUser activeUser;

    public UserStateController(){
        if (userStateController==null){
            userStateController =this;
        }
    }

    public void setState(userState userState){
        this.userState = userState;
    }

    public userState getState(){
        return this.userState;
    }
}
