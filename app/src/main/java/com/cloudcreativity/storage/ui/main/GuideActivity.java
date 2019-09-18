package com.cloudcreativity.storage.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.ui.loginAndRegister.LoginActivity;
import com.cloudcreativity.storage.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/9/9 17:12
 * e-mail: xxw0701@sina.com
 */
public class GuideActivity extends AppCompatActivity {

    private List<View> content = new ArrayList<>();
    private List<View> dots = new ArrayList<>();
    private int[] images = {R.mipmap.guide1,R.mipmap.guide2,R.mipmap.guide3};
    private int currentIndex = 0;
    private Button btnVisit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ViewPager viewPager = findViewById(R.id.vp_guide);
        LinearLayout llDots = findViewById(R.id.ll_dots);
        btnVisit = findViewById(R.id.btn_visit);
        for(int i=0;i<images.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(images[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            content.add(imageView);
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.rightMargin = 20;
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.ic_dot);
            dots.add(view);
            llDots.addView(view);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                changeDot(position);
                if(position==2){
                    btnVisit.setVisibility(View.VISIBLE);
                }else{
                    btnVisit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btnVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.get().setIsFirst(false);
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
            }
        });
        viewPager.setAdapter(new PageAdapter());
        viewPager.setCurrentItem(currentIndex);
        changeDot(currentIndex);

    }

    private void changeDot(int pos){
        for(int i=0;i<images.length;i++){
            dots.get(i).setSelected(false);
        }
        dots.get(pos).setSelected(true);
    }

    class PageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
            container.removeView(content.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(content.get(position));
            return content.get(position);
        }
    }
}
