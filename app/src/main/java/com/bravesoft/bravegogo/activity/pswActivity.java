package com.bravesoft.bravegogo.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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

public class pswActivity extends AppCompatActivity implements TextWatcher{
    private EditText pswEdit;
    private ImageView pswClear;
    private Button login;
    private boolean isCanSee=false;//密码是否可见,默认不可见
    private SharedPreferences sp;//存储已经注册的状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psw);
        Activitys.loginActivitys.add(this);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.33);   //高度设置为屏幕的0.7
        p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.8
        getWindow().setAttributes(p);     //设置生效
        initView();
        pswClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCanSee){
                    pswClear.setImageResource(R.mipmap.bg_login_pwd_eye_press);
                    pswEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                }else {
                    pswClear.setImageResource(R.mipmap.bg_login_pwd_eye);
                    pswEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                }
                isCanSee=!isCanSee;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //存储登录以后状态，mainactivity中判断是否打开侧滑布局
                sp=getSharedPreferences("isLogin",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("login",true);
                editor.commit();
                sp=getSharedPreferences("savePsw",MODE_PRIVATE);
                String psw = sp.getString("psw", null);
                if (psw!=null&&pswEdit.getText().toString().equals(psw)){
                    Toast.makeText(pswActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    for (AppCompatActivity a:Activitys.loginActivitys){
                        a.finish();
                    }
                }else {
                    Toast.makeText(pswActivity.this,"密码不正确",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void back(View view) {
        finish();
    }

    private void initView(){
        pswEdit= (EditText) findViewById(R.id.pswEdit);
        pswClear= (ImageView) findViewById(R.id.pswClear);
        login= (Button) findViewById(R.id.login);
        pswEdit.addTextChangedListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length()>=6){
            login.setBackgroundResource(R.drawable.radoconfirm);
            sp=getSharedPreferences("savePsw",MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("psw",editable.toString());
            editor.commit();
        }
    }
}
