package com.example.nasrmohamed.xiottask.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.nasrmohamed.xiottask.R;
import com.example.nasrmohamed.xiottask.UserPackage.UserData;

public class AboutActivity extends AppCompatActivity {

    private UserData myUser;
    private TextView txtClientID,txtServer,txtPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Initializer();
    }

    /****************************************/
    /****** Initialzies the Variables ******/
    /**************************************/
    private void Initializer() {
        myUser = new UserData(this);
        txtClientID = findViewById(R.id.txt_about_client_id);
        txtServer = findViewById(R.id.txt_about_server);
        txtPort = findViewById(R.id.txt_about_port);

        txtClientID.setText(myUser.getClientID());
        txtServer.setText(myUser.getServerUrl());
        txtPort.setText(myUser.getPort());

    }
}
