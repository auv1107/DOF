package com.sctdroid.app.dof;

import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixindong on 9/7/16.
 */
public class ImageTouchDelegate implements View.OnTouchListener {

    private static final String TAG = ImageTouchDelegate.class.getSimpleName();

    public ImageTouchDelegate() {
        mPaths = new ArrayList<>();
    }

    List<Path> mPaths;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX() - v.getX();
        float y = event.getY() - v.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Path path = new Path();
                path.moveTo(x, y);
                mPaths.add(path);
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                path = mPaths.get(mPaths.size()-1);
                path.lineTo(x, y);
                break;
        }
        v.postInvalidate();
        return true;
    }
}
