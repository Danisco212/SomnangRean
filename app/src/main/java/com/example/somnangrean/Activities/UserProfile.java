package com.example.somnangrean.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.somnangrean.Controllers.UserStateController;
import com.example.somnangrean.Models.User.ActivityUser;
import com.example.somnangrean.R;
import com.example.somnangrean.WebServiceHandlers.WebServiceToJsonHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity {
    private TextView username;
    private TextView email;
    private TextView id;
    private TextView rating;
    private LinearLayout user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initializeUI();
    }

    private void initializeUI(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(UserStateController.activeUser.getUserName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        id = findViewById(R.id.id);
        user = findViewById(R.id.user);
        rating = findViewById(R.id.rating);
        user.setVisibility(View.GONE);
        userDetails();
    }

    // getting updated user information
    private void userDetails(){
        Call<ActivityUser> call = new WebServiceToJsonHandler().profileInfo(getIntent().getExtras().getInt("id"));
        call.enqueue(new Callback<ActivityUser>() {
            @Override
            public void onResponse(Call<ActivityUser> call, Response<ActivityUser> response) {
                if (!response.isSuccessful()){
                    return;
                }
                user.setVisibility(View.VISIBLE);
                ActivityUser user = response.body();
                UserStateController.activeUser.setUserName(user.getUserName());
                UserStateController.activeUser.setRating(user.getRating());
                UserStateController.activeUser.setEmail(user.getEmail());
                username.setText(user.getUserName());
                email.setText(user.getEmail());
                rating.setText(id.getText()+String.valueOf(user.getRating()));
                id.setText(id.getText()+String.valueOf(user.getId()));
            }

            @Override
            public void onFailure(Call<ActivityUser> call, Throwable t) {
                Toast.makeText(UserProfile.this, "Cant connect to the database, Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
