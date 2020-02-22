package com.example.somnangrean.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.somnangrean.Models.Category;
import com.example.somnangrean.R;

import org.w3c.dom.Text;

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
        TextView category = listView.findViewById(R.id.categoryName);
        TextView categoryTotal = listView.findViewById(R.id.categoryTotal);

        category.setText(categories.get(position).getName());
        categoryTotal.setText(String.valueOf(categories.get(position).getTotal()));
        return listView;
    }
}
