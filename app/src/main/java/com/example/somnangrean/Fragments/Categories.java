package com.example.somnangrean.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        ArrayList<Category> allCategories = new ArrayList<>();
        Category category = new Category("English", 20);
        Category category1 = new Category("Math", 20);
        Category category2 = new Category("Science", 20);
        Category category3 = new Category("Coding", 20);
        allCategories.add(category);
        allCategories.add(category1);
        allCategories.add(category2);
        allCategories.add(category3);
        categories.setAdapter(new CategoriesAdapter(getActivity(), allCategories));
    }

}
