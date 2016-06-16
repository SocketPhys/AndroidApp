
package org.incubatex.incubatex;

/**
 * Created by avik on 6/16/2016.
*/

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CountDownFragment extends Fragment {

    private TextView[] unitLabelTextViews;
    private TextView[][] digitTextViews;
    private TextView labelTextView;

    private CountDown countDown;

    public CountDownFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_countdown, container, false);
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
        createCountDown();
        return rootView;
    }

    private class CountDown extends CountDownTimer{
        private boolean hackingStarted;
        public CountDown(long millisUntilFinished, boolean started) {
            super(millisUntilFinished, 1000); // update every second
            hackingStarted = started;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // compute values
            long totalSecondsLeft = millisUntilFinished / 1000;
            long hours = totalSecondsLeft / 216000;
            long minutes = totalSecondsLeft % 3600 / 60;
            long seconds = totalSecondsLeft % 60;

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

        }
    }

    private void createCountDown() {
        if(this.countDown != null){
            this.countDown.cancel();
        }
        long millisUntilFinished;
        boolean started;
        Calendar now = new GregorianCalendar();
        Calendar hackingBegins = new GregorianCalendar(2016, 7, 6, 11, 0); // 12pm, August 6
        Calendar hackingEnds = new GregorianCalendar(2016, 7, 7, 11, 0); // 12 pm, August 7
        if(now.compareTo(hackingBegins) < 0){
            millisUntilFinished = hackingBegins.getTimeInMillis() - now.getTimeInMillis();
            started = false;
        } else {
            millisUntilFinished = hackingEnds.getTimeInMillis() - now.getTimeInMillis();
            started = true;
        }
        this.countDown = new CountDown(millisUntilFinished, started);
        this.countDown.start();
        Log.d("MillisUntilFinished",millisUntilFinished+"");
    }

    private void updateTextViewDigit(TextView textView, int digit){
        Log.d("Updating TextView", digit+"");
        textView.setText(String.valueOf(digit));
    }
}