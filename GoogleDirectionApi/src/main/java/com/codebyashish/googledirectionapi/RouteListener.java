package com.codebyashish.googledirectionapi;

import java.util.ArrayList;

public interface RouteListener {
    void onRouteFailure(Exceptions e);

    void onRouteStart();

    void onRouteSuccess(ArrayList<RouteInfoModel> list, int indexing);

    void onRouteCancelled();
}
