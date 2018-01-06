package com.vijay.demoapps.jsonresponseapp;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.vijay.demoapps.jsonresponseapp.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.vijay.demoapps.jsonresponseapp.Constants.DBG;

/**
 * Created by vijay on 1/5/2018.
 */

public class ResponseProcessor implements Response.Listener<JSONObject>, Response.ErrorListener {

    private List<DummyContent.DummyItem> booksItemsList;

    public ResponseProcessor() {
        booksItemsList = new ArrayList<DummyContent.DummyItem>(26);
    }

    @Override
    public void onResponse(JSONObject response) {
        // on success, parse objects
        // Process the JSON
        Log.d(TAG, "ResponseProcess:onResponse");
        try{
            booksItemsList.clear();
            // Loop through the array elements
            JSONObject data = response.getJSONObject("data");
            JSONObject result = data.getJSONObject("result");
            JSONArray arrayObject = result.getJSONArray("items");
            for(int i=0;i<arrayObject.length();i++){
                // Get current json object
                JSONObject booksInfo = arrayObject.getJSONObject(i);
                String title = booksInfo.getString("title");
                String media = booksInfo.getString("media");
                booksItemsList.add(i,new DummyContent.DummyItem(i+"",title, media));
                if(DBG) Log.d(TAG, "Info itmes: " + i + " title: " + title + " media: "+ media);
            }
        }catch (JSONException e){
            Log.e(TAG, "erroor processing response: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // on error, check error and notify
        Log.d(TAG, "ResponseProcess:onErrorResponse" + error.getMessage());
        booksItemsList.clear();
    }

    public List<DummyContent.DummyItem> getBooksItemsList(){
        return booksItemsList;

        }
}
