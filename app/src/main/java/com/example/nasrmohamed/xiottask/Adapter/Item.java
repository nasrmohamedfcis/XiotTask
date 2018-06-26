package com.example.nasrmohamed.xiottask.Adapter;

public class Item {
    String topic,message,qos;

    public Item(String topic, String message, String qos) {
        this.topic = topic;
        this.message = message;
        this.qos = qos;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQos() {
        return qos;
    }

    public void setQos(String qos) {
        this.qos = qos;
    }
}
