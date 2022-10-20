package com.example.mywifilocation;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mywifilocation.db.Fingerprint;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;



public class MainActivity extends AppCompatActivity {
    private boolean t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void database(View view){

        LitePal.deleteAll(Fingerprint.class);//一旦点击线下训练指纹库会重新建立
        Intent intent=new Intent(MainActivity.this,ColletDate.class);
        startActivity(intent);
    }


    public void skipLocation(View view){
        Intent intent=new Intent(MainActivity.this,LocationActivity.class);
        startActivity(intent);

    }

}
