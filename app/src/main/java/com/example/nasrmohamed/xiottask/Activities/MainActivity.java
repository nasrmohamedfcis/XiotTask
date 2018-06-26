package com.example.nasrmohamed.xiottask.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nasrmohamed.xiottask.Adapter.Item;
import com.example.nasrmohamed.xiottask.Adapter.ListViewAdapter;
import com.example.nasrmohamed.xiottask.ConnectionManger.MqttConnection;
import com.example.nasrmohamed.xiottask.R;
import com.example.nasrmohamed.xiottask.Services.MqttMessageService;
import com.example.nasrmohamed.xiottask.UserPackage.UserData;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserData user;
    private MqttConnection myConnection;

    private ListView publishedList;
    private ListViewAdapter myAdapter;
    private List<Item> list;

    private String LOGTAG = "client_connection";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initializer();
    }


    /****************************************/
    /****** Initialzies the Variables ******/
    /**************************************/
    private void Initializer() {
        user = new UserData(this);

        myConnection = new MqttConnection(this);
        //myClient = myConnection.getMqttClient(user.getServerUrl(),user.getClientID(),user.getCleanSession());
        myConnection.getMqttClient(user.getServerUrl(),user.getClientID());

        publishedList = findViewById(R.id.lv_published);
        list = new ArrayList<>();
        //SetAdapter("greetings","HELLO","0");

        Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent toConnectionActivity = new Intent(this,ConnectingActivity.class);

        switch (id){
            case R.id.action_logout:
                Toast.makeText(this, getString(R.string.Logged_out), Toast.LENGTH_LONG).show();
                user.setLogged(false);
                finish();
                startActivity(toConnectionActivity);
                break;

            case R.id.action_new_connection:
                Toast.makeText(this, getString(R.string.Logged_out), Toast.LENGTH_LONG).show();
                user.setLogged(false);
                finish();
                startActivity(toConnectionActivity);
                break;

            case R.id.acton_about:
                Intent toAbout = new Intent(this,AboutActivity.class);
                startActivity(toAbout);
                break;
            case R.id.action_subscribe:
                Intent toSubscribe = new Intent(this,SubscribeActivity.class);
                startActivity(toSubscribe);
                break;
        }

        /*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(this, getString(R.string.Logged_out), Toast.LENGTH_SHORT).show();
            return true;
        }
        */


        return super.onOptionsItemSelected(item);
    }

    /****************************************/
    //        Handles on Backpressed       //
    // when the user press back it will    //
    // go to the home screen instead of    //
    //        ConnectingActivity           //
    /***************************************/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }


    /********************************************/
    /******          Publish Hello         *****/
    /*****  QOS = 1  & Topic = greetings  ******/
    /********       By deafult         *******/
    /****************************************/
    public void SendHello(View view){

        MemoryPersistence memPer = new MemoryPersistence();

        final MqttAndroidClient client = new MqttAndroidClient(
                this, user.getServerUrl(), user.getClientID(), memPer);

        try {
            client.connect(null, new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken mqttToken) {
                    Log.i(LOGTAG, "Client connected");
                    Log.i(LOGTAG, "Topics=" + mqttToken.getTopics());

                    MqttMessage message = new MqttMessage("Hello".getBytes());
                    message.setQos(1);
                    message.setId(320);
                    message.setRetained(true);
                    message.setRetained(false);

                    try {
                        client.publish("greetings", message);
                        Log.i(LOGTAG, "Message published");

                        SetAdapter("greetings","HELLO","1");

                        client.disconnect();
                        Log.i(LOGTAG, "client disconnected");

                    } catch (MqttPersistenceException e) {
                        e.printStackTrace();

                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken arg0, Throwable arg1) {
                    Log.i(LOGTAG, "Client connection failed: " + arg1.getMessage());

                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    /********************************************/
    /******          Publish BYE         *****/
    /*****  QOS = 1  & Topic = going  ******/
    /********       By deafult         *******/
    /****************************************/
    public void SendBye(View view) throws MqttException, UnsupportedEncodingException {
        /*
        myConnection.publishMessage(myClient,"Bye",0,"going");
        list.add(new Item("going","Bye","0"));
        myAdapter = new ListViewAdapter(this,list);
        publishedList.setAdapter(myAdapter);
        */

        MemoryPersistence memPer = new MemoryPersistence();

        final MqttAndroidClient client = new MqttAndroidClient(
                this, user.getServerUrl(), user.getClientID(), memPer);

        try {
            client.connect(null, new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken mqttToken) {
                    Log.i(LOGTAG, "Client connected");
                    Log.i(LOGTAG, "Topics=" + mqttToken.getTopics());

                    MqttMessage message = new MqttMessage("BYE".getBytes());
                    message.setQos(1);
                    message.setId(320);
                    message.setRetained(true);
                    message.setRetained(false);

                    try {
                        client.publish("greetings", message);
                        Log.i(LOGTAG, "Message published");

                        SetAdapter("going","BYE","1");

                        client.disconnect();
                        Log.i(LOGTAG, "client disconnected");

                    } catch (MqttPersistenceException e) {
                        e.printStackTrace();

                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken arg0, Throwable arg1) {
                    Log.i(LOGTAG, "Client connection failed: " + arg1.getMessage());
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /********************************************/
    /***** Appends an item to the ListView *****/
    /*******************************************/
    private void SetAdapter(String topic,String message,String qos){

        list.add(new Item("Topic: "+topic,"Message: "+message,"QOS: "+qos));

        myAdapter = new ListViewAdapter(getApplicationContext(),list);
        publishedList.setAdapter(myAdapter);
    }


}
