package com.vijay.demoapps.jsonresponseapp;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by vijay on 1/5/2018.
 */

public interface DataCallback {
    void onSuccess(JSONObject resultArray);
    void onFailure(VolleyError error);
}
