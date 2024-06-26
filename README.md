# Google Direction Api 🗺️

<a href="https://youtu.be/DRaRStNyZ0k"><img src="https://img.shields.io/badge/YouTube_Tutorial-4k%20Views-%23FF0000.svg"/></a>
<a href="https://jitpack.io/#DangiAshish/Google-Direction-Api"><img src="https://jitpack.io/v/DangiAshish/Google-Direction-Api.svg"/></a>
<a href="https://www.codefactor.io/repository/github/dangiashish/google-direction-api"><img src="https://www.codefactor.io/repository/github/dangiashish/google-direction-api/badge" alt="CodeFactor" /></a>

## ℹ️ Library Details : 
This is an android library which provides you a direction path between two points (locations) such as device location to destination location. To use this library, you need to create a project on <a href="https://cloud.google.com/">Goolge Cloud Console</a> and enabble the Map SDK along with <a href="https://console.cloud.google.com/marketplace/product/google/directions-backend.googleapis.com">Google Direction Api</a>. After the all completion of the setup, just follow these simple steps to implement the code in your project.

#### Demo
<table border="1">
        <tr>
            <td align="center"><img src = "https://github.com/dangiashish/Google-Direction-Api/assets/70362030/92ff9cda-eb92-4a5f-a2b4-ec5435699523" width=250/></td>
            <td align="center"> 
		    <a href = "https://github.com/dangiashish/Google-Direction-Api/assets/70362030/30d99832-dd52-45f0-bb95-dbccb83e6676"> Click to watch video demo </a>
		    <br/>
		    OR
		    <br/>
		    <a href = "https://youtu.be/DRaRStNyZ0k"> Youtube Tutotial Of Implemetation</a>
	    </td>
        </tr>
        <tr>
            <td align="center">Image</td>
            <td align="center">Video demo</td>
        </tr>
    </table>


<!-- ## 📚 Langguages Used :
* [Java][0] : 

[0]:  

-->

## ℹ️ Implementation Process : 

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
           Log.e("TAG", " latlngs are null");
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
    Log.w("TAG", "onRoutingFailure: " + e);
}
```

#### On Route Start
```java
@Override
public void onRouteStart() {
    Log.d("TAG", "yes started");
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
             Log.e("TAG", "onRoutingSuccess: routeIndexing" + routeIndexing);
             polylineOptions.color(Color.BLACK);
             polylineOptions.width(12);
             polylineOptions.addAll(routeInfoModelArrayList.get(routeIndexing).getPoints());
             polylineOptions.startCap(new RoundCap());
             polylineOptions.endCap(new RoundCap());
             Polyline polyline = map.addPolyline(polylineOptions);
             polylines.add(polyline);
	  }
     }

}
```
 
#### On Route Canceled
```java
@Override
public void onRouteCancelled() {
    Log.d("TAG", "route canceled")
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
