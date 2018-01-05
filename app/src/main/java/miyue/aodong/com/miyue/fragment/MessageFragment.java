package miyue.aodong.com.miyue.fragment;

import android.view.View;

import com.netease.nim.uikit.business.recent.RecentContactsFragment;

import miyue.aodong.com.miyue.R;
import miyue.aodong.com.miyue.base.BaseFragment;

/**
 * Created by syh11 on 2017/12/15.
 */

public class MessageFragment extends BaseFragment {
    @Override
    protected View initView() {
        try {
            View view = View.inflate(context, R.layout.fragment_message, null);
            return view;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void initData() {
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
        RecentContactsFragment f = (RecentContactsFragment) getFragmentManager()
                .findFragmentById(R.id.contact_list_fragment);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
