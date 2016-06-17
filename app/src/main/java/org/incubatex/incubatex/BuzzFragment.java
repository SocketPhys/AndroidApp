package org.incubatex.incubatex;

/**
 * Created by avik on 6/16/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

public class BuzzFragment extends Fragment {
    private CityData cityData;

    public BuzzFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cityData = new GsonBuilder().create().fromJson(getArguments().getString("cityData"), CityData.class);
        View rootView = inflater.inflate(R.layout.fragment_buzz, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.testBuzzTextView);
        textView.setText(cityData.getTwitterSearchTerm());
        return rootView;
    }
}