package com.example.somnangrean.WebServiceHandlers;

import com.example.somnangrean.Models.Answer.APIAnswers;
import com.example.somnangrean.Models.Question.Question;
import com.example.somnangrean.Models.User.ActivityUser;
import com.example.somnangrean.Models.User.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceHandler {

    @POST("auth/login")
    Call<ActivityUser> loginUser(@Body User user);

    @GET("auth/getquestions")
    Call<Question> recentQuestions();

    @GET("auth/{q_id}/getanswers")
    Call<APIAnswers> questionAnswers(@Path("q_id") int questionID);
}
