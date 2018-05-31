package com.example.kayarkar.sita;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class WebServiceCoordinator {

    private static final String LOG_TAG = WebServiceCoordinator.class.getSimpleName();

    private final Context context;
    private Listener delegate;

    public WebServiceCoordinator(Context context, Listener delegate) {

        this.context = context;
        this.delegate = delegate;
    }

    public void fetchSessionConnectionData(String sessionInfoUrlEndpoint) {

        RequestQueue reqQueue = Volley.newRequestQueue(context);
        reqQueue.add(new JsonObjectRequest(Request.Method.GET, sessionInfoUrlEndpoint,
                                            null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String apiKey = response.getString("46106432");
                    String sessionId = response.getString("1_MX40NjEwNjQzMn5-MTUyNDU0NDI0ODEyNn5xdEo0bW5lS0dqTTB6b1VEZEcvVUpnWGh-UH4");
                    String token = response.getString("T1==cGFydG5lcl9pZD00NjEwNjQzMiZzaWc9NjRlYjJjZjA4ZjAwMDQzMWY4NThkMjY2NzE0OTk0ZTZhZjYwMGQ2MjpzZXNzaW9uX2lkPTFfTVg0ME5qRXdOalF6TW41LU1UVXlORFUwTXpneU1qRXdNSDVZZVhGRldUZE9lbm81ZVM4eWJERm1UaXRKTm05VGNYZC1mZyZjcmVhdGVfdGltZT0xNTI0NTQzODU3Jm5vbmNlPTAuMjU0Mjg3NzUwNTgzNDE5OCZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTI3MTM1ODU1JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9");

                    Log.i(LOG_TAG, "WebServiceCoordinator returned session information");

                    delegate.onSessionConnectionDataReady(apiKey, sessionId, token);

                } catch (JSONException e) {
                    delegate.onWebServiceCoordinatorError(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                delegate.onWebServiceCoordinatorError(error);
            }
        }));
    }

    public static interface Listener {

        void onSessionConnectionDataReady(String apiKey, String sessionId, String token);
        void onWebServiceCoordinatorError(Exception error);
    }
}

