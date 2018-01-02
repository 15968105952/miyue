package miyue.aodong.com.miyue.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initView();
        initData();
    }


    public abstract int getLayoutResId();

    protected abstract void initView();

    protected abstract void initData();




}
