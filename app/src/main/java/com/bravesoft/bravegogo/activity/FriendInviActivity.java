package com.bravesoft.bravegogo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bravesoft.bravegogo.R;
import com.bravesoft.bravegogo.interfaces.Activitys;

public class FriendInviActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_invi);
        Activitys.activitys.add(this);
    }

    public void invitationBack(View view) {
        finish();
    }
}
