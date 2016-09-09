package com.sctdroid.app.dof.StatusMachine;

import com.sctdroid.app.dof.IScene;
import com.sctdroid.app.dof.MaskLayer;

/**
 * Created by lixindong on 9/9/16.
 */
public class PencelEditStatus implements IStatus {
    IScene mScene;
    public PencelEditStatus(IScene scene) {
        mScene = scene;
    }
    @Override
    public void preEnter() {

    }

    @Override
    public void enter() {
        mScene.updateMaskLayerMode(MaskLayer.MODE_PENCEL);
    }

    @Override
    public void preExit() {

    }

    @Override
    public void exit() {

    }
}
