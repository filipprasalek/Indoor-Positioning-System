package com.filipprasalek.engine.calculator;

import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.filipprasalek.engine.domain.BeaconStatus;
import com.filipprasalek.engine.domain.Point;

import java.util.List;
import java.util.Map;

public class MappingSexing {

    private static double calculateBeaconWeight(double distanceToBeacon) {
        return (1 / Math.pow(distanceToBeacon, 2));
    }

    public static Point estimateUserPosition(List<Beacon> discoveredBeacons, Map<String,BeaconStatus> beaconMap) {
        double xiSum = 0.0;
        double yiSum = 0.0;
        double wiSum = 0.0;

        for (Beacon beacon : discoveredBeacons) {
            double currentBeaconWeight =
                    calculateBeaconWeight(RegionUtils.computeAccuracy(beacon));
            BeaconStatus currentBeaconStatus = beaconMap.get(beacon.getMajor() + ":" + beacon.getMinor());
            Point currentBeaconCoordinates = new Point(currentBeaconStatus.getX(),currentBeaconStatus.getY());

            xiSum += currentBeaconCoordinates.getX() * currentBeaconWeight;
            yiSum += currentBeaconCoordinates.getY() * currentBeaconWeight;
            wiSum += currentBeaconWeight;
        }

        return new Point((xiSum / wiSum), (yiSum / wiSum));
    }
}
