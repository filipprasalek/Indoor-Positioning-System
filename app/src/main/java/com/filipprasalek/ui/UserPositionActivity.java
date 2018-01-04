package com.filipprasalek.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.filipprasalek.R;
import com.filipprasalek.engine.RangingEngine;

public class UserPositionActivity extends AppCompatActivity {

    static final int MAX_METRES_WIDTH = 5;
    static final int MAX_METRES_HEIGHT = 7;
    RangingEngine rangingEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_position);
        final ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.room);


        rangingEngine = new RangingEngine();


        final ViewTreeObserver observer = constraintLayout.getViewTreeObserver();
        float widthMeter = 144f;
        float heightMeter = 149f;

        constraintLayout.addView(new BeaconDot(getApplicationContext(), (widthMeter * 2.6f) + (widthMeter) , (heightMeter * 0) +(heightMeter), "beetroot"));
        constraintLayout.addView(new BeaconDot(getApplicationContext(), (widthMeter * 0) + (widthMeter), (heightMeter * 5) + (heightMeter), "candy"));
        constraintLayout.addView(new BeaconDot(getApplicationContext(), (widthMeter * 0) + (widthMeter), (heightMeter * 0) + (heightMeter), "lemon"));

        rangingEngine.mapUserPosition(this, widthMeter, heightMeter);
        this.findViewById(R.id.send_button).setOnClickListener(new HandleSendButtonClick());

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

    private class HandleSendButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(UserPositionActivity.this, "Sent", Toast.LENGTH_LONG).show();
        }
    }

    private class BeaconDot extends View {
        Paint paint = new Paint();
        private float x;
        private float y;
        private String name;

        public BeaconDot(Context context, float x, float y, String name) {
            super(context);
            this.x = x;
            this.y = y;
            this.name = name;
        }

        @Override
        public void onDraw(Canvas canvas) {
            switch (name) {
                case "beetroot":
                    paint.setColor(ContextCompat.getColor(getContext(), R.color.beetroot));
                    break;
                case "candy":
                    paint.setColor(ContextCompat.getColor(getContext(), R.color.candy));
                    break;
                case "lemon":
                    paint.setColor(ContextCompat.getColor(getContext(), R.color.lemon));
                    break;
                default:
                    paint.setColor(Color.RED);
            }
            canvas.drawCircle(x, y, 10, paint);
        }
    }

}
