package com.appikot.games.snakelikesapricot.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PlaygroundView extends View
{
    private static final String TAG = "PlaygroundView";

    private static final int DEFAULT_NUM_TAILS = 10;
    private static final int DEFAULT_SPEED = 250;
    private static final int DEFAULT_COUNTDOWN = 25;
    private static final int DEFAULT_MAX_SCORE = 20;
    private static final int DEFAULT_MIN_SCORE = 5;
    private static final int TAIL_INCREMENT = 1;
    private static final int SPEED_LIMIT = 150;
    private static final int SPEED_REDUCTION = 2;
    private static final int SCORE_REDUCTION_DELAY = 3;
    private static final int SPEED_CLEAR = 10;

    public static final Point MOVE_UP = new Point(0,-1);
    public static final Point MOVE_DOWN = new Point(0,1);
    public static final Point MOVE_LEFT = new Point(-1,0);
    public static final Point MOVE_RIGHT = new Point(1,0);
    public static final Point MOVE_STATION = new Point(0,0);

    private Point snakeHead, fruit;
    private Point snakeDirection = new Point(0,0);
    private ArrayList<Point> snakeTrails = new ArrayList<Point>();
    private int tails = DEFAULT_NUM_TAILS;

    private int boardXDots = 0, boardYDots = 0;
    private int dotSize = 32, dotPad = 1;
    private Paint paint = new Paint();

    private boolean isBoardSet = false;

    public PlaygroundView(Context context)
    {
        super(context);
        mHandler = new Handler();
    }

    protected void reset()
    {
        snakeHead = new Point();
        snakeHead.x = (int) (boardXDots * 1/3);
        snakeHead.y = (int) (boardYDots * 1/3);
        fruit = new Point();
        fruit.x = (int) (boardXDots * 2/3);
        fruit.y = (int) (boardYDots * 2/3);
        snakeDirection = MOVE_STATION;
        tails = DEFAULT_NUM_TAILS;
        mInterval = DEFAULT_SPEED;
        isClearing = false;
        stopRepeatingTask();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: " + getWidth() + "," + getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w,h,oldw,oldh);
        Log.d(TAG, "onSizeChanged: " + getWidth() + "," + getHeight());
        if (!isBoardSet)
        {
            measureGrid();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: " + getWidth() + "," + getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        drawBoard(canvas);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect)
    {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        Log.d(TAG, "onFocusChanged: " + getWidth() + "," + getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (snakeHead == null)
            {
                reset();
            }

            if (snakeDirection == MOVE_STATION)
            {
                startRepeatingTask();
            }

            int touchX = (int) ev.getX();
            int touchY = (int) ev.getY();
            int snakeHeadX = snakeHead.x * dotSize;
            int snakeHeadY = snakeHead.y * dotSize;

            if (!snakeDirection.equals(MOVE_RIGHT) && !snakeDirection.equals(MOVE_LEFT))
            {
                if (snakeHeadX < touchX)
                {
                    snakeDirection = MOVE_RIGHT;
                }
                if (snakeHeadX > touchX)
                {
                    snakeDirection = MOVE_LEFT;
                }
            } else {
                if (snakeHeadY < touchY)
                {
                    snakeDirection = MOVE_DOWN;
                }
                if (snakeHeadY > touchY)
                {
                    snakeDirection = MOVE_UP;
                }
            }

            if (snakeDirection.equals(MOVE_DOWN))
            {
                Log.d(TAG,"Snake Move: (v) - DOWN");
            } else if (snakeDirection.equals(MOVE_LEFT))
            {
                Log.d(TAG,"Snake Move: (<) - LEFT");
            } else if (snakeDirection.equals(MOVE_RIGHT))
            {
                Log.d(TAG,"Snake Move: (>) - RIGHT");
            } else if (snakeDirection.equals(MOVE_UP))
            {
                Log.d(TAG,"Snake Move: (^) - UP");
            } else {
                Log.d(TAG,"Snake Move: STATION");
            }
        }
        return true;
    }

    private void measureGrid()
    {
        Log.d(TAG,"measureGrid()");
        isBoardSet = true;
        int width = getWidth();
        int height = getHeight();

        boardXDots = (width / dotSize)-1;
        boardYDots = (height / dotSize)-1;
        int boardXModDots = width % dotSize;
        int boardYModDots = height % dotSize;
        Log.d(TAG,"measureGrid" +
                " Dots W: " + boardXDots + "," + boardXModDots +
                " Dots H: " + boardYDots + "," + boardYModDots);

        getLayoutParams().width = boardXDots * dotSize;
        getLayoutParams().height = boardYDots * dotSize;

        float oriX = getX();
        float oriY = getY();
        setX((float) (boardXModDots + dotSize) / 2 + oriX);
        setY((float) (boardYModDots + dotSize) / 2 + oriY);
        reset();
    }

    private void drawBoard(Canvas canvas)
    {
        if (snakeDirection.equals(MOVE_STATION)) return;

        paint.setStyle(Paint.Style.FILL);

        // Draw Board
        paint.setColor(Color.DKGRAY);
        for (int y = 0; y < boardYDots; y++)
        {
            for (int x = 0; x < boardXDots; x++)
            {
                drawDot(x, y, canvas, paint);
            }
        }

        if (isClearing)
        {
            // Clearing Tail
            if (snakeTrails != null && snakeTrails.size() > 0)
            {
                for (int i = 0; i < snakeTrails.size(); i++)
                {
                    paint.setColor(Color.parseColor("#69F0AE"));
                    drawDot(snakeTrails.get(i), canvas, paint);
                }

                snakeTrails.remove(0);
            } else {
                reset();
            }
        } else {
            // Draw Snake
            if (snakeHead != null)
            {
                snakeHead.x += snakeDirection.x;
                snakeHead.y += snakeDirection.y;
                snakeHead = getValidPoint(snakeHead);
                paint.setColor(Color.parseColor("#FF8A80"));
                drawDot(snakeHead, canvas, paint);
            }

            // Draw Tail
            if (snakeTrails != null)
            {
                for (int i = 0; i < snakeTrails.size(); i++)
                {
                    paint.setColor(Color.parseColor("#69F0AE"));
                    drawDot(snakeTrails.get(i), canvas, paint);

                    if (snakeTrails.get(i).equals(snakeHead)) {
                        isClearing = true;
                        mInterval = SPEED_CLEAR;
                    }
                }

                snakeTrails.add(new Point(snakeHead.x,snakeHead.y));
                while (snakeTrails.size() >= tails) {
                    snakeTrails.remove(0);
                }
            }

            // Draw Fruit
            if (fruit != null)
            {
                if (fruit.equals(snakeHead))
                {
                    tails+=TAIL_INCREMENT;
                    fruit.x = (int) Math.floor(Math.random()*boardXDots);
                    fruit.y = (int) Math.floor(Math.random()*boardYDots);
                    if (mInterval > SPEED_LIMIT)
                    {
                        mInterval-=SPEED_REDUCTION;
                    }
                    if (mListener != null)
                        mListener.onScoreHitEvent(score);
                    resetCountDown();
                }
                paint.setColor(Color.parseColor("#FFFF8D"));
                drawDot(fruit, canvas, paint);
            }
        }
    }

    private void drawDot(Point point, Canvas canvas, Paint paint)
    {
        drawDot(point.x, point.y, canvas, paint);
    }

    private void drawDot(int x, int y, Canvas canvas, Paint paint)
    {
        int left = x * dotSize + dotPad;
        int top = y * dotSize + dotPad;
        int right = (x + 1) * dotSize - dotPad;
        int bottom = (y + 1) * dotSize - dotPad;
        canvas.drawRect(left,top,right,bottom,paint);
    }

    private Point getValidPoint(Point point)
    {
        return getValidPoint(point.x, point.y);
    }

    private Point getValidPoint(int x, int y)
    {
        Point point = new Point();
        point.x = x;
        point.y = y;
        if (x >= boardXDots)
            point.x = 0;
        if (x < 0)
            point.x = boardXDots - 1;
        if (y >= boardYDots)
            point.y = 0;
        if (y < 0)
            point.y = boardYDots - 1;
        return point;
    }

    private boolean isClearing = false;
    private int mInterval = DEFAULT_SPEED;
    private Handler mHandler;
    protected OnBoardEventListener mListener;
    protected int score = DEFAULT_MAX_SCORE, countDown = DEFAULT_COUNTDOWN;

    private void resetCountDown()
    {
        score = DEFAULT_MAX_SCORE;
        countDown = DEFAULT_COUNTDOWN;
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                invalidate();
                Log.i(TAG, "scoreUp:"+score+", countDown:"+countDown);
                if (countDown <= 0)
                {
                    if (score > DEFAULT_MIN_SCORE)
                    {
                        score--;
                        countDown+=SCORE_REDUCTION_DELAY;
                    }
                } else {
                    countDown--;
                }
            } finally {
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        if (mListener != null)
            mListener.onStartEvent();
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        if (mListener != null)
            mListener.onStopEvent();
        mHandler.removeCallbacks(mStatusChecker);
    }

    public void setOnBoardEventListener(OnBoardEventListener eventListener) {
        mListener = eventListener;
    }
}
