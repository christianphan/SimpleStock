package com.christianphan.simplestock;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;


public class TabAdapter extends SmartFragmentStatePageAdapter {

    private SmartFragmentStatePageAdapter adapterViewPager;
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Info", "News", "Annual", "Your Shares" };
    private static Context context;

    public TabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                return PageFragment.newInstance(position + 1);
            case 1:
                return PageFragment2.newInstance(position + 1);
            case 2:
                return PageFragment3.newInstance(position + 1);
            case 3:
                return PageFragment4.newInstance(position + 1);
            default:
                return null;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }


    public View getTabView() {
        return getTabView();
    }






}