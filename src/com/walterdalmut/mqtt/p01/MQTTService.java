package com.walterdalmut.mqtt.p01;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MQTTService extends Service {

    public static final String BROKER_URL = "tcp://192.168.2.100:1883";

    public static final String clientId = "android-client";

    public static final String TOPIC = "walter";
    private MqttClient mqttClient;


    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            mqttClient = new MqttClient(BROKER_URL, clientId, new MemoryPersistence());

            mqttClient.setCallback(new MessageHandler(this));
            mqttClient.connect();

            mqttClient.subscribe(TOPIC);
        } catch (MqttException e) {
            Toast.makeText(getApplicationContext(), "Something went wrong!" + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        try {
            mqttClient.disconnect(0);
        } catch (MqttException e) {
            Toast.makeText(getApplicationContext(), "Something went wrong!" + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
