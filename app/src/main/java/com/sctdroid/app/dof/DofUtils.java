package com.sctdroid.app.dof;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.enrique.stackblur.StackBlurManager;

/**
 * Created by lixindong on 9/7/16.
 */
public class DofUtils {
    private static final String TAG = DofUtils.class.getSimpleName();

    public static void blur(Context context, Bitmap bkg, View view) {
//        long startMs = System.currentTimeMillis();
//        float radius = 20;
//
//        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
//                (int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(overlay);
//        canvas.translate(-view.getLeft(), -view.getTop());
//        canvas.drawBitmap(bkg, 0, 0, null);
//        overlay = FastBlur.doBlur(overlay, (int)radius, true);
//        view.setBackground(new BitmapDrawable(context.getResources(), overlay));
//        Log.d(TAG, System.currentTimeMillis() - startMs + "ms");
    }

    public static Bitmap doBlur(Bitmap bitmap, int radius) {
        StackBlurManager _stackBlurManager = new StackBlurManager(bitmap);
        _stackBlurManager.process(radius);
        return _stackBlurManager.returnBlurredImage();
    }
}
