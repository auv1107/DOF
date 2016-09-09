package com.sctdroid.app.dof.StatusMachine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixindong on 9/9/16.
 */
public class StepContext {
    List<IStatus> mStatusList = new ArrayList<>();
    int mDefaultIndex = -1;
    int mCurrentIndex = mDefaultIndex;
    public StepContext(IStatus defaultStatus) {
        mCurrentIndex = 0;
        mStatusList.add(defaultStatus);
    }

    public IStatus nextStep() {
        if (hasNext()) {
            return mStatusList.get(++mCurrentIndex);
        } else {
            return null;
        }
    }

    public boolean hasNext() {
        return mStatusList.size() > mCurrentIndex + 1;
    }

    public StepContext next(IStatus status) {
        mStatusList.add(status);
        return this;
    }

    public void enterNextStep() {
        enterStep(mCurrentIndex+1);
        mCurrentIndex += 1;
    }

    public void enterStep(int index) {
        IStatus next = mStatusList.get(index);
        IStatus cur = mStatusList.get(mCurrentIndex);

        next.preEnter();
        cur.preExit();
        next.enter();
        cur.exit();
    }

    public void moveToFirst() {
        enterStep(0);
        mCurrentIndex = 0;
    }
}
