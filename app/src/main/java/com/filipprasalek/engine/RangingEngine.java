package com.filipprasalek.engine;

import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.filipprasalek.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.R.layout.simple_list_item_1;
import static com.filipprasalek.engine.BeaconConfiguration.createBeaconMapping;
import static java.lang.String.format;

public class RangingEngine {

    private static final Map<String, String> placesByBeacons = createBeaconMapping();

    private static final String UU_ID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private static final String DECIMAL_FORMAT = "##.##";

    private static final int SCAN_PERIOD_MILIS = 200;
    private static final int WAIT_TIME_MILLIS = 2000;

    private BeaconManager beaconManager;
    private BeaconRegion beaconRegion;

    private String detectedBeacons(Beacon beacon, double beaconDistance) {
        String beaconKey = format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (placesByBeacons.containsKey(beaconKey)) {
            return placesByBeacons.get(beaconKey) + ": " + new DecimalFormat(DECIMAL_FORMAT).format(beaconDistance) + "m";
        }
        return "No beacons found :C";
    }

    private double getDistance(int rssi, int txPower) {
        return Math.pow(10d, ((double) txPower - rssi) / (10 * 2.2));
    }

    public void listen(final AppCompatActivity activity) {
        beaconRegion = new BeaconRegion("ranged region",
                UUID.fromString(UU_ID), null, null);

        beaconManager = new BeaconManager(activity);

        beaconManager.setBackgroundScanPeriod(SCAN_PERIOD_MILIS, WAIT_TIME_MILLIS);

        final ListView listView = activity.findViewById(R.id.detectetBeaconsListView);
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    List<String> places = buildDetectedBeacons(list);

                    listView.setAdapter(new CustomArrayAdapter(
                            activity.getApplicationContext(), simple_list_item_1, places));

                }
            }
        });
    }

    private List<String> buildDetectedBeacons(List<Beacon> list) {
        List<String> places = new ArrayList<>();
        for (Beacon beacon : list) {
            //double beaconDistance = getDistance(beacon.getRssi(),beacon.getMeasuredPower());
            double beaconDistance = RegionUtils.computeAccuracy(beacon);
            places.add(detectedBeacons(beacon, beaconDistance));
        }
        return places;
    }

    public void startRanging() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(beaconRegion);
            }
        });
    }

    public void stopRanging() {
        beaconManager.stopRanging(beaconRegion);
    }

}
