package com.mm.hant.dragview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mm.hant.dragview.adapter.MessageAdapter;
import com.mm.hant.dragview.adapter.SimpleBaseAdapter;
import com.mm.hant.dragview.entity.MessageDate;
import com.mm.hant.dragview.widget.DragView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private DragView mDragView;


    private TextView mTempTextView;
    private SimpleBaseAdapter mMessageAdapter;
    private int mPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list_view);
        mDragView = (DragView) findViewById(R.id.drag_view);
        mDragView.setOnRemoveUnreadMessageListener(new DragView.OnRemoveUnreadMessageListener() {
            @Override
            public void onRemoveUnreadMessage(boolean isReset) {
                mListView.setEnabled(true);
                mListView.setClickable(true);
                mListView.setLongClickable(true);
                if(isReset){
                    mTempTextView.setVisibility(View.VISIBLE);
                }else{
                    MessageDate messageData = (MessageDate) mMessageAdapter.getItem(mPosition);
                    messageData.setCount("0");
                    mMessageAdapter.notifyDataSetChanged();
                }
            }
        });


        List<MessageDate> message = getMessage();
        mMessageAdapter = new MessageAdapter(this, message, new MessageAdapter.OnCountClickListener() {
            @Override
            public void onCountClick(View convertView, TextView messageCountTextView,int position) {
                mListView.setEnabled(false);
                mListView.setClickable(false);
                mListView.setLongClickable(false);
                mTempTextView = messageCountTextView;
                messageCountTextView.setVisibility(View.GONE);
                mDragView.addMessageCountTextView(messageCountTextView,convertView.getTop());
                mPosition = position;
            }
        });
        mListView.setAdapter(mMessageAdapter);
    }

    public List<MessageDate> getMessage() {
        List<MessageDate> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MessageDate message = new MessageDate();
            message.setMessage("hello i am jack  " + i);
            message.setCount(i + "");
            list.add(message);
        }
        return list;
    }

}
