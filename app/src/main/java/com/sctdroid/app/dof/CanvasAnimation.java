package com.sctdroid.app.dof;

import android.graphics.Canvas;

/**
 * Created by lixindong on 9/7/16.
 */
public interface CanvasAnimation {
    void preOnDraw(Canvas canvas);
    void postOnDraw(Canvas canvas);
}
