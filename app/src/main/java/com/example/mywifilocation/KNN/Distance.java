package com.example.mywifilocation.KNN;

public class Distance {
    private int id;
    private int uid;
    private double distance;
    public Distance(int id ,int uid, double distance){
        this.id = id;

        this.uid = uid;

        this.distance = distance;
    }
    public int getid(){
        return this.id;
    }


    public double getDistance()
    {
        return this.distance;
    }

}
