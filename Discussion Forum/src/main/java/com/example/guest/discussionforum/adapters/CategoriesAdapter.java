package com.example.guest.discussionforum.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.guest.discussionforum.models.Category;

import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Category> categories;

    public CategoriesAdapter(Context context, int resource, ArrayList<Category> categories) {
        super(context, resource);
        this.context = context;
        this.categories = categories;
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position).getName();
    }

    @Override
    public int getCount() {
        return categories.size();
    }


}
