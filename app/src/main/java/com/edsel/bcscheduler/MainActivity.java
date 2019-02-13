package com.edsel.bcscheduler;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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
    public static List<EartquakeRecord> items;
    public List<EartquakeRecord> items2;
    //List<Earthquakes> itemsObject;
    public static Context context;  //done as a hack to get app context
    public AppDatabase db; //make this global to onCreate() and onPause()

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

        // --- Setup pointers to the widgets
        etMag = (EditText) findViewById(R.id.etMagFilter);
        etLng = (EditText) findViewById(R.id.etLongFilter);
        etLat = (EditText) findViewById(R.id.etLatFilter);
        recyclerView= (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

        //preventUserInput(true); //block user until data is loaded

        //whenever the activity is started, it reads data from database and stores it into
        // local array list 'items'
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, db.NAME).fallbackToDestructiveMigration().build();
        //it is very bad practice to pull data from Room on main UI thread,
        //that's why we create another thread which we use for getting the data and displaying it
        new displayAll(db, recyclerView, adapter, context ).execute();
        preventUserInput(false);
    }

    @Override
    protected void onStop(){
        super.onStop();
        db.close();
        finish();
    }

    @Override
    protected void onPause(){
        super.onPause();
        db.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // --- Switch on the item ID that was clicked
        switch (item.getItemId()) {

            case R.id.menuQuit:
                finish(); // --- Quit
                break;

            case R.id.menuMapAll:
                Intent map = new Intent(this, MapAllActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("earthquakes",(Serializable)items);
                map.putExtras(bundle);
                startActivity(map);
                break;

            case R.id.menuDeleteAll:
                new deleteAll( db,  recyclerView, adapter, context).execute(items);
                break;

            case R.id.menuMagFilter:
                preventUserInput(false);
                // --- Determine if the data is valid
                if (magIsValid()) {
                    double mag = Double.parseDouble(etMag.getText().toString());
                    //items.clear();
                    new testMagnitude( db,  recyclerView, adapter, context).execute(mag);
                    etLng.setText("");
                    etLat.setText("");
                } else {
                    toast("Invalid Magnitude Entered!");
                }
                break;

            case R.id.menuLngFilter:
                preventUserInput(false);
                // --- Determine if the data is valid
                if (lngIsValid()) {
                    // --- Get the filtered data!

                    etMag.setText("");
                    etLat.setText("");
                } else {
                    toast("Invalid Longitude Entered!");
                }
                break;

            case R.id.menuLatFilter:
                preventUserInput(false);
                // --- Determine if the data is valid
                if (latIsValid()) {
                    // --- Get the filtered data!
                    // --- Clear the other fields that we are not filtering on
                    etLng.setText("");
                    etMag.setText("");
                } else {
                    toast("Invalid Latitude Entered!");
                }
                break;

            case R.id.menuLoadAll:
                // --- get all data in the database
                new HttpGetTask().execute("http://api.geonames.org/earthquakesJSON?north=90.0&south=0.0&east=-50&west=-160.0&lang=de&maxRows=150&username=bobandroid");
                etMag.setText("");
                etLng.setText("");
                etLat.setText("");
                preventUserInput(false);
                break;

            case R.id.menuDisplayAll:
                preventUserInput(false);
                // --- get all data in the database
                new displayAll(db, recyclerView, adapter, context ).execute();
                etMag.setText("");
                etLng.setText("");
                etLat.setText("");

                break;

            case R.id.menuClear:
                // --- clear all data and get all data in the db.
                etMag.setText("");
                etLng.setText("");
                etLat.setText("");
                //theQuakeData = theDatabase.readAllData(this);
                //theEarthquakesData = theDatabase.readAllData(this);
                //setupListView();
                break;

            default:
                // --- Oops.. should never get here. A real program would
                //     throw an exception, log some data, etc
                toast("Hit Default! Should not be here!!");
                break;
        }
        return true;
    }

    public void toast(String msg) {
        // --- Show the string for a long time
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private boolean magIsValid() {
        boolean isValid = false;

        // --- Check to see if the widget is not empty
        if (!etMag.getText().toString().equals("")) {
            double mag = Double.parseDouble(etMag.getText().toString());

            // --- Check to see if the range is valid
            if (mag > 0 && mag <= MAX_MAGNITUDE) {
                isValid = true;
            }
        }
        return isValid;
    }

    private boolean lngIsValid() {
        boolean isValid = false;

        // --- Check to see if the widget is not empty
        if (!etLng.getText().toString().equals("")) {
            double lng = Double.parseDouble(etLng.getText().toString());

            // --- Check to see if the range is valid
            if (lng >= MIN_LONGITUDE && lng <= MAX_LONGITUDE) {
                isValid = true;
            }
        }
        return isValid;
    }
    private boolean latIsValid() {
        boolean isValid = false;

        // --- Check to see if the widget is not empty
        if (!etLat.getText().toString().equals("")) {
            double lat = Double.parseDouble(etLat.getText().toString());

            // --- Check to see if the range is valid
            if (lat >= MIN_LATITUDE && lat <= MAX_LATITUDE) {
                isValid = true;
            }
        }
        return isValid;
    }

    private class HttpGetTask extends AsyncTask<String,Integer,String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
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
            items = new ArrayList<EartquakeRecord>();
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
                    EartquakeRecord eq = new EartquakeRecord();
                    eq.setDateTime(datetime);
                    eq.setDepth(depth);
                    eq.setLat(lat);
                    eq.setLon(lon);
                    eq.setMagnitude(magnitude);
                    eq.setEQID(eqid);
                    items.add(eq);
                    //db.databaseInterface().insertAll(items.get(i)); //cant do this
                    i++;
                }
                new loadDataBase( db, recyclerView,  adapter, context).execute(items); //now enter all the data in db
            } catch (Exception e){
                // If onPostExecute does not receive a proper JSON string,
                // update the UI to show failed results.
                e.printStackTrace();
            }
        }
    }

    private void preventUserInput(boolean isLocked) {
        if (isLocked) {
            etMag.setFocusable(false);
            etMag.setHint("Please Wait");

            etLng.setFocusable(false);
            etLng.setHint("Please Wait");

            etLat.setFocusable(false);
            etLat.setHint("Please Wait");
        } else {
            etMag.setFocusable(true);
            etMag.setClickable(true);
            etMag.setFocusableInTouchMode(true);
            etMag.setHint("Enter Mag");

            etLng.setFocusable(true);
            etLng.setClickable(true);
            etLng.setFocusableInTouchMode(true);
            etLng.setHint("Enter Lng");

            etLat.setFocusable(true);
            etLat.setClickable(true);
            etLat.setFocusableInTouchMode(true);
            etLat.setHint("Enter Lat");
        }
    }
}
