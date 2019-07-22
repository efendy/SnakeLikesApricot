package com.appikot.games.snakelikesapricot.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appikot.games.snakelikesapricot.GlobalValue;
import com.appikot.games.snakelikesapricot.R;
import com.appikot.generic.views.BaseAppCompatActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity
{
    private static final String TAG = "MainActivity";

    private InterstitialAd mInterstitialAd;

    private int padding = 32;
    private int headerHeight = 120;

    protected PlaygroundView playgroundView;
//    protected BoardView board;

    protected RelativeLayout layoutMain;
    protected TextView labelCurrentScore;
    protected TextView labelHighScore;
    protected TextView tvCurrentScore;
    protected TextView tvHighScore;
    protected TextView tvTitle;
    protected TextView tvTapToPlay;
    protected View invisibleRow;
//    protected AchievementView achievementView;
    protected DisplayMetrics outMetrics;
    protected Context thisContext;

    protected List<String> titleColors = new ArrayList<String>();
    protected int iterColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thisContext = this;

        layoutMain = (RelativeLayout) findViewById(R.id.layoutMain);
        labelCurrentScore = (TextView) findViewById(R.id.labelCurrentScore);
        labelHighScore = (TextView) findViewById(R.id.labelHighScore);
        tvCurrentScore = (TextView) findViewById(R.id.tvCurrentScore);
        tvHighScore = (TextView) findViewById(R.id.tvHighScore);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTapToPlay = (TextView) findViewById(R.id.tvTapToPlay);
        invisibleRow = (View) findViewById(R.id.invisibleRow);

        labelCurrentScore.setTypeface(this.genericTypeface);
        labelHighScore.setTypeface(this.genericTypeface);
        tvCurrentScore.setTypeface(this.genericTypeface);
        tvHighScore.setTypeface(this.genericTypeface);
        tvTitle.setTypeface(this.genericTypeface);
        tvTapToPlay.setTypeface(this.genericTypeface);

        titleColors.add("#FFFFFF");
        titleColors.add("#FFFF8D");
        titleColors.add("#69F0AE");
        titleColors.add("#FF8A80");

        tvTitle.setTextColor(Color.parseColor(titleColors.get(iterColor)));

        MobileAds.initialize(this,
                "ca-app-pub-3092178108959803~7620949276");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3092178108959803/6642893616");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        refreshScore();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        Display display = getWindowManager().getDefaultDisplay();
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        int boardWidth = outMetrics.widthPixels;
        int boardHeight = outMetrics.heightPixels;
        if (playgroundView == null)
        {
            playgroundView = new PlaygroundView(this);
            int aWidth = boardWidth;
            int aHeight = boardHeight - headerHeight;
            playgroundView.setLayoutParams(new ViewGroup.LayoutParams(aWidth, aHeight));
            playgroundView.setY((float) headerHeight);
            layoutMain.addView(playgroundView, 0);
            playgroundView.setOnBoardEventListener(onBoardEventListener);
        }

//        if (achievementView == null)
//        {
//            achievementView = new AchievementView(this);
//            int aWidth = boardWidth;
//            int aHeight = boardHeight;
//            achievementView.setLayoutParams(new ViewGroup.LayoutParams(aWidth, aHeight));
//            layoutMain.addView(achievementView);
//            achievementView.setOnBoardEventListener(onBoardEventListener);
//        }

        Log.d(TAG,"boardWidth/boardHeight:" + boardWidth + "," + boardHeight);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged");
    }

    private static final int DEFAULT_SCORE = 0;
    private int currentScore = DEFAULT_SCORE;

    private OnBoardEventListener onBoardEventListener = new OnBoardEventListener() {
        @Override
        public void onStartEvent() {
            tvTapToPlay.setVisibility(View.INVISIBLE);
            tvTitle.setVisibility(View.INVISIBLE);
//            achievementView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPauseEvent() {

        }

        @Override
        public void onStopEvent() {
            tvTapToPlay.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);

            // Save to Phone - START
            // if highScore is greater than 10K
            //    and currentScore is equal to highScore
//            achievementView.setVisibility(View.VISIBLE);
//            (Test) scView.setImageResource(R.drawable.ic_launcher);
//            scView.setImageBitmap(screenShot(layoutMain));
//            scView.invalidate();
            // Save to Phone - END

            currentScore = DEFAULT_SCORE;
            refreshScore();
            showGameOverAds();
        }

        @Override
        public void onScoreHitEvent(int score) {
            currentScore += score;
            if (GlobalValue.self().highScore < currentScore)
            {
                GlobalValue.self().highScore = currentScore;
                SharedPreferences pref = getSharedPreferences(GlobalValue.self().namespace, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("high-score", currentScore);
                boolean success = editor.commit();
            }
            refreshScore();
        }
    };

    private void refreshScore()
    {
        tvCurrentScore.setText(currentScore+"");
        tvHighScore.setText(GlobalValue.self().highScore+"");
    }

    private void showGameOverAds()
    {
        if (iterColor >= titleColors.size()) iterColor = 0;
        tvTitle.setTextColor(Color.parseColor(titleColors.get(iterColor++)));
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    // Screenshot

    public Bitmap screenShot(View view)
    {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    // Save to photo gallery
    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }
}
