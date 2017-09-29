package com.example.choi.knocktalk.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.choi.knocktalk.Fragment.First;
import com.example.choi.knocktalk.Fragment.Second;
import com.example.choi.knocktalk.Fragment.Third;
import com.example.choi.knocktalk.Interface.Refresh_listener;

/**
 * Created by choi on 17. 8. 28.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    Refresh_listener refresh_listener;

    public ViewPagerAdapter(FragmentManager fm, Refresh_listener refresh_listener) {
        super(fm);
        this.refresh_listener = refresh_listener;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new First();
            case 1:
                return new Second();
            case 2:
                return new Third(refresh_listener);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
