package com.bravesoft.bravegogo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.bravesoft.bravegogo.R;
import com.bravesoft.bravegogo.interfaces.Activitys;
import com.bravesoft.bravegogo.zxing.activity.CaptureActivity;


public class FeedbackActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener,View.OnFocusChangeListener{
    private RadioGroup radOne,radTwo;
    private RadioButton radOne1,radOne2,radOne3
            ,radTwo1,radTwo2,radTwo3;
    private EditText problem,bicycleNum;
    private LinearLayout feedbackLl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Activitys.activitys.add(this);
        initView();
        //获取二维码信息
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            String result = extras.getString("result");
            String[] split = result.split("=");
            bicycleNum.setText(split[1]);
        }
    }

    private void initView(){
        feedbackLl= (LinearLayout) findViewById(R.id.feedbackLl);
        radOne= (RadioGroup) findViewById(R.id.radOne);
        radTwo= (RadioGroup) findViewById(R.id.radTwo);
        radOne1= (RadioButton) findViewById(R.id.radOne1);
        radOne2= (RadioButton) findViewById(R.id.radOne2);
        radOne3= (RadioButton) findViewById(R.id.radOne3);
        radTwo1= (RadioButton) findViewById(R.id.radTwo1);
        radTwo2= (RadioButton) findViewById(R.id.radTwo2);
        radTwo3= (RadioButton) findViewById(R.id.radTwo3);
        problem= (EditText) findViewById(R.id.problem);
        bicycleNum= (EditText) findViewById(R.id.bicycleNum);
        problem.setOnFocusChangeListener(this);
        radOne1.setOnCheckedChangeListener(this);
        radOne2.setOnCheckedChangeListener(this);
        radOne3.setOnCheckedChangeListener(this);
        radTwo1.setOnCheckedChangeListener(this);
        radTwo2.setOnCheckedChangeListener(this);
        radTwo3.setOnCheckedChangeListener(this);
    }

    public void feedBack(View view) {
        finish();
    }
    //两行radiobutton实现单选
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.radOne1:
                radTwo.clearCheck();
                break;
            case R.id.radOne2:
                radTwo.clearCheck();
                break;
            case R.id.radOne3:
                radTwo.clearCheck();
                break;
            case R.id.radTwo1:
                radOne.clearCheck();
                break;
            case R.id.radTwo2:
                radOne.clearCheck();
                break;
            case R.id.radTwo3:
                radOne.clearCheck();
                //startActivity(new Intent(FeedbackActivity.this,MoreProblemActivity.class));
                break;
        }
    }
    //改变编辑框边框色
    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            feedbackLl.setBackgroundResource(R.drawable.user_feedback_content_blue);
        }else {
            feedbackLl.setBackgroundResource(R.drawable.user_feedback_content_grey);
        }
    }
    //更多问题
    public void moreProblem(View view) {
                startActivity(new Intent(FeedbackActivity.this,MoreProblemActivity.class));
            }

    public void scan(View view) {
        startActivity(new Intent(this,CaptureActivity.class));
        finish();
    }
}
