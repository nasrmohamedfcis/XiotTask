package com.example.nasrmohamed.xiottask.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nasrmohamed.xiottask.ConnectionManger.MqttConnection;
import com.example.nasrmohamed.xiottask.R;
import com.example.nasrmohamed.xiottask.UserPackage.UserData;

public class ConnectingActivity extends AppCompatActivity {

    private EditText etClientID,etServerUrl,etPort;
    private CheckBox checkBoxCleanSession;
    private MqttConnection myConnection;
    private UserData myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        myUser = new UserData(this);
        /*
        * Check Whether is a user is logged before
        */
        if(myUser.getLogged() == true){
            Intent toMain = new Intent(this,MainActivity.class);
            startActivity(toMain);
        }
        else{
            setContentView(R.layout.activity_connecting);
            // initializing my variables //
            Initializer();
        }

    }

    /****************************************/
    /****** Initialzies the Variables ******/
    /**************************************/
    private void Initializer() {
        etClientID = findViewById(R.id.et_client_id);
        etServerUrl = findViewById(R.id.et_server);
        etPort = findViewById(R.id.et_port);
        checkBoxCleanSession = findViewById(R.id.checkbox_clean_session);
        myConnection = new MqttConnection(this);
    }

    /****************************************/
    /******** Connect to the Server ********/
    /**************************************/
    public void Connect(View view) {
        if(CheckFields() == true){
            if(checkBoxCleanSession.isChecked() == true){
                myUser.setCleanSession(true);
                myConnection.getMqttClient(myUser.getServerUrl(),
                        myUser.getClientID(),true);
            }
            else if(checkBoxCleanSession.isChecked() == false){
                myUser.setCleanSession(false);
                myConnection.getMqttClient(myUser.getServerUrl(),
                        myUser.getClientID(),false);
            }
        }

    }


    /*****************************************/
    /****** Checks the EditText Fields ******/
    /***************************************/
    private boolean CheckFields(){
        String clientID,serverUrl,port;
        clientID = etClientID.getText().toString();
        serverUrl = etServerUrl.getText().toString();
        port = etPort.getText().toString();
        if(clientID.isEmpty() || serverUrl.isEmpty() || port.isEmpty()){
            Toast.makeText(this, "Please fill the Fields", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            // appending the server url to be a valid host //
            String temp ="tcp://"+serverUrl+":"+port;
            myUser.setClientID(clientID);
            myUser.setServerUrl(temp);
            myUser.setPort(port);
            return true;
        }
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
}
