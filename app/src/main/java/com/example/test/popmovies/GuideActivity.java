package com.example.test.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class GuideActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private PagerAdapter mAdapter;
    private List<View> mViews=new ArrayList<>();
    private Button bt_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    private void initView() {
        viewPager= (ViewPager) findViewById(R.id.view_pager);

        LayoutInflater inflater=LayoutInflater.from(this);
        View guideOne=inflater.inflate(R.layout.guidance01, null);
        View guideTwo=inflater.inflate(R.layout.guidance02,null);
        View guideThree=inflater.inflate(R.layout.guidance03,null);

        mViews.add(guideOne);
        mViews.add(guideTwo);
        mViews.add(guideThree);

        mAdapter=new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view=mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View view=mViews.get(position);
                container.removeView(view);
            }

            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        };

        viewPager.setAdapter(mAdapter);

        bt_home= (Button) guideThree.findViewById(R.id.to_Main);
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuideActivity.this,MainActivity.class);
               startActivity(intent);
                finish();
            }
        });
    }
}
