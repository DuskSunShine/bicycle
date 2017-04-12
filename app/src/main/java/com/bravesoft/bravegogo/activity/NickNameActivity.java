package com.bravesoft.bravegogo.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bravesoft.bravegogo.R;
import com.bravesoft.bravegogo.interfaces.Activitys;

public class NickNameActivity extends AppCompatActivity implements
        View.OnClickListener,TextWatcher{
    private ImageView nickBack,nick_img,loading;
    private Button nickSave;
    private EditText edit_text;
    private Animation animation;
    private SharedPreferences sp;

    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        Activitys.activitys.add(this);
        initView();
        sp=getSharedPreferences("nick",MODE_PRIVATE);
        editor=sp.edit();
    }
    private void initView(){
        nick_img= (ImageView) findViewById(R.id.nick_img);
        nickBack= (ImageView) findViewById(R.id.nickBack);
        nickSave= (Button) findViewById(R.id.nickSave);
        edit_text= (EditText) findViewById(R.id.edit_text);
        loading= (ImageView) findViewById(R.id.loading);
        nickSave.setOnClickListener(this);
        nickBack.setOnClickListener(this);
        nick_img.setOnClickListener(this);
        edit_text.addTextChangedListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nickBack:
                finish();
                break;
            case R.id.nick_img:
                    edit_text.setText("");
                nick_img.setVisibility(View.INVISIBLE);
                nickSave.setTextColor(Color.rgb(202,202,202));
                break;
            case R.id.nickSave:
                if (edit_text.getText()!=null){
                    loading.setVisibility(View.VISIBLE);
                    animation= AnimationUtils.loadAnimation(this,R.anim.loading);
                    loading.startAnimation(animation);
                    editor.putString("mNick",edit_text.getText().toString());
                    editor.commit();
                    //模拟网络提交
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    loading.clearAnimation();
                    loading.setVisibility(View.GONE);
                    finish();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString()!=null){
            nickSave.setTextColor(Color.rgb(32,146,217));
            nick_img.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length()==0){
            nick_img.setVisibility(View.INVISIBLE);
            nickSave.setTextColor(Color.rgb(202,202,202));
        }
    }
    //从新回到界面，取出数据
    @Override
    protected void onResume() {
        super.onResume();
        String mNick = sp.getString("mNick", null);
        if (mNick!=null){
            edit_text.setText(mNick);
        }
    }
}
