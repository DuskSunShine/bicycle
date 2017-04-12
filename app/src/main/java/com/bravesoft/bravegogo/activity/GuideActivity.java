package com.bravesoft.bravegogo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bravesoft.bravegogo.R;
import com.bravesoft.bravegogo.adapter.GuidePageAdapter;

import java.util.ArrayList;
import java.util.List;


public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager guide_pager;
    private LinearLayout llPoint;
    private Button guide_btnGo;
    private List<View> viewList;//图片资源的集合
    private int[] img={R.drawable.bg_feature_1, R.drawable.bg_feature_2,
            R.drawable.bg_feature_3, R.drawable.bg_feature_4};
    private boolean isFirst=true;//是否第一次进入app,默认第一次
    private SharedPreferences sp;
    private static final String ISFIRST="first";//sp存储文件名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        addView();
        addPoint();
        sp=getSharedPreferences(ISFIRST,MODE_PRIVATE);
        isFirst=sp.getBoolean("msg",true);
        if(!isFirst){//不是第一次进入，跳转并销毁当前界面
            startActivity(new Intent(this,SplashActivity.class));
            finish();
        }

    }

    //初始化控件
    private void initView() {
        guide_pager = (ViewPager) findViewById(R.id.guide_pager);
        llPoint= (LinearLayout) findViewById(R.id.llPoint);
        guide_btnGo= (Button) findViewById(R.id.guide_btnGo);
    }
    //添加图片到view
    private void addView() {
        viewList = new ArrayList<>();
        // 将imageview添加到view
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < img.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(img[i]);
            viewList.add(iv);
        }
        // 加入适配器
        guide_pager.setAdapter(new GuidePageAdapter(viewList));
        guide_pager.addOnPageChangeListener(this);

    }

    //添加小圆点
    private void addPoint() {
        // 根据图片多少，添加多少小圆点
        for (int i = 0; i < img.length; i++) {
            LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(20, 20);
            if (i < 1) {
                pointParams.setMargins(0, 0, 0, 0);
            } else {
                pointParams.setMargins(10, 0, 0, 0);
            }
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(pointParams);
            iv.setBackgroundResource(R.mipmap.pointwhite);
            llPoint.addView(iv);
        }
        llPoint.getChildAt(0).setBackgroundResource(R.mipmap.pointblack);

    }

     // 判断小圆点位置

    private void monitorPoint(int position) {
        for (int i = 0; i < img.length; i++) {
            if (i == position) {
                llPoint.getChildAt(position).setBackgroundResource(
                        R.mipmap.pointblack);
            } else {
                llPoint.getChildAt(i).setBackgroundResource(
                        R.mipmap.pointwhite);
            }
        }

    }

    public void guide_btnClick(View view) {
        startActivity(new Intent(GuideActivity.this,SplashActivity.class));
        finish();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        monitorPoint(position);
        // 当滑动到最后一个添加按钮点击进入，
        if (position == img.length - 1) {
            guide_btnGo.setVisibility(View.VISIBLE);
        } else {
            guide_btnGo.setVisibility(View.INVISIBLE);
        }
        /*//循环设置当前页的标记图
        int length = imageIdArray.length;
        for (int i = 0;i<length;i++){
            ivPointArray[position].setBackgroundResource(R.mipmap.pointblack);
            if (position != i){
                ivPointArray[i].setBackgroundResource(R.mipmap.pointwhite);
            }
        }
        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1){
            guide_btnGo.setVisibility(View.VISIBLE);
        }else {
            guide_btnGo.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
