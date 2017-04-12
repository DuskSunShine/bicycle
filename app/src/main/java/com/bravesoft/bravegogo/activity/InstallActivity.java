package com.bravesoft.bravegogo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bravesoft.bravegogo.R;

public class InstallActivity extends AppCompatActivity {
    private Button btnExit;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);
        initView();
    }

    public void installBack(View view) {
        finish();
    }

    private  void initView(){
        btnExit= (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp=getSharedPreferences("isLogin",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("login",false);
                editor.commit();
                startActivity(new Intent(InstallActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
