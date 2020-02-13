package com.example.somnangrean.helpers;

import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.somnangrean.R;

import java.util.regex.Pattern;

/**
 * Class to handle all input verification
 * Author: Daniel Isaac
 */
public class VerifyInput {

    public boolean verifyAll(EditText username, EditText email, EditText password, EditText passwordConf, TextView failedText){
        if (verifyEditText(username) && verifyEditText(email) && verifyEditText(password) && verifyEditText(passwordConf)){
            return true;
        }
        failedText.setText(R.string.empty_fields);
        failedText.setVisibility(View.VISIBLE);
        return false;
    }

    public boolean verifyAll(EditText email, EditText password,TextView failedText){
        if (verifyEditText(email) && verifyEditText(password)){
            return true;
        }
        failedText.setText(R.string.empty_fields);
        failedText.setVisibility(View.VISIBLE);
        return false;
    }

    public boolean verifyEditText(EditText text){
        if (text.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    public boolean verifyEmail(EditText text, TextView failedText){
        if (Patterns.EMAIL_ADDRESS.matcher(text.getText().toString()).matches()){

            return true;
        }
        failedText.setText(R.string.invalid);
        failedText.setVisibility(View.VISIBLE);
        return false;
    }

    public boolean verifyPassword(EditText password, EditText passwordConf, TextView failedText){
        if (password.getText().toString().equals(passwordConf.getText().toString())){

            return true;
        }
        failedText.setText(R.string.invalid_password);
        failedText.setVisibility(View.VISIBLE);
        return false;
    }
}
