package org.incubatex.androidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private EditText cityField;
    private Button okButton;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(org.incubatex.androidapp.R.layout.activity_main);
        prefs = getSharedPreferences("globalPreferences", Context.MODE_PRIVATE);
        if(prefs.getString("city", null) != null){
            prepareForAndLaunchTabActivity(prefs.getString("city", null));
        }
        okButton = (Button) findViewById(org.incubatex.androidapp.R.id.okButton);
        cityField = (EditText) findViewById(org.incubatex.androidapp.R.id.cityField);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putString("city", cityField.getText().toString()).commit();
                RegistrationTokenManager.sendRegistrationToServer(MainActivity.this);
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
        getMenuInflater().inflate(org.incubatex.androidapp.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == org.incubatex.androidapp.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendRegistrationToServer(final String token) {
        RequestQueue queue = Volley.newRequestQueue(this.getBaseContext());
        StringRequest sr = new StringRequest(Request.Method.POST,"http://192.168.1.5/notifications/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ErrorResponse", error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("token", token);
                params.put("city", getSharedPreferences("globalPreferences", Context.MODE_PRIVATE).getString("city", ""));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        queue.add(sr);
    }
}
