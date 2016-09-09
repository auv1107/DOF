package com.sctdroid.app.dof.StatusMachine;

import com.sctdroid.app.dof.IScene;
import com.sctdroid.app.dof.MaskLayer;

/**
 * Created by lixindong on 9/9/16.
 */
public class BlurEditStatus implements IStatus {
    IScene mScene;
    public BlurEditStatus(IScene scene) {
        mScene = scene;
    }
    @Override
    public void preEnter() {

    }

    @Override
    public void enter() {
        mScene.updateMaskLayerMode(MaskLayer.MODE_BLUR);
    }

    @Override
    public void preExit() {

    }

    @Override
    public void exit() {

    }
}
