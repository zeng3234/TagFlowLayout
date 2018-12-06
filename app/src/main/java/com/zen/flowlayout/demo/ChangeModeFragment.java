package com.zen.flowlayout.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zen.flowlayout.R;
import com.zen.flowlayout.SimpleFixWidthView;
import com.zen.flowlayout.TagAdapter;
import com.zen.flowlayout.TagFlowLayout;

public class ChangeModeFragment extends Fragment {
    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView"};

    private TagFlowLayout mFlowLayout;

    private RadioGroup mRadioGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_mode_fragment, null);
        mFlowLayout = view.findViewById(R.id.flowlayout);
        mFlowLayout.setChoiceMode(TagFlowLayout.CHOICE_MODE_LIMIT);
        mFlowLayout.setLimit(5);
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(int position, String str) {
                return buildTagView(str);
            }
        });
        mRadioGroup = view.findViewById(R.id.change_mode_rg);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.change_mode_rb_single:
                        mFlowLayout.setChoiceModeForce(TagFlowLayout.CHOICE_MODE_SINGLE);
                        break;
                    case R.id.change_mode_rb_limit:
                        mFlowLayout.setChoiceModeForce(TagFlowLayout.CHOICE_MODE_LIMIT);
                        break;
                    case R.id.change_mode_rb_multiple:
                        mFlowLayout.setChoiceModeForce(TagFlowLayout.CHOICE_MODE_MULTIPLE);
                        break;
                }
            }
        });
        mRadioGroup.check(R.id.change_mode_rb_limit);
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
