package com.example.choi.knocktalk.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.choi.knocktalk.Fragment.First;
import com.example.choi.knocktalk.Fragment.Second;
import com.example.choi.knocktalk.Fragment.Third;

/**
 * Created by choi on 17. 8. 28.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new First();
            case 1:
                return new Second();
            case 2:
                return new Third();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 3;
    }
}
