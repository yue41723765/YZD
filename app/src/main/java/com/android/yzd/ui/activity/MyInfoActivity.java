package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.MyInfoEntity;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.T;
import com.android.yzd.tools.U;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.custom.HttpParameterBuilder;
import com.android.yzd.ui.custom.PicassoImageLoader;
import com.android.yzd.ui.view.CircleImageView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.onekeyshare.OnekeyShare;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.android.yzd.tools.F.PHOTO;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class MyInfoActivity extends BaseActivity {


    PopupWindow popupWindow;

    View head;
    View sex;
    @BindView(R.id.alter_head)
    RelativeLayout alterHead;
    @BindView(R.id.info_nick)
    TextView infoNick;
    @BindView(R.id.alter_nick)
    RelativeLayout alterNick;
    @BindView(R.id.info_sex)
    TextView infoSex;
    @BindView(R.id.alter_sex)
    RelativeLayout alterSex;
    @BindView(R.id.info_tel)
    TextView infoTel;
    @BindView(R.id.alter_tel)
    RelativeLayout alterTel;
    @BindView(R.id.alter_password)
    RelativeLayout alterPassword;
    @BindView(R.id.info_save)
    Button infoSave;
    @BindView(R.id.info_head)
    CircleImageView infoHead;
    @BindView(R.id.change_password)
    TextView changePassword;
    @BindView(R.id.my_invite_RE)RelativeLayout myInvite;
    @BindView(R.id.my_invite_code)TextView myInviteCode;
    @BindView(R.id.referrer_invite_code)TextView referrerCode;


    UserInfoEntity userInfo;//登陆保存个人信息
    MyInfoEntity myInfo;//接口查询个人信息

    HttpParameterBuilder httpParamet;
    String mSex;
    File mHead;

    String isPassword;
    boolean isUP = true;

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_info;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        init();
        getUserInfo_();
        setImageChoose();
    }

    private void getUserInfo_() {

        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                myInfo = gson.fromJson(gson.toJson(o), MyInfoEntity.class);
                Picasso.with(MyInfoActivity.this).load(myInfo.getHead_pic()).into(infoHead);
                infoNick.setText(myInfo.getNickname());
                infoTel.setText(myInfo.getAccount());
                mSex = myInfo.getSex();
                myInviteCode.setText(myInfo.getInvite_code());
                isPassword=myInfo.getIs_set_password();
                if ("0".equals(isPassword)){
                    changePassword.setText("设置登录密码");
                }
                if ("0".equals(myInfo.getParent_id())){
                    referrerCode.setText("无");
                }else {
                    referrerCode.setText(myInfo.getParent_invite_code());
                }
                switch (myInfo.getSex()) {
                    case "0":
                        infoSex.setText("保密");
                        break;
                    case "1":
                        infoSex.setText("男");
                        break;
                    case "2":
                        infoSex.setText("女");
                        break;
                }
                //更新登陆数据
                userInfo.setAccount(myInfo.getAccount());
                userInfo.setHead_pic(myInfo.getHead_pic());
                userInfo.setNickname(myInfo.getNickname());

                SPUtils.put(MyInfoActivity.this, K.USERINFO, userInfo);
            }
        };
        params.put("m_id", userInfo.getM_id());
        setProgressSubscriber(onNextListener);
        HttpMethods.getInstance(this).memberBaseData(progressSubscriber, params);
    }

    private void init() {
        head = getLayoutInflater().inflate(R.layout.popup_choose_photo, null);
        sex = getLayoutInflater().inflate(R.layout.dialog_sex, null);

        userInfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        httpParamet = new HttpParameterBuilder();
        httpParamet.addParameter("m_id", userInfo.getM_id());

        //加载的框
        popupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                U.setWindows(MyInfoActivity.this, 1f);
            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.alter_head:
                intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, PHOTO);
                break;
            case R.id.privary:
                infoSex.setText("保密");
                popupWindow.dismiss();
                mSex = "0";
                break;
            case R.id.man:
                infoSex.setText("男");
                popupWindow.dismiss();
                mSex = "1";
                break;
            case R.id.woman:
                infoSex.setText("女");
                mSex = "2";
                popupWindow.dismiss();
                break;
            case R.id.alter_sex:
                U.setWindows(this, 0.5f);
                popupWindow.setContentView(sex);
                popupWindow.setAnimationStyle(R.style.PopupAnimationStyle);
                popupWindow.showAtLocation(sex, Gravity.CENTER, 0, 0);
                break;
            case R.id.alter_tel:
                //绑定手机号
                intent = new Intent(this, AuthenticationActivity.class);
                startActivity(intent);
                break;
            case R.id.alter_password:
                //修改密码
                intent = new Intent(this, AlterPasswordActivity.class);
                intent.putExtra(K.ISPASSWORD,isPassword);
                startActivity(intent);
                break;
            case R.id.info_save:
                //保存
                String nickName = infoNick.getText().toString();
                if (nickName.equals("")) {
                    T.show(this, "用户名不能为空!", Toast.LENGTH_SHORT);
                    return;
                }
                if (mSex == null) {
                    T.show(this, "请选择性别!", Toast.LENGTH_SHORT);
                    return;
                }
                if (!isUP) {
                    T.show(this, "图片压缩中，请稍后再试!", Toast.LENGTH_SHORT);
                    return;
                } else {
                    if (mHead == null) {
                        httpParamet.addParameter("head_pic[0]", userInfo.getHead_pic());
                    } else {
                        httpParamet.addParameter("head_pic[0]", mHead);
                    }
                }
                httpParamet.addParameter("nickname", nickName);
                httpParamet.addParameter("sex", mSex);
                alterMyInfo();
                break;
            case R.id.my_invite_RE:
                showShare();
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
             //关闭sso授权
            oks.disableSSOWhenAuthorize();
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
            //oks.setTitle("一站达建材商城");
            // titleUrl是标题的网络链接，QQ和QQ空间等使用
            //oks.setTitleUrl("http://www.tjyizhanda.com");
            // text是分享文本，所有平台都需要这个字段
            //oks.setText("家装辅材，一站购齐，就在一站达！我的邀请码为："+myInfo.getInvite_code()+"首次注册好礼多多，不一样的网上建材辅料APP");
            oks.setText(myInfo.getInvite_code());
             // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //oks.setImageUrl("http://www.tjyizhanda.com");//确保SDcard下面存在此张图片
             // url仅在微信（包括好友和朋友圈）中使用
            //oks.setUrl("http://www.tjyizhanda.com");
             // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            //oks.setComment("特别好用的软件哟！");
            // site是分享此内容的网站名称，仅在QQ空间使用
            //oks.setSite(getString(R.string.app_name));
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            //oks.setSiteUrl("http://www.tjyizhanda.com");
             // 启动分享GUI
            oks.show(this);
    }

    private void alterMyInfo() {
        SubscriberOnNextListener onNext = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(MyInfoActivity.this, "修改个人资料成功", Toast.LENGTH_SHORT);
                getUserInfo_();
            }
        };
        setProgressSubscriber(onNext);
        HttpMethods.getInstance(this).modifyMemberBaseData(progressSubscriber, httpParamet.bulider());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        switch (requestCode) {
            case PHOTO:
                final List<ImageItem> images = (List<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images.size() <= 0) {
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeFile(images.get(0).path);
                infoHead.setImageBitmap(bitmap);
                isUP = false;
                if (infoSave.getAlpha() == 1)
                    infoSave.setAlpha(0.5f);

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Luban.get(MyInfoActivity.this)
                                .load(new File(images.get(0).path))                     //传人要压缩的图片
                                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                                .setCompressListener(new OnCompressListener() { //设置回调

                                    @Override
                                    public void onStart() {
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        mHead = file;
                                        isUP = true;
                                        infoSave.setAlpha(1f);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        //TODO 当压缩过去出现问题时调用
                                    }
                                }).launch();    //启动压缩
                    }
                }.start();
                break;
        }
    }

    private void setImageChoose() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setSelectLimit(1);
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo_();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
