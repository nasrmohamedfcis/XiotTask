package com.example.nasrmohamed.xiottask.UserPackage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.nasrmohamed.xiottask.R;

public class UserData {
    private String clientID,serverUrl,port;
    private Boolean isLogged,isCleanSession;
    private Context context;
    private SharedPreferences userData;
    private SharedPreferences.Editor editor;

/**********************************/
    /********* Constructor ************/
    /*********************************/

    public UserData(Context conext){
        context = conext;

        //initialize shared prefrence
        initSharedPref();
    }

    /**********************************/
    /***** Init. sharedPrefrence ******/
    /*********************************/
    private void initSharedPref() {
        userData = context.getSharedPreferences(context.getString(R.string.pref_name),Context.MODE_PRIVATE);
        editor = userData.edit();
    }

    /*******************************/
    /***** Setter and Getter ******/
    /*****************************/
    public String getClientID() {
        return userData.getString("client_id",null);
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
        editor.putString("client_id",clientID);
        editor.apply();
    }

    public String getPort() {
        return userData.getString("port",null);
    }

    public void setPort(String port) {
        this.port = port;
        editor.putString("port",port);
        editor.apply();
    }

    public String getServerUrl() {
        return userData.getString("server_url",null);
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        editor.putString("server_url",serverUrl);
        editor.apply();
    }

    public Boolean getLogged() {
        return userData.getBoolean("user_logged",false);
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
        editor.putBoolean("user_logged",isLogged);
        editor.apply();
    }

    public Boolean getCleanSession() {
        return userData.getBoolean("clean_session",false);
    }

    public void setCleanSession(Boolean cleanSession) {
        isCleanSession = cleanSession;
        editor.putBoolean("clean_session",isCleanSession);
        editor.apply();
    }
}
