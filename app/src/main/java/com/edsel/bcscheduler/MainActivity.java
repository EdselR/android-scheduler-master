package com.edsel.bcscheduler;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;
    public static RecyclerView.Adapter adapter;
    //private CardViewDataAdapter mAdapter;
    //public static List<EartquakeRecord> items;
    //public List<EartquakeRecord> items2;
    //List<Earthquakes> itemsObject;
    public static Context context;  //done as a hack to get app context
    //public AppDatabase db; //make this global to onCreate() and onPause()

    // --- Setup Constants
    private final double MAX_MAGNITUDE = 10;

    private final double MAX_LONGITUDE = 180;
    private final double MIN_LONGITUDE = -180;

    private final double MAX_LATITUDE = 90;
    private final double MIN_LATITUDE = -90;

    // --- Widgets!
    private EditText etMag;
    private EditText etLng;
    private EditText etLat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext(); //done as a hack to get app context

        System.out.println("in onCreate MainActivity");

//        // --- Setup pointers to the widgets
//        etMag = (EditText) findViewById(R.id.etMagFilter);
//        etLng = (EditText) findViewById(R.id.etLongFilter);
//        etLat = (EditText) findViewById(R.id.etLatFilter);
//        recyclerView= (RecyclerView)findViewById(R.id.recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

        //preventUserInput(true); //block user until data is loaded


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Your Courses"));
        tabLayout.addTab(tabLayout.newTab().setText("Search Course"));
        tabLayout.addTab(tabLayout.newTab().setText("My Schedule"));

        tabLayout.setTabGravity((TabLayout.GRAVITY_FILL));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        final pageAdapter adapter = new pageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                //Toast.makeText(getApplicationContext(),"current tab: " + tab.getPosition(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int i, final float v, final int i2) {
            }
            @Override
            public void onPageSelected(final int i) {

            }
            @Override
            public void onPageScrollStateChanged(final int i) {
            }
        });

    }

    @Override
    protected void onPause(){
        super.onPause();
        //db.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // db.close();
        finish();
    }

    public void toast(String msg) {
        // --- Show the string for a long time
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }



    private class HttpGetTask extends AsyncTask<String,Integer,String> {



        protected String doInBackground(String... params) {
            String queryString = params[0];
            // Set up variables for the try block that need to be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String bookJSONString = null;
            try {

                URL requestURL = new URL(queryString);

                // Open the network connection.
                urlConnection = (HttpURLConnection) requestURL.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Get the InputStream.
                InputStream inputStream = urlConnection.getInputStream();

                // Read the response string into a StringBuilder.
                StringBuilder builder = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // but it does make debugging a *lot* easier if you print out the completed buffer for debugging.
                    builder.append(line + "\n");
                    publishProgress();
                }

                if (builder.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    // return null;
                    return null;
                }
                bookJSONString = builder.toString();

                // Catch errors.
            } catch (IOException e) {
                e.printStackTrace();

                // Close the connections.
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            // Return the raw response.
            return bookJSONString;
        }

        @Override
        protected void onPostExecute(String s) {
            //items = new ArrayList<Earthquakes>();
           // items = new ArrayList<EartquakeRecord>();
            double lat;
            double lon;
            double depth;
            double magnitude;
            String datetime;
            String eqid;

            try {
                // Convert the response into a JSON object.
                JSONObject jsonObject = new JSONObject(s); //get top level object
                // Get the JSONArray of book items.
                JSONArray itemsArray = jsonObject.getJSONArray("earthquakes"); //array of earthquakes objects

                // Initialize iterator and results fields.
                int i = 0;
                // Look for results in the items array
                while (i < itemsArray.length()) {
                    // Get the current item information.
                    JSONObject earthquakeObject = itemsArray.getJSONObject(i);
                    lat = earthquakeObject.getDouble("lat");
                    lon = earthquakeObject.getDouble("lng");
                    depth = earthquakeObject.getDouble("depth");
                    magnitude = earthquakeObject.getDouble("magnitude");
                    datetime = earthquakeObject.getString("datetime");
                    eqid = earthquakeObject.getString("eqid");
                    //Earthquakes eq = new Earthquakes();
//                    EartquakeRecord eq = new EartquakeRecord();
//                    eq.setDateTime(datetime);
//                    eq.setDepth(depth);
//                    eq.setLat(lat);
//                    eq.setLon(lon);
//                    eq.setMagnitude(magnitude);
//                    eq.setEQID(eqid);
//                    items.add(eq);
                    //db.databaseInterface().insertAll(items.get(i)); //cant do this
                    i++;
                }
               // new loadDataBase( db, recyclerView,  adapter, context).execute(items); //now enter all the data in db
            } catch (Exception e){
                // If onPostExecute does not receive a proper JSON string,
                // update the UI to show failed results.
                e.printStackTrace();
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}

