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
        } catch (MalformedURLException e) {
            Log.e("RouteDrawing Error", e.getMessage() + " please watch tutorial for more - https://youtu.be/DRaRStNyZ0k");
        }

    }

    protected InputStream getInputStream() {
        try {
            return this.feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            Log.e("RouteDrawing Error", e.getMessage() + " please watch tutorial for more - https://youtu.be/DRaRStNyZ0k");
            return null;
        }
    }
}
