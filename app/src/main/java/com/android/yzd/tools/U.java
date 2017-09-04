package com.android.yzd.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.android.yzd.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/30.
 */

public class U {


    /**
     * 设置添加屏幕的背景透明度
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 是否为手机号 ture--是
     */
    public static boolean isTel(String tel) {
        Pattern p = Pattern.compile("1\\d{10}");
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    public static void setWindows(Activity activity, float alphe) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alphe;
        activity.getWindow().setAttributes(lp);
    }


    /**
     * 时间戳转为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String timeStampToStr_(String timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        long lcc_time = Long.valueOf(timestamp);
        String re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 时间戳转为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String timeStampToStrDMY(long timestamp) {
//
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return String.format(sdf.format(new Date(timestamp)), calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }

    /**
     * 时间戳转为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String timeStampToStr(String timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lcc_time = Long.valueOf(timestamp);
        String re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * @param context
     * @param layout
     * @param lable
     * @param width
     * @param height
     */
    public static PopupWindow showPopup(Context context, View view, View lable, int width, int height) {
        PopupWindow popupWindow = new PopupWindow(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setContentView(view);
        popupWindow.setAnimationStyle(R.style.TopStyle);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int[] location = new int[2];
        int popupWidth = view.getMeasuredWidth();
        int popupHeight = view.getMeasuredHeight();
        lable.getLocationOnScreen(location);
        popupWindow.showAtLocation(lable, Gravity.NO_GRAVITY, location[0] - popupWidth / 2 - width, location[1] + popupHeight / 2 - height);
        return popupWindow;
    }

    public static PopupWindow showPopup(Context context, View content, View lable, int width, int height, int gravity) {
        PopupWindow popupWindow = new PopupWindow(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setContentView(content);
        popupWindow.setAnimationStyle(R.style.TopStyle);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int[] location = new int[2];
        int popupWidth = content.getMeasuredWidth();
        int popupHeight = content.getMeasuredHeight();
        lable.getLocationOnScreen(location);
        popupWindow.showAtLocation(lable, gravity, location[0] - popupWidth / 2 - width, location[1] + popupHeight / 2 - height);
        return popupWindow;
    }

}
