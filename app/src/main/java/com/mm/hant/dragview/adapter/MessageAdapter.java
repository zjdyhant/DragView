package com.mm.hant.dragview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mm.hant.dragview.R;
import com.mm.hant.dragview.entity.MessageDate;
import com.mm.hant.dragview.widget.DragTextView;

import java.util.List;

/**
 * Created by hantao on 16/12/11.
 */

public class MessageAdapter extends SimpleBaseAdapter<MessageDate> {


    private OnCountClickListener mListener;

    public MessageAdapter(Context context) {
        super(context);
    }

    public MessageAdapter(Context context, List<MessageDate> data, OnCountClickListener onCountClickListener) {
        super(context, data);
        mListener = onCountClickListener;
    }

    @Override
    public int getItemResource() {
        return R.layout.list_item_message;
    }

    @Override
    public View getItemView(final int position, final View convertView, ViewHolder holder) {
        MessageDate messageDate = (MessageDate) getItem(position);
        TextView textViewMessage = holder.getView(R.id.txt_content);
        textViewMessage.setText(messageDate.getMessage());
        final DragTextView messageCount = holder.getView(R.id.txt_count);
        messageCount.setTextColor(0xffffffff);
        //当未读消息数大于0的时候，显示messageCount
        if (!TextUtils.isEmpty(messageDate.getCount()) && Integer.parseInt(messageDate.getCount()) > 0) {
            messageCount.setVisibility(View.VISIBLE);
            messageCount.setText(Integer.parseInt(messageDate.getCount()) > 15 ? "99+" : messageDate.getCount());
        } else {
            messageCount.setVisibility(View.GONE);
        }
        //点击messageCount的回调
        messageCount.setOnTouchDownListener(new DragTextView.OnTouchDownListener() {
            @Override
            public void onTouchDown(TextView textView) {
                if (mListener != null) {
                    View view = (View) convertView.getParent();
                    view.setEnabled(false);
                    view.setClickable(false);
                    view.setLongClickable(false);
                    mListener.onCountClick(convertView, messageCount, position);
                }
            }
        });
        return convertView;
    }

    public interface OnCountClickListener {
        void onCountClick(View convertView, TextView messageCountTextView, int position);
    }
}
