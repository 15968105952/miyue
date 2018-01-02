package miyue.aodong.com.miyue;

import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
        fragments.add(new FindFragment());
        fragments.add(new VideoShowFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MyFragment());
        rgHome.check(R.id.rb_find);

    }

}
