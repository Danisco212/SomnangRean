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

import com.example.somnangrean.Adapters.RecentQuestionsListAdapter;
import com.example.somnangrean.Models.Question.Question;
import com.example.somnangrean.R;
import com.example.somnangrean.WebServiceHandlers.WebServiceToJsonHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryResult extends AppCompatActivity {

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("category"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loading = findViewById(R.id.loading);
        questions = findViewById(R.id.catQuestions);
        loading.setVisibility(View.VISIBLE);

        Call<Question> call = new WebServiceToJsonHandler().categoryQuestion(getIntent().getExtras().getString("category"));
        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (!response.isSuccessful()){
                    loading.setVisibility(View.GONE);
                    return;
                }
                loading.setVisibility(View.GONE);
                Question question = response.body();
                layoutManager = new LinearLayoutManager(CategoryResult.this);
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

                        intent.setClass(CategoryResult.this, Answers.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {

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
