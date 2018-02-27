package com.yjnull.cameragoogle.camera;

import android.graphics.Matrix;

/**
 * Created by yangy on 2018/2/2.
 */

public class EventUtil {
    public static final int UPDATE_FACE_RECT = 0x001;


    public static void prepareMatrix(Matrix matrix, boolean mirror, int displayOrientation,
                                     int viewWidth, int viewHeight) {
        matrix.setScale(mirror ? -1 : 1, 1);
        matrix.postRotate(displayOrientation);

        matrix.postScale(viewWidth/2000f, viewHeight/2000f);
        matrix.postTranslate(viewWidth/2f, viewHeight/2f);
    }

}
