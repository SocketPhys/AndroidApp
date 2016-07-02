package org.incubatex.androidapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.GsonBuilder;

/**
 * Created by avikj on 6/29/16.
 */
public class VenueMapFragment extends Fragment{
    private CityData cityData;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cityData = new GsonBuilder().create().fromJson(getArguments().getString("cityData"), CityData.class);
        String imageFileName = cityData.getCity(); // this image is stored in the assets/ directory
        System.out.println(imageFileName);
        // figure out how to read it and display in an ImageView
        final View rootView = inflater.inflate(R.layout.fragment_venue_map, container, false);
        Resources res = getResources();
        ImageView imgView=(ImageView) rootView.findViewById(R.id.imageView);
        String mDrawableName = imageFileName;
        int resID = res.getIdentifier(mDrawableName , "drawable", rootView.getContext().getPackageName());

        imgView.setImageResource(resID);
        return rootView;
    }




}
