package com.jdd.sample.studyapp.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.ui.BaseActivity;

/**
 * @author lc
 */
public class AnimationActivity extends BaseActivity {

    // 动画
    // 传统动画：帧动画 补间动画（淡入淡出，位移，缩放大小，旋转）
    // 属性动画

    private Interpolator currentInterpolator;
    private Interpolator[] interpolatorArray;
    private String[] interpolatorNameArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        setTitle("Animation");
        setToolbarAsBack(v -> finish());

        initInterpolatorArray();
        initFrameAnimation();
        initTweenAnimation();
        initObjectAnimation();
        initCustomObjectAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animation_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_interpolator_select:
                showInterpolatorSelectDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initInterpolatorArray() {
        interpolatorArray = new Interpolator[]{
                new AccelerateDecelerateInterpolator(),
                new AccelerateInterpolator(),
                new AnticipateOvershootInterpolator(),
                new AnticipateInterpolator(),
                new BounceInterpolator(),
                new CycleInterpolator(10),
                new DecelerateInterpolator(),
                new LinearInterpolator(),
                new OvershootInterpolator()
        };
        interpolatorNameArray = new String[interpolatorArray.length];
        for (int i = 0; i < interpolatorArray.length; i++) {
            interpolatorNameArray[i] = interpolatorArray[i].getClass().getSimpleName();
        }
        currentInterpolator = interpolatorArray[0];
    }

    /** 插值器选择弹框 */
    private void showInterpolatorSelectDialog() {
        int currentInterpolatorIndex = 0;
        for (int i = 0; i < interpolatorArray.length; i++) {
            if (interpolatorArray[i] == currentInterpolator) {
                currentInterpolatorIndex = i;
                break;
            }
        }

        new AlertDialog.Builder(this)
                .setTitle("选择插值器")
                .setSingleChoiceItems(
                        interpolatorNameArray, currentInterpolatorIndex, (dialog, which) -> {
                            currentInterpolator = interpolatorArray[which];
                            dialog.dismiss();
                        }).show();
    }

    ///////////////////////// 帧动画 /////////////////////////

    private void initFrameAnimation() {
        ImageView loadingIv = findViewById(R.id.iv_coin_loading);
        AnimationDrawable drawable = (AnimationDrawable) getResources().getDrawable(R.drawable.coin_animation);
        loadingIv.setBackgroundDrawable(drawable);

        findViewById(R.id.btn_tween_start).setOnClickListener(v -> {
            drawable.start();
        });

        findViewById(R.id.btn_tween_stop).setOnClickListener(v -> {
            drawable.stop();
        });
    }

    ///////////////////////// 补间动画 /////////////////////////

    private void initTweenAnimation() {
        View view = findViewById(R.id.viewTween);
        view.setOnClickListener(v -> Toast.makeText(this, "view tween on click", Toast.LENGTH_SHORT).show());
        findViewById(R.id.alpha).setOnClickListener(v -> alpha(view));
        findViewById(R.id.translate).setOnClickListener(v -> translate(view));
        findViewById(R.id.scale).setOnClickListener(v -> scale(view));
        findViewById(R.id.rotate).setOnClickListener(v -> rotate(view));
        findViewById(R.id.btn_tween_set).setOnClickListener(v -> tweenAnimationSet(view));

        findViewById(R.id.alpha_xml).setOnClickListener(v -> alphaXml(view));
        findViewById(R.id.translate_xml).setOnClickListener(v -> translateXml(view));
        findViewById(R.id.scale_xml).setOnClickListener(v -> scaleXml(view));
        findViewById(R.id.rotate_xml).setOnClickListener(v -> rotateXml(view));
        findViewById(R.id.btn_tween_set_xml).setOnClickListener(v -> tweenAnimationSetXml(view));
    }

    /** 透明度 */
    private void alpha(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setInterpolator(currentInterpolator);
        view.startAnimation(alphaAnimation);
    }

