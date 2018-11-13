package com.am.trendo.utils;

import android.location.Location;

public final class LocationUtils {
    public static String getStringDistance(double distanceInMeter) {
        return distanceInMeter + " m";
    }

    public static float getDistance(Location locationA, Location locationB) {
        return locationA.distanceTo(locationB);
    }
}
