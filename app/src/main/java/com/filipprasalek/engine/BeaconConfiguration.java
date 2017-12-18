package com.filipprasalek.engine;

import com.filipprasalek.domain.BeaconStatusInterface;

import java.util.HashMap;
import java.util.Map;

import static com.filipprasalek.domain.BeaconNames.Beetrot;
import static com.filipprasalek.domain.BeaconNames.Candy;
import static com.filipprasalek.domain.BeaconNames.Lemon;
import static com.filipprasalek.domain.BeaconDictionary.BEETROT_MM;
import static com.filipprasalek.domain.BeaconDictionary.CANDY_MM;
import static com.filipprasalek.domain.BeaconDictionary.LEMON_MM;

public class BeaconConfiguration {
    public static Map<String, BeaconStatusInterface> createBeaconMapping() {
        Map<String, BeaconStatusInterface> placesByBeacons = new HashMap<>();
        placesByBeacons.put(BEETROT_MM, BeaconStatusFactory.getBeaconStatus(Beetrot));
        placesByBeacons.put(CANDY_MM, BeaconStatusFactory.getBeaconStatus(Candy));
        placesByBeacons.put(LEMON_MM, BeaconStatusFactory.getBeaconStatus(Lemon));
        return placesByBeacons;
    }
}



