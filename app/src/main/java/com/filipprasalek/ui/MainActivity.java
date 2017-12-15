package com.filipprasalek.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.filipprasalek.R;
import com.filipprasalek.engine.RangingEngine;


public class MainActivity extends AppCompatActivity {

    RangingEngine rangingEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rangingEngine = new RangingEngine();
        rangingEngine.listen(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        rangingEngine.startRanging();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rangingEngine.stopRanging();
    }
}