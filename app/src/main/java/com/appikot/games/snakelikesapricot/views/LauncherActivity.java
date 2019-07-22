package com.appikot.games.snakelikesapricot.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.appikot.games.snakelikesapricot.GlobalValue;
import com.appikot.games.snakelikesapricot.R;
import com.appikot.generic.views.BaseActivity;

public class LauncherActivity extends BaseActivity
{
    private static final String TAG = "LauncherActivity";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    @Override
    public void onBackPressed()
    {
        Log.d("CDA", "onBackPressed Called");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        SharedPreferences pref = getSharedPreferences(GlobalValue.self().namespace, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putInt("high-score", 0);
//        boolean success = editor.commit();
        int highScore = pref.getInt("high-score", 0);
        GlobalValue.self().highScore = highScore;

        (new Handler()).postDelayed(new Runnable()
        {
            public void run()
            {
                toNextView();
            }
        }, 2800);
    }

    private void toNextView()
    {
        Intent intent = null;
        intent = new Intent(LauncherActivity.this, MainActivity.class);
//        intent = new Intent(LauncherActivity.this, SnakeActiviy.class);
        startActivity(intent);
        this.finish();
    }
}
