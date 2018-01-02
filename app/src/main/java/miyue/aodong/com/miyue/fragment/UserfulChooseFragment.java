package miyue.aodong.com.miyue.fragment;

import android.view.View;
import android.widget.Toast;

import miyue.aodong.com.miyue.R;
import miyue.aodong.com.miyue.base.BaseFragment;

/**
 * Created by syh11 on 2017/12/15.
 */

public class UserfulChooseFragment extends BaseFragment {
    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_all,null);
        Toast.makeText(getContext(),"ssss",Toast.LENGTH_LONG).show();
        return view;
    }

    @Override
    protected void initData() {

    }
}
