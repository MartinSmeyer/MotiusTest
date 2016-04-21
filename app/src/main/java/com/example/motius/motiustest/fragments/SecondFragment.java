package com.example.motius.motiustest.fragments;

/**
 * Created by Martin on 20.04.2016.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.example.motius.motiustest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

//API/Json parsing --> project list
public class SecondFragment extends Fragment{


    View view;

    private int[] imageId = {
            R.drawable.computer,
            R.drawable.transport,
            R.drawable.people,
            R.drawable.logo50days
    };

    ListView list;
    ProgressBar progressBar;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    static final String API_URL = "https://www.motius.de/api/usecases/?format=json";

    public SecondFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_two, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        oslist = new ArrayList<HashMap<String, String>>();

        new RetrieveFeedTask().execute();


        return view;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        // show a progressBar while fetching data
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {
            //  without blocking UI Thread, tries to build up connection and fetches data

            while (true) {
                if(isOnline()) {
                    try {

                        URL url = new URL(API_URL);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        try {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                stringBuilder.append(line).append("\n");
                            }
                            bufferedReader.close();
                            return stringBuilder.toString();
                        } finally {
                            urlConnection.disconnect();
                        }
                    } catch (Exception e) {
                        Log.e("WARNING", e.getMessage(), e);
                        //return null;
                    }
                }
            }
        }

        protected void onPostExecute(String response) {
            //if not empty, parse json response
            if(response != null) {

            progressBar.setVisibility(View.GONE);

            Log.i("INFO", response);

            try {
                JSONArray jArray = (JSONArray) new JSONTokener(response).nextValue();

                for (int i=0; i < jArray.length(); i++)
                {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array

                    String titleStr = oneObject.getString("title");
                    String bodyStr = oneObject.getString("body");

                    bodyStr= Html.fromHtml(bodyStr).toString(); //removing HTML tags

                    String substr=bodyStr;

                    if(bodyStr.length()>=250) {
                        substr = bodyStr.substring(0, 249)+"..."; //if too long cut string
                    }

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("title", titleStr);
                    map.put("body", substr);
                    map.put("image",Integer.toString(imageId[i%imageId.length])); //add some images


                    oslist.add(map);
                    list=(ListView) view.findViewById(R.id.list1);

                    // adapter to Listview
                    ListAdapter adapter = new SimpleAdapter(getActivity(), oslist,
                            R.layout.list_v,
                            new String[] { "title","body","image" }, new int[] {
                            R.id.entrytitle,R.id.body,R.id.image});

                    list.setAdapter(adapter);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            }
        }
    }

}