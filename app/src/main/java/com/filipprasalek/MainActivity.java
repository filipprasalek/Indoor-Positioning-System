package com.filipprasalek;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final Map<String, String> placesByBeacons;

    static {
        placesByBeacons = new HashMap<>();
        placesByBeacons.put("44099:58515", "beetroot");
        placesByBeacons.put("21883:57819", "candy");
        placesByBeacons.put("7191:40932", "lemon");
    }

    private BeaconManager beaconManager;
    private BeaconRegion beaconRegion;

    private String detectedBeacons(Beacon beacon, double beaconDistance) {
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (placesByBeacons.containsKey(beaconKey)) {
            return placesByBeacons.get(beaconKey) + ": " + new DecimalFormat("##.##").format(beaconDistance) + "m";
        }
        return "No beacons found :C";
    }

    private double getDistance(int rssi, int txPower) {
        return Math.pow(10d, ((double) txPower - rssi) / (10 * 2.2));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconRegion = new BeaconRegion("ranged region",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);

        beaconManager = new BeaconManager(this);
        beaconManager.setBackgroundScanPeriod(200, 2000);

        final ListView listView = findViewById(R.id.detectetBeaconsListView);
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    List<String> places = new ArrayList<>();
                    for (Beacon beacon : list) {
                        //double beaconDistance = getDistance(beacon.getRssi(),beacon.getMeasuredPower());
                        double beaconDistance = RegionUtils.computeAccuracy(beacon);
                        places.add(detectedBeacons(beacon, beaconDistance));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, places){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent){

                            View view = super.getView(position, convertView, parent);
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);
                            tv.setTextColor(Color.BLACK);

                            return view;
                        }
                    };
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(beaconRegion);
            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(beaconRegion);
        super.onPause();
    }
}