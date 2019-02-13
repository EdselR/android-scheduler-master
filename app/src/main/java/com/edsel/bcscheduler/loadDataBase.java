package com.edsel.bcscheduler;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class loadDataBase extends AsyncTask<List<CourseRecord>,Void,List<CourseRecord>> {

    // Variables for the search input field, and results TextViews
    private AppDatabaseMod db;
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public Context context;  //done as a hack to get app context
    double mag;
    List<CourseRecord> items;

    // Constructor providing a reference to the views in MainActivity
    public loadDataBase(AppDatabaseMod db, RecyclerView recyclerView, RecyclerView.Adapter adapter, Context context ) {
        this.db = db;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.context = context;
    }


    @Override
    protected List<CourseRecord> doInBackground(List<CourseRecord>... params) {
        items = params[0];
        //update query
        db.databaseInterface().dropTheTable(); //clear any data before reloading
        for (int i =0; i< items.size(); i++) {
            db.databaseInterface().insertAll(items.get(i));
        }
        System.out.println("item size: " + items.size() );
        return items;
    }

    @Override
    protected void onPostExecute(List<CourseRecord> s) {
        super.onPostExecute(s);
        Toast.makeText(context, "Thank you for waiting!\nData available for querying!", Toast.LENGTH_LONG).show();
        adapter= new UserAdapter(items);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}

