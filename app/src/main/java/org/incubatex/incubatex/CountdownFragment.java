
package org.incubatex.incubatex;

/**
 * Created by avik on 6/16/2016.
*/

import android.app.ActionBar;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CountDownFragment extends Fragment {

    private TextView[] unitLabelTextViews;
    private TextView[][] digitTextViews;
    private TextView labelTextView;
    private ImageView progressRect;

    private CountDown countDown;

    private CityData cityData;

    private int viewHeight = Integer.MAX_VALUE;
    private boolean hackingEnded;
    public CountDownFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        hackingEnded = false;
        cityData = new GsonBuilder().create().fromJson(getArguments().getString("cityData"), CityData.class);
        final View rootView = inflater.inflate(R.layout.fragment_countdown, container, false);
        unitLabelTextViews = new TextView[]{
                (TextView) rootView.findViewById(R.id.unitLabelTextView0),
                (TextView) rootView.findViewById(R.id.unitLabelTextView1),
                (TextView) rootView.findViewById(R.id.unitLabelTextView2)
        };
        digitTextViews = new TextView[][]{
                {
                        (TextView) rootView.findViewById(R.id.digitTextView00),
                        (TextView) rootView.findViewById(R.id.digitTextView01)
                },{
                        (TextView) rootView.findViewById(R.id.digitTextView10),
                        (TextView) rootView.findViewById(R.id.digitTextView11)
                },{
                        (TextView) rootView.findViewById(R.id.digitTextView20),
                        (TextView) rootView.findViewById(R.id.digitTextView21)
                }
        };
        labelTextView = (TextView) rootView.findViewById(R.id.labelTextView);
        progressRect = (ImageView) rootView.findViewById(R.id.progressRect);
        for(TextView[] number : digitTextViews)
            for(TextView digit : number)
                digit.setTextColor(Color.parseColor(cityData.getColor()));
        rootView.post(new Runnable() {
            @Override
            public void run() {
                CountDownFragment.this.viewHeight = rootView.getHeight();
                if(hackingEnded){
                    RelativeLayout.LayoutParams progressRectLayoutParams = new RelativeLayout.LayoutParams(progressRect.getWidth(), viewHeight);
                    progressRectLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    progressRect.setLayoutParams(progressRectLayoutParams);
                }
            }
        });
        createCountDown();

        return rootView;
    }

    private class CountDown extends CountDownTimer{
        private boolean hackingStarted;
        public CountDown(long millisUntilFinished, boolean started) {
            super(millisUntilFinished, 1000); // update every second
            hackingStarted = started;
            if(hackingStarted){
                labelTextView.setText("Until Hacking Ends");
            } else {
                labelTextView.setText("Until Hacking Begins");
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // compute values
            long totalSecondsLeft = millisUntilFinished / 1000;
            long hours = totalSecondsLeft / 3600;
            long minutes = totalSecondsLeft % 3600 / 60;
            long seconds = totalSecondsLeft % 60;
            if(hackingStarted){
                RelativeLayout.LayoutParams progressRectLayoutParams = new RelativeLayout.LayoutParams(progressRect.getWidth(), viewHeight-(int)(viewHeight*totalSecondsLeft/60));
                progressRectLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                progressRect.setLayoutParams(progressRectLayoutParams);
            }
            // Log.d("Time Left", String.format("%02d:%02d:%02d", hours, minutes, seconds));
            if(!digitTextViews[0][0].getText().equals(String.valueOf(hours / 10))){ // update hours 10's place
                updateTextViewDigit(digitTextViews[0][0], (int)(hours / 10));
            }
            if(!digitTextViews[0][1].getText().equals(String.valueOf(hours % 10))){ // update hours 1's place
                updateTextViewDigit(digitTextViews[0][1], (int)(hours % 10));
            }

            if(!digitTextViews[1][0].getText().equals(String.valueOf(minutes / 10))){ // update minutes 10's place
                updateTextViewDigit(digitTextViews[1][0], (int)(minutes / 10));
            }
            if(!digitTextViews[1][1].getText().equals(String.valueOf(minutes % 10))){ // update minutes 1's place
                updateTextViewDigit(digitTextViews[1][1], (int)(minutes % 10));
            }

            if(!digitTextViews[2][0].getText().equals(String.valueOf(seconds / 10))){ // update seconds 10's place
                updateTextViewDigit(digitTextViews[2][0], (int)(seconds / 10));
            }
            if(!digitTextViews[2][1].getText().equals(String.valueOf(seconds % 10))){ // update seconds 1's place
                updateTextViewDigit(digitTextViews[2][1], (int)(seconds % 10));
            }
        }

        @Override
        public void onFinish() {
            updateTextViewDigit(digitTextViews[2][1], 0);

            CountDownFragment.this.createCountDown();
        }
    }

    private void createCountDown() {
        if(this.countDown != null){
            this.countDown.cancel();
        }
        long millisUntilFinished;
        boolean started;
        Calendar now = new GregorianCalendar();
        Calendar hackingBegins = new GregorianCalendar(2016, 5, 16, 23, 45); // 12pm, June 17
        Log.d("hackingBegins", hackingBegins.getTime().toString());
        Log.d("now",now.getTime().toString());
        Calendar hackingEnds = new GregorianCalendar(2016, 5, 16, 23, 46); // 12 pm, August 7
        if(now.compareTo(hackingBegins) < 0){
            millisUntilFinished = hackingBegins.getTimeInMillis() - now.getTimeInMillis();
            started = false;
        } else if(now.compareTo(hackingEnds) < 0){
            millisUntilFinished = hackingEnds.getTimeInMillis() - now.getTimeInMillis();
            started = true;
        } else { // hacking has ended
            hackingEnded = true;
            labelTextView.setText("Hacking Has Ended");
            return;
        }
        this.countDown = new CountDown(millisUntilFinished, started);
        this.countDown.start();
        Log.d("MillisUntilFinished",millisUntilFinished+"");
    }

    private void updateTextViewDigit(TextView textView, int digit){
        textView.setText(String.valueOf(digit));
    }
}