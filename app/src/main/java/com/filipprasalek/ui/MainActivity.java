package com.filipprasalek.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.detect_list_view:
                return true;
            case R.id.user_position_view:
                Intent intent = new Intent(this, UserPositionActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}