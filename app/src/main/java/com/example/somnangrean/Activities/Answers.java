package com.example.somnangrean.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.somnangrean.Adapters.QuestionAnswersListAdapter;
import com.example.somnangrean.Controllers.UserStateController;
import com.example.somnangrean.Models.Answer.APIAnswers;
import com.example.somnangrean.Models.Answer.Answer;
import com.example.somnangrean.Models.Answer.PostAnswer;
import com.example.somnangrean.R;
import com.example.somnangrean.WebServiceHandlers.WebServiceToJsonHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Answers extends AppCompatActivity {

    private Bundle questionBundle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private QuestionAnswersListAdapter adapter;
    private TextView question;
    private TextView Username;
    private TextView category;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        initializeUI();
    }

    private void initializeUI(){
        loading = findViewById(R.id.postingAnswer);
        Username = findViewById(R.id.QuestionuserName);
        question = findViewById(R.id.question);
        category = findViewById(R.id.category);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.questionAnswers);
        layoutManager = new LinearLayoutManager(this);

        setSupportActionBar(toolbar);
        if (getIntent().getExtras()!=null){
            questionBundle = getIntent().getExtras();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        UserStateController userStateController;
        new UserStateController();
        userStateController = UserStateController.userStateController;
        if (userStateController.getState()== UserStateController.userState.loggedin){
            inflater.inflate(R.menu.answer, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (loading.getVisibility()==View.GONE){
                    super.onBackPressed();
                }
                break;
            case R.id.answer:
                // answer question
                if (loading.getVisibility()==View.GONE){
                    answerQuestion().show();
                }
                break;
            case R.id.refresh:
                if (loading.getVisibility()==View.GONE){
                    //refresh recycler view
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (loading.getVisibility()==View.GONE){
            super.onBackPressed();
        }
    }

    // creating the answer dialog
    private Dialog answerQuestion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // inflate the layout
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.answer_question_dialog, null);
        // set the layout in the alert dialog
        builder.setView(v)
                .setPositiveButton("Answer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText answer = v.findViewById(R.id.userAnswer);
                        postAnswer(answer);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        return builder.create();
    }

    // answering question
    private void postAnswer(EditText userAnswer){
        loading.setVisibility(View.VISIBLE);
        PostAnswer answer = new PostAnswer(userAnswer.getText().toString(), questionBundle.getInt("q_id"));
        UserStateController userStateController;
        new UserStateController();
        userStateController = UserStateController.userStateController;
        Call<Void> call = new WebServiceToJsonHandler().postAnswer("Bearer " +userStateController.activeUser.getToken(), answer);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    loading.setVisibility(View.GONE);
                    Toast.makeText(Answers.this, "Failed: " +response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                loading.setVisibility(View.GONE);
                Toast.makeText(Answers.this, "Answer has been posted, refresh to see", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(Answers.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
