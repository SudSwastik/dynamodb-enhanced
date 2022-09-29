package com.sudarshan.dynamodbenhanced.model;


import java.util.List;

public class Customer {
    private String accountId;
    private int subId;            // primitive types are supported
    private String name;

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Customer() {
    }

    public Customer(String accountId, int subId, String name, List<Item> items) {
        this.accountId = accountId;
        this.subId = subId;
        this.name = name;
        this.items = items;
    }

    public String getAccountId() { return this.accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }


    public int getSubId() { return this.subId; }
    public void setSubId(int subId) { this.subId = subId; }


    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

}