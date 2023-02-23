package com.codebyashish.googledirectionapi.modelclass;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class RouteInfoModel {
    private String name;
    private final List<LatLng> points = new ArrayList<>();
    private List<StepsModel> stepsModels = new ArrayList<>();
    private String copyright;
    private String warning;
    private String country;
    private LatLngBounds latLgnBounds;
    private int length;
    private String polyline;
    private String durationText;
    private int durationValue;
    private String distanceText;
    private int distanceValue;
    private String endAddressText;
    private PolylineOptions polyOptions;

    public PolylineOptions getPolyOptions() {
        return this.polyOptions;
    }

    public void setPolyOptions(PolylineOptions polyOptions) {
        this.polyOptions = polyOptions;
    }

    public String getEndAddressText() {
        return this.endAddressText;
    }

    public void setEndAddressText(String endAddressText) {
        this.endAddressText = endAddressText;
    }

    public String getDurationText() {
        return this.durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    public String getDistanceText() {
        return this.distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }

    public int getDurationValue() {
        return this.durationValue;
    }

    public void setDurationValue(int durationValue) {
        this.durationValue = durationValue;
    }

    public int getDistanceValue() {
        return this.distanceValue;
    }

    public void setDistanceValue(int distanceValue) {
        this.distanceValue = distanceValue;
    }

    public void setSegments(List<StepsModel> stepsModels) {
        this.stepsModels = stepsModels;
    }

    public RouteInfoModel() {
    }

    public void addPoint(LatLng p) {
        this.points.add(p);
    }

    public void addPoints(List<LatLng> points) {
        this.points.addAll(points);
    }

    public List<LatLng> getPoints() {
        return this.points;
    }

    public void addSegment(StepsModel s) {
        this.stepsModels.add(s);
    }

    public List<StepsModel> getSegments() {
        return this.stepsModels;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCopyright() {
        return this.copyright;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getWarning() {
        return this.warning;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return this.country;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public String getPolyline() {
        return this.polyline;
    }

    public LatLngBounds getLatLgnBounds() {
        return this.latLgnBounds;
    }

    public void setLatLgnBounds(LatLng northeast, LatLng southwest) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(northeast);
        builder.include(southwest);
        this.latLgnBounds = builder.build();
    }
}
