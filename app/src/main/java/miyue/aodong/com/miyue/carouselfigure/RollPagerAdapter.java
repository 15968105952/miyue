package miyue.aodong.com.miyue.carouselfigure;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import miyue.aodong.com.miyue.R;

public class RollPagerAdapter<T> extends PagerAdapter {
    private  Context mcontext;
    private  ArrayList<T> dataBeens=new ArrayList();
    private int i = 0;

    public RollPagerAdapter(Context mcontext, int i, ArrayList<T> dataBeens) {
        this.mcontext=mcontext;
        this.i = i;
        this.dataBeens=dataBeens;
    }

    @Override
    public int getCount() {
        int k = 0;
        switch (i) {
            case 0:
                k = Integer.MAX_VALUE;
                break;
            case 1:
                k = 1;
                break;
            case 2:
                k = Integer.MAX_VALUE;
                break;
            case 3:
                k = Integer.MAX_VALUE;
                break;
        }
        return k;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (i == 0) {
            position = position % dataBeens.size();
        } else {
            position = position % dataBeens.size() % i;
        }
        View view = View.inflate(mcontext, R.layout.activity_home_carousel_item, null);
        setData(view, position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private void setData(View view, int position) {
        final T s = dataBeens.get(position);
        ImageView im = (ImageView) view.findViewById(R.id.im);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件
                Toast.makeText(mcontext,"点击条目"+s,Toast.LENGTH_SHORT).show();
            }
        });
    }
}