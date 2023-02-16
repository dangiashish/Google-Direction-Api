package com.codebyashish.googledirectionapi;

import java.util.ArrayList;

public interface RouteListener {
    void onRouteFailure(Exceptions e);

    void onRouteStart();

    void onRouteSuccess(ArrayList<Route> list, int indexing);

    void onRouteCancelled();
}
