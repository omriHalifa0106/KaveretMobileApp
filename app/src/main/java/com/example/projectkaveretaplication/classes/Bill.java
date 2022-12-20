package com.example.projectkaveretaplication.classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Bill {

    private int id;
    private String user_name;
    private String date;
    private String items;
    private int sum;

    public Bill(int id, String user_name, String date, String items, int sum) {
        this.id = id;
        this.user_name = user_name;
        this.date = date;
        this.items = items;
        this.sum = sum;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
            obj.put("user_name", user_name);
            obj.put("cartItems", items);
            obj.put("date", date);
            obj.put("sum", sum);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }


}
