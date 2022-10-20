package com.example.mywifilocation.KNN;

import com.example.mywifilocation.db.Fingerprint;
import java.lang.reflect.Field;



import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class KNN {
    List<Fingerprint> fingerprintList;//已知点
    private Fingerprint unknown;//未知点
    CompareClass compare;
    public KNN(){

    }
    public KNN(List<Fingerprint> fingerprints,Fingerprint unknown){
        this.fingerprintList=fingerprints;
        this.unknown=unknown;
    }


    public String compute(boolean sb){

        // 计算所有已知点到未知点的欧式距离，并根据距离对所有已知点排序
        CompareClass compare = new CompareClass();
        Set<Distance> distanceSet = new TreeSet<Distance>(compare);
        if(sb){//指纹定位方式
        for(Fingerprint f:fingerprintList){
            distanceSet.add(new Distance(f.getid(),unknown.getid(),oudistance(f,unknown)));
        }
        }
        else
        {//坐标定位方式
            for(Fingerprint f:fingerprintList){
                distanceSet.add(new Distance(f.getid(),unknown.getid(),oudistance1(f,unknown)));
            }
        }
        //选取最近的k个点
        double k=3;
        List<Distance> distanceList=new ArrayList<Distance>(distanceSet);
        Map<String, Integer> map = getNumberOfType(distanceList, fingerprintList, k);

        // 2、计算频率

        Map<String, Double> p = computeP(map, k);

        unknown.setType(maxP(p));

        System.out.println(" 未知点的类型为："+unknown.getType());

       return unknown.getType();
    }



/******工具方法
 ******/

    //欧式距离计算方法二维和三维
    public static double oudistance(Fingerprint point1, Fingerprint point2) {
        double temp=Math.pow(point1.getAp1()-point2.getAp1(),2)
                +Math.pow(point1.getap2()-point2.getap2(),2)
                +Math.pow(point1.getap3()-point2.getap3(),2)
                ;
        return  Math.sqrt(temp);

    }
    public static double oudistance1(Fingerprint point1, Fingerprint point2) {
        double temp=Math.pow(point1.getx()-point2.getx(),2)
                +Math.pow(point1.gety()-point2.gety(),2);
        return  Math.sqrt(temp);

    }

    //计算每个分类中所包含点数方法
    public static Map<String,Integer> getNumberOfType(List<Distance> listDistance, List<Fingerprint> listPoint, double k)
    {

        Map<String, Integer> map = new HashMap<String, Integer>();

        int i = 0;

        System.out.println(" 选取的 k 个点，由近及远依次为：");

        for (Distance distance : listDistance) {

            System.out.println("id 为 " + distance.getid() + ", 距离为："

                    + distance.getDistance());

            long id = distance.getid();

            // 通过 id 找到所属类型, 并存储到 HashMap 中

            for (Fingerprint point : listPoint) {

                if (point.getid() == id) {

                    if (map.get(point.getType()) != null)

                        map.put(point.getType(), map.get(point.getType()) + 1);

                    else {

                        map.put(point.getType(), 1);

                    }

                }

            }

            i++;

            if (i >= k)

                break;

        }

        return map;

    }

    //计算频率方法
    public static Map<String,Double> computeP(Map<String, Integer> map, double k)
    {

        Map<String, Double> p = new HashMap<String, Double>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            p.put(entry.getKey(), entry.getValue() / k);

        }

        return p;

    }
    // 找出最大频率

    public static String maxP(Map<String,Double> map) {

        String key = null;

        double value = 0.0;

        for (Map.Entry<String, Double> entry : map.entrySet()) {

            if (entry.getValue()> value) {

                key = entry.getKey();

                value = entry.getValue();

            }

        }

        return key;

    }






}
