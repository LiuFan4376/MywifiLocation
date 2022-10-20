package com.example.mywifilocation.util;

public class StepPositioningHandler {

    private Location mCurrentLocation;
    private static final int eRadius = 6371000; //rayon de la terre en m

    public Location getmCurrentLocation() {
        return mCurrentLocation;
    }

    public void setmCurrentLocation(Location mCurrentLocation) {
        this.mCurrentLocation = mCurrentLocation;
    }

    public Location computeNextStep(float stepSize,float bearing) {
        bearing = (float) (bearing * Math.PI / 180);
        Location newLoc = new Location(mCurrentLocation.getxAxis(), mCurrentLocation.getyAxis());
        float angDistance = stepSize / eRadius;
        double newX = mCurrentLocation.getxAxis() - stepSize*Math.sin(bearing);
        double newY = mCurrentLocation.getyAxis() - stepSize*Math.cos(bearing);
        newLoc.setxAxis(newX);
        newLoc.setyAxis(newY);
        mCurrentLocation = newLoc;
        return newLoc;
    }
}

