package com.zen.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 支持多选，单选
 */
public class TagFlowLayout extends FlowLayout implements TagAdapter.OnDataChangedListener {
    public TagFlowLayout(Context context) {
        super(context);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Normal list that does not indicate choices
     */
    public static final int CHOICE_MODE_NONE = 0;

    /**
     * The list allows up to one choice
     */
    public static final int CHOICE_MODE_SINGLE = 1;
    /**
     * The list allows limit choices
     */
    public static final int CHOICE_MODE_LIMIT = 2;

    /**
     * The list allows multiple choices
     */
    public static final int CHOICE_MODE_MULTIPLE = 3;
    /**
     * Controls if/how the user may choose/check items in the list
     */
    private int mChoiceMode = CHOICE_MODE_NONE;
    private int mLimit = 1;

    private TagAdapter mAdapter;
    private OnTagClickListener mOnTagClickListener;
    private OnSelectListener mOnSelectListener;
    private Set<Integer> mSelectedSet = new HashSet<>();


    public void setAdapter(TagAdapter adapter) {
        this.mAdapter = adapter;
        mAdapter.setOnDataChangedListener(this);
        onChanged();
    }

    public interface OnSelectListener {
        void onSelected(View view, int position);
    }

    public interface OnTagClickListener {
        boolean onTagClick(View view, int position);
    }

    public void setOnTagClickListener(OnTagClickListener pListener) {
        mOnTagClickListener = pListener;
    }

    public void setOnSelectListener(OnSelectListener pListener) {
        mOnSelectListener = pListener;
    }

    /**
     * 只允许设置一次
     *
     * @param choiceMode
     */
    public void setChoiceMode(int choiceMode) {
        if (mChoiceMode == CHOICE_MODE_NONE && choiceMode != CHOICE_MODE_NONE) {
            mChoiceMode = choiceMode;
        }
    }

    /**
     * 强制改变当前模式,<br/>注意: 改模式的话，之前所有状态全部清空
     *
     * @param choiceMode
     */
    public void setChoiceModeForce(int choiceMode) {
        if (mChoiceMode != choiceMode) {
            clearAll();
            mChoiceMode = choiceMode;
        }
    }


    public void setLimit(int limit) {
        mLimit = limit;
    }

    public int[] getSelectedIds() {
        if (mSelectedSet.isEmpty()) {
            return new int[]{};
        }
        Iterator<Integer> iterator = mSelectedSet.iterator();
        int[] r = new int[mSelectedSet.size()];
        int i = 0;
        while (iterator.hasNext()) {
            r[i++] = iterator.next();
        }
        return r;
    }

    @Override
    public void onChanged() {
        removeAllViews();
        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View view = mAdapter.getView(i, mAdapter.getItem(i));
            final int pos = i;
            addView(view);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItem(v, pos);
                    if (mOnTagClickListener != null) {
                        mOnTagClickListener.onTagClick(v, pos);
                    }

                }
            });
        }
    }

    private void selectItem(View view, int position) {
        //当前模式
        switch (mChoiceMode) {
            case CHOICE_MODE_SINGLE:
                if (!mSelectedSet.isEmpty()) {
                    if (!mSelectedSet.contains(position)) {
                        int last = mSelectedSet.iterator().next();
                        View lastView = getChildAt(last);
                        removeSelect(lastView, last);
                        addSelect(view, position);
                    } else {
                        //重复点，不做操作
                    }
                } else {
                    //新的
                    addSelect(view, position);
                }
                break;
            case CHOICE_MODE_LIMIT:
                //

                if (mSelectedSet.contains(position)) {
                    removeSelect(view, position);
                } else if (mSelectedSet.size() < mLimit) {
                    addSelect(view, position);
                }
                break;
            case CHOICE_MODE_MULTIPLE:
                //
                if (mSelectedSet.contains(position)) {
                    removeSelect(view, position);
                } else {
                    addSelect(view, position);
                }
                break;
        }
    }

    private void addSelect(View view, int position) {
        mSelectedSet.add(position);
        view.setSelected(true);
        if (mOnSelectListener != null) {
            mOnSelectListener.onSelected(view, position);
        }
    }

    private void removeSelect(View view, int position) {
        view.setSelected(false);
        mSelectedSet.remove(position);
    }

    private void clearAll() {
        if (!mSelectedSet.isEmpty()) {
            Iterator<Integer> iterator = mSelectedSet.iterator();
            while (iterator.hasNext()) {
                int index = iterator.next();
                View lastView = getChildAt(index);
                lastView.setSelected(false);
            }
            mSelectedSet.clear();
        }
    }

    protected boolean isLastFixWidth() {
        return true;
    }
}
