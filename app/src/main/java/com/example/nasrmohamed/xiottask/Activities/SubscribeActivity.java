package com.example.nasrmohamed.xiottask.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nasrmohamed.xiottask.Adapter.Item;
import com.example.nasrmohamed.xiottask.Adapter.ListViewAdapter;
import com.example.nasrmohamed.xiottask.ConnectionManger.MqttConnection;
import com.example.nasrmohamed.xiottask.R;
import com.example.nasrmohamed.xiottask.UserPackage.UserData;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

import java.util.ArrayList;
import java.util.List;

public class SubscribeActivity extends AppCompatActivity {

    private UserData user;
    private MqttConnection myConnection;
    private MqttAndroidClient client;

    private List<Item> myList;
    private ListView lvSubscribe;
    private ListViewAdapter myAdapter;
    private EditText etTopic;
    private String TAG = "subscribe_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        Initializer();
    }

    /****************************************/
    /****** Initialzies the Variables ******/
    /**************************************/
    private void Initializer() {

        etTopic = findViewById(R.id.et_topic_subscribe);
        lvSubscribe = findViewById(R.id.lv_subscribe);
        myList = new ArrayList<>();
        user = new UserData(this);
        myConnection = new MqttConnection(this);
        client = myConnection.getMqttClient(user.getServerUrl(),user.getClientID());
    }

    /*******************************************/
    /****** Handles the Subscribig Event ******/
    /*****   The QOS is Always set to 1  *****/
    /****************************************/
    public void Subscribe(View view) throws MqttException {
        final String topic = etTopic.getText().toString();
        if (topic.isEmpty() != true) {

            final IMqttToken token = client.subscribe(topic,1);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken iMqttToken) {
                    Log.d(TAG, "Subscribe Successfully " + topic);
                    //MqttWireMessage myMsg = iMqttToken.getResponse();
                    //String msg=null;
                   /*
                    try {
                        msg = myMsg.getPayload().toString();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    */
                    SetAdapter(topic,"","1");
                }

                @Override
                public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                    Log.e(TAG, "Subscribe Failed " + topic);
                }

            });
        }
        else {
            Toast.makeText(this, "Please add a Topic ", Toast.LENGTH_SHORT).show();
        }
    }


    /********************************************/
    /***** Appends an item to the ListView *****/
    /*******************************************/
    private void SetAdapter(String topic,String message,String qos){

        myList.add(new Item("Topic: "+topic,"Message: "+message,"QOS: "+qos));

        myAdapter = new ListViewAdapter(getApplicationContext(),myList);
        lvSubscribe.setAdapter(myAdapter);
    }

}


