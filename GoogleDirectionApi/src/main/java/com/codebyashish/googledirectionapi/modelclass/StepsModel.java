package com.codebyashish.googledirectionapi.modelclass;


import com.google.android.gms.maps.model.LatLng;

public class StepsModel {
    private LatLng start;
    private String instruction;
    private int length;
    private double distance;

    public StepsModel() {
    }

    public void setInstruction(String turn) {
        this.instruction = turn;
    }

    public String getInstruction() {
        return this.instruction;
    }

    public void setPoint(LatLng point) {
        this.start = point;
    }

    public LatLng startPoint() {
        return this.start;
    }

    public StepsModel copy() {
        StepsModel copy = new StepsModel();
        copy.start = this.start;
        copy.instruction = this.instruction;
        copy.length = this.length;
        copy.distance = this.distance;
        return copy;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return this.distance;
    }
}
