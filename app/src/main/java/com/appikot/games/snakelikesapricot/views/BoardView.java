package com.appikot.games.snakelikesapricot.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

public class BoardView extends View
{
    private static final String TAG = "BoardView";

    private static final int DEFAULT_PAINT_COLOR = Color.DKGRAY;
    private static final int DEFAULT_NUMBER_OF_COLUMNS = 5;
    private static final int DEFAULT_NUMBER_OF_ROWS = 5;
    private static final int DEFAULT_DOT_SIZE = 32;
    private static final int DEFAULT_DOT_PAD = 1;

    private int numColumns = DEFAULT_NUMBER_OF_COLUMNS, numRows = DEFAULT_NUMBER_OF_ROWS;
    private int dotSize = DEFAULT_DOT_SIZE, dotPad = DEFAULT_DOT_PAD;
    private final Paint paint = new Paint();
    private int[][] gridValues;
    private int[] gridColors;

    public BoardView(Context context)
    {
        super(context);
        paint.setColor(DEFAULT_PAINT_COLOR);
        paint.setStyle(Paint.Style.FILL);
        gridValues = new int[numColumns][numRows];
        gridColors = new int[8];
        gridColors[0] = DEFAULT_PAINT_COLOR;
        validateGridValues();
    }

    public void setGridDimension(int columns, int rows)
    {
        numColumns = columns;
        numRows = rows;
        gridValues = new int[numColumns][numRows];
        validateGridValues();
    }

    public void setGridValues(int[][] values)
    {
        gridValues = values;
        gridValues = new int[numColumns][numRows];
        validateGridValues();
    }

    public void setGridColors(int[] colors)
    {
        gridColors = colors;
        invalidate();
    }

    public void setGridColor(int index, int color)
    {
        gridColors[index] = color;
        invalidate();
    }

    public int getNumColumns()
    {
        return numColumns;
    }

    public int getNumRows()
    {
        return getNumRows();
    }

    public int getDotSize()
    {
        return dotSize;
    }

    public int getDotPad()
    {
        return dotPad;
    }

    public int[][] getGridValues()
    {
        return gridValues;
    }

    public void validateGridValues()
    {
        for (int row = 0; row < numRows; row++)
        {
            for (int column = 0; column < numColumns; column++)
            {
                Log.i(TAG, "gridValues["+column+"]["+row+"] = "+ gridValues[column][row]);
                gridValues[column][row] = 0;
            }
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: " + getWidth() + "," + getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w,h,oldw,oldh);
        Log.i(TAG, "onSizeChanged: " + getWidth() + "," + getHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout: " + getWidth() + "," + getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            for (int row = 0; row < numRows; row++)
            {
                for (int column = 0; column < numColumns; column++)
                {
                    drawDot(column, row, canvas, paint);
                }
            }
        } catch (Error error)
        {
            // reset()
            // throw event()
        }
    }

    private void drawDot(int column, int row, Canvas canvas, Paint paint)
    {
        int left = column * dotSize + dotPad;
        int top = row * dotSize + dotPad;
        int right = (column + 1) * dotSize - dotPad;
        int bottom = (row + 1) * dotSize - dotPad;
        canvas.drawRect(left,top,right,bottom,paint);
    }

    private Point getValidPosition(int column, int row)
    {
        Point point = new Point();
        point.x = column;
        point.y = row;
        if (column >= numColumns)
            point.x = 0;
        if (column < 0)
            point.x = numColumns - 1;
        if (row >= numRows)
            point.y = 0;
        if (row < 0)
            point.y = numRows - 1;
        return point;
    }
}
