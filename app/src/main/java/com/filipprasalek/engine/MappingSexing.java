package com.filipprasalek.engine;

import com.estimote.coresdk.observation.region.Region;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.recognition.packets.Beacon;
import java.util.List;
import java.util.Map;

public class MappingSexing {
    private double calculateBeaconWeight(double distanceToBeacon) {
//        return (1 / Math.pow(distanceToBeacon, Parameters.DEGREE_OF_WEIGHT));
        return 2137;
    }

    public Point estimateUserPosition(List<Beacon> beaconList, Map<String,BeaconStatus> beaconMapping) {
        double xiSum = 0.0;
        double yiSum = 0.0;
        double wiSum = 0.0;
//
//        for (Beacon beacon : beaconList) {
//            double currentBeaconWeight =
//                    calculateBeaconWeight(RegionUtils.computeAccuracy(beacon));
//            Point currentBeaconCoordinates =
//                    beaconMapping.get(beacon.getMajor() + ":" + beacon.getMinor());
//            xiSum += currentBeaconCoordinates.getX() * currentBeaconWeight;
//            yiSum += currentBeaconCoordinates.getY() * currentBeaconWeight;
//            wiSum += currentBeaconWeight;
//        }

        return null;
//        return new Point((xiSum / wiSum), (yiSum / wiSum));
    }
}
