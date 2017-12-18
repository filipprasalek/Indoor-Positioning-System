package com.filipprasalek.engine;

import com.filipprasalek.domain.BeaconNames;
import com.filipprasalek.domain.BeaconStatus;
import com.filipprasalek.domain.BeaconStatusInterface;

import static com.filipprasalek.domain.BeaconDictionary.*;

public class BeaconStatusFactory {

    private BeaconStatusFactory(){}

    public static BeaconStatusInterface getBeaconStatus(BeaconNames type){
        switch (type){
            case Beetrot:
                return getBeetrot();
            case Candy:
                return getCandy();
            case Lemon:
                return getLemon();
            default:
                return null;
        }
    }

    private static BeaconStatusInterface getBeetrot() {
        BeaconStatus beacon = new BeaconStatus();
        beacon.setName(BEETROT);
        beacon.setMm(BEETROT_MM);
        beacon.setX(BEETROT_X);
        beacon.setY(BEETROT_Y);
        return beacon;
    }

    private static BeaconStatusInterface getCandy() {
        BeaconStatus beacon = new BeaconStatus();
        beacon.setName(CANDY);
        beacon.setMm(CANDY_MM);
        beacon.setX(CANDY_X);
        beacon.setY(CANDY_Y);
        return beacon;
    }

    private static BeaconStatusInterface getLemon() {
        BeaconStatus beacon = new BeaconStatus();
        beacon.setName(LEMON);
        beacon.setMm(LEMON_MM);
        beacon.setX(LEMON_X);
        beacon.setY(LEMON_Y);
        return beacon;
    }
}
