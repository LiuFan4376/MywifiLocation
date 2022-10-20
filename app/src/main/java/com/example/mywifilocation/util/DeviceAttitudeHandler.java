package com.example.mywifilocation.util;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**航向检测类，用来检测行人走动的方向。在初始化部分确定监听的传感器类型为线性传感器
        需要说明的是这里的航向角实际上是手机头的方向，在方向的范围为（0o,360o]，其中，正北方向为360o，顺时针方向增大。**/
public class DeviceAttitudeHandler extends Activity implements
        SensorEventListener {

    SensorManager sm;
    Sensor sensor;

    public float[] orientationVals = new float[3];
    private final int sensorType = Sensor.TYPE_ORIENTATION;

    public DeviceAttitudeHandler(SensorManager sm) {
        super();
        this.sm = sm;
        sensor = sm.getDefaultSensor(sensorType);
    }

    public void start() {
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void stop() {
        sm.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    public void onSensorChanged(SensorEvent event) {

        orientationVals[0] = (float) event.values[0];
        orientationVals[1] = (float) event.values[1]; // axe de rotation
        orientationVals[2] = (float) event.values[2];

    }
}

