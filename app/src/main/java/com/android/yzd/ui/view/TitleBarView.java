package com.android.yzd.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.yzd.R;

/**
 * Created by Administrator on 2016/10/1 0001.
 */
//com.android.yzd.ui.view.TitleBarView
public class TitleBarView extends RelativeLayout {

    private ImageView back;
    private TextView title;
    private TextView right_text;
    private ImageView right_image;
    private RelativeLayout rl_edit;
    private RelativeLayout title_rl;
    private TextView edit;
    private TextView edit_hint;

    public TitleBarView(Context context) {
        this(context, null);
    }

    public TitleBarView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseTitleBar);
        int visible = ta.getInt(R.styleable.BaseTitleBar_setLeftImageVisibility, 0);
        setVisibility(back, visible);
        visible = ta.getInt(R.styleable.BaseTitleBar_setRightImageVisibility, 8);
        setVisibility(right_image, visible);
        visible = ta.getInt(R.styleable.BaseTitleBar_setRightTextVisibility, 8);
        setVisibility(right_text, visible);
        visible = ta.getInt(R.styleable.BaseTitleBar_setTitleVisibility, 0);
        setVisibility(title, visible);
        visible = ta.getInt(R.styleable.BaseTitleBar_setEditRlVisibility, 8);
        setVisibility(rl_edit, visible);
        visible = ta.getInt(R.styleable.BaseTitleBar_setEditHintVisibility, 0);
        setVisibility(edit_hint, visible);
        int color = ta.getColor(R.styleable.BaseTitleBar_setBackground, context.getResources().getColor(R.color.colorPrimary));
        setBackGround(title_rl, color);
        color = ta.getColor(R.styleable.BaseTitleBar_setTitleColor, context.getResources().getColor(android.R.color.white));
        setTextColor(title, color);
        color = ta.getColor(R.styleable.BaseTitleBar_setRightTextColor, context.getResources().getColor(android.R.color.white));
        setTextColor(right_text, color);

        int drawable = ta.getResourceId(R.styleable.BaseTitleBar_setLeftImage, R.mipmap.arrow_left_white);
        setDrawable(back, drawable);
        drawable = ta.getResourceId(R.styleable.BaseTitleBar_setRightImage, R.mipmap.ic_launcher);
        setDrawable(right_image, drawable);

        String str = ta.getString(R.styleable.BaseTitleBar_setRightText);
        setString(right_text, str);
        str = ta.getString(R.styleable.BaseTitleBar_setTitle);
        setString(title, str);
        str = ta.getString(R.styleable.BaseTitleBar_setEditText);
        setString(edit, str);
        ta.recycle();

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ((Activity) getContext()).finishAfterTransition();
                    } else {
                        ((Activity) getContext()).finish();
                    }
                }
            }
        });
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.base_titlebar, this);
        back = (ImageView) view.findViewById(R.id.titleBar_back);
        title = (TextView) view.findViewById(R.id.titleBar_title);
        right_text = (TextView) view.findViewById(R.id.titleBar_right_text);
        right_image = (ImageView) view.findViewById(R.id.titleBar_right_image);
        rl_edit = (RelativeLayout) view.findViewById(R.id.rl_edit);
        edit = (TextView) view.findViewById(R.id.titleBar_edit);
        edit_hint = (TextView) view.findViewById(R.id.titleBar_edit_hint);
        title_rl = (RelativeLayout) view.findViewById(R.id.title_rl);
    }

    private void setVisibility(View view, int visible) {
        view.setVisibility(visible);
    }

    private void setBackGround(RelativeLayout view, int color) {
        view.setBackgroundColor(color);
    }

    private void setTextColor(TextView text, int color) {
        text.setTextColor(color);
    }

    private void setDrawable(ImageView image, int resId) {
        image.setImageResource(resId);
    }

    private void setString(TextView text, String str) {
        text.setText(str);
    }
}

