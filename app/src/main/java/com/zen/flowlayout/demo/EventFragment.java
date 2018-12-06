package com.zen.flowlayout.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zen.flowlayout.R;
import com.zen.flowlayout.TagAdapter;
import com.zen.flowlayout.TagFlowLayout;

public class EventFragment extends Fragment {
    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView"};

    private TagFlowLayout mFlowLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.normal_fragment, null);
        mFlowLayout = view.findViewById(R.id.flowlayout);
        mFlowLayout.setChoiceMode(TagFlowLayout.CHOICE_MODE_SINGLE);
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(int position, String str) {
                return buildTagView(str);
            }
        });
//        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position) {
//                Toast.makeText(getActivity(), "" + mVals[position], Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(View view, int position) {
                Toast.makeText(getActivity(), "" + mVals[position], Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private TextView buildTagView(String pstr) {
        TextView tv = new TextView(getActivity());
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
