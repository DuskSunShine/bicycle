package com.bravesoft.bravegogo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bravesoft.bravegogo.R;
import com.bravesoft.bravegogo.interfaces.Activitys;

import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity implements TextWatcher{
    private EditText loginEdit;
    private ImageView bgClear;
    private Button nextStep;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Activitys.loginActivitys.add(this);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.33);   //高度设置为屏幕的0.7
        p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.8
        getWindow().setAttributes(p);     //设置生效
        initView();
        bgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEdit.setText("");
                nextStep.setBackgroundResource(R.drawable.rado);

            }
        });

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginEdit.getText().length()==11&&
                        loginEdit.getText().toString().matches("[1][358]\\d{9}")){
                    initSmsSDK();

                    sp=getSharedPreferences("regist",MODE_PRIVATE);
                    String phone = sp.getString("phone", null);
                    //判断如果号码注册成功，已经存在
                    if (phone!=null&&phone.equals(loginEdit.getText().toString())){
                        startActivity(new Intent(LoginActivity.this,pswActivity.class));
                    }else {
                        SMSSDK.getVerificationCode("86",loginEdit.getText().toString());
                        startActivity(new Intent(LoginActivity.this,SmsActivity.class).putExtra("num",
                                loginEdit.getText().toString()));
                    }

                }else {
                    Toast.makeText(LoginActivity.this,"请填写正确号码",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void back(View view) {
        finish();
    }

   private void initView(){
       loginEdit= (EditText) findViewById(R.id.loginEdit);
       bgClear= (ImageView) findViewById(R.id.bgClear);
       nextStep= (Button) findViewById(R.id.nextStep);
       loginEdit.addTextChangedListener(this);
   }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence!=null){
            bgClear.setVisibility(View.VISIBLE);
            nextStep.setBackgroundResource(R.drawable.radoconfirm);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length()==0){
            nextStep.setBackgroundResource(R.drawable.rado);
            bgClear.setVisibility(View.INVISIBLE);
        }
    }

    private void initSmsSDK(){
        SMSSDK.initSDK(LoginActivity.this, "1c4251f3efea8", "1c5c5f6d0e8755ba0b86982415c51518");
    }
}
