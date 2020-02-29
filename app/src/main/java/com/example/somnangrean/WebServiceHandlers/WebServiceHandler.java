package com.example.somnangrean.WebServiceHandlers;

import com.example.somnangrean.Models.Answer.APIAnswers;
import com.example.somnangrean.Models.Answer.PostAnswer;
import com.example.somnangrean.Models.Question.PostQuestion;
import com.example.somnangrean.Models.Question.Question;
import com.example.somnangrean.Models.ResponseString;
import com.example.somnangrean.Models.User.ActivityUser;
import com.example.somnangrean.Models.User.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebServiceHandler {

    @POST("auth/login")
    Call<ActivityUser> loginUser(@Body User user);

    @GET("auth/getquestions")
    Call<Question> recentQuestions();

    @GET("auth/{q_id}/getanswers")
    Call<APIAnswers> questionAnswers(@Path("q_id") int questionID);

    @POST("auth/postquestion")
    Call<Void> askQuestion(@Header("Authorization") String token, @Body PostQuestion question);

    @POST("auth/postanswer")
    Call<Void> postAnswer(@Header("Authorization") String token, @Body PostAnswer answer);

    @PUT("auth/{id}/upvote")
    Call<Void> upvote(@Header("Authorization") String token, @Path("id") int id);

    @PUT("auth/{id}/downvote")
    Call<Void> downvote(@Header("Authorization") String token, @Path("id") int id);

    @GET("auth/{category}/category")
    Call<Question> categoryQuestions(@Path("category") String category);

}
