package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.NewIntegralListBean;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.L;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.T;
import com.android.yzd.ui.custom.BaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class ConfirmConversionActivity extends BaseActivity {
    @BindView(R.id.find_image)ImageView findImage;
    @BindView(R.id.find_image_t)ImageView findImage_t;
    @BindView(R.id.find_title)TextView findTitle;
    @BindView(R.id.find_title_t)TextView findTitle_t;
    @BindView(R.id.find_content)TextView findContent;
    @BindView(R.id.find_content_t)TextView findContent_t;
    @BindView(R.id.conversion_minus)ImageView minusImg;
    @BindView(R.id.conversion_add)ImageView addImg;
    @BindView(R.id.conversion_edit)TextView showNum;
    @BindView(R.id.find_show_integral)TextView showIntegral;
    @BindView(R.id.show_less_integral)TextView lessIntegral;

    UserInfoEntity userInfo;

    private int needIntegral=0;
    private int myIntegral=0;
    String cou_id;
    @Override
    public int getContentViewId() {
        return R.layout.activity_true_conversion;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        userInfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        NewIntegralListBean integralListBean = getIntent().getParcelableExtra(K.DATA);
        L.d("TAG","---"+integralListBean.getExpired_day()+"-"+integralListBean.getNeed_integral());
        myIntegral=Integer.parseInt(userInfo.getIntegral());
        findContent.setText(integralListBean.getNeed_integral());
        findContent_t.setText(integralListBean.getNeed_integral());
        findTitle.setText("有效期为："+integralListBean.getExpired_day()+"天");
        findTitle_t.setText("有效期为："+integralListBean.getExpired_day()+"天");
        if (integralListBean.getNeed_integral()==null){
            Toast.makeText(this, "为空", Toast.LENGTH_SHORT).show();
            return;
        }
        needIntegral=Integer.parseInt(integralListBean.getNeed_integral());
        showIntegral.setText("-"+needIntegral);
        cou_id=integralListBean.getCou_id();
    }


    private int  count=1;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sure_conversion:
                if (myIntegral-needIntegral<0){
                    lessIntegral.setVisibility(View.VISIBLE);
                    lessIntegral.setText(myIntegral+"  "+"当前积分不足");
                    return;
                }
                exchangeCoupon();
                break;
            case R.id.conversion_add:
                count=Integer.parseInt(showNum.getText().toString());
                count+=1;
                int integralCount=needIntegral*count;
                if (myIntegral-integralCount<0&&myIntegral>needIntegral){
                    lessIntegral.setVisibility(View.VISIBLE);
                    int num=needIntegral-(integralCount-myIntegral);
                    lessIntegral.setText(num+"  "+"当前积分不足");
                    return;
                }else if (myIntegral<needIntegral){
                    lessIntegral.setVisibility(View.VISIBLE);
                    lessIntegral.setText(myIntegral+"  "+"当前积分不足");
                    return;
                }
                showIntegral.setText("-"+integralCount);
                showNum.setText(count+"");
                break;
            case R.id.conversion_minus:
                count=Integer.parseInt(showNum.getText().toString());
                if (count==1){
                    showNum.setText(count+"");
                    showIntegral.setText("-"+needIntegral);
                    if (myIntegral-needIntegral<0){
                        lessIntegral.setVisibility(View.VISIBLE);
                        lessIntegral.setText(myIntegral+"  "+"当前积分不足");
                    }
                }else {
                    count-=1;
                    integralCount=needIntegral*count;
                    showIntegral.setText("-"+integralCount);
                    showNum.setText(count+"");
                    lessIntegral.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    private void exchangeCoupon() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                lessIntegral.setVisibility(View.VISIBLE);
                lessIntegral.setText("兑换成功");
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("m_id", userInfo.getM_id());
        httpParamet.addParameter("cou_id", cou_id);
        httpParamet.addParameter("num", showNum.getText().toString());
        HttpMethods.getInstance(this).exchangeCoupon(progressSubscriber, httpParamet.bulider());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
