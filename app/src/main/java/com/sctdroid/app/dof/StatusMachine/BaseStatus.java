package com.sctdroid.app.dof.StatusMachine;

/**
 * Created by lixindong on 9/9/16.
 */
public class BaseStatus implements IStatus {
    @Override
    public void preEnter() {

    }

    @Override
    public void enter() {

    }

    @Override
    public void preExit() {

    }

    @Override
    public void exit() {

    }

    BaseStatus mPreviousStatus = null;
    BaseStatus mNextStatus = null;

    public BaseStatus() {

    }


}
