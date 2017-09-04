package com.android.yzd.ui.thread;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/1/15.
 */
public class TimeCount extends CountDownTimer {

    private TextView text;
    private String prompt;

    public void setTimeCount(TextView text, String prompt) {
        this.text = text;
        this.prompt = prompt;
    }

    public TimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        text.setClickable(false);
        text.setAlpha(0.5f);
        text.setText(millisUntilFinished / 1000 + prompt);
    }

    @Override
    public void onFinish() {
        text.setClickable(true);
        text.setAlpha(1f);
        text.setText("重新发送");
    }

    public void onStop() {
        this.cancel();
    }
}
