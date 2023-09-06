# Google Direction Api 🗺️

[![](https://jitpack.io/v/DangiAshish/Google-Direction-Api.svg)](https://jitpack.io/#DangiAshish/Google-Direction-Api)

## ℹ️ Library Info : 
This is an android library which provides you a direction path between two points (locations) such as device location to destination location. To use this library, you need to create a project on Goolge Cloud Console and enabble the Map SDK along with Google Direction Api. After the all completion of the setup, just follow these simple steps to implement the code in your project.


#### Demo

<img src="https://github.com/dangiashish/Google-Direction-Api/sample/GIF_20230221_124204.gif" alt="gif" style="width:200px; height:400px"/>



### Gradle

Add jitpack repository in your `build.gradle` (project-level) file :
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
	  implementation 'com.github.dangiashish:Google-Direction-Api:1.6'
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
                .context(MainActivity.this)  // pass your activity or fragment's context
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this).alternativeRoutes(true)
                .waypoints(userLoc, destLoc)
                .build();
        routeDrawing.execute();
       }

}
```

##### ⚠ Note : Please restrict your api keys on GCP for your specific android package name 

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

## Connect with me:
[![Github Profile](https://skillicons.dev/icons?i=github)](https://github.com/dangiashish)
[![LinkedIn](https://skillicons.dev/icons?i=linkedin)](https://linkedin.com/in/ashishkumardangi)
[![Instagram](https://skillicons.dev/icons?i=instagram)](https://instagram.com/coder.ashish)

## License :
```
MIT License

Copyright (c) 2023 Ashish Dangi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
