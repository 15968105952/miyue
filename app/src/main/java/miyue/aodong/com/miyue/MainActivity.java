package miyue.aodong.com.miyue;

import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

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
import miyue.aodong.com.miyue.wanyiyun.preferences.UserPreferences;

public class MainActivity extends BaseActivity {
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
        login();
        fragments.add(new FindFragment());
        fragments.add(new VideoShowFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MyFragment());
        rgHome.check(R.id.rb_find);

    }
    private void login() {
        final String account ="13938217043";
        final String token = "mazhuang1";
        // 登录
        LoginInfo info = new LoginInfo("13938217043", "mazhuang123"); // config...
        NIMClient.getService(AuthService.class).login(info).setCallback(callback);
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
