package com.filipprasalek.engine;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.filipprasalek.R;
import com.filipprasalek.domain.BeaconStatusInterface;
import com.filipprasalek.domain.Point;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.R.layout.simple_list_item_1;
import static com.estimote.coresdk.observation.region.RegionUtils.computeAccuracy;
import static java.lang.String.format;

public class RangingEngine {

    private static final Map<String, BeaconStatusInterface> placesByBeacons = BeaconConfiguration.createBeaconMapping();
    private static final String UU_ID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private static final String DECIMAL_FORMAT = "##.##";
    private static final String REGION_TYPE = "ranged region";
    private static final int WAIT_TIME_MILLIS = 2000;
    private static final int SCAN_PERIOD_MILIS = 200;
    private static List<Beacon> beacons = new ArrayList<>();
    private BeaconManager beaconManager;
    private BeaconRegion beaconRegion;
    private DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);

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

    public void listen(final AppCompatActivity activity) {
        beaconRegion = new BeaconRegion(REGION_TYPE,
                UUID.fromString(UU_ID), null, null);

        beaconManager = new BeaconManager(activity);

        beaconManager.setBackgroundScanPeriod(SCAN_PERIOD_MILIS, WAIT_TIME_MILLIS);

        final ListView listView = activity.findViewById(R.id.detectetBeaconsListView);
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    List<String> places = buildDetectedBeaconsList(list);
                    beacons = new ArrayList<>(list);

                    listView.setAdapter(new CustomArrayAdapter(
                            activity.getApplicationContext(), simple_list_item_1, places));

                    setTextViews(activity);
                }
            }
        });
    }

    public void mapUserPosition(final AppCompatActivity activity, final float metersX, final float metersY){
        beaconRegion = new BeaconRegion(REGION_TYPE,
                UUID.fromString(UU_ID), null, null);

        beaconManager = new BeaconManager(activity);

        beaconManager.setBackgroundScanPeriod(SCAN_PERIOD_MILIS, WAIT_TIME_MILLIS);
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
                if (!list.isEmpty()) {

                    beacons = new ArrayList<>(list);

                    Point userPosition = MappingEngine.estimateUserPosition(beacons,placesByBeacons);
                    View user = activity.findViewById(R.id.userPosition);
                    user.setX(((float)userPosition.getX() * metersX) + metersX);
                    user.setY(((float)userPosition.getY() * metersY) + metersY);

                }
            }
        });
    }

    public Point getUserPosition() {
        return MappingEngine.estimateUserPosition(beacons, placesByBeacons);
    }


    private void setTextViews(AppCompatActivity activity) {
        TextView textViewX = activity.findViewById(R.id.xCoordTextView);
        TextView textViewY = activity.findViewById(R.id.yCoordTextView);

        Point userPosition = MappingEngine.estimateUserPosition(beacons,placesByBeacons);

        textViewX.setText(String.format("%s", decimalFormat.format(userPosition.getX())));
        textViewY.setText(String.format("%s", decimalFormat.format(userPosition.getY())));
    }

    private List<String> buildDetectedBeaconsList(List<Beacon> list) {
        List<String> places = new ArrayList<>();
        for (Beacon beacon : list) {
            //double beaconDistance = getDistance(beacon.getRssi(),beacon.getMeasuredPower());
            double beaconDistance = computeAccurateDistance(beacon);
            places.add(mapBeaconDataToString(beacon, beaconDistance));
        }

        return places;
   }

    private String mapBeaconDataToString(Beacon beacon, double beaconDistance) {
        String beaconKey = format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (placesByBeacons.containsKey(beaconKey)) {
            return placesByBeacons.get(beaconKey) + ": " + decimalFormat.format(beaconDistance) + "m";
        }
        return "unknown beacon: " + decimalFormat.format(beaconDistance) + "m";
    }

    private double computeDistance(int rssi, int txPower) {
        return Math.pow(10d, ((double) txPower - rssi) / (10 * 2.2));
    }

    private double computeAccurateDistance(Beacon beacon) {
        return computeAccuracy(beacon);
    }

}
