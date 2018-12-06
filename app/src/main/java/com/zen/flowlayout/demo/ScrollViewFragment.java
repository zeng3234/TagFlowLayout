package com.zen.flowlayout.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zen.flowlayout.R;
import com.zen.flowlayout.SimpleFixWidthView;
import com.zen.flowlayout.TagAdapter;
import com.zen.flowlayout.TagFlowLayout;

import org.w3c.dom.Text;

public class ScrollViewFragment extends Fragment {

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    private TagFlowLayout mFlowLayout;
    private TextView mIdsTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sc, container, false);
        mFlowLayout = view.findViewById(R.id.flowlayout);
        mFlowLayout.setChoiceMode(TagFlowLayout.CHOICE_MODE_MULTIPLE);
        mIdsTv = view.findViewById(R.id.sc_ids);
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(int position, String str) {
                return buildTagView(str);
            }
        });
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position) {
                int[] ids = mFlowLayout.getSelectedIds();
                StringBuilder sb = new StringBuilder();
                for (int i : ids) {
                    sb.append(i).append(",");
                }
                mIdsTv.setText(sb.toString());
                return false;
            }
        });
        return view;
    }

    private TextView buildTagView(String pstr) {
        SimpleFixWidthView tv = new SimpleFixWidthView(getActivity());
        tv.setText(pstr);
        tv.setBackgroundResource(R.drawable.tag_test_item_bg);
        ViewGroup.MarginLayoutParams lps = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        lps.setMargins(5, 5, 5, 5);
        tv.setPadding(15, 15, 15, 15);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(lps);
        return tv;
    }
}
