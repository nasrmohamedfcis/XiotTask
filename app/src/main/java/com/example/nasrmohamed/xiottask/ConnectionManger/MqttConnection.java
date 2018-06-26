package com.example.nasrmohamed.xiottask.ConnectionManger;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import com.example.nasrmohamed.xiottask.Activities.MainActivity;
import com.example.nasrmohamed.xiottask.UserPackage.UserData;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import java.io.UnsupportedEncodingException;

/*************************************************/
/******** This Class is a helper class  *********/
/*****   It Handles the connection request  ****/
/***** And the Publish & Subscribe Requests ***/
/*********************************************/

public class MqttConnection {
    private static final String TAG = "PahoMqttClient";
    private MqttAndroidClient mqttAndroidClient;
    private Context context;
    private UserData user;

    public MqttConnection(Context context) {
        this.context = context;
        user = new UserData(context);
    }

    /**************************************************/
    public MqttAndroidClient getMqttClient(String brokerUrl, String clientId, final Boolean isCleanSession) {

        mqttAndroidClient = new MqttAndroidClient(context, brokerUrl, clientId);
        try {
            IMqttToken token = mqttAndroidClient.connect(getMqttConnectionOption(isCleanSession));
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());
                    Intent toMain = new Intent(context, MainActivity.class);

                    // indicates that the connection is established
                    // and the user is logged
                    user.setLogged(true);
                    context.startActivity(toMain);
                    Log.d(TAG, "Success");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(context, "Connection Error ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Failure " + exception.toString());
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        return mqttAndroidClient;
    }

    /**************************************************/

    public MqttAndroidClient getMqttClient(String brokerUrl, String clientId) {

        mqttAndroidClient = new MqttAndroidClient(context, brokerUrl, clientId);
        try {
            IMqttToken token = mqttAndroidClient.connect(getMqttConnectionOption(user.getCleanSession()));
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());
                    Log.d(TAG, "Success");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(context, "Connection Error ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Failure " + exception.toString());
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        return mqttAndroidClient;
    }

    /******************************/
    //    Set the Connection Up   //
    /*****************************/
    @NonNull
    private MqttConnectOptions getMqttConnectionOption(Boolean isCleanSession) {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(isCleanSession);
        mqttConnectOptions.setAutomaticReconnect(false);
        return mqttConnectOptions;
    }

    /***********************************/
    //    Handles the Disconnection   //
    /*********************************/
    @NonNull
    private DisconnectedBufferOptions getDisconnectedBufferOptions() {
        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
        disconnectedBufferOptions.setBufferEnabled(true);
        disconnectedBufferOptions.setBufferSize(100);
        disconnectedBufferOptions.setPersistBuffer(false);
        disconnectedBufferOptions.setDeleteOldestMessages(false);
        return disconnectedBufferOptions;
    }



}
