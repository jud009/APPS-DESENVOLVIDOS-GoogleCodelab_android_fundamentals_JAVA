package com.subs.usernavigation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.subs.usernavigation.fragment.TabFragment1;
import com.subs.usernavigation.fragment.TagFragment2;
import com.subs.usernavigation.fragment.TagFragment3;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private final int mNumOfTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int mNumOfTabs) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = mNumOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TabFragment1();
            case 1:
              return new TagFragment2();
            case 2:
                return new TagFragment3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
