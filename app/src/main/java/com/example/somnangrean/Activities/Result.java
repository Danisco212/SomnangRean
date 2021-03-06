package com.example.somnangrean.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.somnangrean.Adapters.RecentQuestionsListAdapter;
import com.example.somnangrean.Models.Question.Question;
import com.example.somnangrean.R;
import com.example.somnangrean.WebServiceHandlers.WebServiceToJsonHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Result extends AppCompatActivity {

    private ProgressBar loading;
    private RecyclerView questions;
    private RecyclerView.LayoutManager layoutManager;
    private RecentQuestionsListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_result);
        initializeUI();
    }

    private void initializeUI(){
        Call<Question> call;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loading = findViewById(R.id.loading);
        questions = findViewById(R.id.catQuestions);
        loading.setVisibility(View.VISIBLE);

        if (getIntent().getExtras().getString("category")==null){
            getSupportActionBar().setTitle("Search Results..");
            call = new WebServiceToJsonHandler().search(getIntent().getExtras().getString("search"));
        }else{
            getSupportActionBar().setTitle(getIntent().getExtras().getString("category"));
            call = new WebServiceToJsonHandler().categoryQuestion(getIntent().getExtras().getString("category"));
        }

        categoriesList(call);

    }

    private void categoriesList(Call<Question> call){
        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (!response.isSuccessful()){
                    loading.setVisibility(View.GONE);
                    Toast.makeText(Result.this, response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                loading.setVisibility(View.GONE);
                Question question = response.body();
                if (question.getData().length<=0){
                    Toast.makeText(Result.this, "No Results", Toast.LENGTH_LONG).show();
                }
                layoutManager = new LinearLayoutManager(Result.this);
                adapter = new RecentQuestionsListAdapter(question);

                questions.setAdapter(adapter);
                questions.setLayoutManager(layoutManager);

                adapter.setOnItemClickListener(new RecentQuestionsListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        // send the answer intent to the other activity, and then call its answers and load them into the thingy
                        bundle.putInt("q_id", question.getData()[position].getId());
                        bundle.putString("body", question.getData()[position].getBody());
                        bundle.putString("category", question.getData()[position].getCategory_name());
                        bundle.putString("username", question.getData()[position].getUser_name());
                        intent.putExtras(bundle);

                        intent.setClass(Result.this, Answers.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(Result.this, "Cant connect to the database", Toast.LENGTH_SHORT).show();
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
