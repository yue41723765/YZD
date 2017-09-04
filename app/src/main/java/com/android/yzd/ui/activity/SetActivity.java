package com.android.yzd.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.SetEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.DataCleanManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.T;
import com.android.yzd.tools.update.DownLoadService;
import com.android.yzd.tools.update.Version;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.BaseDialog;
import com.hyphenate.chat.EMClient;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class SetActivity extends BaseActivity {

    BaseDialog dialog;
    Version versionInfo;
    @BindView(R.id.set_cacheSize)
    TextView setCacheSize;
    @BindView(R.id.set_clearCache)
    RelativeLayout setClearCache;
    @BindView(R.id.set_about)
    RelativeLayout setAbout;
    @BindView(R.id.set_tel)
    TextView setTel;
    @BindView(R.id.set_callTel)
    RelativeLayout setCallTel;
    @BindView(R.id.versions)
    TextView versions;
    @BindView(R.id.find_new_ver)
    TextView findNewVer;
    @BindView(R.id.set_versions)
    RelativeLayout setVersions;
    @BindView(R.id.set_feedback)
    RelativeLayout setFeedback;
    @BindView(R.id.exit_login)
    TextView exitLogin;

    View view;
    TextView content;
    SetEntity setEntity;

    @Override
    public int getContentViewId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        dialog = new BaseDialog(this);

        try {
            setCacheSize.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

        view = getLayoutInflater().inflate(R.layout.dialog_call, null);
        content = (TextView) view.findViewById(R.id.tel);
        versionInfo();
        getSetInfo();
    }

    private void getSetInfo() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                setEntity = gson.fromJson(gson.toJson(o), SetEntity.class);
                content.setText(setEntity.getService_line());
            }
        };
        setProgressSubscriber(onNextListener, false);
        HttpMethods.getInstance(this).setPage(progressSubscriber, new HashMap<String, String>());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.set_clearCache:
                DataCleanManager.clearAllCache(this);
                try {
                    setCacheSize.setText(DataCleanManager.getTotalCacheSize(this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.set_about:
                intent = new Intent(this, AboutActivity.class);
                intent.putExtra(K.DATA, setEntity);
                startActivity(intent);
                break;
            case R.id.set_callTel:
                if (setEntity == null)
                    return;
                dialog.showDialog(view);
                break;
            case R.id.cancel:
                dialog.dismiss();
                break;
            case R.id.sure:
                intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + setEntity.getService_line());
                intent.setData(data);
                startActivity(intent);
                dialog.dismiss();
                break;
            case R.id.set_versions:
                try {
                    //if (!getVersionName().equals(versionInfo.getName())) {
                    if (getVersionCode()!=Integer.parseInt(versionInfo.getCode())){
                        showDialog();
                    } else {
                        T.show(this, "当前版本为最新版本！", Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.set_feedback:
                intent = new Intent(this, FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.exit_login:

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("是否立即退出登录?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                EMClient.getInstance().logout(true, null);
                                SPUtils.put(SetActivity.this, K.USERINFO, null);
                                SPUtils.put(SetActivity.this, K.ISLOG, true);
                                T.show(SetActivity.this, "退出成功!", Toast.LENGTH_SHORT);

                                intent = new Intent(MainActivity.REFRESH);
                                intent.putExtra(K.STATUS, 1);
                                sendBroadcast(intent);

                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();

                break;
        }
    }

    private void versionInfo() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                versionInfo = gson.fromJson(gson.toJson(o), Version.class);
                try {
                    //if (!getVersionName().equals(versionInfo.getName())) {
                    if (getVersionCode()!=Integer.parseInt(versionInfo.getCode())){
                        findNewVer.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        setProgressSubscriber(onNextListener, false);
        HttpMethods.getInstance(this).upgrade(progressSubscriber);
    }

    /*
 * 获取当前程序的版本号
 */
    private int getVersionCode() throws PackageManager.NameNotFoundException {
        //获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        //不知道上边写的是啥 反正获取版本号里边必须得有版本名称
        PackageInfo packInfo=packageManager.getPackageInfo(packageInfo.packageName,0);
        return packInfo.versionCode;
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("升级提示");
        builder.setMessage("版本：" + versionInfo.getName() + "\n" + "更新消息:" + versionInfo.getMessage());
        builder.setNegativeButton("稍后再说", new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("马上更新", new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(SetActivity.this, DownLoadService.class);
                intent.putExtra("url", versionInfo.getUri());
                startService(intent);
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
