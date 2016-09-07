package com.sctdroid.app.dof;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;

/**
 * Created by lixindong on 9/7/16.
 */
public class ImageTouchDelegate implements View.OnTouchListener {

    private static final String TAG = ImageTouchDelegate.class.getSimpleName();

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        for (int i = 0; i < event.getSize(); i++) {
            Log.d(TAG, "point " + i + ": " + event.getX(i) + "," + event.getY(i));
        }
        return true;
    }
}
