package com.codebyashish.googledirectionapi;


import com.google.android.gms.maps.model.LatLng;

public class Segment {
    private LatLng start;
    private String instruction;
    private int length;
    private double distance;

    public Segment() {
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

    public Segment copy() {
        Segment copy = new Segment();
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
