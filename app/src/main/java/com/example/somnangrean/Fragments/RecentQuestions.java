package com.example.somnangrean.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.somnangrean.Activities.Answers;
import com.example.somnangrean.Adapters.RecentQuestionsListAdapter;
import com.example.somnangrean.Models.Question.Question;
import com.example.somnangrean.R;
import com.example.somnangrean.WebServiceHandlers.WebServiceToJsonHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecentQuestions extends Fragment {

    private ProgressBar loading;
    private RecyclerView recyclerView;


    public RecentQuestions() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recent_questions, container, false);
        loadQuestions(v);
        return v;
    }


    private void loadQuestions(View v) {
        loading = v.findViewById(R.id.loadingQuestions);
        recyclerView = v.findViewById(R.id.recentQuestions);
        loading.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Call<Question> call = new WebServiceToJsonHandler().recentQuestions();

        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (!response.isSuccessful()) {

                } else {
                    loading.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    Question questions = response.body();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    RecentQuestionsListAdapter mAdapter = new RecentQuestionsListAdapter(questions);

                    mAdapter.setOnItemClickListener(position -> {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        // send the answer intent to the other activity, and then call its answers and load them into the thingy
                        bundle.putInt("q_id", questions.getData()[position].getId());
                        bundle.putString("body", questions.getData()[position].getBody());
                        bundle.putString("category", questions.getData()[position].getCategory_name());
                        bundle.putString("username", questions.getData()[position].getUser_name());
                        intent.putExtras(bundle);

                        if (getContext()!=null){
                            intent.setClass(getContext(), Answers.class);
                        }
                        startActivity(intent);
                    });

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mAdapter);


                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {

            }
        });
    }


}
