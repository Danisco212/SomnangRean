package com.example.somnangrean.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.somnangrean.Adapters.QuestionAnswersListAdapter;
import com.example.somnangrean.Models.Answer.APIAnswers;
import com.example.somnangrean.R;
import com.example.somnangrean.WebServiceHandlers.WebServiceToJsonHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Answers extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private QuestionAnswersListAdapter adapter;
    private TextView question;
    private TextView Username;
    private TextView category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        initializeUI();
    }

    private void initializeUI(){
        Username = findViewById(R.id.QuestionuserName);
        question = findViewById(R.id.question);
        category = findViewById(R.id.category);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.questionAnswers);
        layoutManager = new LinearLayoutManager(this);

        setSupportActionBar(toolbar);
        if (getIntent().getExtras()!=null){
            Bundle questionBundle = getIntent().getExtras();

            getSupportActionBar().setTitle(questionBundle.getString("category"));
            Username.setText(questionBundle.getString("username"));
            question.setText(questionBundle.getString("body"));
            category.setText(questionBundle.getString("category"));
            fillingAnswers();
        }else{
            getSupportActionBar().setTitle("APIAnswers");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void fillingAnswers(){
        Call<APIAnswers> call = new WebServiceToJsonHandler().questionAnswers(getIntent().getExtras().getInt("q_id"));

        call.enqueue(new Callback<APIAnswers>() {
            @Override
            public void onResponse(Call<APIAnswers> call, Response<APIAnswers> response) {
                if (!response.isSuccessful()){
                    return;
                }
                APIAnswers answers = response.body();
                adapter = new QuestionAnswersListAdapter(answers);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<APIAnswers> call, Throwable t) {

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
