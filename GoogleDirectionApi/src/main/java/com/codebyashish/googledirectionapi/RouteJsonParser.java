package com.codebyashish.googledirectionapi;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RouteJsonParser extends XMLParser {
    private int distance;


    public RouteJsonParser(String feedUrl) {
        super(feedUrl);
    }

    public ArrayList<RouteInfoModel> parse() throws ErrorHandling {
        ArrayList<RouteInfoModel> routeInfoModels = new ArrayList<>();
        String result = convertStreamToString(this.getInputStream());
        if (result == null) {
            throw new ErrorHandling("No result found");
        } else {
            try {
                JSONObject json = new JSONObject(result);
                if (!json.getString("status").equals(KeyConstants.OK)) {
                    throw new ErrorHandling(json);
                } else {
                    JSONArray jsonRoutes = json.getJSONArray("routes");
                    for(int i = 0; i < jsonRoutes.length(); ++i) {
                        RouteInfoModel routeInfoModel = new RouteInfoModel();
                        StepsModel stepsModel = new StepsModel();
                        JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
                        JSONObject jsonBounds = jsonRoute.getJSONObject("bounds");
                        JSONObject jsonNortheast = jsonBounds.getJSONObject("northeast");
                        JSONObject jsonSouthwest = jsonBounds.getJSONObject("southwest");
                        routeInfoModel.setLatLgnBounds(new LatLng(jsonNortheast.getDouble("lat"), jsonNortheast.getDouble("lng")), new LatLng(jsonSouthwest.getDouble("lat"), jsonSouthwest.getDouble("lng")));
                        JSONObject leg = jsonRoute.getJSONArray("legs").getJSONObject(0);
                        JSONArray steps = leg.getJSONArray("steps");

                        routeInfoModel.setName(leg.getString(KeyConstants.START_ADDRESS) + " to " + leg.getString(KeyConstants.END_ADDRESS));
                        routeInfoModel.setCopyright(jsonRoute.getString("copyrights"));
                        routeInfoModel.setDurationText(leg.getJSONObject("duration").getString("text"));
                        routeInfoModel.setDurationValue(leg.getJSONObject("duration").getInt("value"));
                        routeInfoModel.setDistanceText(leg.getJSONObject("distance").getString("text"));
                        routeInfoModel.setDistanceValue(leg.getJSONObject("distance").getInt("value"));
                        routeInfoModel.setEndAddressText(leg.getString("end_address"));
                        routeInfoModel.setLength(leg.getJSONObject("distance").getInt("value"));
                        if (!jsonRoute.getJSONArray("warnings").isNull(0)) {
                            routeInfoModel.setWarning(jsonRoute.getJSONArray("warnings").getString(0));
                        }

                        for(int y = 0; y < steps.length(); ++y) {
                            JSONObject step = steps.getJSONObject(y);
                            JSONObject start = step.getJSONObject(KeyConstants.START_LOCATION);
                            LatLng position = new LatLng(start.getDouble("lat"), start.getDouble("lng"));
                            stepsModel.setPoint(position);
                            int length = step.getJSONObject("distance").getInt("value");
                            this.distance += length;
                            stepsModel.setLength(length);
                            stepsModel.setDistance((double)this.distance / 1000.0);
                            stepsModel.setInstruction(step.getString("html_instructions").replaceAll("<(.*?)*>", ""));
                            routeInfoModel.addPoints(this.decodePolyLine(step.getJSONObject("polyline").getString("points")));
                            routeInfoModel.addSegment(stepsModel.copy());
                        }

                        routeInfoModels.add(routeInfoModel);
                    }

                    return routeInfoModels;
                }
            } catch (JSONException var20) {
                throw new ErrorHandling("JSONException. Msg: " + var20.getMessage());
            }
        }
    }

    private static String convertStreamToString(InputStream input) {
        if (input == null) {
            return null;
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder sBuf = new StringBuilder();

            try {
                String line;
                try {
                    while((line = reader.readLine()) != null) {
                        sBuf.append(line);
                    }
                } catch (IOException e) {
                    Log.e("RouteDrawing Error", e.getMessage());
                }
            } finally {
                try {
                    input.close();
                    reader.close();
                } catch (IOException e) {
                    Log.e("RouteDrawing Error", e.getMessage());
                }

            }

            return sBuf.toString();
        }
    }

    private List<LatLng> decodePolyLine(String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<>();
        int lat = 0;
        int lng = 0;

        while(index < len) {
            int shift = 0;
            int result = 0;

            int b;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 31) << shift;
                shift += 5;
            } while(b >= 32);

            int dlat = (result & 1) != 0 ? ~(result >> 1) : result >> 1;
            lat += dlat;
            shift = 0;
            result = 0;

            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 31) << shift;
                shift += 5;
            } while(b >= 32);

            int dlng = (result & 1) != 0 ? ~(result >> 1) : result >> 1;
            lng += dlng;
            decoded.add(new LatLng((double)lat / 100000.0, (double)lng / 100000.0));
        }

        return decoded;
    }
}
