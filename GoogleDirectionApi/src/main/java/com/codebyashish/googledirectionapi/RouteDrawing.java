package com.codebyashish.googledirectionapi;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.codebyashish.googledirectionapi.utilities.Constants;
import com.codebyashish.googledirectionapi.utilities.RouteListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteDrawing extends AbstractRouting {
    private final AbstractRouting.TravelMode travelMode;
    private final boolean alternativeRoutes;
    private final List<LatLng> waypoints;
    private final int avoidKinds;
    private final boolean optimize;
    private final String language;
    private String key;
    private final Context context;

    private RouteDrawing(Builder builder) {
        super(builder.listener);
        this.travelMode = builder.travelMode;
        this.waypoints = builder.waypoints;
        this.avoidKinds = builder.avoidKinds;
        this.optimize = builder.optimize;
        this.alternativeRoutes = builder.alternativeRoutes;
        this.language = builder.language;
//        this.key = builder.key;
        this.context = builder.context;
    }

    protected String constructURL() {
        StringBuilder stringBuilder = new StringBuilder(Constants.DIRECTION_BASE_URL);
        LatLng origin = this.waypoints.get(0);
        stringBuilder.append("origin=");
        stringBuilder.append(origin.latitude);
        stringBuilder.append(',');
        stringBuilder.append(origin.longitude);
        LatLng destination = this.waypoints.get(this.waypoints.size() - 1);
        stringBuilder.append("&destination=");
        stringBuilder.append(destination.latitude);
        stringBuilder.append(',');
        stringBuilder.append(destination.longitude);
        stringBuilder.append("&mode=");
        stringBuilder.append(this.travelMode.getValue());
        if (this.waypoints.size() > 2) {
            stringBuilder.append("&waypoints=");
            if (this.optimize) {
                stringBuilder.append("optimize:true|");
            }

            for (int i = 1; i < this.waypoints.size() - 1; ++i) {
                LatLng p = this.waypoints.get(i);
                stringBuilder.append("via:");
                stringBuilder.append(p.latitude);
                stringBuilder.append(",");
                stringBuilder.append(p.longitude);
                stringBuilder.append("|");
            }
        }

        if (this.avoidKinds > 0) {
            stringBuilder.append("&avoid=");
            stringBuilder.append(AvoidKind.getRequestParam(this.avoidKinds));
        }

        if (this.alternativeRoutes) {
            stringBuilder.append("&alternatives=true");
        }

        stringBuilder.append("&sensor=true");
        if (this.language != null) {
            stringBuilder.append("&language=").append(this.language);
        }

        if (this.key != null) {
            stringBuilder.append("&key=").append(getMapKey(context));
        }

        return stringBuilder.toString();
    }

    public static class Builder {
        private AbstractRouting.TravelMode travelMode;
        private boolean alternativeRoutes;
        private List<LatLng> waypoints;
        private int avoidKinds;
        private RouteListener listener;
        private boolean optimize;
        private String language;
        private String key;
        private Context context;

        public Builder() {
            this.travelMode = TravelMode.DRIVING;
            this.alternativeRoutes = false;
            this.waypoints = new ArrayList<>();
            this.avoidKinds = 0;
            this.listener = null;
            this.optimize = false;
            this.language = null;
//            this.key = null;
            this.context = null;
        }

        public Builder travelMode(AbstractRouting.TravelMode travelMode) {
            this.travelMode = travelMode;
            return this;
        }

        public Builder alternativeRoutes(boolean alternativeRoutes) {
            this.alternativeRoutes = alternativeRoutes;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }


        public Builder waypoints(LatLng... points) {
            this.waypoints.clear();
            Collections.addAll(this.waypoints, points);
            return this;
        }

        public Builder waypoints(List<LatLng> waypoints) {
            this.waypoints = new ArrayList<>(waypoints);
            return this;
        }

        public Builder optimize(boolean optimize) {
            this.optimize = optimize;
            return this;
        }

       /* public Builder key(String key) {
            this.key = key;
            return this;
        }*/

         public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder withListener(RouteListener listener) {
            this.listener = listener;
            return this;
        }

        public RouteDrawing build() {
            if (this.waypoints.size() < 2) {
                throw new IllegalArgumentException("Must supply at least two waypoints to route between.");
            } else if (this.waypoints.size() <= 2 && this.optimize) {
                throw new IllegalArgumentException("You need at least three waypoints to enable optimize");
            } else {
                return new RouteDrawing(this);
            }
        }
    }

    private static String getMapKey(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String apiKey = null;

        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = applicationInfo.metaData;

            if (metaData != null && metaData.containsKey("com.google.android.geo.API_KEY")) {
                apiKey = metaData.getString("com.google.android.geo.API_KEY");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return apiKey;
    }
}
