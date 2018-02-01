package com.yjnull.cameragoogle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by yangy on 2017/7/13.
 */

public class RectView extends View {
    Paint mPaint;
    Rect mRect;

    static RectView mRectView = null;

    public static RectView getInstance(Context mContext, Rect rect){
        if(mRectView == null){
            mRectView = new RectView(mContext, rect);
        }
        return mRectView;
    }

    public RectView(Context context, Rect rect) {
        super(context);

        mRect = rect;

        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(16);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(mRect, mPaint);
    }
}
