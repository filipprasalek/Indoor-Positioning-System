package com.filipprasalek.engine.domain;

public class Candy implements BeaconStatus {

    public final static String name = "candy";
    public final static String mm = "21883:57819";
    public final static double x = 0.0;
    public final static double y = 0.0;

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMM() {
        return mm;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
