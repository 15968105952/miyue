package miyue.aodong.com.miyue.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    public Context context;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        context = getActivity();
        View view = initView();
        initData();
        return view;
    }


    protected abstract View initView();

    protected abstract void initData();


}
