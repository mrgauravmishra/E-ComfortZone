package com.example.guest.discussionforum.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.guest.discussionforum.R;
import com.example.guest.discussionforum.models.Category;
import com.example.guest.discussionforum.models.Message;
import com.example.guest.discussionforum.ui.CategoryDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ArrayList<Message> mMessages = new ArrayList<>();
    View mView;
    Context mContext;

    public FirebaseCategoryViewHolder(View view) {
        super(view);
        mView = view;
        mContext = view.getContext();
        view.setOnClickListener(this);
        Log.d("What is this?", view.toString());
    }

    public void bindCategory(Category category) {
        TextView categoryName = (TextView) mView.findViewById(R.id.categoryName);

        categoryName.setText(category.getName());
    }

    @Override
    public void onClick(View v) {
        Log.d("What this?", v.toString());
        Log.d("Position: ", String.valueOf(getLayoutPosition()));
        int itemPosition = getLayoutPosition();
        Intent intent = new Intent(mContext, CategoryDetailActivity.class);
        intent.putExtra("position", itemPosition);

        intent.putExtra("messages", Parcels.wrap(mMessages));
        mContext.startActivity(intent);
    }
}
