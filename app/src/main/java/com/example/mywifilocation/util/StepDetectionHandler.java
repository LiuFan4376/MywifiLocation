package com.example.mywifilocation.util;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
/**步态检测类，用来检测是否走了一步。在初始化部分确定监听的传感器类型为线性传感器。
判断是否走了一步的标准是线性加速度传感器的沿y轴方向（手机头指向的方向）的加速度值变化是否大于1，
 如果大于1的话，将通过接口将检测结果返回Activity。
 *
 */
public class StepDetectionHandler extends Activity implements
        SensorEventListener {
//传感器与传感器管理对象
    SensorManager sm;
    Sensor sensor;
    private StepDetectionListener mStepDetectionListener;//传感器监听对象
    int step = 0;
    private SensorEvent e;

    public StepDetectionHandler(SensorManager sm) {//构造函数，给传感器管理与管理器（线性加速度）赋值
        super();
        this.sm = sm;
        sensor = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }
//传感器注册监听器方法
    public void start() {
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }
//传感器取消注册监听器方法
    public void stop() {
        sm.unregisterListener(this);
    }


    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    //传感器触发执行方法:判断沿y轴方向（手机头指向的方向）的加速度值变化是否大于1
    public void onSensorChanged(SensorEvent e) {
        float y;

        if (e.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            y = e.values[1];
            //
            if (y > 1 && mStepDetectionListener != null) {
                onNewStepDetected();

            }
        }
    }

    public void onNewStepDetected() {
        float distanceStep = 0.8f;
        step++;
        mStepDetectionListener.newStep(distanceStep);
    }
     //传感器监听对象实例化方法
    public void setStepListener(StepDetectionListener listener) {
        mStepDetectionListener = listener;
    }

    public interface StepDetectionListener {
        public void newStep(float stepSize);

    }

}



