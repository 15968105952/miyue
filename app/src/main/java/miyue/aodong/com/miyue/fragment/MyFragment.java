package miyue.aodong.com.miyue.fragment;

import android.view.View;

import miyue.aodong.com.miyue.R;
import miyue.aodong.com.miyue.base.BaseFragment;


/**
 * Created by wangdh on 2016/8/25.
 */
public class MyFragment extends BaseFragment {
    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_my,null);
        return view;
    }

    @Override
    protected void initData() {

    }
}
