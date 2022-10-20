package com.example.mywifilocation.db;

;
import org.litepal.crud.LitePalSupport;

public class Fingerprint extends LitePalSupport {
    /**指纹数据结构:三个ap，位置坐标（x,y），指纹类型type**/
    private int id;
    private double x;
    private double y;
    private int ap1;
    private int ap2;
    private int ap3;
    private String type;
    public Fingerprint(){

    }
    public Fingerprint(int id,double x,double y ,int ap1 ,int ap2 ,int ap3,String type){
        this.id=id;this.x=x;this.y=y;this.ap1=ap1;this.ap2=ap2;this.ap3=ap3;this.type=type;
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public int getAp1() {
        return ap1;
    }

    public int getap2() {
        return ap2;
    }
    public int getap3() {
        return ap3;
    }
    public int getid() {
        return id;
    }
    public String getType(){return type;}

    public void setX(double x) {
        this.x = x;
    }

    public void sety(double y) {
        this.y = y;
    }

    public void setAp1(int ap1) {
        this.ap1 = ap1;
    }

    public void setAp2(int ap2) {
        this.ap2 = ap2;
    }
    public void setAp3(int ap3) {
        this.ap3 = ap3;
    }
    public void setid(int id){
        this.id=id;
    }
    public void setType(String type){
        this.type=type;
    }


}