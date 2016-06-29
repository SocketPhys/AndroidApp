package org.incubatex.androidapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.gson.GsonBuilder;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 * Fragments are instantiated here.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private CityData cityData;

    public SectionsPagerAdapter(FragmentManager fm, CityData cityData) {
        super(fm);
        this.cityData = cityData;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){
            case 0:
                CountDownFragment cdf = new CountDownFragment();
                putCityDataAsArg(cdf);
                return cdf;
            case 1:
                ScheduleFragment sf = new ScheduleFragment();
                putCityDataAsArg(sf);
                return sf;
            case 2:
                BuzzFragment bf = new BuzzFragment();
                putCityDataAsArg(bf);
                return bf;
            case 3:
                VenueMapFragment vmf = new VenueMapFragment();
                putCityDataAsArg(vmf);
                return vmf;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Dashboard";
            case 1:
                return "Schedule";
            case 2:
                return "Buzz";
            case 3:
                return "Map";
        }
        return null;
    }

    private void putCityDataAsArg(Fragment fragment){
        Bundle args = new Bundle();
        args.putString("cityData", new GsonBuilder().create().toJson(cityData).toString()); // serialize cityData as json
        fragment.setArguments(args);
    }
}