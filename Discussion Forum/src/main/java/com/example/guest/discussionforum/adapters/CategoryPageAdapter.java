package com.example.guest.discussionforum.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.guest.discussionforum.models.Message;
import com.example.guest.discussionforum.ui.CategoryDetailFragment;

import java.util.ArrayList;

public class CategoryPageAdapter extends FragmentPagerAdapter {
    private ArrayList<Message> mMessage;

    public CategoryPageAdapter(FragmentManager fm, ArrayList<Message> messages) {
        super(fm);
        mMessage = messages;
    }

    @Override
    public Fragment getItem(int position) {
        return CategoryDetailFragment.newInstance(mMessage.get(position));
    }

    @Override
    public int getCount() {
        return mMessage.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMessage.get(position).getCategory();
    }
}
