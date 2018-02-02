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
import android.widget.ImageView;

/**
 * Created by yangy on 2018/2/2.
 */

public class FaceView extends ImageView {
    private static final String TAG = "FaceView";
    private Context mContext;
    private Paint mLinePaint;
    private Camera.Face[] mFaces;
    private Matrix matrix = new Matrix();
    private RectF mRectF = new RectF();
    private Drawable mFaceIndicator = null;

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initPaint();
        mContext = context;
    }

    private void initPaint() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.YELLOW);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(5f);

    }

    public void setFaces(Camera.Face[] faces) {
        this.mFaces = faces;
        invalidate();
    }
    public void clearFaces(){
        mFaces = null;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ==================== Face View Draw");
        canvas.save();
        mRectF.set(100, 100, 100, 100);
        canvas.drawRect(mRectF, mLinePaint);
        canvas.restore();

        super.onDraw(canvas);
    }
}
