package com.example.projectkaveretaplication.classes;

import org.json.JSONException;
import org.json.JSONObject;

public class RecentEntries {
    private int id;
    private String user_name;
    private String date;

    public RecentEntries(int id, String user_name, String date) {
        this.id = id;
        this.user_name = user_name;
        this.date = date;
    }

    public RecentEntries(String user_name) {
        this.id = 0;
        this.user_name = user_name;
        this.date = "";
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

    @Override
    public String toString() {
        return "RecentEntries{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
            obj.put("user_name", user_name);
            obj.put("date", date);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
