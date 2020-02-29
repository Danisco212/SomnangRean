package com.example.somnangrean.WebServiceHandlers;

import com.example.somnangrean.Models.Answer.APIAnswers;
import com.example.somnangrean.Models.Answer.PostAnswer;
import com.example.somnangrean.Models.Question.PostQuestion;
import com.example.somnangrean.Models.Question.Question;
import com.example.somnangrean.Models.ResponseString;
import com.example.somnangrean.Models.User.ActivityUser;
import com.example.somnangrean.Models.User.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.temporal.ValueRange;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceToJsonHandler {

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.127:8012/EmailAuthentication/public/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    // retrofit declaring the interfaces functions

    WebServiceHandler webServiceHandler = retrofit.create(WebServiceHandler.class);

    public Call<ActivityUser> loginUser(User user){
        Call<ActivityUser> call = webServiceHandler.loginUser(user);

        return call;
    }

    public Call<Question> recentQuestions(){
        Call<Question> call = webServiceHandler.recentQuestions();
        return call;
    }

    public Call<APIAnswers> questionAnswers(int q_id){
        Call<APIAnswers> call = webServiceHandler.questionAnswers(q_id);
        return call;
    }

    public Call<Void> askQuestion(String token, PostQuestion question){
        Call<Void> call = webServiceHandler.askQuestion(token, question);
        return call;
    }

    public Call<Void> postAnswer(String token, PostAnswer answer){
        Call<Void> call = webServiceHandler.postAnswer(token, answer);
        return call;
    }

    public Call<Void> upvote(String token, int id){
        Call<Void> call = webServiceHandler.upvote(token, id);
        return call;
    }

    public Call<Void> downvote(String token, int id){
        Call<Void> call = webServiceHandler.downvote(token, id);
        return call;
    }

    public Call<Question> categoryQuestion(String category){
        Call<Question> call = webServiceHandler.categoryQuestions(category);
        return call;
    }
}
