package com.example.somnangrean.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.somnangrean.Controllers.UserStateController;
import com.example.somnangrean.Models.Question.PostQuestion;
import com.example.somnangrean.R;
import com.example.somnangrean.WebServiceHandlers.WebServiceToJsonHandler;
import com.example.somnangrean.helpers.VerifyInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskQuestion extends AppCompatActivity {

    private Spinner spinner;
    private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        initializeUI();
    }

    private void initializeUI(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ask Question");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner = findViewById(R.id.category);
        loading = findViewById(R.id.postingQuestion);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (loading.getVisibility()==View.GONE){
            this.finish();
            startActivity(new Intent().setClass(this, MainActivity.class));
        }else{
            Toast.makeText(this, "Cant go back while posting question", Toast.LENGTH_SHORT);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.askQuestion:
                if (loading.getVisibility()==View.GONE){
                    askQuestion();
                }
                break;
            case android.R.id.home:
                if (loading.getVisibility()==View.GONE){
                    this.finish();
                    startActivity(new Intent().setClass(this, MainActivity.class));
                }else{
                    Toast.makeText(this, "Cant go back while posting question", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void askQuestion(){
        loading.setVisibility(View.VISIBLE);
        int catID = spinner.getSelectedItemPosition()+1;
        EditText body = findViewById(R.id.questionBody);
        PostQuestion question = new PostQuestion(body.getText().toString().trim(), catID);
        UserStateController userStateController;
        new UserStateController();
        userStateController = UserStateController.userStateController;
        if (new VerifyInput().verifyEditText(body)){
            Toast.makeText(getApplicationContext(), "Question asked", Toast.LENGTH_SHORT).show();
            Call<Void> call = new WebServiceToJsonHandler().askQuestion("Bearer " + userStateController.activeUser.getToken(), question);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()){
                        Log.e("why", "why did u fail");
                        loading.setVisibility(View.GONE);
                        Toast.makeText(AskQuestion.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    loading.setVisibility(View.GONE);
                    Intent userIntent = new Intent();
                    userIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    userIntent.setClass(AskQuestion.this, MainActivity.class);
                    startActivity(userIntent);
                    AskQuestion.this.finish();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("Nt Posted", t.getMessage());
                    loading.setVisibility(View.GONE);
                    Toast.makeText(AskQuestion.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            return;
        }

    }
}
