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
import com.android.yzd.been.AddressEntity;
import com.android.yzd.been.IntegralListBean;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
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

    IntegralListBean integralListBean;
    UserInfoEntity userInfo;
    AddressEntity addressEntity;

    private int needIntegral;
    private int myIntegral;

    @Override
    public int getContentViewId() {
        return R.layout.activity_true_conversion;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        userInfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        integralListBean = getIntent().getParcelableExtra(K.DATA);
        myIntegral=Integer.parseInt(userInfo.getIntegral());
        Picasso.with(this).load(integralListBean.getGoods_logo()).into(findImage);
        Picasso.with(this).load(integralListBean.getGoods_logo()).into(findImage_t);
        findTitle.setText(integralListBean.getGoods_name());
        findTitle_t.setText(integralListBean.getGoods_name());
        findContent.setText(integralListBean.getGoods_brief());
        findContent_t.setText(integralListBean.getGoods_brief());
        needIntegral=Integer.parseInt(integralListBean.getNeed_integral());
        showIntegral.setText("-"+needIntegral);
    }


    private int  count=1;
    private int integralCount;
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
                integralCount=needIntegral*count;
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
                showIntegral.setText("-"+needIntegral);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        httpParamet.addParameter("cou_id", integralListBean.getI_g_id());
        httpParamet.addParameter("num", showNum.getText());
        HttpMethods.getInstance(this).exchangeCoupon(progressSubscriber, httpParamet.bulider());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
