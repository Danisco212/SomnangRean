package com.example.somnangrean.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.somnangrean.Activities.AskQuestion;
import com.example.somnangrean.Activities.Result;
import com.example.somnangrean.Activities.Login;
import com.example.somnangrean.Controllers.UserStateController;
import com.example.somnangrean.R;
import com.example.somnangrean.helpers.VerifyInput;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Home extends Fragment {

    private Button searchBtn;
    private EditText searchTerm;

    public Home() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        searchTerm = view.findViewById(R.id.searchTerm);
        searchBtn = view.findViewById(R.id.search);
        searchBtn.setOnClickListener(v -> {
            if (new VerifyInput().verifyEditText(searchTerm)){
                if (isNetworkAvailable()){
                    search();
                }else{
                    Toast.makeText(getContext(), "no internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        askQuestion(view);
        return view;
    }

    private void askQuestion(View v){
        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(v1 -> {
            if (UserStateController.userStateController.getState()==UserStateController.userState.loggedin){
                startActivity(new Intent().setClass(getContext(), AskQuestion.class));
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Login");
                builder.setMessage("You must be logged in to post a question!");
                builder.setPositiveButton("Login", (dialog, which) -> {
                    loginPage(true);
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {

                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void search(){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("search", searchTerm.getText().toString().trim());
        intent.putExtras(bundle);
        intent.setClass(getContext(), Result.class);
        startActivity(intent);
    }

    private void loginPage(boolean post){
        Intent i = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean("post", post);
        i.putExtras(bundle);
        i.setClass(getContext(), Login.class);
        startActivity(i);
    }

    // checking for internet connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}
