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

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.filipprasalek.R;
import com.filipprasalek.engine.RangingEngine;

public class UserPositionActivity extends AppCompatActivity {

    RangingEngine rangingEngine;
    static final int MAX_METRES_WIDTH = 5;
    static final int MAX_METRES_HEIGHT = 7;

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


//        observer.addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//
//                        // TODO: foreach beacon from beaconsList
//                        float widthMeter = constraintLayout.getWidth() / MAX_METRES_WIDTH;
//                        float heightMeter = constraintLayout.getHeight() / MAX_METRES_HEIGHT;
//
//                        constraintLayout.addView(new BeaconDot(getApplicationContext(), (widthMeter * 2.6f) + (widthMeter) , (heightMeter * 0) +(heightMeter), "beetroot"));
//                        constraintLayout.addView(new BeaconDot(getApplicationContext(), (widthMeter * 0) + (widthMeter), (heightMeter * 5) + (heightMeter), "candy"));
//                        constraintLayout.addView(new BeaconDot(getApplicationContext(), (widthMeter * 0) + (widthMeter), (heightMeter * 0) + (heightMeter), "lemon"));
//
//                        rangingEngine.mapUserPosition(trzebaToWywalic,userPosition, widthMeter, heightMeter);
//
//                        // TODO: take values from dictionary
//
//                        constraintLayout.addView(userPosition);
//                    }
//                });

    }

    private class UserPosition extends View {
        Paint paint = new Paint();
        private float x;
        private float y;

        public UserPosition(Context context, float x, float y) {
            super(context);
            this.x = x;
            this.y = y;
        }

        @Override
        public void onDraw(Canvas canvas) {
            paint.setColor(Color.WHITE);
            canvas.drawRect(x, y, x + 20, y + 20, paint);
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
