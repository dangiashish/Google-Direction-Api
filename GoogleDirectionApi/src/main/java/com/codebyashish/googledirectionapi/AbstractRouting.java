package com.codebyashish.googledirectionapi;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public abstract class AbstractRouting extends AsyncTask<Void, Void, ArrayList<RouteInfoModel>> {
    protected ArrayList<RouteListener> listenerArrayList = new ArrayList<>();
    private ErrorHandling errorHandling = null;

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

    protected void dispatchOnFailure(ErrorHandling exception) {
        for (RouteListener mListener : this.listenerArrayList) {
            mListener.onRouteFailure(exception);
        }

    }

    protected void dispatchOnSuccess(ArrayList<RouteInfoModel> routeInfoModel, int shortestRouteIndex) {
        for (RouteListener mListener : this.listenerArrayList) {
            mListener.onRouteSuccess(routeInfoModel, shortestRouteIndex);
        }

    }

    private void dispatchOnCancelled() {
        for (RouteListener mListener : this.listenerArrayList) {
            mListener.onRouteCancelled();
        }

    }

    protected ArrayList<RouteInfoModel> doInBackground(Void... voids) {
        ArrayList<RouteInfoModel> result = new ArrayList<>();

        try {
            result = (new RouteJsonParser(this.constructURL())).parse();
        } catch (ErrorHandling errorHandling) {
            this.errorHandling = errorHandling;
        }

        return result;
    }

    protected abstract String constructURL();

    protected void onPreExecute() {
        this.dispatchOnStart();
    }

    protected void onPostExecute(ArrayList<RouteInfoModel> result) {
        if (!result.isEmpty()) {
            int shortestRouteIndex = 0;
            int minDistance = Integer.MAX_VALUE;

            for(int i = 0; i < result.size(); ++i) {
                PolylineOptions polylineOptions = new PolylineOptions();
                RouteInfoModel routeInfoModel = result.get(i);
                if (routeInfoModel.getLength() < minDistance) {
                    shortestRouteIndex = i;
                    minDistance = routeInfoModel.getLength();
                }

                for (LatLng point : routeInfoModel.getPoints()) {
                    polylineOptions.add(point);
                }

                result.get(i).setPolyOptions(polylineOptions);
            }

            this.dispatchOnSuccess(result, shortestRouteIndex);
        } else {
            this.dispatchOnFailure(this.errorHandling);
        }

    }

    protected void onCancelled() {
        this.dispatchOnCancelled();
    }

    public enum AvoidKind {
        TOLLS(1, "tolls"),
        HIGHWAYS(2, "highways"),
        FERRIES(4, "ferries");

        private final String param;
        private final int bit;

        AvoidKind(int bit, String param) {
            this.bit = bit;
            this.param = param;
        }

        int getBitValue() {
            return this.bit;
        }

        static String getRequestParam(int bit) {
            String ret = "";
            AvoidKind[] arr$ = values();

            for (AvoidKind kind : arr$) {
                if ((bit & kind.bit) == kind.bit) {
                    ret = ret + kind.param;
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
