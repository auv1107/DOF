package com.sctdroid.app.dof;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lixindong on 9/8/16.
 */
public class MaskLayer extends ImageView {
    private static final String TAG = MaskLayer.class.getSimpleName();
    private Bitmap mBitmap;
    private PorterDuffXfermode mShaderXFermode;
    private Xfermode mNormal;

    public MaskLayer(Context context) {
        this(context, null);
    }

    public MaskLayer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskLayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
//        setBackgroundColor(Color.TRANSPARENT);
        setWillNotDraw(false);
    }

    ImageTouchDelegate mDelegate;

    public void setTouchDelegate(ImageTouchDelegate delegate) {
        mDelegate = delegate;
        setOnTouchListener(mDelegate);
    }

    public void init() {
        mPath = new Path();
        mPath.moveTo(0,0);
        mPath.lineTo(300,500);
        mPath.lineTo(500,300);
        mPath.lineTo(0, 1000);
        mPath.lineTo(1000, 0);
        mPath.lineTo(0,0);

        mPaint = getDefaultPaint();

        mShaderXFermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mNormal = new PorterDuffXfermode(PorterDuff.Mode.XOR);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car);
        post(new Runnable() {
            @Override
            public void run() {
                mSrc.set(0,0,mBitmap.getWidth(),mBitmap.getHeight());
                float scale = mBitmap.getWidth() * 1.0f / getWidth();
                int height = (int) (mBitmap.getHeight() / scale);
                int top = (getHeight() - height) / 2;
                mDst.set(0, top, getWidth(), top+height);
            }
        });
    }

    public Paint getDefaultPaint() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(60);
        paint.setAntiAlias(true);

        return paint;
    }

    Path mPath;
    Paint mPaint;
    Rect mSrc = new Rect();
    Rect mDst = new Rect();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int saveFlags = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, saveFlags);
        if (mDelegate != null && mDelegate.mPaths.size() > 0) {
            for (Path path : mDelegate.mPaths) {
                canvas.drawPath(path, mPaint);
            }
        }
        mPaint.setXfermode(mShaderXFermode);
        canvas.drawBitmap(mBitmap, mSrc, mDst, mPaint);
        mPaint.setXfermode(null);
        canvas.restore();
    }
}
