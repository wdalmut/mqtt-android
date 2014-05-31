package com.walterdalmut.mqtt.p01;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    public static final String SERVICE_CLASSNAME = "com.walterdalmut.mqtt.p01.MQTTService";
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button = (Button) findViewById(R.id.button1);
        updateButton();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateButton();
    }

    private void updateButton() {
        if (serviceIsRunning()) {
            button.setText("Stop Service");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button.setText("Start Service");
                    stopMQTTService();
                    updateButton();
                }
            });

        } else {
            button.setText("Start Service");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button.setText("Stop Service");
                    startMQTTService();
                    updateButton();
                }
            });
        }
    }

    private void startMQTTService() {

        final Intent intent = new Intent(this, MQTTService.class);
        startService(intent);
    }

    private void stopMQTTService() {

        final Intent intent = new Intent(this, MQTTService.class);
        stopService(intent);
    }

    private boolean serviceIsRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SERVICE_CLASSNAME.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
