package org.incubatex.androidapp;

/**
 * Created by avik on 6/16/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

public class BuzzFragment extends Fragment {
    private CityData cityData;
    private Tweet[] tweets;
    private ListView tweetListView;
    public BuzzFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cityData = new GsonBuilder().create().fromJson(getArguments().getString("cityData"), CityData.class);
        View rootView = inflater.inflate(org.incubatex.androidapp.R.layout.fragment_buzz, container, false);
        tweetListView = (ListView) rootView.findViewById(R.id.tweetListView);
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url ="http://incubatex-avikj.rhcloud.com/tweets?hashtag="+cityData.getTwitterHashtag();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        tweets = new GsonBuilder()
                                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .create()
                                .fromJson(response, Tweet[].class);
                        tweetListView.setAdapter(
                                new TweetListAdapter(getContext(), R.layout.list_item_tweet, tweets));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BuzzFragment.this.getContext(), "HTTP GET Request Failed :(", Toast.LENGTH_SHORT).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return rootView;
    }
}