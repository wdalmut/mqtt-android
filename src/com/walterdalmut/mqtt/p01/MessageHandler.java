package com.walterdalmut.mqtt.p01;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;


public class MessageHandler implements MqttCallback {

    private ContextWrapper context;

    public MessageHandler(ContextWrapper context) {
        this.context = context;
    }

    @Override
    public void connectionLost(Throwable cause) {
    	// Gestione della perdita della connessione
    }

    @Override
    public void messageArrived(MqttTopic topic, MqttMessage message) throws Exception {

        final NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        final Intent intent = new Intent(context, MainActivity.class);
        final PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 0);
        
    	Notification n  = new Notification.Builder(this.context)
        	.setContentTitle("New message: " +  new String(message.getPayload()))
        	.setContentText("MQTT Message")
        	.setSmallIcon(R.drawable.snow)
        	.setContentIntent(activity)
        	.setAutoCancel(true)
        	.build();

        n.flags |= Notification.FLAG_AUTO_CANCEL;

        n.number += 1;
        notificationManager.notify(0, n);
    }

    @Override
    public void deliveryComplete(MqttDeliveryToken token) {
        //We do not need this because we do not publish
    }
}
