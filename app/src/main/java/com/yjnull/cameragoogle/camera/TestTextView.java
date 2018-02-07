package com.yjnull.cameragoogle.camera;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by yangy on 2018/2/6.
 */

public class TestTextView extends TextView {
    private static final String TAG = "TestTextView";
    private int mLastX = 0;
    private int mLastY = 0;

    private Scroller mScroller;

    public TestTextView(Context context) {
        super(context);
        Log.d(TAG, "构造, Context-------: ");
        init();
    }

    public TestTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "构造, Context AttributeSet-------: ");
        init();
    }

    public TestTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "构造, Context AttributeSet defStyleAttr-------: ");
        init();
    }

    private void init(){
        mScroller = new Scroller(getContext());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "move, ACTION_DOWN-------: ");
                //smoothScrollTo(-100, 0);
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.d(TAG, "move, deltaX: " + deltaX + " deltaY: " + deltaY +
                        " getRawX: " + x +" getRawY: " + y +
                        " getTranslationX: " + getTranslationX() +" getTranslationY: " + getTranslationY());
                setTranslationX((int) getTranslationX() + deltaX);
                setTranslationY((int) getTranslationY() + deltaY);
                break;

            case MotionEvent.ACTION_UP:
                Log.d(TAG, "move, ACTION_UP--------: ");
                ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(3000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float animatedFraction = animation.getAnimatedFraction();
                        scrollTo((int) (300 * animatedFraction), 0);
                    }
                });
                animator.start();
                //ObjectAnimator.ofFloat(this, "translationX", 0, 300).setDuration(2000).start();
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;*/
        //return true;
        Log.d(TAG, "move, onTouchEvent--------: ");
        return super.onTouchEvent(event);
    }

    private void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int dx = destX - scrollX;
        Log.d(TAG, "smoothScrollTo,--------: " + scrollX + ",  dx: " + dx);
        mScroller.startScroll(scrollX, 0, dx, 0, 2000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
