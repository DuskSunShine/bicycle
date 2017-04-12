package com.bravesoft.bravegogo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bravesoft.bravegogo.R;
import com.bravesoft.bravegogo.utils.ChangeDrawImgInch;

public class MoreProblemActivity extends AppCompatActivity {
    private RadioButton rdo1, rdo2, rdo3, rdo4, rdo5, rdo6, rdo7, rdo8;
    private RadioGroup rdoGroup;
    private Button feedback_cancel, feedback_confirm;
    private boolean canFinish=false;//弹出窗口是否可以被关闭
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_problem);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.65);   //高度设置为屏幕的0.7
        p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.8
        getWindow().setAttributes(p);     //设置生效
        initView();
        changeSize();
        feedback_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        feedback_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canFinish==true) {
                    finish();
                }
            }
        });
    }

    private void initView() {
        rdo1 = (RadioButton) findViewById(R.id.rdo1);
        rdo2 = (RadioButton) findViewById(R.id.rdo2);
        rdo3 = (RadioButton) findViewById(R.id.rdo3);
        rdo4 = (RadioButton) findViewById(R.id.rdo4);
        rdo5 = (RadioButton) findViewById(R.id.rdo5);
        rdo6 = (RadioButton) findViewById(R.id.rdo6);
        rdo7 = (RadioButton) findViewById(R.id.rdo7);
        rdo8 = (RadioButton) findViewById(R.id.rdo8);
        feedback_cancel= (Button) findViewById(R.id.feedback_cancel);
        rdoGroup = (RadioGroup) findViewById(R.id.rdoGroup);
        feedback_cancel = (Button) findViewById(R.id.feedback_cancel);
        feedback_confirm = (Button) findViewById(R.id.feedback_confirm);


        rdoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                feedback_confirm.setBackgroundResource(R.drawable.radoconfirm);
                switch (i) {
                    case R.id.rdo1:
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_selected, rdo1);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_unselected, rdo2);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_unselected, rdo3);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_unselected, rdo4);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_unselected, rdo5);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_unselected, rdo6);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_unselected, rdo7);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_unselected, rdo8);
                        canFinish=!canFinish;
                        break;
                    case R.id.rdo2:
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_selected, rdo2);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo1);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo3);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo4);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo5);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo6);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo7);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo8);
                        canFinish=!canFinish;
                        break;
                    case R.id.rdo3:
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_selected, rdo3);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo2);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo1);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo4);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo5);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo6);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo7);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo8);
                        canFinish=!canFinish;
                        break;
                    case R.id.rdo4:
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_selected, rdo4);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo2);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo3);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo1);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo5);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo6);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo7);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo8);
                        canFinish=!canFinish;
                        break;
                    case R.id.rdo5:
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_selected, rdo5);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo2);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo3);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo4);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo1);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo6);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo7);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo8);
                        canFinish=!canFinish;
                        break;
                    case R.id.rdo6:
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_selected, rdo6);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo2);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo3);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo4);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo5);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo1);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo7);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo8);
                        canFinish=!canFinish;
                        break;
                    case R.id.rdo7:
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_selected, rdo7);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo2);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo3);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo4);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo5);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo6);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo1);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo8);
                        canFinish=!canFinish;
                        break;
                    case R.id.rdo8:
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this, R.drawable.common_selected, rdo8);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo2);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo3);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo4);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo5);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo6);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo7);
                        ChangeDrawImgInch.changeInchRight(MoreProblemActivity.this,R.drawable.common_unselected,rdo1);
                        canFinish=!canFinish;
                        break;
                }

            }
        });
    }

    //改变图片大小
    private void changeSize() {
        ChangeDrawImgInch.changeInchRight(this, R.drawable.common_unselected, rdo1);
        ChangeDrawImgInch.changeInchRight(this, R.drawable.common_unselected, rdo2);
        ChangeDrawImgInch.changeInchRight(this, R.drawable.common_unselected, rdo3);
        ChangeDrawImgInch.changeInchRight(this, R.drawable.common_unselected, rdo4);
        ChangeDrawImgInch.changeInchRight(this, R.drawable.common_unselected, rdo5);
        ChangeDrawImgInch.changeInchRight(this, R.drawable.common_unselected, rdo6);
        ChangeDrawImgInch.changeInchRight(this, R.drawable.common_unselected, rdo7);
        ChangeDrawImgInch.changeInchRight(this, R.drawable.common_unselected, rdo8);
    }
}
