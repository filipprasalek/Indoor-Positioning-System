package com.filipprasalek.engine;

import com.estimote.coresdk.recognition.packets.Beacon;
import com.filipprasalek.domain.BeaconStatusInterface;
import com.filipprasalek.domain.Point;

import java.util.List;
import java.util.Map;

import static com.estimote.coresdk.observation.region.RegionUtils.computeAccuracy;

public class MappingEngine {

    public static Point estimateUserPosition(List<Beacon> discoveredBeacons,
                                             Map<String, BeaconStatusInterface> beaconMap) {
        double xiSum = 0.0;
        double yiSum = 0.0;
        double wiSum = 0.0;

        for (Beacon beacon : discoveredBeacons) {
            double currentBeaconWeight = calculateBeaconWeight(computeAccuracy(beacon));
            BeaconStatusInterface currentBeaconStatus = beaconMap.get(concatMajorAndMinor(beacon));

            xiSum += currentBeaconStatus.getX() * currentBeaconWeight;
            yiSum += currentBeaconStatus.getY() * currentBeaconWeight;
            wiSum += currentBeaconWeight;
        }

        return new Point((xiSum / wiSum), (yiSum / wiSum));
    }

    private static double calculateBeaconWeight(double distanceToBeacon) {
        return (1 / Math.pow(distanceToBeacon, 2));
    }

    private static String concatMajorAndMinor(Beacon beacon) {
        return beacon.getMajor() + ":" + beacon.getMinor();
    }
}
