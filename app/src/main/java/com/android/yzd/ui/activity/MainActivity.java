package com.android.yzd.ui.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.T;
import com.android.yzd.tools.update.DownLoadService;
import com.android.yzd.tools.update.Version;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.fragment.ClassifyFragment;
import com.android.yzd.ui.fragment.FindFragment;
import com.android.yzd.ui.fragment.HomeFragment;
import com.android.yzd.ui.fragment.MyFragment;
import com.android.yzd.ui.fragment.ShoppingCartFragment;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/1 0001.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tools)
    RadioGroup mainTools;


    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    Fragment mContent;

    HomeFragment homeFragment;
    ClassifyFragment classifyFragment;
    ShoppingCartFragment shoppingCartFragment;
    FindFragment findFragment;
    MyFragment myFragment;

    public static final String REFRESH = "com.android.yzd.ui.activity.MainActivity.refresh";
    @BindView(R.id.main_home)
    RadioButton mainHome;
    @BindView(R.id.main_classify)
    RadioButton mainClassify;
    @BindView(R.id.main_shoppingCart)
    RadioButton mainShoppingCart;
    @BindView(R.id.main_find)
    RadioButton mainFind;
    @BindView(R.id.main_my)
    RadioButton mainMy;
    @BindView(R.id.main_contents)
    FrameLayout mainContents;

    RadioButton contentButton;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }


    public void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(REFRESH);
        registerReceiver(receiver, filter);
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(REFRESH)) {
                int status = intent.getExtras().getInt(K.STATUS);
                switch (status) {
                    case 1:
                        showFragment(homeFragment, mainHome);
                        mainHome.setChecked(true);
                        break;
                    case 2:
                        showFragment(classifyFragment, mainClassify);
                        mainClassify.setChecked(true);
                        break;
                    case 3:
                        mainShoppingCart.setChecked(true);
                        showFragment(shoppingCartFragment, mainShoppingCart);
                        break;
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        versionInfo();
        register();
        homeFragment = new HomeFragment();
        classifyFragment = new ClassifyFragment();
        shoppingCartFragment = new ShoppingCartFragment();
        findFragment = new FindFragment();
        myFragment = new MyFragment();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_contents, homeFragment);
        transaction.commit();
        mContent = homeFragment;
        mainTools.setOnCheckedChangeListener(onCheckedChangeListener);
        contentButton = mainHome;
    }


    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.main_home:
                    showFragment(homeFragment, mainHome);
                    break;
                case R.id.main_classify:
                    showFragment(classifyFragment, mainClassify);
                    break;
                case R.id.main_shoppingCart:
                    if (getUserInfo() == null) {
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        contentButton.setChecked(true);
                    } else {
                        showFragment(shoppingCartFragment, mainShoppingCart);
                    }
                    break;
                case R.id.main_find:
                    showFragment(findFragment, mainFind);
                    break;
                case R.id.main_my:
                    if (getUserInfo() == null) {
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        contentButton.setChecked(true);
                    } else {
                        showFragment(myFragment, mainMy);
                    }
                    break;
            }
        }
    };


    private void showFragment(Fragment to, RadioButton button) {
        if (mContent != to) {
            transaction = fragmentManager.beginTransaction();

            if (!to.isAdded()) {
                transaction.hide(mContent).add(R.id.main_contents, to);
            } else {
                transaction.hide(mContent).show(to);
            }
            mContent = to;
            contentButton = button;
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();      //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("当前账号在其他平台登录！");
                        builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EMClient.getInstance().logout(true, null);
                                // TODO Auto-generated method stub
                                AppManager.getAppManager().finishAllActivity();

                                SPUtils.put(MainActivity.this, K.USERINFO, new UserInfoEntity());
                                SPUtils.put(MainActivity.this, K.ISLOG, true);
                                EMClient.getInstance().logout(true);
                                intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder.show();

                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)) {
                            //连接不到聊天服务器
                        } else {
                            //当前网络不可用，请检查网络设置
                            T.show(MainActivity.this, "当前网络不可用，请检查网络设置", Toast.LENGTH_SHORT);
                        }

                    }
                }
            });
        }
    }
    /*
       * 更新
       */
    Version versionInfo;
    private void versionInfo() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                versionInfo = gson.fromJson(gson.toJson(o), Version.class);
                try {
                    //if (!getVersionName().equals(versionInfo.getName())) {
                    if (getVersionCode()!=Integer.parseInt(versionInfo.getCode())){
                        showDialog();
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
    * 更新：获取当前程序的版本号
    */
    private int getVersionCode() throws PackageManager.NameNotFoundException {
        //获取packagemanager的实例
        PackageManager packageManager = this.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
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
                Intent intent = new Intent(MainActivity.this, DownLoadService.class);
                intent.putExtra("url", versionInfo.getUri());
                startService(intent);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
