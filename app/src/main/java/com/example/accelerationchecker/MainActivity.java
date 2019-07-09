package com.example.accelerationchecker;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity.class";
    private TextView tvAccelerationData;
    private TextView tvLinearAccelerationData;
    private TextView tvmotionSensorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensorLinearAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        Sensor sensorMotion = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);

        sensorManager.registerListener(this, sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorLinearAcceleration, SensorManager.
                SENSOR_DELAY_NORMAL);

        // TODO : Try motion sensor and location data for sensing motion data

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                showAccelerometerData(event);
                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:
                showLinearAcceleartionData(event);
                break;

        }
    }

    private void showLinearAcceleartionData(SensorEvent event) {
        float xAxis = event.values[0];
        float yAxis = event.values[1];
        float zAxis = event.values[2];

        double magnitude = getValueOfMagnitude(xAxis, yAxis, zAxis);
        tvLinearAccelerationData = findViewById(R.id.tv_linearAccData);
        tvLinearAccelerationData.setText("Linear Acc. magnitude : " + magnitude);
    }

    private void showAccelerometerData(SensorEvent event) {
        float xAxis = event.values[0];
        float yAxis = event.values[1];
        float zAxis = event.values[2];

        double magnitude = getValueOfMagnitude(xAxis, yAxis, zAxis);
        tvAccelerationData = findViewById(R.id.tv_accelerationMagnitude);
        tvAccelerationData.setText("Acceleration magnitude : " + magnitude);
    }

    private double getValueOfMagnitude(float xAxis, float yAxis, float zAxis) {
        float totalSquareValue = (xAxis * xAxis) + (yAxis * yAxis) + (zAxis * zAxis);
        return Math.sqrt((totalSquareValue));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        if(sensor.getId() == Sensor.TYPE_ACCELEROMETER) {
//            tvLinearAccelerationData = findViewById(R.id.tv_accuracy);
//            tvLinearAccelerationData.setText("Accuracy : " + accuracy);
//        }
    }
}
