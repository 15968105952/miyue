package miyue.aodong.com.miyue.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import miyue.aodong.com.miyue.R;
import miyue.aodong.com.miyue.adapter.ModeAdapter;
import miyue.aodong.com.miyue.adapter.ViewHolder;
import miyue.aodong.com.miyue.base.BaseFragment;
import miyue.aodong.com.miyue.carouselfigure.StartRoll;
import miyue.aodong.com.miyue.listener.PullToRefreshListener;
import miyue.aodong.com.miyue.view.PullToRefreshRecyclerView;
import miyue.aodong.com.miyue.wanyiyun.activity.VideoActivity;
import miyue.aodong.com.miyue.wanyiyun.manager.SessionHelper;

/**
 * Created by syh11 on 2017/12/15.
 */

public class UserfulChooseFragment extends BaseFragment implements PullToRefreshListener {
    @InjectView(R.id.S_viewPager)
    ViewPager SViewPager;
    @InjectView(R.id.ll_dots)
    LinearLayout llDots;
    @InjectView(R.id.rl_beautiful_girl)
    PullToRefreshRecyclerView rlBeautifulGirl;
    private ModeAdapter adapter;
    private List<String> data;
    private int i = 0;
    private StartRoll<String> stringStartRoll;
    private ArrayList<String> dataBeens = new ArrayList<>();

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_user_choose, null);
        ButterKnife.inject(this, view);
        //添加HeaderView
        View headView = View.inflate(context, R.layout.layout_head_view, null);
        //头布局中的子控件设置点击事件
        LinearLayout  rl1 = (LinearLayout) headView.findViewById(R.id.rl_name1);
        LinearLayout  rl2 = (LinearLayout) headView.findViewById(R.id.rl_name2);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"rl1",Toast.LENGTH_SHORT).show();
                VideoActivity.start(getContext(), "1824902907");
            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"rl2",Toast.LENGTH_SHORT).show();
                 /*跳转到通讯录列表界面*/
                SessionHelper.startP2PSession(getContext(), "13938217043");
            }
        });
        rlBeautifulGirl.addHeaderView(headView);
        //设置EmptyView（没有数据时的默认布局）
        View emptyView = View.inflate(context, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        rlBeautifulGirl.setEmptyView(emptyView);
        //设置列表的样式
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rlBeautifulGirl.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    protected void initData() {
        //进行轮播
        StartCarrousel();
        data = new ArrayList<>();
        data.add("苹果");
        data.add("香蕉");
        data.add("葡萄");
        data.add("荔枝");
        data.add("栗子");
        data.add("火龙果");
        data.add("西瓜");
        //创建适配器，参数为1.上下文    2.布局id   3.当前item的position
        adapter = new ModeAdapter<String>(getContext(), R.layout.item_mode, data) {

            @Override
            public void convert(ViewHolder holder, String s, final int position) {

                //设置文本信息
//                holder.setText(R.id.textView, s);
                //设置图片
                // holder.setImageResource(R.id.iv,)


                //设置字体颜色
                // holder.setTextColor(R.id.textView, );

                //设置当前界面是否可见
                // holder.setViewVisiable();

                //设置当前控件的背景色
                //holder.setViewBackgroundResource();

                // 设置点击事件
                holder.setOnclickListener(R.id.activity_main, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "我是第" + position + "个item", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        };
        rlBeautifulGirl.setAdapter(adapter);
        //设置是否开启上拉加载
        rlBeautifulGirl.setLoadingMoreEnabled(true);
        //设置是否开启下拉刷新
        rlBeautifulGirl.setPullRefreshEnabled(true);
        //设置是否显示上次刷新的时间
        rlBeautifulGirl.displayLastRefreshTime(true);
        //设置刷新回调
        rlBeautifulGirl.setPullToRefreshListener(this);
    }

    private void StartCarrousel() {
        getData();
        stringStartRoll = new StartRoll<>(getActivity(), dataBeens, SViewPager, llDots);
        stringStartRoll.setViewpagerData();
    }

    //设置数据源
    public void getData() {
        dataBeens.clear();
        for (int j = 0; j < 5; j++) {
            dataBeens.add(j + "");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    //用户下拉刷新加载数据
    @Override
    public void onRefresh() {
        rlBeautifulGirl.postDelayed(new Runnable() {
            @Override
            public void run() {
                rlBeautifulGirl.setRefreshComplete();
                //模拟没有数据的情况
                data.clear();
                adapter.notifyDataSetChanged();
//                int size = data.size();
//                for (int i = size; i < size + 4; i++) {
//                    data.add("" + i + i + i + i);
//                }
//                adapter.notifyDataSetChanged();
            }
        }, 3000);
    }

    @Override
    public void onLoadMore() {
        rlBeautifulGirl.postDelayed(new Runnable() {
            @Override
            public void run() {
                i++;
                if (i >= 4) {
                    //这个地方可以设置数据全部加载完毕以后，提示用户没有更多数据的提示信息
//                    recyclerView.addFooterView(footerView);
//                    recyclerView.setLoadingMoreEnabled(false);
                    rlBeautifulGirl.setLoadMoreFail();
                    return;
                }
                rlBeautifulGirl.setLoadMoreComplete();
                //模拟加载数据的情况
                int size = data.size();
                for (int i = size; i < size + 6; i++) {
                    data.add("" + i + i + i + i);
                }
                adapter.notifyDataSetChanged();

            }
        }, 3000);
    }
}
