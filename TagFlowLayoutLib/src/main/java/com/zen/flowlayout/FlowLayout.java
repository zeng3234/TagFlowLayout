package com.zen.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureVertical(widthMeasureSpec, heightMeasureSpec);
    }


    private void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        int mTotalLength = 0;
        //此处计算的是当前view的大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int count = getChildCount();
        //计算每个子类的大小
        int maxCalcWidth = 0;//最大宽度，当前view的width为wrap_content时有效
        int singleWidth = 0;//当前行宽度
        int realMaxWidth = sizeWidth - getPaddingLeft() - getPaddingRight();//去除当前view的padding就是子类可使用的大小
        mTotalLength += getPaddingTop() + getPaddingBottom();
        int lineHeight = 0;
        //
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);//InnerTv继承textView啥都没做,可以不用重写子view的onMeasure。。

            if (child == null) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);//计算子view的宽高
            MarginLayoutParams lps = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lps.leftMargin + lps.rightMargin;
            int childHeight = child.getMeasuredHeight() + lps.topMargin + lps.bottomMargin;

            if (singleWidth + childWidth > realMaxWidth) {//超出最大宽度
                //上一个重新绘制
                if (isLastFixWidth()) {
                    int pre = i - 1;
                    if (pre >= 0) {
                        View prev = getChildAt(pre);
                        if (prev instanceof IFixWidthView) {
                            ((IFixWidthView) prev).reMeasure(realMaxWidth - singleWidth + prev.getMeasuredWidth());
                            measureChild(prev, widthMeasureSpec, heightMeasureSpec);
                        }
                    }
                }
                //下一行
                maxCalcWidth = Math.max(maxCalcWidth, singleWidth);
                singleWidth = childWidth;
                mTotalLength += lineHeight;
                lineHeight = childHeight;
            } else {
                singleWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //最后一行
            if (i == count - 1) {
                maxCalcWidth = Math.max(maxCalcWidth, singleWidth);
                mTotalLength += lineHeight;
            }
        }
        //设置最终高度
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : maxCalcWidth,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : mTotalLength
        );

    }

    /**
     * 尾部那个长度增加，满屏
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int realWidth = r - l - getPaddingRight();//- getPaddingLeft()此处去掉paddingLeft,因为layout(l 不是从0开始
        int left = getPaddingLeft();//记录当前行的left,
        int top = getPaddingTop();//记录当前行的top

        //每一行的最高
        int lineHeight = 0;
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
            int vw = view.getMeasuredWidth();
            int vh = view.getMeasuredHeight();
            if (left + vw + lp.rightMargin + lp.leftMargin > realWidth) {
                left = getPaddingLeft();
                top += lineHeight;//last,
                lineHeight = vh + lp.topMargin + lp.bottomMargin;
            }
            lineHeight = Math.max(lineHeight, vh + lp.topMargin
                    + lp.bottomMargin);
            int cl = left + lp.leftMargin;
            int ct = top + lp.topMargin;
            int cr = cl + vw;
            int cb = ct + vh;

            view.layout(cl, ct, cr, cb);
            //绘制完成后，cl需要加上宽度和margin
            left += (vw + lp.rightMargin + lp.leftMargin);//
        }

    }

    /**
     * 最后个view宽度满屏据查看{@link com.zen.flowlayout.SimpleFixWidthView} 中的 {@link com.zen.flowlayout.SimpleFixWidthView#onMeasure(int widthMeasureSpec, int heightMeasureSpec)}<br/>
     *
     * @return
     */
    protected boolean isLastFixWidth() {
        return false;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
