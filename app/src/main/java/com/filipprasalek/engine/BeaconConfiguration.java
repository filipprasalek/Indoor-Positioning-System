package com.filipprasalek.engine;

import java.util.HashMap;
import java.util.Map;

// TODO: extend a class/inteface
public class BeaconConfiguration {

    class Beetrot {
        public final static String name = "beetrot";
        public final static String mm = "44099:58515";
        public final static double x = 0.0;
        public final static double y = 0.0;

    }

    class Candy {
        public final static String name = "candy";
        public final static String mm = "21883:57819";
        public final static double x = 0.0;
        public final static double y = 0.0;
    }

    class Lemon {
        public final static String name = "lemon";
        public final static String mm = "7191:40932";
        public final static double x = 0.0;
        public final static double y = 0.0;
    }

    public static Map<String, BeaconStatus> createBeaconMapping(){
        Map<String, BeaconStatus> placesByBeacons = new HashMap<>();
        placesByBeacons.put(Beetrot.mm, null);
        placesByBeacons.put(Candy.mm, null);
        placesByBeacons.put(Lemon.mm, null);
        return placesByBeacons;
    }

}



