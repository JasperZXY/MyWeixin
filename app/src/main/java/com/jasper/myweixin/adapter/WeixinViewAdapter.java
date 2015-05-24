package com.jasper.myweixin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasper.myweixin.R;
import com.jasper.myweixin.activity.ChatActivity;
import com.jasper.myweixin.entity.WeixinItem;

import java.util.List;

/**
 * Created by Jasper on 2015/5/24.
 */
public class WeixinViewAdapter extends BaseAdapter {
    private List<WeixinItem> items;
    private Context ctx;
    private LayoutInflater mInflater;

    public WeixinViewAdapter(Context ctx, List<WeixinItem> items) {
        this.ctx = ctx;
        this.items = items;
        mInflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final WeixinItem item = items.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.main_tab_weixin_item, null);

            viewHolder = new ViewHolder();
            viewHolder.time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.say = (TextView) convertView.findViewById(R.id.tv_say);
            viewHolder.head = (ImageView) convertView.findViewById(R.id.img_head);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.time.setText(item.getTime());
        viewHolder.userName.setText(item.getUserName());
        viewHolder.say.setText(item.getSay());
        viewHolder.head.setImageResource(item.getHead());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ChatActivity.class);
                intent.putExtra(ChatActivity.USER_NAME, item.getUserName());
                intent.putExtra(ChatActivity.HEAD, item.getHead());
                ctx.startActivity(intent);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        public TextView time;
        public TextView userName;
        public TextView say;
        public ImageView head;
        void setTime() {
        }
    }
}
