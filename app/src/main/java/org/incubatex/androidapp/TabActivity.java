package org.incubatex.androidapp;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.GsonBuilder;

public class TabActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter sectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;

    private TabLayout tabLayout;

    private int[] tabIcons = {
        R.drawable.ic_alarm_selector, // countdown icon
        R.drawable.ic_calendar_selector, //schedule icon
        R.drawable.ic_twitter_selector, // twitter icon
        R.drawable.ic_map_selector
    };

    private CityData cityData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // cityData = new GsonBuilder().create().fromJson(getIntent().getExtras().getString("cityData"), CityData.class);
        setContentView(R.layout.activity_tabs);
        cityData = (CityData) new GsonBuilder().create().fromJson(getIntent().getExtras().getString("cityData"), CityData.class);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), cityData);

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor(cityData.getColor()));
        tabLayout.setupWithViewPager(viewPager);
        setUpTabIcons();
     }

    private void setUpTabIcons(){
        for(int i = 0; i < tabLayout.getTabCount(); i++)
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
