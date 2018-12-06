package com.zen.flowlayout.demo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zen.flowlayout.R;

public class MainActivity extends AppCompatActivity {

    private String[] titles = new String[]{
            "SingleChoice", "LimitChoice",
            "MultipleChoice",
            "ChangeMode",
            "ClickEvent",
            "ScrollView"
    };
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0:
                        return new SingleChoiceFragment();
                    case 1:
                        return new LimitChoiceFragment();
                    case 2:
                        return new MultipleChoiceFragment();
                    case 3:
                        return new ChangeModeFragment();
                    case 4:
                        return new EventFragment();
                    case 5:
                        return new ScrollViewFragment();
                    default:
                        return new SingleChoiceFragment();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return titles[position];
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
