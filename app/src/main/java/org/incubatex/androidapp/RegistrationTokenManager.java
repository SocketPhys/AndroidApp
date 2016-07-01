package org.incubatex.androidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by avikj on 6/30/16.
 */
public class RegistrationTokenManager {
    private static final String REGISTRATION_POST_URL = "http://192.168.1.5:3000/notifications/register";

    public static void sendRegistrationToServer(final Context context) {
        final SharedPreferences prefs  = context.getSharedPreferences("globalPreferences", Context.MODE_PRIVATE);
        Log.d("TokenManager", "Sending token to server");
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, REGISTRATION_POST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
                prefs.edit().putBoolean("registrationTokenSent", true).commit();
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
                params.put("token", FirebaseInstanceId.getInstance().getToken());
                params.put("city", prefs.getString("city", ""));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }
}
