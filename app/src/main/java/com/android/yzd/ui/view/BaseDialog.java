package com.android.yzd.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import com.android.yzd.R;


/**
 * Created by Administrator on 2016/8/23.
 */

public class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
    }

    public void showDialog(int resId) {
        setContentView(resId);
        Window window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.DiaLog); //设置窗口弹出动画
        setCanceledOnTouchOutside(true);
        show();
    }

    public void showDialog(View view) {
        setContentView(view);
        Window window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.DiaLog); //设置窗口弹出动画
        setCanceledOnTouchOutside(true);
        show();
    }

}
