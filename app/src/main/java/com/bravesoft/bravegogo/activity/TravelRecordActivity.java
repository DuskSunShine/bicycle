package com.bravesoft.bravegogo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bravesoft.bravegogo.R;
import com.bravesoft.bravegogo.interfaces.Activitys;

public class TravelRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travele_record);
        Activitys.activitys.add(this);
    }

    public void travelBack(View view) {
        finish();
    }

    public void goTravel(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
