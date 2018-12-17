package com.example.guest.discussionforum.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.guest.discussionforum.R;
import com.example.guest.discussionforum.R2;
import com.example.guest.discussionforum.adapters.FirebaseCategoryViewHolder;
import com.example.guest.discussionforum.models.Category;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mCategoriesReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @BindView(R2.id.categoryRecyclerView) RecyclerView mCategoryRecyclerView;
    @BindView(R2.id.addCategory) Button mAddCategory;
    @BindView(R2.id.categoryName) EditText mCategoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        mCategoriesReference = FirebaseDatabase
                .getInstance()
                .getReference().child("categories");
        setUpFirebaseAdapter();

        mAddCategory.setOnClickListener(this);
    }

    private void setUpFirebaseAdapter() {

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(mCategoriesReference,Category.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Category, FirebaseCategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseCategoryViewHolder viewHolder, int position, @NonNull Category model) {
                viewHolder.bindCategory(model);

            }

            @Override
            public FirebaseCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_list_item,parent,false);

                return new FirebaseCategoryViewHolder(itemView);
            }
        };


        mCategoryRecyclerView.setHasFixedSize(true);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCategoryRecyclerView.setAdapter(mFirebaseAdapter);
    };


    @Override
    public void onClick(View v) {
        if(v == mAddCategory) {
            String name = mCategoryName.getText().toString();
            Category category = new Category(name);
            saveCategoryToFirebase(category);
        }
    }

    public void saveCategoryToFirebase(Category category) {
        mCategoriesReference.push().setValue(category);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mFirebaseAdapter.cleanup();
    }
}
