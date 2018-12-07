package com.zen.flowlayout.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zen.flowlayout.R;
import com.zen.flowlayout.TagAdapter;
import com.zen.flowlayout.TagFlowLayout;
import com.zen.flowlayout.TagItemView;

public class SampleTagFragment extends Fragment {
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
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position) {
                TagItemView v = (TagItemView) view;
                String str = v.test_getRadiusStr();
                Log.d("mylog", str);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private TextView buildTagView(String pstr) {
        TagItemView.Builder builder = new TagItemView.Builder(getContext());
        builder.wh(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        builder.margin(5, 5, 5, 5);
        builder.padding(35, 10, 35, 10);
        builder.radius(15);
//        builder.bg(TagItemView.Builder.TYPE_RECTANGLE, Color.WHITE,2,Color.YELLOW);
        builder.bg(TagItemView.Builder.TYPE_CIRCLE, Color.WHITE, 2, Color.GREEN);
        builder.bgSelected(Color.WHITE, Color.RED);
        builder.lastFixWidth(true);
        TagItemView itemView = builder.build();
        itemView.setText(pstr);
        return itemView;
    }
}
