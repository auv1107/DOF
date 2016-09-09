package com.sctdroid.app.dof.StatusMachine;

/**
 * Created by lixindong on 9/9/16.
 */
public interface IStatus {
    void preEnter();
    void enter();
    void preExit();
    void exit();
}
