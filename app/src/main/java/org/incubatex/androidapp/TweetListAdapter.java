package org.incubatex.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by avikj on 6/29/16.
 */
public class TweetListAdapter extends ArrayAdapter<Tweet> {
    public TweetListAdapter(Context context, int resource, Tweet[] tweets) {
        super(context, resource, tweets);
    }@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tweetView = inflater.inflate(R.layout.list_item_tweet, parent, false);
        ((TextView)tweetView.findViewById(R.id.nameTextView)).setText(tweet.getUser().getName());
        ((TextView)tweetView.findViewById(R.id.usernameTextView)).setText("@"+tweet.getUser().getScreenName());
        ((TextView)tweetView.findViewById(R.id.tweetTextTextView)).setText(tweet.getText().replace("&amp;", "&"));
        return tweetView;
    }
}
