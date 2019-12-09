package com.example.sub_5_made_project_akhir.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sub_5_made_project_akhir.R;
import com.example.sub_5_made_project_akhir.view.fragment.FavMovieFragment;
import com.example.sub_5_made_project_akhir.view.fragment.FavTvFragment;

public class SectionPageAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionPageAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.fav_movie,
            R.string.fav_tv,
    };
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavMovieFragment();
                break;
            case 1:
                fragment = new FavTvFragment();
                break;
        }
        return fragment;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
    @Override
    public int getCount() {
        return 2;
    }
}
