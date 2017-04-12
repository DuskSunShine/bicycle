package com.bravesoft.bravegogo.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bravesoft.bravegogo.R;
import com.bravesoft.bravegogo.interfaces.Activitys;

public class ChangePswActivity extends AppCompatActivity implements
        View.OnClickListener, View.OnFocusChangeListener {
    private ImageView btnBack, oldClear, newClear, confirmClear;
    private Button savePsw;
    private EditText edit_oldPsw, edit_newPsw, edit_confirmPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psw);
        Activitys.activitys.add(this);
        initView();
    }
    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        oldClear = (ImageView) findViewById(R.id.oldClear);
        newClear = (ImageView) findViewById(R.id.newClear);
        confirmClear = (ImageView) findViewById(R.id.confirmClear);
        savePsw = (Button) findViewById(R.id.savePsw);
        edit_oldPsw = (EditText) findViewById(R.id.edit_oldPsw);
        edit_newPsw = (EditText) findViewById(R.id.edit_newPsw);
        edit_confirmPsw = (EditText) findViewById(R.id.edit_confirmPsw);
        btnBack.setOnClickListener(this);
        oldClear.setOnClickListener(this);
        newClear.setOnClickListener(this);
        confirmClear.setOnClickListener(this);
        savePsw.setOnClickListener(this);
        edit_oldPsw.addTextChangedListener(new MyTextChange(edit_oldPsw));
        edit_newPsw.addTextChangedListener(new MyTextChange(edit_newPsw));
        edit_confirmPsw.addTextChangedListener(new MyTextChange(edit_confirmPsw));
        edit_oldPsw.setOnFocusChangeListener(this);
        edit_newPsw.setOnFocusChangeListener(this);
        edit_confirmPsw.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.savePsw:
                if (savePsw.getCurrentTextColor() == Color.rgb(32, 146, 217)) {
                    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.oldClear:
                edit_oldPsw.setText("");
                oldClear.setVisibility(View.INVISIBLE);
                savePsw.setTextColor(Color.rgb(202, 202, 202));
                break;
            case R.id.newClear:
                edit_newPsw.setText("");
                newClear.setVisibility(View.INVISIBLE);
                savePsw.setTextColor(Color.rgb(202, 202, 202));
                break;
            case R.id.confirmClear:
                edit_confirmPsw.setText("");
                confirmClear.setVisibility(View.INVISIBLE);
                savePsw.setTextColor(Color.rgb(202, 202, 202));
                break;
        }
    }
    //多个edittext获取焦点监听
    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.edit_oldPsw:
                if (!b) {
                    oldClear.setVisibility(View.INVISIBLE);
                } else if (b&&edit_oldPsw.getText().length()>0){
                    oldClear.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.edit_newPsw:
                if (!b) {
                    newClear.setVisibility(View.INVISIBLE);
                } else if (b&&edit_newPsw.getText().length()>0){
                    newClear.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.edit_confirmPsw:
                if (!b) {
                    confirmClear.setVisibility(View.INVISIBLE);
                } else if (b&&edit_confirmPsw.getText().length()>0){
                    confirmClear.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    //多个edittext的监听
    class MyTextChange implements TextWatcher {
        private EditText editText;
        public MyTextChange(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch (editText.getId()) {
                case R.id.edit_oldPsw:
                    if (charSequence != null) {
                        oldClear.setVisibility(View.VISIBLE);
                        return;
                    }
                    break;
                case R.id.edit_newPsw:
                    if (charSequence != null) {
                        newClear.setVisibility(View.VISIBLE);
                        return;
                    }
                    break;
                case R.id.edit_confirmPsw:
                    if (charSequence != null) {
                        confirmClear.setVisibility(View.VISIBLE);
                        return;
                    }
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (editText.getId()) {
                case R.id.edit_oldPsw:
                    if (editable.length() == 0) {
                        oldClear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case R.id.edit_newPsw:
                    if (editable.length() == 0) {
                        newClear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case R.id.edit_confirmPsw:
                    if (editable.length() == 0) {
                        confirmClear.setVisibility(View.INVISIBLE);
                    }
                    break;
            }
            //改变保存按钮颜色？？？
            if (edit_oldPsw.getText().length() >= 6 &&
                    edit_newPsw.getText().length() >= 6 &&
                    edit_confirmPsw.getText().length() >= 6) {
                savePsw.setTextColor(Color.rgb(32, 146, 217));
            } else {
                savePsw.setTextColor(Color.rgb(202, 202, 202));
            }

        }
    }

}
