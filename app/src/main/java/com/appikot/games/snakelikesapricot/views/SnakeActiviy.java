package com.appikot.games.snakelikesapricot.views;

import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appikot.games.snakelikesapricot.R;
import com.appikot.generic.views.BaseAppCompatActivity;

public class SnakeActiviy extends BaseAppCompatActivity
{
    private static final String TAG = "SnakeActiviy";

    public static final Point MOVE_UP = new Point(0,-1);
    public static final Point MOVE_DOWN = new Point(0,1);
    public static final Point MOVE_LEFT = new Point(-1,0);
    public static final Point MOVE_RIGHT = new Point(1,0);
    public static final Point MOVE_STATION = new Point(0,0);

    private int padding = 32;
    private int headerHeight = 120;

    protected RelativeLayout layoutMain;
    protected TextView labelCurrentScore;
    protected TextView labelHighScore;
    protected TextView tvCurrentScore;
    protected TextView tvHighScore;
    protected View invisibleRow;
    protected DisplayMetrics outMetrics;
    protected BoardView boardView;

    private int boardColumns = 0;
    private int boardRows = 0;
    private int[][] gridValues;
    private int[] gridColors;
    private int dotSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutMain = (RelativeLayout) findViewById(R.id.layoutMain);
        labelCurrentScore = (TextView) findViewById(R.id.labelCurrentScore);
        labelHighScore = (TextView) findViewById(R.id.labelHighScore);
        tvCurrentScore = (TextView) findViewById(R.id.tvCurrentScore);
        tvHighScore = (TextView) findViewById(R.id.tvHighScore);
        invisibleRow = (View) findViewById(R.id.invisibleRow);

        Display display = getWindowManager().getDefaultDisplay();
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        int boardWidth = outMetrics.widthPixels;
        int boardHeight = outMetrics.heightPixels - headerHeight;

        if (boardView == null)
        {
            boardView = new BoardView(this);
            dotSize = boardView.getDotSize();

            int numColumns = (boardWidth / dotSize) - 1;
            int numRows =  (boardHeight / dotSize) - 1;
            int padColumn = boardWidth % dotSize + dotSize;
            int padRow = boardHeight % dotSize + dotSize;

            ViewGroup.LayoutParams params =
                    new ViewGroup.LayoutParams(
                            numColumns * dotSize,
                            numRows * dotSize);
            boardView.setLayoutParams(params);
            boardView.setX((float) padColumn/2);
            boardView.setY((float) padRow/2 + headerHeight);
            boardView.setGridDimension(numColumns, numRows);

            Log.i(TAG, numColumns+","+numRows+","+padColumn+","+padRow+","+
                    boardView.getX()+","+boardView.getY());
            layoutMain.addView(boardView, 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");

        if (boardView != null)
        {
//            boardView.setGridDimension();
//            boardView.setGridValues();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(TAG, "onResume");

        if (boardView != null)
        {
//            boardView.setGridDimension();
//            boardView.setGridValues();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i(TAG, "onWindowFocusChanged");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            int touchX = (int) event.getX();
            int touchY = (int) event.getY();

        }
        return super.onTouchEvent(event);
    }
}
