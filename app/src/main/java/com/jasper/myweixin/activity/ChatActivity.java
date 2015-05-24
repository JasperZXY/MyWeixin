package com.jasper.myweixin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jasper.myweixin.R;
import com.jasper.myweixin.adapter.ChatMsgViewAdapter;
import com.jasper.myweixin.entity.ChatMsg;
import com.jasper.myweixin.utils.TimeUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends Activity {
    public static final String USER_NAME = "userName";
    public static final String HEAD = "HEAD";

    private Button btnBack;
    private Button btnSend;
    private EditText mEditTextContent;
    private ListView listView;
    private ChatMsgViewAdapter chatMsgViewAdapter;
    private List<ChatMsg> msgs = new ArrayList<ChatMsg>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        btnBack =  (Button) findViewById(R.id.btn_back);
        btnSend =  (Button) findViewById(R.id.btn_send);
        mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
        listView = (ListView) findViewById(R.id.listview);

        btnSend.setOnClickListener(new ButtonClickListener());
        btnBack.setOnClickListener(new ButtonClickListener());

        ((TextView) findViewById(R.id.tv_username)).setText(getIntent().getStringExtra(USER_NAME));

        String[]dataArray = new String[]{"2015-05-24 18:00", "2015-05-24 18:10",
            "2015-05-24 18:11", "2015-05-24 18:20",
            "2015-05-24 18:30", "2015-05-24 18:35",
            "2015-05-24 18:40", "2015-05-24 18:50"};

        String userName = getIntent().getStringExtra(USER_NAME);
        int head = getIntent().getIntExtra(HEAD, R.drawable.xiaohei);
        for (int i=0; i<dataArray.length; i++) {
            ChatMsg msg = new ChatMsg();
            msg.setDate(dataArray[i]);
            msg.setName(userName);
            msg.setText("在吗");
            msg.setHead(head);
            msg.setSelf(false);
            msgs.add(msg);
        }

        chatMsgViewAdapter = new ChatMsgViewAdapter(this, msgs);
        listView.setAdapter(chatMsgViewAdapter);
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_send:
                    send();

            }
        }
    }

    private void send() {
        String contString = mEditTextContent.getText().toString();
        if (contString.length() > 0)
        {
            ChatMsg msg = new ChatMsg();
            msg.setDate(TimeUtil.dateTime2string(new Date()));
            msg.setName("我");
            msg.setSelf(true);
            msg.setText(contString);

            msgs.add(msg);
            chatMsgViewAdapter.notifyDataSetChanged();

            mEditTextContent.setText("");

           listView.setSelection(listView.getCount() - 1);
        }
    }
}
