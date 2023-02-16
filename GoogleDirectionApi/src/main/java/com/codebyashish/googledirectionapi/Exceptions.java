package com.codebyashish.googledirectionapi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Exceptions extends Exception {
    private String statusCode;
    private String message;

    public Exceptions(JSONObject json) {
        if (json == null) {
            this.statusCode = "";
            this.message = "Cannot parse";
        } else {
            try {
                this.statusCode = json.getString("status");
                this.message = json.getString("error_message");
            } catch (JSONException e) {
               Log.e("RouteInfoModel json error" , "JSON parsing error : " + e.getMessage());
            }

        }
    }

    public Exceptions(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatusCode() {
        return this.statusCode;
    }
}
