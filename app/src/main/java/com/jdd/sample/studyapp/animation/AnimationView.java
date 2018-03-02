package com.jdd.sample.studyapp.animation;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * @author lc. 2018-03-02 09:49
 * @since 0.5.1
 */

public class AnimationView extends View {

    private ValueAnimator mAnimator;

    public AnimationView(Context context) {
        super(context);
        init();
    }

    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mAnimator = ValueAnimator.ofFloat();
        mAnimator.setDuration(1000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 将当前动画的数值作为空间的高度
                mDrawHeight = (float) animation.getAnimatedValue();
                // 绘制
                postInvalidate();
            }
        });

        // 用来绘制的画笔
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        // 用来绘制数值 Text 的画笔
        mTextPaint = new TextPaint(mPaint);
        mTextPaint.setTextSize(dp2px(getContext(), 14));
    }

    private float mDrawHeight;

    private Paint mPaint;

    private TextPaint mTextPaint;

    private RectF mDrawRect = new RectF();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int halfLineWidth = dp2px(getContext(), 3);

        int left = width / 2 - halfLineWidth;
        int right = width / 2 + halfLineWidth;
        int bottom = height;
        int top = height;
        mDrawRect.set(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制当前的高度
        mDrawRect.set(mDrawRect.left, mDrawRect.bottom - mDrawHeight, mDrawRect.right, mDrawRect.bottom);
        canvas.drawRect(mDrawRect, mPaint);

        // 绘制当前数据的 Text
        canvas.drawText(String.valueOf(mDrawHeight), 0, mDrawRect.bottom / 2, mTextPaint);
    }

    /** 设置动画执行时间 */
    public void setDuration(int duration) {
        mAnimator.setDuration(duration);
    }

    /** 动画数值变化过程 */
    public void setValueArray(float[] valueArray) {
        mAnimator.setFloatValues(valueArray);
    }

    /** 设置插值器 */
    public void setInterpolator(Interpolator interpolator) {
        mAnimator.setInterpolator(interpolator);
    }

    /** 开始执行动画 */
    public void start() {
        if (mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        mAnimator.start();
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
