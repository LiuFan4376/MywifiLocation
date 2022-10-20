package com.example.mywifilocation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mywifilocation.KNN.KNN;
import com.example.mywifilocation.util.*;
import com.example.mywifilocation.db.Fingerprint;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class LocationActivity extends AppCompatActivity {
    private int id;
    private double x;
    private double y;
    private int ap1;
    private int ap2;
    private int ap3;
    private int rssi;
    private int i;
    private TextView tv;
    private String ssid;
    private String t;

    private SensorManager mSensorManager;
    StepDetectionHandler sdh;
    StepPositioningHandler sph;
    DeviceAttitudeHandler dah;
    boolean isWalking = false;
    Location lKloc;
    int stepCounter = 0;

    //实现定时采集rssi功能
    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            i=i+1;
            WifiManager wifi_service = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifi_service.getConnectionInfo();
            ssid=wifiInfo.getSSID();
            rssi=rssi+wifiInfo.getRssi();
            Log.d ("测试采集RSSI累加值:",String.valueOf(rssi));
            Log.d ("测试累加值",String.valueOf(i));
          //  Toast.makeText(ColletDate.this, "采集WiFi的名称："+ssid,Toast.LENGTH_SHORT).show();
            handler.postDelayed(this, 2000);
        }
    };



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        tv=(TextView) findViewById(R.id.textView3);
        id=1;
        lKloc = new Location(0.0, 0.0);
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sdh = new StepDetectionHandler(sm);
        sdh.setStepListener(mStepDetectionListener);
        dah = new DeviceAttitudeHandler(sm);
        sph = new StepPositioningHandler();
        sph.setmCurrentLocation(lKloc);
        isWalking = true;
    }

    protected void onResume() {
        super.onResume();
        sdh.start();
        dah.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        sdh.stop();
        dah.stop();
    }

    private StepDetectionHandler.StepDetectionListener mStepDetectionListener = new StepDetectionHandler.StepDetectionListener() {
        public void newStep(float stepSize) {
            stepCounter++;
            Location newloc = sph.computeNextStep(stepSize, dah.orientationVals[0]);
            Log.d("测试记录坐标", newloc.getxAxis() + " " + newloc.getyAxis()+ " " + dah.orientationVals[0]);
            if (isWalking) {
                x=newloc.getxAxis();
                y=newloc.getyAxis();
                tv.setText( String.valueOf(newloc.getxAxis())+","+String.valueOf(newloc.getyAxis()));

            }
        }
    };


    //采集ap1
    public void collectap1(View view){
        i=0;
        rssi=0;
        handler.postDelayed(runnable, 2000);
    }
    public void end1(View view){
        handler.removeCallbacks(runnable);
        ap1=rssi/i;
        //Toast.makeText(ColletDate.this, "一共测试了"+String.valueOf(i)+"组数据，"+"平均RSSI:"+ap1,Toast.LENGTH_SHORT).show();
    }
    //采集ap2
    public void collectap2(View view){
        i=0;
        rssi=0;
        handler.postDelayed(runnable, 2000);
    }
    public void end2(View view){
        handler.removeCallbacks(runnable);
        ap2=rssi/i;
       // Toast.makeText(ColletDate.this, "一共测试了"+String.valueOf(i)+"组数据，"+"平均RSSI:"+ap2,Toast.LENGTH_SHORT).show();
    }
    //采集ap3
    public void collectap3(View view){
        i=0;
        rssi=0;
        handler.postDelayed(runnable, 2000);
    }
    public void end3(View view){
        handler.removeCallbacks(runnable);
        ap3=rssi/i;
       // Toast.makeText(ColletDate.this, "一共测试了"+String.valueOf(i)+"组数据，"+"平均RSSI:"+ap3,Toast.LENGTH_LONG).show();
    }

    public void locate(View view){

        Fingerprint fingerprint=new Fingerprint();
        fingerprint.setid(id);
        fingerprint.setX(x);
        fingerprint.sety(y);
        fingerprint.setAp1(ap1);
        fingerprint.setAp2(ap2);
        fingerprint.setAp3(ap3);
        List<Fingerprint> fingerprintlist= LitePal.findAll(Fingerprint.class);
        Log.d("指纹数目",        String.valueOf(fingerprintlist.size()));

       KNN k1=new KNN(fingerprintlist,fingerprint);
       t= k1.compute(true);
       Log.d("指纹定位测试",         t);

        Toast.makeText(LocationActivity.this, "指纹定位结果："+t+"  "+"坐标定位结果"+k1.compute(false),Toast.LENGTH_SHORT).show();



    }
    public void back(View v){
        Intent intent=new Intent(LocationActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
