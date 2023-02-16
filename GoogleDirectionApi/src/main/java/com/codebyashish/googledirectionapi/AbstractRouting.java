package com.codebyashish.googledirectionapi;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public abstract class AbstractRouting extends AsyncTask<Void, Void, ArrayList<Route>> {
    protected ArrayList<RouteListener> listenerArrayList = new ArrayList<>();
    protected static final String DIRECTIONS_API_URL = "https://maps.googleapis.com/maps/api/directions/json?";
    private Exceptions exceptions = null;

    protected AbstractRouting(RouteListener listener) {
        this.registerListener(listener);
    }

    public void registerListener(RouteListener mListener) {
        if (mListener != null) {
            this.listenerArrayList.add(mListener);
        }

    }

    protected void dispatchOnStart() {
        for (RouteListener mListener : this.listenerArrayList) {
            mListener.onRouteStart();
        }

    }

    protected void dispatchOnFailure(Exceptions exception) {
        for (RouteListener mListener : this.listenerArrayList) {
            mListener.onRouteFailure(exception);
        }

    }

    protected void dispatchOnSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        for (RouteListener mListener : this.listenerArrayList) {
            mListener.onRouteSuccess(route, shortestRouteIndex);
        }

    }

    private void dispatchOnCancelled() {
        for (RouteListener mListener : this.listenerArrayList) {
            mListener.onRouteCancelled();
        }

    }

    protected ArrayList<Route> doInBackground(Void... voids) {
        ArrayList<Route> result = new ArrayList<>();

        try {
            result = (new GoogleParser(this.constructURL())).parse();
        } catch (Exceptions var4) {
            this.exceptions = var4;
        }

        return result;
    }

    protected abstract String constructURL();

    protected void onPreExecute() {
        this.dispatchOnStart();
    }

    protected void onPostExecute(ArrayList<Route> result) {
        if (!result.isEmpty()) {
            int shortestRouteIndex = 0;
            int minDistance = Integer.MAX_VALUE;

            for(int i = 0; i < result.size(); ++i) {
                PolylineOptions mOptions = new PolylineOptions();
                Route route = result.get(i);
                if (route.getLength() < minDistance) {
                    shortestRouteIndex = i;
                    minDistance = route.getLength();
                }

                for (LatLng point : route.getPoints()) {
                    mOptions.add(point);
                }

                result.get(i).setPolyOptions(mOptions);
            }

            this.dispatchOnSuccess(result, shortestRouteIndex);
        } else {
            this.dispatchOnFailure(this.exceptions);
        }

    }

    protected void onCancelled() {
        this.dispatchOnCancelled();
    }

    public enum AvoidKind {
        TOLLS(1, "tolls"),
        HIGHWAYS(2, "highways"),
        FERRIES(4, "ferries");

        private final String _sRequestParam;
        private final int _sBitValue;

        AvoidKind(int bit, String param) {
            this._sBitValue = bit;
            this._sRequestParam = param;
        }

        int getBitValue() {
            return this._sBitValue;
        }

        static String getRequestParam(int bit) {
            String ret = "";
            AvoidKind[] arr$ = values();

            for (AvoidKind kind : arr$) {
                if ((bit & kind._sBitValue) == kind._sBitValue) {
                    ret = ret + kind._sRequestParam;
                    ret = ret + "|";
                }
            }

            return ret;
        }
    }

    public enum TravelMode {
        BIKING("bicycling"),
        DRIVING("driving"),
        WALKING("walking"),
        TRANSIT("transit");

        private final String values;

        TravelMode(String value) {
            this.values = value;
        }

        String getValue() {
            return this.values;
        }
    }
}
