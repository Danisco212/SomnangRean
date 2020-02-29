package com.example.somnangrean.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.somnangrean.Activities.CategoryResult;
import com.example.somnangrean.Adapters.CategoriesAdapter;
import com.example.somnangrean.Models.Category;
import com.example.somnangrean.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Categories extends Fragment {
    private ListView categories;

    public Categories() {
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
        View v = inflater.inflate(R.layout.fragment_categories, container, false);
        loadCategories(v);
        return v;
    }

    private void loadCategories(View view){
        categories = view.findViewById(R.id.categories);
        ArrayList<String> allCategories = new ArrayList<>();
        allCategories.add("English");
        allCategories.add("Science");
        categories.setAdapter(new CategoriesAdapter(getActivity(), allCategories));
        categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: open new activity with the category questions
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("category", allCategories.get(position));
                intent.putExtras(bundle);
                intent.setClass(getContext(), CategoryResult.class);
                startActivity(intent);
            }
        });
    }

}
