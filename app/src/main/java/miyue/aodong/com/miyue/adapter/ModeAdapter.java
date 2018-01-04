package miyue.aodong.com.miyue.adapter;

import android.content.Context;

import java.util.List;


/**
 * Created by Kun on 2016/12/14.
 * GitHub: https://github.com/AndroidKun
 * CSDN: http://blog.csdn.net/a1533588867
 * Description:模版
 */

public abstract class ModeAdapter<T> extends BaseAdapter<T> {

//    public ModeAdapter(Context context, List datas) {
//        super(context, R.layout.item_mode, datas);
//    }

//    @Override
//    public void convert(ViewHolder holder, Object o, final int position) {
//        holder.setText(R.id.textView, (String) o);
//
//
//        holder.setOnclickListener(R.id.activity_main, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "我是第" + position + "个item", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }

    public ModeAdapter(Context context, int layoutid, List datas) {
        super(context, layoutid, datas);
    }

    @Override
    public abstract void convert(ViewHolder holder, T t, int position);


}
