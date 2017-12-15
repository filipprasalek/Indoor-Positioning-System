package com.filipprasalek.engine.domain;

public class Beetrot implements BeaconStatus {

    public final static String name = "beetrot";
    public final static String mm = "44099:58515";
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
