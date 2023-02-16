package com.codebyashish.googledirectionapi;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class XMLParser {
    protected URL feedUrl;

    protected XMLParser(String feedUrl) {
        try {
            this.feedUrl = new URL(feedUrl);
        } catch (MalformedURLException var3) {
            Log.e("RouteDrawing Error", var3.getMessage());
        }

    }

    protected InputStream getInputStream() {
        try {
            return this.feedUrl.openConnection().getInputStream();
        } catch (IOException var2) {
            Log.e("RouteDrawing Error", var2.getMessage());
            return null;
        }
    }
}
