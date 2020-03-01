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
import com.example.somnangrean.Models.User.ActivityUser;
import com.example.somnangrean.Models.User.RegisterUser;
import com.example.somnangrean.R;
import com.example.somnangrean.WebServiceHandlers.WebServiceToJsonHandler;
import com.example.somnangrean.helpers.VerifyInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private Button signUp;
    private EditText emailText;
    private EditText userNameText;
    private EditText passwordText;
    private EditText passwordConfirmationText;
    private TextView failedText;
    private VerifyInput verifyInput;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeUI();
    }

    private void initializeUI(){
        verifyInput = new VerifyInput();
        signUp = findViewById(R.id.signup);
        emailText = findViewById(R.id.signupEmail);
        userNameText = findViewById(R.id.signupUser);
        passwordText = findViewById(R.id.signupPassword);
        passwordConfirmationText = findViewById(R.id.signupPasswordConf);
        failedText = findViewById(R.id.signupFailure);
        loading = findViewById(R.id.signupProgess);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loading.getVisibility() == View.GONE) {
                    if (verifyInput.verifyAll(userNameText, emailText, passwordText, passwordConfirmationText, failedText)) {
                        if (verifyInput.verifyEmail(emailText, failedText)) {
                            if (verifyInput.verifyPassword(passwordText, passwordConfirmationText, failedText)) {
                                loading.setVisibility(View.VISIBLE);
                                failedText.setVisibility(View.GONE);
                                Call<ActivityUser> call = new WebServiceToJsonHandler().registerUser(
                                  new RegisterUser(emailText.getText().toString(),
                                          passwordText.getText().toString(),
                                          passwordConfirmationText.getText().toString(),
                                          userNameText.getText().toString())
                                );
                                call.enqueue(new Callback<ActivityUser>() {
                                    @Override
                                    public void onResponse(Call<ActivityUser> call, Response<ActivityUser> response) {
                                        if (!response.isSuccessful()){
                                            if (response.code()==404){
                                                errorMessage("Email already taken");
                                            }
                                            return;
                                        }
                                        ActivityUser user = response.body();
                                        registerUser(user);
                                    }

                                    @Override
                                    public void onFailure(Call<ActivityUser> call, Throwable t) {
                                        errorMessage(t.getMessage());
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
    }

    private void registerUser(ActivityUser user){
        UserStateController userStateController;
        new UserStateController();
        userStateController = UserStateController.userStateController;
        userStateController.setState(UserStateController.userState.loggedin);
        UserStateController.activeUser = user;
        Intent userIntent = new Intent();
        userIntent.setClass(this, MainActivity.class);

        userIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(userIntent);
        this.finish();
    }

    private void errorMessage(String message){
        loading.setVisibility(View.GONE);
        failedText.setVisibility(View.VISIBLE);
        failedText.setText(message);
    }

    public void logIn(View v){
        Intent i = new Intent();
        i.setClass(this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (loading.getVisibility()==View.VISIBLE){

        }else{
            super.onBackPressed();
        }
    }
}
