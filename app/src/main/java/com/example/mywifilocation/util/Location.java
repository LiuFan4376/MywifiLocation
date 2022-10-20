package com.example.mywifilocation.util;

public class Location {
    /*x轴坐标*/
    private Double xAxis;
    /*y轴坐标*/
    private Double yAxis;
    public Location(Double xAxis, Double yAxis) {
        super();
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
    public Double getxAxis() {
        return xAxis;
    }
    public void setxAxis(Double xAxis) {
        this.xAxis = xAxis;
    }
    public Double getyAxis() {
        return yAxis;
    }
    public void setyAxis(Double yAxis) {
        this.yAxis = yAxis;
    }
}



