package com.example.mywifilocation.KNN;
import java.util.Comparator;

//比较器类

public class CompareClass implements Comparator<Distance> {

    @Override
    public int compare(Distance d1, Distance d2) {
        return d1.getDistance()>d2.getDistance()?20 : -1;


    }
}

