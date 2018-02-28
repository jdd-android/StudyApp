package com.jdd.sample.studyapp.ui.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lc. 2018-02-27 13:39
 * @since 1.0.0
 */

public class TagLayout<T extends Object> extends ViewGroup {

    private static final String TAG = TagLayout.class.getSimpleName();

    private static final int VIEW_ITEM_TAG_KEY = R.id.id_tag_layout_tag_key;

    private int mItemMarginHorizontal = 0;

    private int mItemMarginVertical = 0;

    private int mTagItemLayout = 0;

    private TagActionCallBack<T> mTagActionCallBack;

    private List<T> mTagList = new ArrayList<>();

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(21)
    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagLayout);

        mItemMarginHorizontal = (int) typedArray.getDimension(R.styleable.TagLayout_tag_margin_horizontal, mItemMarginHorizontal);
        mItemMarginVertical = (int) typedArray.getDimension(R.styleable.TagLayout_tag_margin_vertical, mItemMarginVertical);
        mTagItemLayout = typedArray.getResourceId(R.styleable.TagLayout_tag_layout, 0);

        typedArray.recycle();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d(TAG, "onMeasure");

        // 获取父控件为 TagLayout 设置的大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        // 获取父控件为 TagLayout 设置的测量模式 (参考鸿洋大神博客 http://blog.csdn.net/lmj623565791/article/details/38339817)
        // 有以下三种测量模式
        // MeasureSpec.EXACTLY; 表示设置了精确的值，一般当childView设置其宽、高为精确值、match_parent时，ViewGroup会将其设置为EXACTLY；
        // MeasureSpec.AT_MOST; 表示子布局被限制在一个最大值内，一般当childView设置其宽、高为wrap_content时，ViewGroup会将其设置为AT_MOST；
        // MeasureSpec.UNSPECIFIED; 表示子布局想要多大就多大，一般出现在AadapterView的item的heightMode中、ScrollView的childView的heightMode中；此种模式比较少见。
        int modeWidth = MeasureSpec.getMode(sizeWidth);
        int modeHeight = MeasureSpec.getMode(sizeHeight);

        // 宽高固定，无需计算显示所有 TAG 需要的高度
        if (modeWidth == MeasureSpec.EXACTLY && modeHeight == MeasureSpec.EXACTLY) {
            // 设置 TagLayout 的宽高
            setMeasuredDimension(sizeWidth, sizeHeight);
            return;
        }

        // 测量 TagLayout 的宽高，计算出显示所有 TAG 需要的宽高

        int sizeWidthValid = sizeWidth - getPaddingLeft() - getRight();
        // 记录最长一行 TAG 的宽度
        int width = 0;
        // 记录显示所有 TAG 需要占用的总高度
        int height = 0;

        // 记录当前行已经占用的宽度
        int lineWidth = 0;
        // 记录当前行中最大的 TAG 高度
        int lineMaxHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE) {
                continue;
            }
            // 测量 child 大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            // 获取 child layoutParams
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            // 测量大小
            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            Log.d(TAG, "childWidth=" + childWidth + "  childHeight=" + childHeight);

            // 如果本行加入该 child 则会超出目前的最大宽度，换行
            if ((lineWidth + childWidth) > sizeWidthValid) {
                width = Math.max(width, lineWidth);
                height = height + lineMaxHeight + mItemMarginVertical;

                Log.d("TagLayout", "换行 " + ((TextView) childView).getText() + " height+" + lineMaxHeight + "=" + height);

                lineWidth = childWidth;
                lineMaxHeight = childHeight;
            } else {
                // 否则，累加当前行的宽度，计算当前行的最大高度
                lineWidth = childWidth + lineWidth + mItemMarginHorizontal;
                lineMaxHeight = Math.max(childHeight, lineMaxHeight);
            }

            // 如果是最后一个 child，累加 height
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineMaxHeight;
                Log.d("TagLayout", "最后 child  " + ((TextView) childView).getText() + " height+" + lineMaxHeight + "=" + height);
            }
        }

        width = width + getPaddingLeft() + getPaddingRight();
        height = height + getPaddingTop() + getPaddingBottom();

        Log.d(TAG, "width=" + width + " height=" + height);

        if (modeWidth == MeasureSpec.EXACTLY) {
            Log.d(TAG, "width mode exactly");
        }
        if (modeHeight == MeasureSpec.EXACTLY) {
            Log.d(TAG, "height mode exactly");
        }

        // 设置 TagLayout 的宽高
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int startLeft = getPaddingLeft();
        int startTop = getPaddingTop();

        int drawWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int lineWidth = 0;
        int lineBottom = 0;
        int left, top, right, bottom;

        int layoutLeft = startLeft;
        int layoutTop = startTop;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE) {
                continue;
            }

            MarginLayoutParams childLp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            // 本行没有足够空间，换行
            if ((childWidth + childLp.leftMargin + childLp.rightMargin + lineWidth) > drawWidth) {
                layoutLeft = startLeft;
                layoutTop = lineBottom + mItemMarginVertical;

                left = layoutLeft + childLp.leftMargin;
                top = layoutTop + childLp.topMargin;
                right = left + childWidth;
                bottom = top + childHeight;
                childView.layout(left, top, right, bottom);

                lineBottom = bottom + childLp.bottomMargin;
                lineWidth = childWidth + childLp.leftMargin + childLp.rightMargin;
                layoutLeft = right + childLp.rightMargin + mItemMarginHorizontal;
            } else {
                left = layoutLeft + childLp.leftMargin;
                top = layoutTop + childLp.topMargin;
                right = left + childWidth;
                bottom = top + childHeight;
                childView.layout(left, top, right, bottom);

                lineBottom = Math.max(lineBottom, bottom + childLp.bottomMargin);
                lineWidth = right + childLp.rightMargin - startLeft;
                layoutLeft = right + childLp.rightMargin + mItemMarginHorizontal;
            }
        }
    }

    public void addTag(T tag) {
        View tagView = createTagView();
        tagView.setTag(VIEW_ITEM_TAG_KEY, tag);
        if (mTagActionCallBack != null) {
            mTagActionCallBack.onCreate(tag, tagView);
        }
        addView(tagView);
    }

    public void removeTag(T tag) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (CommonUtils.equals(getChildAt(i).getTag(VIEW_ITEM_TAG_KEY), tag)) {
                removeViewAt(i);
            }
        }
        for (int i = mTagList.size() - 1; i >= 0; i--) {
            if (CommonUtils.equals(mTagList.get(i), tag)) {
                mTagList.remove(i);
            }
        }
    }

    public void setTags(T... tags) {
        removeAllViews();

        if (tags == null) {
            return;
        }

        mTagList.clear();
        mTagList.addAll(Arrays.asList(tags));

        for (T tag : tags) {
            addTag(tag);
        }
    }

    public void setTagActionCallBack(TagActionCallBack<T> tagActionCallBack) {
        mTagActionCallBack = tagActionCallBack;
    }

    protected View createTagView() {
        if (mTagItemLayout == 0) {
            return null;
        }
        return LayoutInflater.from(getContext()).inflate(mTagItemLayout, this, false);
    }

    public interface TagActionCallBack<T> {
        /**
         * Tag 被创建时被回调，此时还没有被添加到{@link TagLayout}
         *
         * @param tag  Tag 绑定的数据
         * @param view Tag 对应的 View
         */
        void onCreate(T tag, View view);
    }
}
