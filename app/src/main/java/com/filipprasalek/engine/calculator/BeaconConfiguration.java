package com.filipprasalek.engine.calculator;

import com.filipprasalek.engine.domain.BeaconStatus;
import com.filipprasalek.engine.domain.Beetrot;
import com.filipprasalek.engine.domain.Candy;
import com.filipprasalek.engine.domain.Lemon;

import java.util.HashMap;
import java.util.Map;

public class BeaconConfiguration {
    public static Map<String, BeaconStatus> createBeaconMapping(){
        Map<String, BeaconStatus> placesByBeacons = new HashMap<>();
        placesByBeacons.put(Beetrot.mm, new Beetrot());
        placesByBeacons.put(Candy.mm, new Candy());
        placesByBeacons.put(Lemon.mm, new Lemon());
        return placesByBeacons;
    }
}



