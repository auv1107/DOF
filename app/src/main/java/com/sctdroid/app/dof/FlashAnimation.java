package com.sctdroid.app.dof;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by lixindong on 9/7/16.
 */
public class FlashAnimation {
    public static void flashOnView(View view, float startAlpha, float endAlpha, int duration, int delay) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", startAlpha, endAlpha, startAlpha).setDuration(duration*2);
        animator.setStartDelay(delay);
        animator.start();
    }
}
