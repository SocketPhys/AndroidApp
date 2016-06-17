package org.incubatex.incubatex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.gson.GsonBuilder;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
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
                Bundle args = new Bundle();
                args.putString("cityData", new GsonBuilder().create().toJson(cityData).toString()); // serialize cityData as json
                cdf.setArguments(args);
                return cdf;
            default:
                return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
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
        }
        return null;
    }
}