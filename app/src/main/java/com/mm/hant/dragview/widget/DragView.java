package com.mm.hant.dragview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mm.hant.dragview.R;


/**
 * Created by hantao on 16/12/10.
 */

public class DragView extends FrameLayout {

    private TextView mTextViewCount;

    private Paint mPaint;
    private Path mPath;
    private float mRadius;
    private int mSrcRadius;
    private float mActionDownX;
    private float mActionDownY;
    private float mActionMoveX;
    private float mActionMoveY;
    private boolean mStopDrawCircle;
    private boolean mCanReset = true;
    private int mStartX;
    private int mStartY;
    private OnRemoveUnreadMessageListener mRemoveUnreadMessageListener;


    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#ff0000"));
        mPaint.setAntiAlias(true);
        mPath = new Path();
    }

    public void addMessageCountTextView(TextView textView, int marginTop) {

        mTextViewCount = new TextView(getContext());
        ViewGroup.LayoutParams srcLayoutParams = textView.getLayoutParams();
        mSrcRadius = srcLayoutParams.width / 2;
        mRadius = mSrcRadius;
        mStartX = textView.getLeft();
        mStartY = marginTop + textView.getTop();
        LayoutParams layoutParams = new LayoutParams(srcLayoutParams.width, srcLayoutParams.height);
        layoutParams.setMargins(mStartX, mStartY, 0, 0);
        mTextViewCount.setBackgroundResource(R.drawable.circle_red);
        mTextViewCount.setText(textView.getText());
        mTextViewCount.setTextColor(textView.getTextColors());
        mTextViewCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize());
        mTextViewCount.setGravity(textView.getGravity());
        mTextViewCount.setLayoutParams(layoutParams);
        addView(mTextViewCount);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float distance = (float) Math.sqrt(Math.pow(mActionMoveY - mActionDownY, 2) + Math.pow(mActionDownX - mActionMoveX, 2));
        mRadius = mSrcRadius - distance / 10;
        if (mRadius < 0) {
            return;
        }
        if (mRadius < mSrcRadius / 3) {
            mStopDrawCircle = true;
            mCanReset = false;
        } else {
            mCanReset = true;
        }
        if (!mStopDrawCircle && mTextViewCount != null) {
            int centerX = mStartX + mSrcRadius;
            int centerY = mStartY + mSrcRadius;
            canvas.drawCircle(centerX, centerY, mRadius, mPaint);
            float dx = (float) (mRadius * (Math.sin(Math.atan((mActionMoveY - centerY) / (mActionMoveX - centerX)))));
            float dy = (float) (mRadius * (Math.cos(Math.atan((mActionMoveY - centerY) / (mActionMoveX - centerX)))));
            float x1 = centerX - dx;
            float y1 = centerY + dy;
            float x2 = mTextViewCount.getX() + mTextViewCount.getWidth() / 2 - dx;
            float y2 = mTextViewCount.getY() + mTextViewCount.getHeight() / 2 + dy;
            float x3 = mTextViewCount.getX() + mTextViewCount.getWidth() / 2 + dx;
            float y3 = mTextViewCount.getY() + mTextViewCount.getHeight() / 2 - dy;
            float x4 = centerX + dx;
            float y4 = centerY - dy;
            float halfX = (mActionMoveX + centerX) / 2;
            float halfY = (mActionMoveY + centerY) / 2;
            mPath.reset();
            mPath.moveTo(x1, y1);
            mPath.quadTo(halfX, halfY, x2, y2);
            mPath.lineTo(x3, y3);
            mPath.quadTo(halfX, halfY, x4, y4);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActionDownX = event.getX();
                mActionDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mActionMoveX = event.getX();
                mActionMoveY = event.getY();
                if (mTextViewCount != null) {
                    mTextViewCount.setTranslationX(mActionMoveX - mActionDownX);
                    mTextViewCount.setTranslationY(mActionMoveY - mActionDownY);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mTextViewCount != null) {
                    if (mCanReset) {
                        mTextViewCount.setTranslationX(0);
                        mTextViewCount.setTranslationY(0);
                    }
                    if (mRemoveUnreadMessageListener != null) {
                        mRemoveUnreadMessageListener.onRemoveUnreadMessage(mCanReset);
                    }
                    mStopDrawCircle = false;
                    removeView(mTextViewCount);
                    mTextViewCount = null;
                }
                mActionDownX = 0;
                mActionMoveX = 0;
                mActionMoveY = 0;
                mActionDownY = 0;
                break;
        }
        invalidate();
        return mTextViewCount == null ? super.onTouchEvent(event) : true;

    }

    public void setOnRemoveUnreadMessageListener(OnRemoveUnreadMessageListener onRemoveUnreadMessageListener) {
        mRemoveUnreadMessageListener = onRemoveUnreadMessageListener;
    }

    public interface OnRemoveUnreadMessageListener {
        void onRemoveUnreadMessage(boolean isReset);
    }

}
