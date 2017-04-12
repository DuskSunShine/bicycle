package com.bravesoft.bravegogo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bravesoft.bravegogo.R;
import com.bravesoft.bravegogo.interfaces.Activitys;
import com.bumptech.glide.Glide;
import com.liji.takephoto.TakePhoto;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalInfo extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llHeadImg, nickName, sexual,
            birth, authentication, changePsw;
    private CircleImageView personalHead;
    private TakePhoto takePhoto;
    private ImageView orderBack;
    private TextView nickText;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        Activitys.activitys.add(this);
        initView();

    }

    private void initView() {
        llHeadImg = (LinearLayout) findViewById(R.id.llHeadImg);
        nickName = (LinearLayout) findViewById(R.id.nickName);
        sexual = (LinearLayout) findViewById(R.id.sexual);
        birth = (LinearLayout) findViewById(R.id.birth);
        authentication = (LinearLayout) findViewById(R.id.authentication);
        changePsw = (LinearLayout) findViewById(R.id.changePsw);
        personalHead = (CircleImageView) findViewById(R.id.personalHead);
        orderBack = (ImageView) findViewById(R.id.orderBack);
        nickText = (TextView) findViewById(R.id.nickText);
        llHeadImg.setOnClickListener(this);
        nickName.setOnClickListener(this);
        sexual.setOnClickListener(this);
        birth.setOnClickListener(this);
        authentication.setOnClickListener(this);
        changePsw.setOnClickListener(this);
        orderBack.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //修改昵称
        sp = getSharedPreferences("nick", MODE_PRIVATE);
        String mNick = sp.getString("mNick", null);
        if (mNick != null) {
            nickText.setText(mNick);
        }
        //再次进入头像不变
        sp=getSharedPreferences("ImgHeadPath",MODE_PRIVATE);
        String path = sp.getString("path", null);
        if (path!=null) {
            Glide.with(PersonalInfo.this)
                    .load("file://" + path)
                    .centerCrop().into(personalHead);
        }
        Intent intent = getIntent();
        if (intent!=null){
            String num = intent.getStringExtra("num");
            nickText.setText(num);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llHeadImg:
                takePhoto = new TakePhoto(this);

                takePhoto.setOnPictureSelected(new TakePhoto.onPictureSelected() {
                    @Override
                    public void select(String path) {
                        Glide.with(PersonalInfo.this)
                                .load("file://" + path)
                                .centerCrop().into(personalHead);
                        //保存头像路径
                        sp=getSharedPreferences("ImgHeadPath",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("path",path);
                        editor.commit();
                    }
                });
                takePhoto.show();
                break;
            case R.id.nickName:
                startActivity(new Intent(this, NickNameActivity.class));
                break;
            case R.id.orderBack:
                finish();
                break;
            case R.id.changePsw:
                startActivity(new Intent(this, ChangePswActivity.class));
                break;
            case R.id.authentication:
                startActivity(new Intent(this,PaymentActivity.class));
                break;
        }
    }
}
