package com.edsel.bcscheduler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class pageAdapter extends FragmentStatePagerAdapter {

    int numTabs;

    public pageAdapter(FragmentManager manager, int numTabs){
        super(manager);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){

            case 0:
                return new yourCourse_fragment();
            case 1:
                return new searchCourse_fragment();
            case 2:
                return new mySchedule_fragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
