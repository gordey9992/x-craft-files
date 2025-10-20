package com.github.xcraftfiles.utils;

import org.bukkit.Location;

public class LocationUtils {
    public static String formatLocation(Location location) {
        return location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }
    
    public static boolean isInRadius(Location loc1, Location loc2, double radius) {
        return loc1.distance(loc2) <= radius;
    }
}
