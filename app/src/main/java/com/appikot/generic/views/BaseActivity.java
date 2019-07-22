package com.appikot.generic.views;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by EFENDYS on 4/5/2016.
 */
public class BaseActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass

        // Stop method tracing that the activity started during onCreate()
        android.os.Debug.stopMethodTracing();
    }
//    @Override
//    public void onSaveInstanceState(Bundle frozenState)
//    {
//        super.onSaveInstanceState(frozenState);
//    }

//    @Override
//    public void onBackPressed()
//    {
//        Log.d("CDA", "onBackPressed Called");
//    }
}
