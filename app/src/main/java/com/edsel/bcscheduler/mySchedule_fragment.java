package com.edsel.bcscheduler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.WeekView;

public class mySchedule_fragment extends Fragment {


    private View rootView;
    WeekView mWeekView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.timetable, container, false);
        setRetainInstance(true);


        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) rootView.findViewById(R.id.weekView);

//// Set an action when any event is clicked.
//        mWeekView.setOnEventClickListener(mEventClickListener);
//
//// The week view has infinite scrolling horizontally. We have to provide the events of a
//// month every time the month changes on the week view.
//        mWeekView.setMonthChangeListener(mMonthChangeListener);
//
//// Set long press listener for events.
//        mWeekView.setEventLongPressListener(mEventLongPressListener);


        return rootView;
    }



}
