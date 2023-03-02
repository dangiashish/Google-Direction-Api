# Google Direction Api

[![](https://jitpack.io/v/DangiAshish/Google-Direction-Api.svg)](https://jitpack.io/#DangiAshish/Google-Direction-Api)

#### Demo

<img src="https://github.com/DangiAshish/Google-Direction-Api/blob/b7cf938dda7465982f6bce0d78fb5408bc90a644/GIF_20230221_124204.gif" alt="gif" style="width:200px; height:400px"/>

### Gradle

Add dependency in your `build.gradle` (project-level) file :
```gradle
allprojects {
      repositories {
	...
	maven { url 'https://jitpack.io' }
	}
}
```
##### OR 
in your `settings.gradle`
 
```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
### Add dependency :

Add dependency in your `build.gradle` (module-level) file :
```groovy
dependencies {
	  implementation 'com.github.DangiAshish:Google-Direction-Api:1.4'
}
```

#### in your map activity 

```java
public void getRoutePoints(LatLng start, LatLng end) {
       if (start == null || end == null) {
           Toast.makeText(this, "Unable to get location", Toast.LENGTH_LONG).show();
           Log.e("ASHISH", " latlngs are null");
       } else {
           RouteDrawing routeDrawing = new RouteDrawing.Builder()
              .key(getResources()
              .getString(R.string.google_maps_key))
              .travelMode(AbstractRouting.TravelMode.DRIVING)
              .withListener(this).alternativeRoutes(true)
              .waypoints(start, end)
	      .build();
           routeDraawing.execute();
       }

}
```

Implement the `RouteListener` interface class in your `Activity/Fragment` to override the operations

#### On Route Failed
```java
@Override
public void onRouteFailure(ErrorHandling e) {
    Log.w("ashish checkroute", "onRoutingFailure: " + e);
}
```

#### On Route Start
```java
@Override
public void onRouteStart() {
    Log.d("ashish checkroute", "yes started");
}
    
```

#### On Route Success
```java
@Override
public void onRouteSuccess(ArrayList<RouteInfoModel> routeInfoModelArrayList, int routeIndexing) {
     if (polylines != null) {
         polylines.clear();
     }
     PolylineOptions polylineOptions = new PolylineOptions();
     ArrayList<Polyline> polylines = new ArrayList<>();
     for (int i = 0; i < routeInfoModelArrayList.size(); i++) {
         if (i == routeIndexing) {
             Log.e("ASHISH", "onRoutingSuccess: routeIndexing" + routeIndexing);
             polylineOptions.color(getResources().getColor(R.color.black));
             polylineOptions.width(12);
             polylineOptions.addAll(routeInfoModelArrayList.get(routeIndexing).getPoints());
             polylineOptions.startCap(new RoundCap());
             polylineOptions.endCap(new RoundCap());
             polyline = map.addPolyline(polylineOptions);
             polylines.add(polyline);
	  }
     }

}
```
 
#### On Route Canceled
```java
@Override
public void onRouteCancelled() {
    Log.d("ASHISH", "route canceled")
    // restart your route drawing
}
```

####Thanks
