package miyue.aodong.com.miyue;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import miyue.aodong.com.miyue.base.BaseActivity;
import miyue.aodong.com.miyue.base.BaseFragment;
import miyue.aodong.com.miyue.fragment.FindFragment;
import miyue.aodong.com.miyue.fragment.MessageFragment;
import miyue.aodong.com.miyue.fragment.MyFragment;
import miyue.aodong.com.miyue.fragment.VideoShowFragment;
import miyue.aodong.com.miyue.wanyiyun.DemoCache;
import miyue.aodong.com.miyue.wanyiyun.activity.VideoActivity;
import miyue.aodong.com.miyue.wanyiyun.manager.AVChatProfile;
import miyue.aodong.com.miyue.wanyiyun.manager.SessionHelper;
import miyue.aodong.com.miyue.wanyiyun.modle.Extras;
import miyue.aodong.com.miyue.wanyiyun.preferences.Preferences;
import miyue.aodong.com.miyue.wanyiyun.preferences.UserPreferences;

public class MainActivity extends BaseActivity {
    private static final String EXTRA_APP_QUIT = "APP_QUIT";
    @InjectView(R.id.fl_content)
    FrameLayout flContent;
    @InjectView(R.id.rb_find)
    RadioButton rbFind;
    @InjectView(R.id.rb_videoshow)
    RadioButton rbVideoshow;
    @InjectView(R.id.rb_message)
    RadioButton rbMessage;
    @InjectView(R.id.rb_my)
    RadioButton rbMy;
    @InjectView(R.id.rg_home)
    RadioGroup rgHome;
    private List<BaseFragment> fragments = new ArrayList<BaseFragment>();
    private static final String TAG = "MainActivity";
    private AbortableFuture<LoginInfo> loginRequest;

    private RequestCallback<LoginInfo> callback =
            new RequestCallback<LoginInfo>() {
                @Override
                public void onSuccess(LoginInfo param) {
//                        TODO 登录成功 存储信息 保证下次自动登录 1020025  1020723

                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                    isBoolean = true;
                }

                @Override
                public void onFailed(int code) {
                    Toast.makeText(MainActivity.this, "登录成功错误吗" + code, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onException(Throwable exception) {
                    Toast.makeText(MainActivity.this, "登录成功exception" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
                // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
            };


    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
        rgHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonId) {
                int index = 0;
                switch (radioButtonId){
                    case R.id.rb_find:
                        index = 0;
                        break;
                    case R.id.rb_videoshow:
                        index=1;
                        break;
                    case R.id.rb_message:
                        index = 2;
                        break;
                    case R.id.rb_my:
                        index = 3;
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,fragments.get(index)).commit();
            }
        });
    }

    @Override
    protected void initData() {
//        login();
        fragments.add(new FindFragment());
        fragments.add(new VideoShowFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MyFragment());
        rgHome.check(R.id.rb_find);
        //退出登陆
        onParseIntent();
        //监听是否强制下线
        registerObservers(true);

    }
    /********聊天部分监听*********/
    private void registerObservers(boolean register) {
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, register);
//        MyUserInfoCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver,register);
    }


    //被其他设备剔下线
    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {
                Preferences.saveUserToken("");

                if (code == StatusCode.PWD_ERROR) {
                    LogUtil.e("Auth", "user password error");
                    Toast.makeText(MainActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else if (code == StatusCode.KICKOUT) {
                    LogUtil.i("Auth", "Kicked!");
//                    showTip("您的账号已在其他设备登录！");
                    Toast.makeText(MainActivity.this, "您的账号已在其他设备登录！", Toast.LENGTH_SHORT).show();
                } else {
                    LogUtil.i("Auth", "Kicked!");
//                    showTip("您的账号已被踢下线！");
                    Toast.makeText(MainActivity.this, "您的账号已被踢下线！", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent();
                intent.setAction("exitApp");
                sendBroadcast(intent);

//                onLogout();
            }
        }
    };

    private void onParseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            IMMessage message = (IMMessage) getIntent().getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
            switch (message.getSessionType()) {
                case P2P:
                    SessionHelper.startP2PSession(this, message.getSessionId());
                    break;
                case Team:
                    SessionHelper.startTeamSession(this, message.getSessionId());
                    break;
                default:
                    break;
            }
        } else if (intent.hasExtra(EXTRA_APP_QUIT)) {
//            onLogout();
            return;
        } else if (intent.hasExtra(VideoActivity.INTENT_ACTION_AVCHAT)) {
            if (AVChatProfile.getInstance().isAVChatting()) {
                Intent localIntent = new Intent();
                localIntent.setClass(this, VideoActivity.class);
                startActivity(localIntent);
            }
        } else if (intent.hasExtra(Extras.EXTRA_JUMP_P2P)) {
            Intent data = intent.getParcelableExtra(Extras.EXTRA_DATA);
            String account = data.getStringExtra(Extras.EXTRA_ACCOUNT);
            if (!TextUtils.isEmpty(account)) {
                SessionHelper.startP2PSession(this, account);
            }
        }
    }



    private void login() {
        final String account ="1824902907";
        final String token = "mazhuang123";
        /*// 登录
        LoginInfo info = new LoginInfo("13938217043", "mazhuang123"); // config...
        NIMClient.getService(AuthService.class).login(info).setCallback(callback);*/
        // 登录
        loginRequest = NimUIKit.login(new LoginInfo(account, token), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                LogUtil.i(TAG, "login success");
                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
//                onLoginDone();

                DemoCache.setAccount(account);
                saveLoginInfo(account, token);

                // 初始化消息提醒配置
                initNotificationConfig();

                // 进入主界面
//                MainActivity.start(LoginActivity.this, null);
//                finish();
            }

            @Override
            public void onFailed(int code) {
//                onLoginDone();
                if (code == 302 || code == 404) {
                    Toast.makeText(MainActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
                Toast.makeText(MainActivity.this, R.string.login_exception, Toast.LENGTH_LONG).show();
//                onLoginDone();
            }
        });
    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }

    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = DemoCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }
}
