package org.incubatex.androidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by avik on 6/17/2016.
 */
public class IncubateFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        SharedPreferences prefs = getSharedPreferences("globalPreferences", Context.MODE_PRIVATE);
        if(prefs.getString("city", null) != null) {
            RegistrationTokenManager.sendRegistrationToServer(this.getApplicationContext());
            prefs.edit().putBoolean("registrationTokenSent", false).commit();
        } else {
            prefs.edit().putBoolean("registrationTokenSent", false).commit();
        }
    }
}
