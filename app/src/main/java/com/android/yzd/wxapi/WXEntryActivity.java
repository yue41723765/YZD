package com.android.yzd.wxapi;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.WXAccessTokenInfo;
import com.android.yzd.been.WXErrorInfo;
import com.android.yzd.been.WXUserInfo;
import com.android.yzd.tools.L;

import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.ui.activity.LoginActivity;
import com.android.yzd.ui.activity.TestActivity;
import com.android.yzd.ui.custom.BaseActivity;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by 33181 on 2017/9/4.
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    public static final String APP_ID = "wx5c5be12f9933f83d";
    private static final String APP_SECRET = "8f371386d414cd350b78162c9752c126";
    private static final String WEIXIN_REFRESH_TOKEN_KEY = "wx_refresh_token_key";
    private IWXAPI api;
    private Gson mGson;
    @Override
    public int getContentViewId() {
        return R.layout.activity_wx_result;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this,APP_ID);
        api.registerApp(APP_ID);
        api.handleIntent(getIntent(), this);
        mGson=new Gson();
        try {
            boolean result =  api.handleIntent(getIntent(), this);
            if(!result){
                L.d("参数不合法，未被SDK处理，退出");
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.transparencyBar(this);
        }
        ButterKnife.bind(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data,this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch(baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
                SendAuth.Resp sendResp= (SendAuth.Resp) baseResp;
                if (sendResp != null) {
                    String code = sendResp.code;
                    getAccess_token(code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this, "失败一次", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(this, "失败两次", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                Toast.makeText(this, "失败两次", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
    /**
     * 获取openid accessToken值用于后期操作
     * @param code 请求码
     */
    private String openid;
    HttpUtils utils=new HttpUtils();
    private void getAccess_token(final String code) {
        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + APP_ID
                + "&secret="
                + APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";
        //网络请求，根据自己的请求方式

        utils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(responseInfo.result);
                    openid = jsonObject.getString("openid").toString().trim();
                    String access_token = jsonObject.getString("access_token").toString().trim();
                    getUserMesg(access_token, openid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private String nickname;
    private String head_pic;
    private void getUserMesg(String access_token, String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        //网络请求，根据自己的请求方式
        utils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(responseInfo.result);
                    nickname = jsonObject.getString("nickname");
                    head_pic = jsonObject.getString("headimgurl");
                    loadPicture(head_pic);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message msg=new Message();
                msg.what=1;
                msg.obj=responseInfo.result;
                mHandler.sendMessage(msg);
            }
            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    /*
   *
   * 把图片下载到本地
   *
   */
    private File mHead;
    String dcimFile=null;
    protected String loadPicture(final String  figure) {
        HttpUtils utils=new HttpUtils();
        utils.download(figure, Environment.getExternalStorageDirectory() + "/Yzd/HeadImg/WxHead.png", new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                dcimFile=Environment.getExternalStorageDirectory() + "/Yzd/HeadImg/WxHead.png";
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
        return dcimFile;
    }
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    intent=new Intent(WXEntryActivity.this,LoginActivity.class);
                    intent.putExtra("nickname",nickname);
                    intent.putExtra("account_type","1");
                    intent.putExtra("openid",openid );
                    intent.putExtra("head_pic",head_pic);
                    startActivity(intent);
                    finish();
            }
            return false;
        }
    });
}
