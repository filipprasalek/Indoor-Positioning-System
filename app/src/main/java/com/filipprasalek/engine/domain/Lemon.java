package com.filipprasalek.engine.domain;

public class Lemon implements BeaconStatus {

    public final static String name = "lemon";
    public final static String mm = "7191:40932";
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
