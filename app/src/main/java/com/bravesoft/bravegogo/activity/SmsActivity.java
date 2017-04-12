package com.bravesoft.bravegogo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bravesoft.bravegogo.R;
import com.bravesoft.bravegogo.interfaces.Activitys;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SmsActivity extends AppCompatActivity implements TextWatcher{
    private String num;
    private EditText confirmSms;
    private TextView SmsPhoneNum;
    private Button button;
    private Handler handler;
    private TextView timeText;
    private int time=60;//倒计时
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        Activitys.loginActivitys.add(this);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.33);   //高度设置为屏幕的0.7
        p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.8
        getWindow().setAttributes(p);     //设置生效
        initView();
        initSdk();
        //获取传递的电话号码
        Intent intent = getIntent();
        if (intent!=null){
            num = intent.getStringExtra("num");
        }
        SmsPhoneNum.setText(num);
        //倒计时
        handler=new Handler();

        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                time--;
                if (time>=0){
                    handler.postDelayed(this,1000);
                    timeText.setText(time+"s");
                }else {
                    button.setVisibility(View.VISIBLE);
                    timeText.setVisibility(View.INVISIBLE);
                }

            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public void back(View view) {
        finish();
    }

    public void backTo(View view) {
        finish();
    }

    private void initView(){
        confirmSms= (EditText) findViewById(R.id.confirmSms);
        SmsPhoneNum= (TextView) findViewById(R.id.SmsPhoneNum);
        button= (Button) findViewById(R.id.button);
        timeText= (TextView) findViewById(R.id.timeText);
        confirmSms.addTextChangedListener(this);
        //点击重新发送获取验证码
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSDK.getVerificationCode("86",num);
            }
        });

    }
   private void initSdk(){
       SMSSDK.initSDK(SmsActivity.this, "1c4251f3efea8", "1c5c5f6d0e8755ba0b86982415c51518");

       EventHandler eventHandler=new EventHandler(){
           /**
            * 获取短信验证后触发
            * @param event event EVENT_GET_VERIFICATION_CODE（获取验证码） EVENT_SUBMIT_VERIFICATION_CODE（提交验证码）
            * @param result SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.RESULT_ERROR表示操作失败
            * @param data 事件操作的结果
            */
           @Override
           public void afterEvent(int event, int result, Object data) {
               if (result == SMSSDK.RESULT_COMPLETE) {
                   //回调完成
                   if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                       //提交验证码成功
                       sp=getSharedPreferences("regist",MODE_PRIVATE);
                       SharedPreferences.Editor editor=sp.edit();
                       editor.putString("phone",num);
                       editor.commit();
                        startActivity(new Intent(SmsActivity.this,pswActivity.class));
                        finish();
                   }
               }
           }
       };
       // 注册回调监听接口
       SMSSDK.registerEventHandler(eventHandler);
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length()==4){
            SMSSDK.submitVerificationCode("86", num, confirmSms.getText().toString());
            return;
        }
    }
}
