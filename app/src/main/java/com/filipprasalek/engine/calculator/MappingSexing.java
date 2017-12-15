package com.filipprasalek.engine.calculator;

import com.estimote.coresdk.recognition.packets.Beacon;
import com.filipprasalek.engine.domain.BeaconStatus;
import com.filipprasalek.engine.domain.Point;

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
