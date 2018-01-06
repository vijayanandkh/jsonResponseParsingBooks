package com.vijay.demoapps.jsonresponseapp;

import android.content.res.Resources;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vijay.demoapps.jsonresponseapp.dummy.DummyContent;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements DataCallback, ItemFragment.OnListFragmentInteractionListener, ReadInputFragment.OnFragmentInteractionListener {
    public final static String FRAGMENT_REQUEST_TAG = "ITEM_REQUEST_FRAGMENT";
    public final static String FRAGMENT_RESULT_TAG = "ITEM_RESULT_FRAGMENT";
    private static final String TAG = MainActivity.class.getSimpleName();

    private String parameters;
    ItemFragment itemFragment;
    ReadInputFragment readInputFragment;
    ResponseProcessor responseProcessor = new ResponseProcessor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        readInputFragment = (ReadInputFragment) fm.findFragmentByTag(FRAGMENT_REQUEST_TAG);
        itemFragment = (ItemFragment) fm.findFragmentByTag(FRAGMENT_RESULT_TAG);
        if(readInputFragment == null) {
            readInputFragment = ReadInputFragment.newInstance(null,null);
            fm.beginTransaction().replace(R.id.frame_placeholder, readInputFragment, FRAGMENT_REQUEST_TAG).commit();
        }
        if(itemFragment == null) {
            itemFragment = ItemFragment.newInstance(1);
        }


    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onButtonClicked(String parameter) {
        this.parameters = parameter;
        Log.d(TAG, "MainActivity replacing fragment and calling network operations");
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_placeholder, itemFragment, FRAGMENT_RESULT_TAG).commit();
        fetchPasses.run();
    }

    Runnable fetchPasses = new Runnable() {
        @Override
        public void run() {
            fetchPassesInfo(MainActivity.this);
        }
    };

    private void fetchPassesInfo(final DataCallback callback) {
        Log.d(TAG, "MainActivity fetchPassesInfo calling network operations");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL+getParameters(), null, new Response.Listener<JSONObject> () {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "fetchPassesInfo:onResponse");
                responseProcessor.onResponse(response);
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "fetchPassesInfo:onErrorResponse: " + error.getMessage());
                responseProcessor.onErrorResponse(error);
                callback.onFailure(error);
            }
        });
        NetworkOpertions.getInstance(getApplicationContext()).addToRequestQueue(getApplicationContext(), jsonObjectRequest);
    };

    private String getParameters() {
        return parameters;
    }

    @Override
    public void onSuccess(JSONObject resultArray) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    if (!isFinishing()) {
                        ItemFragment frag = (ItemFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_RESULT_TAG);
                        frag.updateAdapter(responseProcessor.getBooksItemsList());
                    }
            }
        });
    }

    @Override
    public void onFailure(VolleyError error) {

    }


}
