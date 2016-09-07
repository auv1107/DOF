package com.sctdroid.app.dof;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixindong on 9/7/16.
 */
public class FlashCanvasAnimation implements CanvasAnimation {
    private List<Pair<Rect, Float>> pairs = new ArrayList<>();
    private Paint paint = new Paint();
    @Override
    public void preOnDraw(Canvas canvas) {

    }

    @Override
    public void postOnDraw(Canvas canvas) {
        if (pairs == null) return;

        canvas.save();
        for (Pair<Rect, Float> pair : pairs) {
            paint.setAlpha((int) (pair.second * 255));
            canvas.clipRect(pair.first);
            canvas.drawPaint(paint);
        }
        canvas.restore();
    }

    public void setPairs(List<Pair<Rect, Float>> pairs) {
        this.pairs = pairs;
    }
}
