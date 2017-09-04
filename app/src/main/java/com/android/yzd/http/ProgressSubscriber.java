package com.android.yzd.http;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.yzd.ui.view.BaseDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/8/29.
 * 加载网络封装包
 */

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;
    BaseDialog dialog;

    private boolean isShow = true;

    //点击可取消
    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, false);
        dialog = new BaseDialog(context);
    }

    //自定义是否取消
    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context, boolean cancelable) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, cancelable);
        dialog = new BaseDialog(context);
    }

    //是否显示
    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context, boolean cancelable, boolean isShow) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        if (isShow)
            mProgressDialogHandler = new ProgressDialogHandler(context, this, cancelable);
        dialog = new BaseDialog(context);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }


    public void showDialog(int res) {
        dialog.showDialog(res);
    }

    public void showDialog(View view) {
        dialog.showDialog(view);
    }

    public void dismiss() {
        dialog.dismiss();

    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        try {
            if (e instanceof SocketTimeoutException) {
                Toast.makeText(context, "网络太慢啦，再试一次吧！", Toast.LENGTH_SHORT).show();
            } else if (e instanceof ConnectException) {
                Toast.makeText(context, "网断了，请检查您的网络状态", Toast.LENGTH_SHORT).show();
            } else {
                String erro = e.getMessage();
                Toast.makeText(context, erro, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t);
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
