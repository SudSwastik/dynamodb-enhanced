package com.sudarshan.dynamodbenhanced.model;

import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class Item {

    private String bag;
    private String uniform;

    public Item() {
    }

    public Item(String bag, String uniform) {
        this.bag = bag;
        this.uniform = uniform;
    }

    public String getBag() {
        return bag;
    }

    public void setBag(String bag) {
        this.bag = bag;
    }

    public String getUniform() {
        return uniform;
    }

    public void setUniform(String uniform) {
        this.uniform = uniform;
    }
}
