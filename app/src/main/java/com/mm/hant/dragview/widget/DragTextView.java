package com.mm.hant.dragview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by hantao on 16/12/11.
 */

public class DragTextView extends TextView {

    private OnTouchDownListener mListener;

    public DragTextView(Context context) {
        super(context);
    }

    public DragTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && mListener != null) {
            mListener.onTouchDown(this);
        }

        return super.onTouchEvent(event);
    }

    public void setOnTouchDownListener(OnTouchDownListener onTouchDownListener) {
        mListener = onTouchDownListener;
    }

    public interface OnTouchDownListener {
        void onTouchDown(TextView textView);
    }
}
