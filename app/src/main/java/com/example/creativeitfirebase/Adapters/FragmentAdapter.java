package com.example.creativeitfirebase.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.creativeitfirebase.Fragment.ChatFragment;
import com.example.creativeitfirebase.Fragment.FeedFragment;
import com.example.creativeitfirebase.Fragment.UserFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    String [] name = {"Home","User","Chat"};

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return new FeedFragment();
            case 1:
                return new UserFragment();
            case 2:
                return new ChatFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return name[position];
    }
}
