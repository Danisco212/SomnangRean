package com.example.somnangrean.WebServiceHandlers;

import com.example.somnangrean.Models.Question.Question;
import com.example.somnangrean.Models.User.ActivityUser;
import com.example.somnangrean.Models.User.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebServiceHandler {

    @POST("auth/login")
    Call<ActivityUser> loginUser(@Body User user);

    @GET("auth/getquestions")
    Call<Question> recentQuestions();
}
