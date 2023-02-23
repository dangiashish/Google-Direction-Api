package com.codebyashish.googledirectionapi.utilities;

import com.codebyashish.googledirectionapi.modelclass.RouteInfoModel;
import com.codebyashish.googledirectionapi.utilities.ErrorHandling;

import java.util.ArrayList;

public interface RouteListener {
    void onRouteFailure(ErrorHandling e);

    void onRouteStart();

    void onRouteSuccess(ArrayList<RouteInfoModel> list, int indexing);

    void onRouteCancelled();
}
