package com.filipprasalek.domain;

public class BeaconStatus implements BeaconStatusInterface {

    private String name;
    private String mm;
    private double x;
    private double y;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public String getMM() {
        return mm;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setY(double y) {
        this.y = y;
    }
}
