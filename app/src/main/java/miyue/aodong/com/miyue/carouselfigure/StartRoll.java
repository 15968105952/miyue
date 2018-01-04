package miyue.aodong.com.miyue.carouselfigure;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import miyue.aodong.com.miyue.R;

/**
 * Created by anynew on 2017/5/17.
 */

public class StartRoll<T> {

    private FragmentActivity mactivity;
    private ViewPager mViewPager;
    private ArrayList<T> dataBeens;
    private LinearLayout ll_dots;
    PagerAdapter rollPagerAdapter;

    public StartRoll(FragmentActivity mactivity, ArrayList<T> dataBeens, ViewPager mViewPager, LinearLayout ll_dots) {
        this.mactivity=mactivity;
        this.dataBeens=dataBeens;
        this.mViewPager = mViewPager;
        this.ll_dots = ll_dots;
    }

   /* public StartRoll(Context mactivity, ArrayList<T> dataBeens , ViewPager mViewPager, LinearLayout ll_dots){
        this.mactivity=mactivity;
        this.dataBeens=dataBeens;
        this.mViewPager = mViewPager;
        this.ll_dots = ll_dots;
    }*/

    /**
     * 滑动时的处理
     */
    private void init() {
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler.removeMessages(msg_auto_play_next);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        //抬起继续轮播
                        startRoll();
                        break;
                }
                return mactivity.onTouchEvent(event);
            }
        });
    }

    /**
     * 解决条目为4以内崩溃问题
     */
    public void setViewpagerData() {
        init();

        // TODO: 2017/5/2
        if (dataBeens.size() == 1) {//不轮播，不显示点
            if (rollPagerAdapter == null) {
                rollPagerAdapter = new RollPagerAdapter(mactivity,1,dataBeens);
            }
            if (mViewPager != null) {
                mViewPager.setAdapter(rollPagerAdapter);
                rollPagerAdapter.notifyDataSetChanged();
                mViewPager.setCurrentItem(0);
            }
        } else if (dataBeens.size() == 2) {//条目+2
            addPoints();
            if (rollPagerAdapter == null) {
                rollPagerAdapter = new RollPagerAdapter(mactivity,2,dataBeens);
            }
            if (mViewPager != null) {
                mViewPager.setAdapter(rollPagerAdapter);
                rollPagerAdapter.notifyDataSetChanged();
                mViewPager.setCurrentItem(dataBeens.size() * 10000000);
            }
            dataBeens.add(dataBeens.get(0));
            dataBeens.add(dataBeens.get(1));
            startRoll();
            initListener(2);
        } else if (dataBeens.size() == 3) {//条目+3
            addPoints();
            if (rollPagerAdapter == null) {
                rollPagerAdapter = new RollPagerAdapter(mactivity,3,dataBeens);
            }
            if (mViewPager != null) {
                mViewPager.setAdapter(rollPagerAdapter);
                rollPagerAdapter.notifyDataSetChanged();
                mViewPager.setCurrentItem(dataBeens.size() * 10000000);
            }
            dataBeens.add(dataBeens.get(0));
            dataBeens.add(dataBeens.get(1));
            dataBeens.add(dataBeens.get(2));
            startRoll();
            initListener(3);
        } else {//正常
            addPoints();
            if (rollPagerAdapter == null) {
                rollPagerAdapter = new RollPagerAdapter(mactivity,0,dataBeens);//正常为0
            }
            if (mViewPager != null) {
                mViewPager.setAdapter(rollPagerAdapter);
                rollPagerAdapter.notifyDataSetChanged();
                mViewPager.setCurrentItem(dataBeens.size() * 10000000);
            }
            startRoll();
            initListener(0);
        }
    }

    private static final int msg_auto_play_next = 0;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (!mactivity.isFinishing()) {
                // 让每5秒钟切换一次页面
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
                handler.sendEmptyMessageDelayed(0, 5000);
            }
        }
    };

    private void startRoll() {
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessageDelayed(msg_auto_play_next, 5000);
    }

    //添加点
    private void addPoints() {
        if (ll_dots != null) {
            ll_dots.removeAllViews();
            for (int i = 0; i < dataBeens.size(); i++) {
                ImageView ivPoint = new ImageView(mactivity);
                ivPoint.setBackgroundResource(R.drawable.point_bg);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 36;
                ll_dots.addView(ivPoint, params);
            }
            ll_dots.getChildAt(0).setSelected(true);
        }
    }

    /**
     * 点的选中处理
     */
    private int lastSelectPosition = 0;
    public void initListener(final int datasize) {
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                }
                @Override
                public void onPageSelected(int position) {
                    int k = dataBeens.size();
                    switch (datasize) {
                        case 2:
                            k = dataBeens.size() - 2;
                            break;
                        case 3:
                            k = dataBeens.size() - 3;
                            break;
                        case 0:
                            k = dataBeens.size();
                            break;
                    }

                    position = position % dataBeens.size() % k;

                    for (int i = 0; i < ll_dots.getChildCount(); i++) {
                        if (i == position) {
                            ll_dots.getChildAt(position).setSelected(true);
                        } else {
                            ll_dots.getChildAt(i).setSelected(false);
                        }
                    }
                    ll_dots.getChildAt(lastSelectPosition).setSelected(
                            false);
                    ll_dots.getChildAt(position).setSelected(true);
                    lastSelectPosition = position;
                }
            });
        }
    }


}
