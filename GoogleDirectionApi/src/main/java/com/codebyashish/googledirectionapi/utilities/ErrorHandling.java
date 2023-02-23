package com.codebyashish.googledirectionapi.utilities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorHandling extends Exception {
    private String statusCode;
    private String message;
    private int status;

    public ErrorHandling(JSONObject json) {
        if (json == null) {
            this.statusCode = "";
            this.message = "Cannot parse";
        } else {
            try {
                this.statusCode = json.getString("status");
                this.message = json.getString("error_message");
            } catch (JSONException e) {
               Log.e("route json error" , "JSON parsing error : " + e.getMessage());
            }

        }
    }

    public ErrorHandling(int status){
       this.status = status;
    }

    public ErrorHandling(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatusCode() {
        return this.statusCode;
    }
}
