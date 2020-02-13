package com.example.somnangrean.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.somnangrean.Controllers.UserStateController;
import com.example.somnangrean.Models.Question.PostQuestion;
import com.example.somnangrean.Models.User.ActivityUser;
import com.example.somnangrean.Models.User.User;
import com.example.somnangrean.R;
import com.example.somnangrean.WebServiceHandlers.WebServiceToJsonHandler;
import com.example.somnangrean.helpers.VerifyInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private Button loginBtn;
    private EditText emailText;
    private EditText passwordText;
    private VerifyInput verifyInput;
    private TextView failedText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeUI();
    }

    private void initializeUI(){
        verifyInput = new VerifyInput();
        loginBtn = findViewById(R.id.loginBtn);
        emailText = findViewById(R.id.loginEmail);
        passwordText = findViewById(R.id.loginPassword);
        failedText = findViewById(R.id.loginFailure);
        progressBar = findViewById(R.id.loginProgress);


        loginBtn.setOnClickListener(v -> {
            // resetting the view
            progressBar.setVisibility(View.GONE);
            failedText.setVisibility(View.GONE);
            // handles login
            if (verifyInput.verifyAll(emailText, passwordText, failedText)){
                if (verifyInput.verifyEmail(emailText, failedText)){
                    // login
                    progressBar.setVisibility(View.VISIBLE);
                    Call<ActivityUser> call = new WebServiceToJsonHandler().loginUser(new User("danielchibuikem@gmail.com", "123456"));
                    // calling the asynchronous task
                    call.enqueue(new Callback<ActivityUser>() {
                        @Override
                        public void onResponse(Call<ActivityUser> call, Response<ActivityUser> response) {
                            if (!response.isSuccessful()){return;}
                            ActivityUser activityUser = response.body();
                            loginUser(activityUser);
                        }
                        @Override
                        public void onFailure(Call<ActivityUser> call, Throwable t) {
                            errorMessage(t.getMessage());
                        }
                    });
                }else{return;} // invalid email
            }else{ return;} // empty fields

        });
    }

    // send user information to app
    private void loginUser(ActivityUser user){
        UserStateController userStateController;
        new UserStateController();
        userStateController = UserStateController.userStateController;
        userStateController.setState(UserStateController.userState.loggedin);
        UserStateController.activeUser = user;
        Intent userIntent = new Intent();

        Bundle bundle = getIntent().getExtras();
        if (bundle==null){
            userIntent.setClass(this, MainActivity.class);
        }else{
            userIntent.setClass(this, AskQuestion.class);
        }
        userIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(userIntent);
        this.finish();
    }
    private void errorMessage(String message){
        progressBar.setVisibility(View.GONE);
        failedText.setVisibility(View.VISIBLE);
        failedText.setText(message);
    }
    // to sign in page
    public void signIn(View v){
        if (progressBar.getVisibility()==View.VISIBLE){
            return;
        }else{
            Intent i = new Intent();
            i.setClass(this, Register.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        if (progressBar.getVisibility()==View.VISIBLE){
            return;
        }else{
            super.onBackPressed();
        }
    }
}
