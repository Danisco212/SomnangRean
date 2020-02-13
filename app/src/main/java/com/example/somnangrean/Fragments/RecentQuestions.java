package com.example.somnangrean.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        Call<Question> call = new WebServiceToJsonHandler().recentQuestions();

        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (!response.isSuccessful()) {

                } else {
                    Question questions = response.body();
                    RecyclerView recyclerView = v.findViewById(R.id.recentQuestions);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    RecentQuestionsListAdapter mAdapter = new RecentQuestionsListAdapter(questions);

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mAdapter);

                    mAdapter.setOnItemClickListener(position ->
                            startActivity(new Intent().setClass(getContext(), Answers.class))
                            // send the answer intent to the other activity, and then call its answers and load them into the thingy
                    );
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {

            }
        });
    }


}