    /** 位移 */
    private void translate(View view) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, -200, 0, 0);
        translateAnimation.setDuration(2000);
        translateAnimation.setInterpolator(currentInterpolator);
        view.startAnimation(translateAnimation);
    }

    /** 缩放 */
    private void scale(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.5f, 0.0f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setInterpolator(currentInterpolator);
        view.startAnimation(scaleAnimation);
    }

    /** 旋转 */
    private void rotate(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(currentInterpolator);
        view.startAnimation(rotateAnimation);
    }

    /** 补间动画组合 */
    private void tweenAnimationSet(View view) {
        AnimationSet animationSet = new AnimationSet(false);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setInterpolator(currentInterpolator);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, -200, 0, 0);
        translateAnimation.setDuration(2000);
        translateAnimation.setInterpolator(currentInterpolator);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.5f, 0.0f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setInterpolator(currentInterpolator);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(currentInterpolator);

        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(scaleAnimation);

        view.startAnimation(animationSet);
    }

    /** 透明度 Xml */
    private void alphaXml(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
    }

    /** 位移 Xml */
    private void translateXml(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
    }

    /** 缩放 Xml */
    private void scaleXml(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));
    }

    /** 旋转 Xml */
    private void rotateXml(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));
    }

    /** 补间动画组合 Xml */
    private void tweenAnimationSetXml(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tween_set));
    }

    ///////////////////////// 属性动画 /////////////////////////

    private void initObjectAnimation() {
        View view = findViewById(R.id.object_animation);
        findViewById(R.id.btn_object_animation_start).setOnClickListener(v -> showObjectAnimation(view));
    }

    private void showObjectAnimation(View view) {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1.0f);
        alphaAnimator.setDuration(2000);
        alphaAnimator.setInterpolator(currentInterpolator);

        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(view, "x", view.getX(), view.getX() - 200f, view.getX());
        translateAnimator.setDuration(2000);
        translateAnimator.setInterpolator(currentInterpolator);

        ObjectAnimator translateAnimator1 = ObjectAnimator.ofFloat(view, "x", view.getX(), view.getX() + 200f, view.getX());
        translateAnimator1.setDuration(2000);
        translateAnimator1.setInterpolator(currentInterpolator);

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0, 1.5f, 1f);
        alphaAnimator.setDuration(2000);
        alphaAnimator.setInterpolator(currentInterpolator);

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0, 1.5f, 1f);
        alphaAnimator.setDuration(2000);
        alphaAnimator.setInterpolator(currentInterpolator);

        ObjectAnimator turnOverXAnimator = ObjectAnimator.ofFloat(view, "rotationX", 180f, 0);
        turnOverXAnimator.setDuration(1000);
        turnOverXAnimator.setInterpolator(currentInterpolator);

        ObjectAnimator turnOverYAnimator = ObjectAnimator.ofFloat(view, "rotationY", 180f, 0);
        turnOverYAnimator.setDuration(1000);
        turnOverYAnimator.setInterpolator(currentInterpolator);

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", 0, 360);
        rotationAnimator.setDuration(1000);
        rotationAnimator.setInterpolator(currentInterpolator);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setTarget(view);
        animatorSet.playTogether(alphaAnimator, translateAnimator1, translateAnimator, scaleXAnimator, scaleYAnimator, turnOverXAnimator, turnOverYAnimator, rotationAnimator);
//        animatorSet.playTogether();
        animatorSet.start();
    }


    ///////////////////////// 属性动画 自定义 /////////////////////////

    private Point startPoint;
    private Point endPoint;
    private Point controlPoint;

    private void initCustomObjectAnimation() {

        FrameLayout.LayoutParams buttonLp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLp.gravity = Gravity.BOTTOM | Gravity.END;

        Button button = new Button(this);
        button.setText("POP");
        button.setLayoutParams(buttonLp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popImage();
            }
        });

        ((FrameLayout) getWindow().getDecorView()).addView(button);

        int windowWidth = getResources().getDisplayMetrics().widthPixels;
        int windowHeight = getResources().getDisplayMetrics().heightPixels;

        int startX = windowWidth - 150;
        int startY = 100;
        int endX = 0;
        int endY = windowHeight - 100;
        int controlX = windowWidth / 2;
        int controlY = 0;

        startPoint = new Point(startX, startY);
        endPoint = new Point(endX, endY);
        controlPoint = new Point(controlX, controlY);

    }

    private void popImage() {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundColor(Color.BLUE);
        FrameLayout.LayoutParams ivLp = new FrameLayout.LayoutParams(100, 100);
        imageView.setLayoutParams(ivLp);

        ((FrameLayout) getWindow().getDecorView()).addView(imageView);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(controlPoint), startPoint, endPoint);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(currentInterpolator);
        valueAnimator.addUpdateListener((animation) -> {
            Point point = (Point) animation.getAnimatedValue();
            imageView.setX(point.x);
            imageView.setY(point.y);
            imageView.invalidate();
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((FrameLayout) getWindow().getDecorView()).removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();

    }

    private class BezierEvaluator implements TypeEvaluator<Point> {

        private Point controlPoint;

        private BezierEvaluator(Point controlPoint) {
            this.controlPoint = controlPoint;
        }

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            int x = (int) ((1 - fraction) * (1 - fraction) * startValue.x + 2 * fraction * (1 - fraction) * controlPoint.x + fraction * fraction * endValue.x);
            int y = (int) ((1 - fraction) * (1 - fraction) * startValue.y + 2 * fraction * (1 - fraction) * controlPoint.y + fraction * fraction * endValue.y);
            return new Point(x, y);
        }
    }
}
