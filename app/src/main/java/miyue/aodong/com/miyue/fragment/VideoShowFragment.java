package miyue.aodong.com.miyue.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import miyue.aodong.com.miyue.R;
import miyue.aodong.com.miyue.base.BaseFragment;


/**
 * Created by wangdh on 2016/8/25.
 */
public class VideoShowFragment extends BaseFragment {

    private TabLayout tab_layout;
    private ViewPager timeline_viewpager;


    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_more,null);
        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout);
        timeline_viewpager = (ViewPager) view.findViewById(R.id.timeline_viewpager);
        return view;
    }

    @Override
    protected void initData() {

        Toast.makeText(getContext(),"Home",Toast.LENGTH_LONG).show();
        tab_layout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
      /*  ShortPagerAdapter adapter = new ShortPagerAdapter(getChildFragmentManager());
        timeline_viewpager.setAdapter(adapter);*/
        tab_layout.setupWithViewPager(timeline_viewpager);

    }

   /* private class ShortPagerAdapter extends FragmentPagerAdapter {
        public String[] mTilte;

        public ShortPagerAdapter(FragmentManager fm) {
            super(fm);
            mTilte = getResources().getStringArray(R.array.login_tab_Title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTilte[position];
        }

        @Override
        public BaseFragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.loginTitleFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }*/
}
