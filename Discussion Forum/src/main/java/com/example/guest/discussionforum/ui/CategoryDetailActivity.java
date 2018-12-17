package com.example.guest.discussionforum.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.guest.discussionforum.R;
import com.example.guest.discussionforum.R2;
import com.example.guest.discussionforum.adapters.CategoryPageAdapter;
import com.example.guest.discussionforum.models.Message;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryDetailActivity extends AppCompatActivity {
    @BindView(R2.id.categoryViewPager) ViewPager mCategoryViewPager;
    private CategoryPageAdapter adapterViewPager;
    ArrayList<Message> mMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        ButterKnife.bind(this);

        mMessages = Parcels.unwrap(getIntent().getParcelableExtra("messages"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new CategoryPageAdapter(getSupportFragmentManager(), mMessages);
        mCategoryViewPager.setAdapter(adapterViewPager);
        mCategoryViewPager.setCurrentItem(startingPosition);
    }
}
