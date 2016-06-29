package org.incubatex.androidapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.GsonBuilder;

/**
 * Created by avikj on 6/29/16.
 */
public class VenueMapFragment extends Fragment{
    private CityData cityData;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cityData = new GsonBuilder().create().fromJson(getArguments().getString("cityData"), CityData.class);
        String imageFileName = cityData.getCity()+".png"; // this image is stored in the assets/ directory
        // figure out how to read it and display in an ImageView
        final View rootView = inflater.inflate(R.layout.fragment_venue_map, container, false);
        return rootView;
    }
}
