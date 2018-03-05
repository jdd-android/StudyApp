package com.jdd.sample.studyapp.touchevent;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.utils.DensityUtils;

/**
 * @author lc. 2018-03-05 16:14
 * @since 0.5.1
 */

public class ChildView extends View {

    private boolean consumeTouchEvent = false;

    private String tag = "";

    private TextPaint textPaint;

    private LogCallBack mLogCallBack;

    public ChildView(Context context) {
        this(context, null);
    }

    public ChildView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        textPaint = new TextPaint();
        textPaint.setTextSize(DensityUtils.dp2px(context, 14));
        textPaint.setColor(getResources().getColor(R.color.appTextColorPrimary));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(tag, DensityUtils.dp2px(getContext(), 4), DensityUtils.dp2px(getContext(), 14), textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (consumeTouchEvent) {
            Log.d(tag, "onTouchEvent consume " + eventString(event));
            callbackLog(tag, "onTouchEvent consume " + eventString(event));
            return true;
        }
        Log.d(tag, "onTouchEvent " + eventString(event));
        callbackLog(tag, "onTouchEvent " + eventString(event));
        return super.onTouchEvent(event);
    }

    private String eventString(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            default:
                return "";
        }
    }

    private void callbackLog(String tag, String content) {
        if (mLogCallBack != null) {
            mLogCallBack.log(tag, content);
        }
    }

    public void setLogCallBack(LogCallBack logCallBack) {
        this.mLogCallBack = logCallBack;
    }

    public void setConsumeTouchEvent(boolean consumeTouchEvent) {
        this.consumeTouchEvent = consumeTouchEvent;
    }

    public boolean isConsumeTouchEvent() {
        return consumeTouchEvent;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String getTag() {
        return tag;
    }
}
