package com.yjnull.cameragoogle.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by yangy on 2018/2/2.
 */

public class FaceView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final String TAG = "FaceView";
    private Context mContext;
    private Paint mLinePaint;
    private Camera.Face[] mFaces;
    private Matrix matrix = new Matrix();
    private RectF mRectF = new RectF();
    private Drawable mFaceIndicator = null;

    public FaceView(Context context) {
        super(context);

        this.mContext = context;
        initPaint();
        mContext = context;

        //手势检测
        GestureDetector mGestureDetector = new GestureDetector(mContext, this);
        mGestureDetector.setIsLongpressEnabled(false);

        //弹性滑动
        Scroller scroller = new Scroller(mContext);
    }

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.YELLOW);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(3f);

    }

    public void setFaces(Camera.Face[] faces) {
        this.mFaces = faces;
        invalidate();
    }

    public void clearFaces() {
        mFaces = null;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mFaces == null || mFaces.length < 1) {
            return;
        }
        EventUtil.prepareMatrix(matrix, false, 0, getWidth(), getHeight());
        canvas.save();
        matrix.postRotate(0);
        canvas.rotate(0);
        for (int i = 0; i < mFaces.length; i++) {
            mRectF.set(mFaces[i].rect);
            matrix.mapRect(mRectF);
            Log.d(TAG, "onDraw: ===================RectF= left : " + mRectF.left +
                    " ,top : " + mRectF.top + " ,right : " + mRectF.right + " ,bottom : " + mRectF.bottom);
            canvas.drawRect(mRectF, mLinePaint);
        }

        Log.d(TAG, "onDraw: ==================mFaces[0]== left : " + mFaces[0].rect.left +
                " ,top : " + mFaces[0].rect.top + " ,right : " + mFaces[0].rect.right + " ,bottom : " + mFaces[0].rect.bottom);
        //mRectF.set(100, 100, 300, 300);
        //canvas.drawRect(mRectF, mLinePaint);
    }


    //=========滑动==========

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }

    //=========滑动==========


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
