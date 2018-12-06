package com.zen.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class SimpleFixWidthView extends TextView implements IFixWidthView {
    public SimpleFixWidthView(Context context) {
        super(context);
    }

    public SimpleFixWidthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleFixWidthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = widthMeasureSpec;
        if (reWidth != 0) {
            width = MeasureSpec.makeMeasureSpec(reWidth, MeasureSpec.EXACTLY);
        }
        super.onMeasure(width, heightMeasureSpec);
    }

    private int reWidth;

    @Override
    public void reMeasure(int width) {
        this.reWidth = width;
    }
}
