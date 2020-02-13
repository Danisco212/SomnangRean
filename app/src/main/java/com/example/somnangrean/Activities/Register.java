package com.example.somnangrean.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.somnangrean.R;
import com.example.somnangrean.helpers.VerifyInput;

public class Register extends AppCompatActivity {

    private Button signUp;
    private EditText emailText;
    private EditText userNameText;
    private EditText passwordText;
    private EditText passwordConfirmationText;
    private TextView failedText;
    private VerifyInput verifyInput;

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

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyInput.verifyAll(userNameText, emailText, passwordText, passwordConfirmationText, failedText)){
                    if (verifyInput.verifyEmail(emailText, failedText)){
                        if (verifyInput.verifyPassword(passwordText, passwordConfirmationText, failedText)){

                        }else{
                            // password doesn't match
                            return;
                        }
                    }else{
                        // false email
                        return;
                    }
                }else{
                    // empty fields
                    return;
                }
            }
        });
    }

    public void logIn(View v){
        Intent i = new Intent();
        i.setClass(this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }
}
