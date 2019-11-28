package com.neelesh.numberpicker.interfaces;

import android.os.SystemClock;
import android.view.View;

public abstract class OnSingleClickListener implements View.OnClickListener {
    private long clickInterval = 1000;
    private long mLastClickTime;

    public OnSingleClickListener() {

    }

    public OnSingleClickListener(long clickInterval) {
        this.clickInterval = clickInterval;
    }

    @Override
    public void onClick(View view) {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;

        mLastClickTime = currentClickTime;

        if (elapsedTime <= clickInterval) {
            return;
        }

        onSingleClick(view);
    }

    public abstract void onSingleClick(View v);
}