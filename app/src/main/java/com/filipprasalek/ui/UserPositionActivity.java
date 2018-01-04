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
import com.filipprasalek.domain.Point;
import com.filipprasalek.engine.RangingEngine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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
            this.sendJsonObject();
        }

        private void sendJsonObject() {
            HttpURLConnection httpcon;
            String url = "http://192.168.0.151:8080/position";
            Point userPosition = rangingEngine.getUserPosition();
            String data = "{ " +
                    "\"X\": \"" + userPosition.getX() + "\"," +
                    "\"Y\": \"" + userPosition.getY() + "\"" +
                    "}";
            String result = null;
            try {
                //Connect
                httpcon = (HttpURLConnection) ((new URL(url).openConnection()));
                httpcon.setDoOutput(true);
                httpcon.setRequestProperty("Content-Type", "application/json");
                httpcon.setRequestProperty("Accept", "application/json");
                httpcon.setRequestMethod("POST");
                httpcon.connect();

                //Write
                OutputStream os = httpcon.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(data);
                writer.close();
                os.close();

                //Read
                BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(), "UTF-8"));

                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                br.close();
                result = sb.toString();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
