package com.jasper.myweixin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasper.myweixin.R;
import com.jasper.myweixin.entity.ChatMsg;

import java.util.List;

/**
 * 聊天窗口ListView用Adapter
 */
public class ChatMsgViewAdapter extends BaseAdapter {

    private List<ChatMsg> msgs;
    private Context ctx;
    private LayoutInflater mInflater;

    public ChatMsgViewAdapter(Context ctx, List<ChatMsg> msgs) {
        this.ctx = ctx;
        this.msgs = msgs;
        mInflater = LayoutInflater.from(ctx);
    }


    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Object getItem(int position) {
        return msgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMsg msg = msgs.get(position);
        ViewHolder viewHolder = null;
        //FIXME 这里的判断不太好，但不这么写，新添加的老是不能走到这个逻辑，直接就else了
        if (convertView == null || (msg.isSelf() && position == msgs.size() - 1)) {
            if (msg.isSelf()) {
                convertView = mInflater.inflate(R.layout.chat_item_right, null);
            } else {
                convertView = mInflater.inflate(R.layout.chat_item_left, null);
            }

            viewHolder = new ViewHolder();
            viewHolder.content = (TextView) convertView.findViewById(R.id.chat_content);
            viewHolder.sendTime = (TextView) convertView.findViewById(R.id.chat_sendtime);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.chat_username);
            viewHolder.head = (ImageView) convertView.findViewById(R.id.chat_head);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.self = msg.isSelf();
        viewHolder.content.setText(msg.getText());
        viewHolder.sendTime.setText(msg.getDate());
        viewHolder.userName.setText(msg.getName());
        viewHolder.head.setImageResource(msg.getHead());
        return convertView;
    }

    private static class ViewHolder {
        public TextView sendTime;
        public TextView userName;
        public TextView content;
        public ImageView head;
        public boolean self = true;
    }
}

