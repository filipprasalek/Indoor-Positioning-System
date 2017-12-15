package com.filipprasalek.engine;

import java.util.HashMap;
import java.util.Map;

public class BeaconConfiguration {

    class Beetrot {
        public final static String name = "beetrot";
        public final static String mm = "44099:58515";
    }

    class Candy {
        public final static String name = "candy";
        public final static String mm = "21883:57819";
    }

    class Lemon {
        public final static String name = "lemon";
        public final static String mm = "7191:40932";
    }

    public static Map<String, String> createBeaconMapping(){
        Map<String, String> placesByBeacons = new HashMap<>();
        placesByBeacons.put(Beetrot.mm, Beetrot.name);
        placesByBeacons.put(Candy.mm, Candy.name);
        placesByBeacons.put(Lemon.mm, Lemon.name);
        return placesByBeacons;
    }

}



