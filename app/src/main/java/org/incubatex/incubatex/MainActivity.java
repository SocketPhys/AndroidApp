package org.incubatex.incubatex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    private EditText cityField;
    private Button okButton;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getPreferences(Context.MODE_PRIVATE);
        if(prefs.getString("city", null) != null){
            prepareForAndLaunchTabActivity(prefs.getString("city", null));
        }
        okButton = (Button) findViewById(R.id.okButton);
        cityField = (EditText) findViewById(R.id.cityField);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putString("city", cityField.getText().toString());
                prepareForAndLaunchTabActivity(cityField.getText().toString());
            }
        });
    }

    private void prepareForAndLaunchTabActivity(String city){
        Intent intent = new Intent(this, TabActivity.class);
        Bundle extras = new Bundle();
        extras.putString("cityData", getCityData(city));
        intent.putExtras(extras);
        startActivity(intent);
        this.finish();
    }

    private String getCityData(String cityName) {
        try {
            InputStream inputStream = getAssets().open("cityData.json");
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            StringBuffer buffer = new StringBuffer();
            while((line = in.readLine()) != null)
                buffer.append(line);
            String jsonString = buffer.toString();
            JsonObject allCitiesJson = new JsonParser().parse(jsonString).getAsJsonObject();
            return allCitiesJson.getAsJsonObject(cityName).toString();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
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
