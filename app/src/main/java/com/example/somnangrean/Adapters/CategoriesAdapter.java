package com.example.somnangrean.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.somnangrean.Models.Category;
import com.example.somnangrean.R;

import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter<Category> {

    private Activity context;
    private ArrayList<Category> categories;

    public CategoriesAdapter(Activity context, ArrayList<Category> categories) {
        super(context, R.layout.category_list, categories);
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.category_list, null, false);
        return listView;
    }
}
