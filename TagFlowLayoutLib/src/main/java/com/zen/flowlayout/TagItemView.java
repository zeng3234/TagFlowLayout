package com.zen.flowlayout;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * @author zeng
 */
public class TagItemView extends TextView implements IFixWidthView {
    public TagItemView(Context context, AttributeSet attrs,
                       int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public TagItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public TagItemView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    int type;
    int radius;
    //normal-attr
    int nStrokeWidth;
    int nStrokeColor;
    int nBgColor;
    //selected-attr
    int sStrokeColor;
    int sBgColor;
    boolean needSelector;
    //
    boolean lastFixWidth;


    private int reWidth;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = widthMeasureSpec;
        if (reWidth != 0) {
            width = MeasureSpec.makeMeasureSpec(reWidth, MeasureSpec.EXACTLY);
        }
        super.onMeasure(width, heightMeasureSpec);
        setBg();
    }

    private void setBg() {

        if (needSelector) {
            Drawable normalBg = buildNormalBg();
            Drawable selectedBg = buildSelectedBg();
            StateListDrawable drawable = new StateListDrawable();
            drawable.addState(new int[]{android.R.attr.state_selected}, selectedBg);//  状态  , 设置按下的图片
            drawable.addState(new int[]{}, normalBg);//默认状态,默认状态下的图片
            setBg(drawable);
        } else {
            Drawable drawable = buildNormalBg();
            setBg(drawable);
        }

    }

    private void setBg(Drawable drawable) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }


    public static GradientDrawable createDrawable(int solidColor, int strokeColor, int strokeWidth, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(solidColor);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setCornerRadius(radius);
        return drawable;
    }


    private Drawable buildNormalBg() {
        if (type == Builder.TYPE_CIRCLE) {
            radius = getMeasuredHeight() / 2;
        }
        return createDrawable(nBgColor, nStrokeColor, nStrokeWidth, radius);
    }

    private Drawable buildSelectedBg() {
        return createDrawable(sBgColor, sStrokeColor, nStrokeWidth, radius);
    }


    @Override
    public void reMeasure(int width) {
        if (lastFixWidth) {
            this.reWidth = width;
        }
    }


    public String test_getRadiusStr() {
        int dp = px2dp(getContext(), radius);
        return "px: " + radius + " dp: " + dp;
    }


    public static class Builder {


        public Builder(Context pContext) {
            this.mContext = pContext;
        }

        public TagItemView build() {
            TagItemView btn = new TagItemView(mContext);
            ViewGroup.MarginLayoutParams lps = new ViewGroup.MarginLayoutParams(width, height);
            btn.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            btn.setGravity(gravity);
            lps.setMargins(marginLeft, marginTop, marginRight, marginBottom);
            btn.setLayoutParams(lps);
            //bg
            btn.radius = radius;
            btn.type = type;
            btn.nBgColor = nBgColor;
            btn.nStrokeColor = nStrokeColor;
            btn.nStrokeWidth = nStrokeWidth;
            btn.needSelector = needSelector;
            btn.sBgColor = sBgColor;
            btn.sStrokeColor = sStrokeColor;
            btn.lastFixWidth = lastFixWidth;
            return btn;
        }

        /**
         * 设置所有属性;<br/>
         * 1,padding
         * 2,margin
         * 3,bg半圆或者圆角
         * 4,selector
         * 5,gravity
         */

        public static final int TYPE_RECTANGLE = 1;
        public static final int TYPE_CIRCLE = 2;
        private Context mContext;
        int width = ViewGroup.MarginLayoutParams.WRAP_CONTENT;
        int height = ViewGroup.MarginLayoutParams.WRAP_CONTENT;
        int marginTop, marginBottom, marginLeft, marginRight;
        int paddingTop, paddingBottom, paddingLeft, paddingRight;
        int gravity = Gravity.CENTER;
        int type;
        int radius;
        //normal-attr
        int nStrokeWidth;
        int nStrokeColor;
        int nBgColor;
        //selected-attr
        int sStrokeColor;
        int sBgColor;
        boolean needSelector = false;
        //
        boolean lastFixWidth = false;


        /**
         * default wrap_content
         *
         * @param width
         * @param height
         * @return
         */
        public Builder wh(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder margin(int l, int t, int r, int b) {
            this.marginTop = t;
            this.marginLeft = l;
            this.marginRight = r;
            this.marginBottom = b;
            return this;
        }

        public Builder marginDp(int l, int t, int r, int b) {
            this.marginTop = dp2px(mContext, t);
            this.marginLeft = dp2px(mContext, l);
            this.marginRight = dp2px(mContext, r);
            this.marginBottom = dp2px(mContext, b);
            return this;
        }

        public Builder padding(int l, int t, int r, int b) {
            this.paddingTop = t;
            this.paddingLeft = l;
            this.paddingRight = r;
            this.paddingBottom = b;
            return this;
        }

        public Builder paddingDp(int l, int t, int r, int b) {
            this.paddingTop = dp2px(mContext, t);
            this.paddingLeft = dp2px(mContext, l);
            this.paddingRight = dp2px(mContext, r);
            this.paddingBottom = dp2px(mContext, b);
            return this;
        }

        /**
         * default Gravity.center
         *
         * @param gravity
         * @return
         */
        private Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        /**
         * 只有在矩形的时候有效，圆形默认是高度一半
         *
         * @param radius
         * @return
         */
        public Builder radius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder radiusDp(int radius) {
            this.radius = dp2px(mContext, radius);
            return this;
        }

        /**
         * 每行最后一个填满
         *
         * @param isLastFixWidth
         * @return
         */
        public Builder lastFixWidth(boolean isLastFixWidth) {
            this.lastFixWidth = isLastFixWidth;
            return this;
        }

        /**
         * 设置默认背景不带selector
         *
         * @param type        TYPE_RECTANGLE OR TYPE_CIRCLE
         * @param bgColor
         * @param strokeWidth
         * @param strokeColor
         * @return
         */
        public Builder bg(int type, int bgColor, int strokeWidth, int strokeColor) {
            this.type = type;
            this.nBgColor = bgColor;
            this.nStrokeWidth = strokeWidth;
            this.nStrokeColor = strokeColor;
            return this;
        }

        public Builder bgSelected(int bgColor, int strokeColor) {
            this.sBgColor = bgColor;
            this.sStrokeColor = strokeColor;
            this.needSelector = true;
            return this;
        }

    }

    /**
     * px转换成dp
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp转换成px
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
