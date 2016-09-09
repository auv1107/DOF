package com.sctdroid.app.dof;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sctdroid.app.dof.StatusMachine.BlurEditStatus;
import com.sctdroid.app.dof.StatusMachine.IStatus;
import com.sctdroid.app.dof.StatusMachine.PencelEditStatus;
import com.sctdroid.app.dof.StatusMachine.StepContext;

public class MainActivity extends AppCompatActivity implements IScene {

    private static final String TAG = MainActivity.class.getSimpleName();
    ImageView mImage;
    SeekBar mSeekbar;
    Bitmap mBitmap;
    MaskLayer mMaskLayer;
    TextView mNextStep;

    int mProcess;
    StepContext mStepContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImage != null) {
//                    FlashAnimation.flashOnView(mImage, 1, 0.1f, 300, 0);
                    testFlashCanvasAnimation();
                    mMaskLayer.mDelegate.mPaths.clear();
                    mMaskLayer.postInvalidate();
                }
            }
        });
        initStatusMachine();
        initObjects();
        findViews();
        initViews();
    }

    private void initStatusMachine() {
        mStepContext = new StepContext(new PencelEditStatus(this))
                .next(new BlurEditStatus(this));
    }

    @Override
    public void updateMaskLayerMode(int mode) {
        if (mMaskLayer != null) {
            mMaskLayer.setMode(mode);
        }
    }

    public void testFlashCanvasAnimation() {
        FlashCanvasAnimation animation = new FlashCanvasAnimation();
    }

    private void initObjects() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            PhotoFileManager.startPickActivity(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        Log.d(TAG, PhotoFileManager.getPath(this, data.getData()));
        String url = PhotoFileManager.getPath(this, data.getData());
        Bitmap bitmap = PhotoFileManager.getLoacalBitmap(url);
        mBitmap = bitmap;
        mImage.setImageBitmap(bitmap);
        mMaskLayer.mDelegate.reset();
        mMaskLayer.setBitmap(bitmap);
        DOFOnImage(mImage, mProcess >> 2);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void findViews() {
        mImage = (ImageView) findViewById(R.id.image);
        mSeekbar = (SeekBar) findViewById(R.id.seekbar);
        mMaskLayer = (MaskLayer) findViewById(R.id.maskLayer);
        mNextStep = (TextView) findViewById(R.id.nextStep);
    }

    public void initViews() {
        mProcess = mSeekbar.getProgress();
        DOFOnImage(mImage, mProcess >> 2);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mProcess = mSeekbar.getProgress();
                DOFOnImage(mImage, mProcess >> 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mMaskLayer.setTouchDelegate(new ImageTouchDelegate());

        mNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStepContext != null && mStepContext.hasNext()) {
                    mStepContext.enterNextStep();
                    mMaskLayer.postInvalidate();
                }
            }
        });
    }
    AsyncTask lastTask = null;

    public void DOFOnImage(final ImageView imageView, final int radius) {
        AsyncTask task = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(final Object o) {
                if (o == null) return;
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap((Bitmap) o);
                    }
                });
            }

            @Override
            protected Object doInBackground(Object[] params) {
                if (params != null && params.length > 0) {
                    Bitmap b = (Bitmap) params[0];
                    b = DofUtils.doBlur(b, radius);
                    return b;
                }
                return null;
            }
        };
        if (lastTask != null && lastTask.getStatus() == AsyncTask.Status.RUNNING && !lastTask.isCancelled()) {
            lastTask.cancel(true);
        }
        lastTask = task;
        task.execute(mBitmap);
    }
}
